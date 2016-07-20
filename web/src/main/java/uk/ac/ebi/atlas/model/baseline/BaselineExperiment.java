package uk.ac.ebi.atlas.model.baseline;

import uk.ac.ebi.atlas.model.AssayGroups;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.ExperimentType;

import java.util.*;

public class BaselineExperiment extends Experiment {

    private ExperimentalFactors experimentalFactors;
    private AssayGroups assayGroups;

    BaselineExperiment(ExperimentType experimentType, String accession, Date lastUpdate, ExperimentalFactors experimentalFactors,
                       String description, String displayName, String species, String kingdom, String ensemblDB, Map<String, String> speciesMapping,
                       boolean hasExtraInfoFile, boolean hasRData, Set<String> pubMedIds, ExperimentDesign experimentDesign, AssayGroups assayGroups, List<String> dataProviderURL, List<String> dataProviderDescription, List<String> alternativeViews, List<String> alternativeViewDescriptions) {
        super(experimentType, accession, lastUpdate, displayName, description, hasExtraInfoFile, hasRData, species,
                kingdom, ensemblDB, speciesMapping, pubMedIds, experimentDesign, dataProviderURL, dataProviderDescription,
                alternativeViews, alternativeViewDescriptions);
        this.experimentalFactors = experimentalFactors;
        this.assayGroups = assayGroups;
    }

    public Set<String> getExperimentRunAccessions() {
        return assayGroups.getAssayAccessions();
    }

    public ExperimentalFactors getExperimentalFactors() {
        return experimentalFactors;
    }

    public AssayGroups getAssayGroups() {
        return assayGroups;
    }

    public SortedSet<Factor> getAssayGroupFactors(Collection<String> assayGroupIds, String factorType) {
        return getExperimentalFactors().getFactors(assayGroupIds, factorType);
    }

    public boolean isTissueExperiment() {
        return getExperimentalFactors().getDefaultQueryFactorType().equals("ORGANISM_PART");
    }

    @Override
    protected Set<String> getAnalysedRowsAccessions() {
        return getExperimentRunAccessions();
    }
}
