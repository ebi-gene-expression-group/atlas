package uk.ac.ebi.atlas.search;

import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.utils.ExperimentInfo;

@Component
public class ExperimentInfoService {

    private final ExperimentTrader experimentTrader;

    private final ImmutableMap<String, Object> experimentsAttributesByAccession (String accession)
    {
        ExperimentInfo fetchExperimentsInfo = (experimentTrader.getPublicExperiment(accession)).buildExperimentInfo();
        return ImmutableMap.of(
                "specificExperimentInfo", fetchExperimentsInfo);
    }

    public ExperimentInfoService(ExperimentTrader experimentTrader) {
        this.experimentTrader = experimentTrader;
    }

    public ImmutableMap<String, Object> fetchSpecificExperimentsAttributes(String accession) {
        return experimentsAttributesByAccession(accession);
    }

}
