package uk.ac.ebi.atlas.trader;

import com.google.gson.JsonObject;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;

import javax.inject.Named;

/*
I have an idea to move access to our various resources to one class, and also expose the information as JSON through
the experiment API.
Then the pipeline can assert that the web server is able to read its files.

As an immediate problem I want to check what resources we have for the plot buttons.


A future API of this class should be probably about returning a Resource which appears to know what it has (but
only
looks on the filesystem when you ask it) and also can turn itself into JSON.


Or resources could have types and you can either ask for all of them within some scope, and get List<Resource> , or
ask for a specific one and get Optional<Resource>.

 */
@Named
public class ResourceTrader {

    public JsonObject resourcesFor(Experiment experiment){
        return new JsonObject();
    }

    public JsonObject resourcesFor(BaselineExperiment baselineExperiment){
        JsonObject result = resourcesFor((Experiment) baselineExperiment);


        return result;
    }

    public JsonObject resourcesFor(DifferentialExperiment differentialExperiment){
        JsonObject result = resourcesFor((Experiment) differentialExperiment);

        return result;
    }

}
