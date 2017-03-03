package uk.ac.ebi.atlas.experimentpage.context;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamOptions;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;

import javax.annotation.Nullable;
import javax.inject.Named;
import java.util.*;

@Named
@Scope("request")
public class BaselineRequestContext extends RequestContext<AssayGroup,BaselineExperiment, BaselineRequestPreferences>
        implements BaselineProfileStreamOptions {
    
    private final ImmutableMap<AssayGroup, String> displayNamePerSelectedAssayGroup;


    public BaselineRequestContext(BaselineRequestPreferences requestPreferences, BaselineExperiment experiment) {
        super(requestPreferences, experiment);
        this.displayNamePerSelectedAssayGroup = displayNamePerSelectedAssayGroup();
    }

    @Override
    public String displayNameForColumn(AssayGroup assayGroup) {
            return getDataColumnsToReturn().contains(assayGroup) ? displayNamePerSelectedAssayGroup.get
                    (assayGroup) : "";
    }

    @Override
    public Double getThresholdForPremium() {
        return requestPreferences.getThresholdForPremium();
    }

    @Override
    public Double getFractionForPremium() {
        return requestPreferences.getFractionForPremium();
    }


    public String getQueryFactorType() {
        return requestPreferences.getQueryFactorType();
    }

    public Set<OntologyTerm> ontologyTermsForColumn(AssayGroup assayGroup){
        ImmutableSet.Builder<OntologyTerm> b = ImmutableSet.builder();
        for(Factor factor : experiment.getExperimentalFactors().getFactorGroup(assayGroup.getId())){
            b.addAll(factor.getValueOntologyTerms());
        }

        return b.build();
    }

    private ImmutableMap<AssayGroup, String> displayNamePerSelectedAssayGroup(){
        Map<AssayGroup, Map<String, Factor>> factorsByTypePerId = new HashMap<>();
        for(AssayGroup assayGroup: getDataColumnsToReturn()){
            factorsByTypePerId.put(assayGroup,
                    experiment.getExperimentDesign()
                            .getFactors(assayGroup.getFirstAssayAccession())
                            .factorsByType
            );
        }
        final Map<String, Set<String>> allValuesPerType = new HashMap<>();

        for(Map.Entry<AssayGroup, Map<String, Factor>> e: factorsByTypePerId.entrySet()){
            for(Map.Entry<String, Factor> ee : e.getValue().entrySet()){
                if(!allValuesPerType.containsKey(ee.getKey())){
                    allValuesPerType.put(ee.getKey(), new HashSet<String>());
                }
                allValuesPerType.get(ee.getKey()).add(ee.getValue().getValue());
            }
        }

        List<String> typesWhoseValuesVaryAcrossSelectedDescriptors =
                FluentIterable.from(experiment.getDisplayDefaults().prescribedOrderOfFilters())
                        .filter(new Predicate<String>() {
                            @Override
                            public boolean apply(@Nullable String type) {
                                return allValuesPerType.containsKey(type) && allValuesPerType.get(type).size() >1 ;
                            }
                        }).toList();

        ImmutableMap.Builder<AssayGroup, String> b = ImmutableMap.builder();

        for(final Map.Entry<AssayGroup, Map<String, Factor>> e : factorsByTypePerId.entrySet()){
            b.put(e.getKey(), Joiner.on(", ").join(FluentIterable.from
                    (typesWhoseValuesVaryAcrossSelectedDescriptors).transform(new Function<String, String>() {
                @Nullable
                @Override
                public String apply(@Nullable String type) {
                    return e.getValue().get(type).getValue();
                }
            })));
        }

        return b.build();
    }

}