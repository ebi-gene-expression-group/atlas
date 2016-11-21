package uk.ac.ebi.atlas.solr.admin.index.conditions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.SetMultimap;
import uk.ac.ebi.atlas.experimentimport.efo.EFOLookupService;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.AssayGroups;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

@Named
public class ConditionsLookupService {

    private static final String ERROR_MESSAGE_TEMPLATE = "No %s found for assay accession '%s'. Check assays defined in configuration.xml match Assay Name/Scan Name in the SDRF.";

    private EFOLookupService efoLookupService;

    @Inject
    public ConditionsLookupService(EFOLookupService efoLookupService){
        this.efoLookupService = efoLookupService;
    }
    
    public ImmutableList<DifferentialCondition> buildPropertiesForDifferentialExperiment(String experimentAccession,
                                                                                       ExperimentDesign experimentDesign, Set<Contrast> contrasts){
        DifferentialConditionsBuilder b = new DifferentialConditionsBuilder(experimentAccession,experimentDesign);
        for(Contrast c: contrasts){
            b.addContrast(c);
        }
        return b.build();
    }

    public ImmutableList<Condition> buildPropertiesForBaselineExperiment(String experimentAccession,
                                                                                   ExperimentDesign experimentDesign,
                                                                                   AssayGroups assayGroups){
        BaselineConditionsBuilder b = new BaselineConditionsBuilder(experimentAccession,experimentDesign);
        for(AssayGroup assayGroup: assayGroups){
            b.addCondition(assayGroup);
        }
        return b.build();
    }


    protected Set<String> collectAssayProperties(ExperimentDesign experimentDesign, String assayAccession, SetMultimap<String, String> assayGroupIdToOntologyTermId) {

        Map<String, String> factors = experimentDesign.getFactorValues(assayAccession);
        Map<String, String> samples = experimentDesign.getSampleCharacteristicsValues(assayAccession);
        Set<String> ontologyIds = assayGroupIdToOntologyTermId.get(assayAccession);
        Set<String> ontologyLabels = efoLookupService.getLabels(ontologyIds);

        checkNotNull(factors, ERROR_MESSAGE_TEMPLATE, "factors", assayAccession);
        checkNotNull(samples, ERROR_MESSAGE_TEMPLATE, "samples", assayAccession);

        ImmutableSet.Builder<String> builder =
                ImmutableSet.<String>builder()
                        .addAll(factors.values())
                        .addAll(samples.values())
                        .addAll(ontologyIds)
                        .addAll(ontologyLabels);

        return builder.build();
    }

    abstract class ConditionsBuilder<Cond extends Condition> {

        protected ImmutableList.Builder<Cond> builder = new ImmutableList.Builder<>();
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

        ImmutableList<Cond> build(){
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
                                assayGroupIdToOntologyTermId)));
            }
        }
    }

    class DifferentialConditionsBuilder extends ConditionsBuilder<DifferentialCondition> {

        public DifferentialConditionsBuilder(String experimentAccession, ExperimentDesign experimentDesign) {
            super(experimentAccession, experimentDesign);
        }

        private void addDifferentialCondition(String contrastId, AssayGroup assayGroup){
            for(String assayAccession: assayGroup){
                builder.add(new DifferentialCondition(
                        experimentAccession,
                        assayGroup.getId(),
                        contrastId,
                        collectAssayProperties(
                                experimentDesign,
                                assayAccession,
                                assayGroupIdToOntologyTermId)));
            }
        }

        public void addContrast(Contrast contrast){
            addDifferentialCondition(contrast.getId(),contrast.getReferenceAssayGroup());
            addDifferentialCondition(contrast.getId(),contrast.getTestAssayGroup());
        }

    }
}
