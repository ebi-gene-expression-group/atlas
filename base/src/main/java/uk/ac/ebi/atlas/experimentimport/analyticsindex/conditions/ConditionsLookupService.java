package uk.ac.ebi.atlas.experimentimport.analyticsindex.conditions;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.SetMultimap;
import uk.ac.ebi.atlas.experimentimport.efo.EFOLookupService;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.Set;

@Named
public class ConditionsLookupService {

    private EFOLookupService efoLookupService;

    @Inject
    public ConditionsLookupService(EFOLookupService efoLookupService){
        this.efoLookupService = efoLookupService;
    }

    public Multimap<String, String> conditionsPerDataColumnDescriptor(BaselineExperiment experiment) {
        Collection<Condition> conditions = buildPropertiesForBaselineExperiment(experiment
                        .getAccession(),
                experiment.getExperimentDesign(), experiment.getDataColumnDescriptors());
        ImmutableSetMultimap.Builder<String, String> builder = ImmutableSetMultimap.builder();

        for (Condition condition : conditions) {
            builder.putAll(condition.getAssayGroupId(), condition.getValues());
        }

        return builder.build();
    }

    public Multimap<String, String> conditionsPerDataColumnDescriptor(DifferentialExperiment experiment) {
        Collection<DifferentialCondition> conditions = buildPropertiesForDifferentialExperiment
                (experiment.getAccession(), experiment
                        .getExperimentDesign(), experiment.getDataColumnDescriptors());

        ImmutableSetMultimap.Builder<String, String> builder = ImmutableSetMultimap.builder();

        for (DifferentialCondition condition : conditions) {
            builder.putAll(condition.getContrastId(), condition.getValues());
        }

        return builder.build();
    }

    ImmutableSet<DifferentialCondition> buildPropertiesForDifferentialExperiment(String experimentAccession,
                                             ExperimentDesign experimentDesign,
                                             Collection<Contrast> contrasts) {
        DifferentialConditionsBuilder b = new DifferentialConditionsBuilder(experimentAccession,experimentDesign);
        contrasts.forEach(b::addContrast);
        return b.build();
    }

    ImmutableSet<Condition> buildPropertiesForBaselineExperiment(String experimentAccession,
                                                                        ExperimentDesign experimentDesign,
                                                                        Collection<AssayGroup> assayGroups) {
        BaselineConditionsBuilder b = new BaselineConditionsBuilder(experimentAccession,experimentDesign);
        assayGroups.forEach(b::addCondition);
        return b.build();
    }


    private Set<String> collectAssayProperties(ExperimentDesign experimentDesign,
                                               String assayAccession,
                                               Set<String> ontologyIds) {
        return ImmutableSet.<String>builder()
                .addAll(experimentDesign.getFactorValues(assayAccession).values())
                .addAll(experimentDesign.getSampleCharacteristicsValues(assayAccession).values())
                .addAll(ontologyIds)
                .addAll(efoLookupService.getLabels(ontologyIds))
                .build();
    }

    abstract class ConditionsBuilder<Cond extends Condition> {

        protected ImmutableSet.Builder<Cond> builder = new ImmutableSet.Builder<>();
        protected final String experimentAccession;
        protected final ExperimentDesign experimentDesign;
        protected final SetMultimap<String, String> assayGroupIdToOntologyTermId;

        public ConditionsBuilder(String experimentAccession,ExperimentDesign experimentDesign){
            this.experimentAccession = experimentAccession;
            this.experimentDesign = experimentDesign;
            this.assayGroupIdToOntologyTermId =
                    efoLookupService.expandOntologyTerms(experimentDesign
                            .getAllOntologyTermIdsByAssayAccession());
        }

        ImmutableSet<Cond> build(){
            return builder.build();
        }
    }

    class BaselineConditionsBuilder extends ConditionsBuilder<Condition> {

        public BaselineConditionsBuilder(String experimentAccession, ExperimentDesign experimentDesign) {
            super(experimentAccession, experimentDesign);
        }
        public void addCondition(AssayGroup assayGroup){
            for(String assayAccession: assayGroup){
                builder.add(new Condition(
                        experimentAccession,
                        assayGroup.getId(),
                        collectAssayProperties(
                                experimentDesign,
                                assayAccession,
                                assayGroupIdToOntologyTermId.get(assayAccession))));
            }
        }
    }

    class DifferentialConditionsBuilder extends ConditionsBuilder<DifferentialCondition> {

        public DifferentialConditionsBuilder(String experimentAccession, ExperimentDesign experimentDesign) {
            super(experimentAccession, experimentDesign);
        }

        private void addDifferentialCondition(String contrastId, AssayGroup assayGroup) {
            for(String assayAccession: assayGroup) {
                builder.add(new DifferentialCondition(
                        experimentAccession,
                        assayGroup.getId(),
                        contrastId,
                        collectAssayProperties(
                                experimentDesign,
                                assayAccession,
                                assayGroupIdToOntologyTermId.get(assayAccession))));
            }
        }

        public void addContrast(Contrast contrast){
            addDifferentialCondition(contrast.getId(),contrast.getReferenceAssayGroup());
            addDifferentialCondition(contrast.getId(),contrast.getTestAssayGroup());
        }

    }
}
