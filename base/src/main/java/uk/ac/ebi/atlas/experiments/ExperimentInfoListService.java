package uk.ac.ebi.atlas.experiments;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.utils.ExperimentInfo;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ExperimentInfoListService {
    private final ExperimentTrader experimentTrader;
    private final Collection<ExperimentType> experimentTypes;
    private final Gson gson = new Gson();

    public ExperimentInfoListService(ExperimentTrader experimentTrader,
                                     Collection<ExperimentType> experimentTypes) {
        this.experimentTrader = experimentTrader;
        this.experimentTypes = experimentTypes;
    }

    public String getExperimentJson(String experimentAccession, String accessKey) {
        Experiment experiment = experimentTrader.getExperiment(experimentAccession, accessKey);
        return gson.toJson(experiment.buildExperimentInfo());
    }

    public JsonObject getExperimentsJson() {
        List<ExperimentInfo> experimentInfos = listPublicExperiments();
        Collections.sort(experimentInfos);
        return gson.toJsonTree(new ExperimentInfoWrapper(experimentInfos)).getAsJsonObject();
    }

    /**
     *  This is a wrapper class used via Gson to produce the right JSON input for DataTables.
     */
    static class ExperimentInfoWrapper {

        private List<ExperimentInfo> aaData;

        public ExperimentInfoWrapper(List<ExperimentInfo> list) {
            this.aaData = list;
        }

        //DataTables requires table data in the aaData property
        public List<ExperimentInfo> getAaData() {
            return aaData;
        }
    }


    public List<ExperimentInfo> listPublicExperiments() {
        List<ExperimentInfo> experimentInfos = Lists.newArrayList();
        for (ExperimentType experimentType : experimentTypes) {
            for (Experiment experiment : experimentTrader.getPublicExperiments(experimentType)) {
                experimentInfos.add(experiment.buildExperimentInfo());
            }
        }
        return experimentInfos;
    }

}
