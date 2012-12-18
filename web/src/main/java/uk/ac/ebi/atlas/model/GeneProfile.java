package uk.ac.ebi.atlas.model;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.geneannotation.GeneNamesProvider;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

public class GeneProfile implements Iterable<Expression> {

    private String geneId;

    private GeneNamesProvider geneNamesProvider;

    private double maxExpressionLevel = 0;
    private double minExpressionLevel = Double.MAX_VALUE;

    private SortedMap<String, Expression> expressions = new TreeMap<>();

    private Set<String> allFactorValues = new HashSet<>();

    private GeneProfile() {
    }

    public GeneProfile add(Expression expression) {
        String factorValue = expression.getFactorValue();
        if (!StringUtils.isEmpty(factorValue)) {
            this.expressions.put(factorValue, expression);
            this.allFactorValues.addAll(expression.getAllFactorValues());
        }
        updateProfileExpression(expression.getLevel());
        return this;
    }

    public GeneProfile setGeneNamesProvider(GeneNamesProvider geneNamesProvider) {
        this.geneNamesProvider = geneNamesProvider;
        return this;
    }

    public String getGeneId() {
        return geneId;
    }

    public void setGeneId(String geneId) {
        this.geneId = geneId;
    }

    public int getSpecificity() {
        return expressions.size();
    }

    public Iterator<Expression> iterator() {
        return expressions.values().iterator();
    }

    public double getMaxExpressionLevel() {
        return maxExpressionLevel;
    }

    public double getMinExpressionLevel() {
        return minExpressionLevel;
    }

    private void updateProfileExpression(double level) {
        if (maxExpressionLevel < level) {
            maxExpressionLevel = level;
        }
        if (level < minExpressionLevel) {
            minExpressionLevel = level;
        }
    }

    public boolean isExpressedAtMostOn(Set<String> selectedFactorValues) {
        return CollectionUtils.isEmpty(selectedFactorValues)
                || selectedFactorValues.containsAll(getFactorValues());
    }

    public Set<String> getFactorValues() {
        return this.expressions.keySet();
    }

    public Set<String> getAllFactorValues() {
        return this.allFactorValues;
    }

    public double getExpressionLevel(String factorValue) {
        Expression expression = expressions.get(factorValue);
        return expression == null ? 0 : expression.getLevel();
    }

    //we decided to lazy load rather then have an attribute because
    // not always the name is used
    public String getGeneName() {
        if (geneNamesProvider != null) {
            return geneNamesProvider.getGeneName(geneId);
        }
        return null;
    }

    public String[] toCsv() {
        String[] csvValues = new String[expressions.size() + 2];
        csvValues[0] = getGeneName();
        csvValues[1] = getGeneId();
        int i = 2;
        for (Expression expression : expressions.values()) {
            csvValues[i++] = "" + expression.getLevel();
        }
        return csvValues;
    }

    @Named("geneProfileBuilder")
    @Scope("prototype")
    public static class Builder {

        private GeneNamesProvider geneNamesProvider;

        private GeneProfile geneProfile;

        private static double DEFAULT_CUTOFF_VALUE = 0D;

        private double cutoffValue = DEFAULT_CUTOFF_VALUE;

        protected Builder() {
        }

        @Inject
        protected void setGeneNamesProvider(GeneNamesProvider geneNamesProvider) {
            this.geneNamesProvider = geneNamesProvider;
        }

        public Builder forGeneId(String geneId) {
            this.geneProfile = new GeneProfile();
            geneProfile.setGeneNamesProvider(geneNamesProvider);
            geneProfile.setGeneId(geneId);
            return this;
        }

        public Builder addExpression(Expression expression) {

            if (expression.isGreaterThan(cutoffValue)) {
                geneProfile.add(expression);
            }

            return this;
        }

        public Builder withCutoff(double cutoff) {
            this.cutoffValue = cutoff;

            return this;
        }

        public GeneProfile create() {
            return geneProfile;
        }

        public boolean containsExpressions() {
            return !geneProfile.expressions.isEmpty();
        }

    }
}
