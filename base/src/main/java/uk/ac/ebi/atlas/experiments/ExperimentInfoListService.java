
package uk.ac.ebi.atlas.experiments;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.utils.ExperimentInfo;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class ExperimentInfoListService {
    private ExperimentTrader experimentTrader;

    private final Collection<ExperimentType> experimentTypes;

    static final DateTimeFormatter expectedDateFormat = DateTimeFormat.forPattern("dd-MM-yyyy");

    public ExperimentInfoListService(ExperimentTrader experimentTrader, Collection<ExperimentType> experimentTypes ) {
        this.experimentTrader = experimentTrader;
        this.experimentTypes = experimentTypes;
    }

    public List<ExperimentInfo> listPublicExperiments() {
        List<ExperimentInfo> experimentInfos = Lists.newArrayList();
        for(ExperimentType experimentType : experimentTypes){
            for(Experiment experiment: experimentTrader.getPublicExperiments(experimentType)){
                experimentInfos.add(experiment.getExperimentInfo());
            }
        }
        return experimentInfos;
    }

    private List<ExperimentInfo> cached = null;

    public List<ExperimentInfo> getLatest() {
        if(cached== null){
            cached = fetchLatest();
        }
        return cached;
    }

    private List<ExperimentInfo> fetchLatest(){
        ImmutableList<ExperimentInfo> l = FluentIterable.from(listPublicExperiments()).toSortedList(new Comparator<ExperimentInfo>
                () {
            @Override
            public int compare(ExperimentInfo o1, ExperimentInfo o2) {
                return (-1) * DateTime.parse(o1.getLastUpdate(), expectedDateFormat).compareTo(DateTime.parse(o2
                        .getLastUpdate(), expectedDateFormat));
            }
        });
        return l.subList(0, Math.min(5, l.size()));
    }


}