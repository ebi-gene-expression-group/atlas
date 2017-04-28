package uk.ac.ebi.atlas.experimentpage.context;

import com.atlassian.util.concurrent.LazyReference;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableMap;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.model.experiment.baseline.FactorGroup;
import uk.ac.ebi.atlas.model.experiment.baseline.RichFactorGroup;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamOptions;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;

import javax.inject.Named;
import java.util.List;

@Named
@Scope("request")
public class BaselineRequestContext extends RequestContext<AssayGroup,BaselineExperiment, BaselineRequestPreferences>
        implements BaselineProfileStreamOptions {

    public BaselineRequestContext(BaselineRequestPreferences requestPreferences, BaselineExperiment experiment) {
        super(requestPreferences, experiment);
    }

    @Override
    public String displayNameForColumn(AssayGroup assayGroup) {
            return getDataColumnsToReturn().contains(assayGroup) ?
                    displayNamePerSelectedAssayGroup.get().get(assayGroup) :
                    "";
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

    LazyReference<ImmutableMap<AssayGroup, String>> displayNamePerSelectedAssayGroup = new LazyReference<ImmutableMap<AssayGroup, String>>() {
        @Override
        protected ImmutableMap<AssayGroup, String> create() throws Exception {
            return displayNamePerSelectedAssayGroup();
        }
    };

    private List<String> typesWhoseValuesToDisplay() {

        List<String> typesWhoseValuesVaryAcrossSelectedDescriptors =
                RichFactorGroup.filterOutTypesWithCommonValues(
                        FluentIterable.from(experiment.getDisplayDefaults().prescribedOrderOfFilters()).transform(new Function<String, String>() {
                            public String apply(String factorHeader) {
                                return Factor.normalize(factorHeader);
                            }
                        }).toList(),

                        FluentIterable.from(getDataColumnsToReturn()).transform(new Function<AssayGroup, FactorGroup>() {
                            @Override
                            public FactorGroup apply(AssayGroup assayGroup) {
                                return experiment.getFactors(assayGroup);
                            }
                        })
                );
        return typesWhoseValuesVaryAcrossSelectedDescriptors.isEmpty()
                ? experiment.getDisplayDefaults().prescribedOrderOfFilters().subList(0, 1)
                : typesWhoseValuesVaryAcrossSelectedDescriptors;
    }

    private ImmutableMap<AssayGroup, String> displayNamePerSelectedAssayGroup() {

        ImmutableMap.Builder<AssayGroup, String> b = ImmutableMap.builder();

        for (AssayGroup assayGroup : getDataColumnsToReturn()) {
            final FactorGroup factorGroup = experiment.getFactors(assayGroup);

            b.put(assayGroup, FluentIterable.from
                    (typesWhoseValuesToDisplay()).transform(new Function<String, String>() {
                @Override
                public String apply(String type) {
                    return factorGroup.factorOfType(Factor.normalize(type)).getValue();
                }
            }).join(Joiner.on(", ")));
        }

        return b.build();
    }


}