package uk.ac.ebi.atlas.resource;

import com.google.gson.JsonObject;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class AtlasResourceFactory {

    private ContrastImageFactory contrastImageFactory;

    @Inject
    public AtlasResourceFactory(ContrastImageFactory contrastImageFactory){
        this.contrastImageFactory = contrastImageFactory;
    }


    public JsonObject listAvailableResources(Experiment experiment){
        JsonObject result = new JsonObject();

        if(experiment instanceof DifferentialExperiment)
            result.add("contrastImage", contrastImageFactory.resourcesPerContrast((DifferentialExperiment)experiment));

        return result;
    }
}
