package uk.ac.ebi.atlas.transcript;

import com.google.common.collect.Maps;
import org.apache.commons.math.util.MathUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public class TranscriptsContribution {

    protected static final String OTHERS = "Others";
    private int totalTranscriptCount;

    private LinkedHashMap<String, Double> transcriptExpressions = new LinkedHashMap<>();

    public Map<String, Double> getTranscriptPercentageRates() {
        double expressionsSum = getExpressionsSum(transcriptExpressions);

        LinkedHashMap<String, Double> percentageMap = Maps.newLinkedHashMap();

        int count = 0;
        for (Map.Entry<String, Double> entry : transcriptExpressions.entrySet()) {
            if (++count < transcriptExpressions.size()) {
                percentageMap.put(entry.getKey(), MathUtils.round((entry.getValue() / expressionsSum) * 100, 1));
            } else {
                percentageMap.put(entry.getKey(), 100 - getExpressionsSum(percentageMap));
            }
        }

        return percentageMap;
    }

    public int getTotalTranscriptCount() {
        return totalTranscriptCount;
    }

    void setTotalTranscriptCount(int totalTranscriptCount) {
        this.totalTranscriptCount = totalTranscriptCount;
    }

    void put(String transcriptId, double fpkm) {
        transcriptExpressions.put(transcriptId, fpkm);
    }

    private double getExpressionsSum(Map<String, Double> transcriptExpressions) {
        double sum = 0d;
        for (Double expression : transcriptExpressions.values()) {
            sum += expression;
        }
        return sum;
    }

}
