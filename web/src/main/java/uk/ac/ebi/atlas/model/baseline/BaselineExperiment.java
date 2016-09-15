package uk.ac.ebi.atlas.model.baseline;

import com.google.gson.JsonObject;
import uk.ac.ebi.atlas.experimentpage.tooltip.AssayGroupSummary;
import uk.ac.ebi.atlas.experimentpage.tooltip.AssayGroupSummaryBuilder;
import uk.ac.ebi.atlas.model.*;

import java.util.*;

public class BaselineExperiment extends Experiment {

    private ExperimentalFactors experimentalFactors;
    private AssayGroups assayGroups;

    BaselineExperiment(ExperimentType experimentType, String accession, Date lastUpdate, ExperimentalFactors experimentalFactors,
                       String description, String displayName, String disclaimer, Species species,
                       boolean hasExtraInfoFile, boolean hasRData, Collection<String> pubMedIds, ExperimentDesign experimentDesign,
                       AssayGroups assayGroups, List<String> dataProviderURL, List<String> dataProviderDescription,
                       List<String> alternativeViews, List<String> alternativeViewDescriptions) {

        super(experimentType, accession, lastUpdate, displayName, description, disclaimer, hasExtraInfoFile, hasRData, species,
              pubMedIds, experimentDesign, dataProviderURL, dataProviderDescription,
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

    @Override
    public Map<String, ?> headerSummary() {
        Map<String, AssayGroupSummary> result = new HashMap<>();
        for(AssayGroup assayGroup: assayGroups){
            result.put(assayGroup.getId(),
                    new AssayGroupSummaryBuilder()
                            .forAssayGroup(assayGroup)
                            .withExperimentDesign(experimentDesign)
                            .build());
        }
        return result;
    }
}
