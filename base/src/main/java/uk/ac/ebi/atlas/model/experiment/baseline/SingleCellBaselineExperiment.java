package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentDisplayDefaults;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.species.Species;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public class SingleCellBaselineExperiment extends Experiment<Cell> {

    public SingleCellBaselineExperiment(ExperimentType type,
                                        String accession,
                                        Date lastUpdate,
                                        String displayName,
                                        String description,
                                        String disclaimer,
                                        Species species,
                                        Collection<String> pubMedIds,
                                        Collection<String> dois,
                                        ExperimentDesign experimentDesign,
                                        List<String> dataProviderURL,
                                        List<String> dataProviderDescription,
                                        List<String> alternativeViews,
                                        List<String> alternativeViewDescriptions,
                                        List<Cell> cells,
                                        ExperimentDisplayDefaults experimentDisplayDefaults) {

        super(type, accession, lastUpdate, displayName, description, disclaimer, species,
                pubMedIds, dois, experimentDesign, dataProviderURL, dataProviderDescription,
                alternativeViews, alternativeViewDescriptions, cells, experimentDisplayDefaults);
    }

    @Override
    protected JsonObject propertiesForAssay(String runOrAssay) {
        return new JsonObject();
    }

    @Override
    public JsonArray groupingsForHeatmap() {
        return new JsonArray();
    }
}
