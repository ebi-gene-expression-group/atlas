package uk.ac.ebi.atlas.transcript;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import org.apache.commons.math.util.MathUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public class TranscriptsContribution {

    private int totalTranscriptCount;

    private LinkedHashMap<String, Double> transcriptExpressions = new LinkedHashMap<>();

    public Map<String, Double> getTranscriptPercentageRates() {
           double expressionsSum = getExpressionsSum();

           return Maps.transformValues(transcriptExpressions, new PercentageFunction(expressionsSum));
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

    private double getExpressionsSum() {
        double sum = 0d;
        for (Double expression : transcriptExpressions.values()) {
            sum += expression;
        }
        return sum;
    }

    private class PercentageFunction implements Function<Double, Double> {

        private Double totalExpression;

        public PercentageFunction(Double totalExpression) {
            this.totalExpression = totalExpression;
        }

        @Override
        public Double apply(java.lang.Double aDouble) {
            return MathUtils.round((aDouble / totalExpression) * 100, 1);
        }
    }

}
