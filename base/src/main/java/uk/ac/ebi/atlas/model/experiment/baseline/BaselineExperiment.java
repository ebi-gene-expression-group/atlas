package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.gson.JsonObject;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentDisplayDefaults;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.species.Species;

import java.util.*;

public class BaselineExperiment extends Experiment<AssayGroup> {

    private ExperimentalFactors experimentalFactors;

    BaselineExperiment(ExperimentType experimentType, String accession, Date lastUpdate, ExperimentalFactors experimentalFactors,
                       String description, String displayName, String disclaimer, Species species,
                       boolean hasRData, Collection<String> pubMedIds, ExperimentDesign experimentDesign,
                       List<AssayGroup> assayGroups, List<String> dataProviderURL, List<String> dataProviderDescription,
                       List<String> alternativeViews, List<String> alternativeViewDescriptions, ExperimentDisplayDefaults experimentDisplayDefaults) {

        super(experimentType, accession, lastUpdate, displayName, description, disclaimer, hasRData, species,
              pubMedIds, experimentDesign, dataProviderURL, dataProviderDescription,
              alternativeViews, alternativeViewDescriptions, assayGroups, experimentDisplayDefaults);

        this.experimentalFactors = experimentalFactors;
    }

    public ExperimentalFactors getExperimentalFactors() {
        return experimentalFactors;
    }

    public SortedSet<Factor> getAssayGroupFactors(Collection<String> assayGroupIds, String factorType) {
        return getExperimentalFactors().getFactors(assayGroupIds, factorType);
    }

    @Override
    protected JsonObject propertiesForAssay(String runOrAssay) {
        boolean analysed = false;
        for(AssayGroup assayGroup : getDataColumnDescriptors()){
            if(assayGroup.assaysAnalyzedForThisDataColumn().contains(runOrAssay)){
                analysed = true;
                break;
            }
        }
        JsonObject result = new JsonObject();
        result.addProperty("analysed", analysed);
        return result;
    }
}
