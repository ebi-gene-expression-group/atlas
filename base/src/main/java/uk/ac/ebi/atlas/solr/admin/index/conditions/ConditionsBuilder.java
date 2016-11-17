package uk.ac.ebi.atlas.solr.admin.index.conditions;

import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.experimentimport.efo.EFOLookupService;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.SetMultimap;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class ConditionsBuilder {

    private static final String ERROR_MESSAGE_TEMPLATE = "No %s found for assay accession '%s'. Check assays defined in configuration.xml match Assay Name/Scan Name in the SDRF.";

    private EFOLookupService efoLookupService;

    public ConditionsBuilder(EFOLookupService efoLookupService){
        this.efoLookupService = efoLookupService;
    }

    @Inject
    public void setEfoLookupService(EFOLookupService efoLookupService) {
        this.efoLookupService = efoLookupService;
    }

    protected abstract Collection buildProperties(Experiment experiment, SetMultimap<String, String>
            ontologyTermIdsByAssayAccession);

    public Collection buildProperties(Experiment experiment){
        return buildProperties(experiment, efoLookupService.expandOntologyTerms(experiment.getExperimentDesign()
                .getAllOntologyTermIdsByAssayAccession()));
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


}
