package uk.ac.ebi.atlas.experimentpage.baseline.genedistribution;

import org.apache.commons.lang3.StringUtils;

import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;

@Named
public class BarChartExperimentAccessKeyTrader {

    private Map<String, String> experimentAccessKeyMap;

    public BarChartExperimentAccessKeyTrader() {
        experimentAccessKeyMap = new HashMap<>();
    }

    public Map<String, String> getExperimentAccessKeyMap() {
        return experimentAccessKeyMap;
    }

    public void setExperimentAccessKeyMap(Map<String, String> experimentAccessKeyMap) {
        this.experimentAccessKeyMap = experimentAccessKeyMap;
    }

    public String getAccessKey(String experimentAccession) {
        if(StringUtils.isNotBlank(experimentAccession)) {
            return experimentAccessKeyMap.get(experimentAccession);
        }
        return null;
    }

}
