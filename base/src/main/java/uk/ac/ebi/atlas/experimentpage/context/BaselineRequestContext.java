package uk.ac.ebi.atlas.experimentpage.context;

import com.atlassian.util.concurrent.LazyReference;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableMap;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.ExpressionUnit;
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
public class BaselineRequestContext<Unit extends ExpressionUnit.Absolute> extends RequestContext<AssayGroup,BaselineExperiment, BaselineRequestPreferences<Unit>>
        implements BaselineProfileStreamOptions<Unit> {

    public BaselineRequestContext(BaselineRequestPreferences<Unit> requestPreferences, BaselineExperiment experiment) {
        super(requestPreferences, experiment);
    }

    @Override
    public String displayNameForColumn(AssayGroup assayGroup) {
            return dataColumnsToBeReturned().contains(assayGroup) ?
                    displayNamePerSelectedAssayGroup.get().get(assayGroup) :
                    "";
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

                        dataColumnsToBeReturned().transform(new Function<AssayGroup, FactorGroup>() {
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

        for (AssayGroup assayGroup : dataColumnsToBeReturned()) {
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

    @Override
    public Unit getExpressionUnit() {
        return requestPreferences.getUnit();
    }
}