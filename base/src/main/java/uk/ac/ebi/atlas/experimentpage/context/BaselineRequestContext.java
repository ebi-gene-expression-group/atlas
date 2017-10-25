package uk.ac.ebi.atlas.experimentpage.context;

import com.atlassian.util.concurrent.LazyReference;
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
import java.util.Optional;

@Named
@Scope("request")
public class BaselineRequestContext<Unit extends ExpressionUnit.Absolute> extends RequestContext<AssayGroup,BaselineExperiment, BaselineRequestPreferences<Unit>>
        implements BaselineProfileStreamOptions<Unit> {

    public BaselineRequestContext(BaselineRequestPreferences<Unit> requestPreferences, BaselineExperiment experiment) {
        super(requestPreferences, experiment);
    }

    @Override
    public String displayNameForColumn(AssayGroup assayGroup) {
            return Optional.ofNullable(displayNamePerSelectedAssayGroup.get().get(assayGroup)).orElse(assayGroup.getId()) ;
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
                        FluentIterable.from(experiment.getDisplayDefaults().prescribedOrderOfFilters()).transform(Factor::normalize).toList(),

                        dataColumnsToBeReturned().transform(experiment::getFactors)
                );

        return typesWhoseValuesVaryAcrossSelectedDescriptors.isEmpty()
                ? experiment.getDisplayDefaults().prescribedOrderOfFilters().subList(0, Math.min(1, experiment.getDisplayDefaults().prescribedOrderOfFilters().size()))
                : typesWhoseValuesVaryAcrossSelectedDescriptors;
    }

    private ImmutableMap<AssayGroup, String> displayNamePerSelectedAssayGroup() {

        ImmutableMap.Builder<AssayGroup, String> b = ImmutableMap.builder();

        for (AssayGroup assayGroup : dataColumnsToBeReturned()) {
            final FactorGroup factorGroup = experiment.getFactors(assayGroup);

            b.put(assayGroup, FluentIterable.from
                    (typesWhoseValuesToDisplay()).transform(type -> factorGroup.factorOfType(Factor.normalize(type)).getValue()).join(Joiner.on(", ")));
        }

        return b.build();
    }

    @Override
    public Unit getExpressionUnit() {
        return requestPreferences.getUnit();
    }
}