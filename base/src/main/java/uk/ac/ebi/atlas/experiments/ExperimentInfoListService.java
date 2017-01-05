package uk.ac.ebi.atlas.experiments;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.utils.ExperimentInfo;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ExperimentInfoListService {
    private ExperimentTrader experimentTrader;

    private final Collection<ExperimentType> experimentTypes;

    private Gson gson = new Gson();

    static final DateTimeFormatter expectedDateFormat = DateTimeFormat.forPattern("dd-MM-yyyy");

    public ExperimentInfoListService(ExperimentTrader experimentTrader, Collection<ExperimentType> experimentTypes ) {
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


    List<ExperimentInfo> listPublicExperiments() {
        List<ExperimentInfo> experimentInfos = Lists.newArrayList();
        for(ExperimentType experimentType : experimentTypes){
            for(Experiment experiment: experimentTrader.getPublicExperiments(experimentType)){
                experimentInfos.add(experiment.buildExperimentInfo());
            }
        }
        return experimentInfos;
    }

    private ImmutableMap<String, Object> cached = null;

    public ImmutableMap<String, Object> getLatestExperimentsListAttributes(){
        if(cached== null){
            cached = ImmutableMap.of("experimentCount", fetchCount(), "latestExperiments", fetchLatest());
        }
        return cached;
    }

    int fetchCount() {
        return listPublicExperiments().size();
    }

    List<ExperimentInfo> fetchLatest(){
        ImmutableList<ExperimentInfo> l =
                FluentIterable.from(listPublicExperiments()).toSortedList(new Comparator<ExperimentInfo>() {
                    @Override
                    public int compare(ExperimentInfo o1, ExperimentInfo o2) {
                        return (-1) *
                                DateTime.parse(o1.getLastUpdate(), expectedDateFormat)
                                        .compareTo(DateTime.parse(o2.getLastUpdate(), expectedDateFormat));
                    }
                });
        return l.subList(0, Math.min(5, l.size()));
    }

}
