package uk.ac.ebi.atlas.home;

import com.atlassian.util.concurrent.LazyReference;
import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.utils.ExperimentInfo;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExperimentInfoService {

    private final ExperimentTrader experimentTrader;

    private final ImmutableMap<String, Object> experimentsAttributesByAccession (String accession)
    {
        List<String> list = new ArrayList<String>();
        list.add(accession);
        List<ExperimentInfo> fetchExperimentsInfo =
                list.stream()
                        .map(experimentTrader::getPublicExperiment)
                        .map(Experiment::buildExperimentInfo)
                        .collect(Collectors.toList());

        return ImmutableMap.of(
                "specificExperimentInfo", fetchExperimentsInfo);

    };

    public ExperimentInfoService(ExperimentTrader experimentTrader) {
        this.experimentTrader = experimentTrader;
    }

    public ImmutableMap<String, Object> fetchSpecificExperimentsAttributes(String accession) {
        return experimentsAttributesByAccession(accession);
    }

}
