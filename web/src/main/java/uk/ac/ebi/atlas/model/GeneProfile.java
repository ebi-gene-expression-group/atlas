package uk.ac.ebi.atlas.model;

import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.geneannotation.GeneNamesProvider;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

public class GeneProfile implements Iterable<Expression> {

    private String geneId;

    private GeneNamesProvider geneNamesProvider;

    private double maxExpressionLevel = 0;
    private double minExpressionLevel = Double.MAX_VALUE;

    private SortedMap<FactorValue, Expression> factorValueExpressions = new TreeMap<>();

    private SortedMap<FactorValue, Expression> expressions = new TreeMap<>();

    private GeneProfile() {
    }

    private GeneProfile add(Expression expression, String queryFactorType) {
        // ToDo: this is wrong, this could lead to overriding certain expressions by reoccurring factor values
        for (FactorValue factorValue : expression.getAllFactorValues()) {
            factorValueExpressions.put(factorValue, expression);
        }

        updateProfileExpression(expression.getLevel());

        expressions.put(expression.getFactorValue(queryFactorType), expression);
        return this;
    }

    protected GeneProfile setGeneNamesProvider(GeneNamesProvider geneNamesProvider) {
        this.geneNamesProvider = geneNamesProvider;
        return this;
    }

    public String getGeneId() {
        return geneId;
    }

    protected void setGeneId(String geneId) {
        this.geneId = geneId;
    }

    public int getSpecificity() {
        return factorValueExpressions.values().size();
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

    public boolean isExpressedOnAnyOf(Set<FactorValue> factorValues) {
        checkArgument(CollectionUtils.isNotEmpty(factorValues));
        return Sets.intersection(this.getAllFactorValues(), factorValues).size() > 0;
    }

    public double getAverageExpressionLevelOn(Set<FactorValue> factorValues) {
        if (CollectionUtils.isEmpty(factorValues)) {
            return 0D;
        }
        double expressionLevel = 0D;
        for (FactorValue factorValue : factorValues) {
            expressionLevel += getExpressionLevel(factorValue);
        }
        return expressionLevel / factorValues.size();
    }

    public Set<FactorValue> getAllFactorValues() {
        return this.factorValueExpressions.keySet();
    }

    public double getExpressionLevel(FactorValue factorValue) {
        Expression expression = factorValueExpressions.get(factorValue);
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


    public double getWeightedExpressionLevelOn(Set<FactorValue> selectedFactorValues, Set<FactorValue> allFactorValues) {
        if (allFactorValues.isEmpty()) {
            return getAverageExpressionLevelOn(selectedFactorValues);
        }
        return getAverageExpressionLevelOn(selectedFactorValues) - getAverageExpressionLevelOn(Sets.difference(allFactorValues, selectedFactorValues));
    }

    @Named("geneProfileBuilder")
    @Scope("prototype")
    public static class Builder {

        private GeneNamesProvider geneNamesProvider;

        private GeneProfile geneProfile;

        private static final double DEFAULT_CUTOFF_VALUE = 0D;

        private double cutoffValue = DEFAULT_CUTOFF_VALUE;

        private GeneExpressionPrecondition geneExpressionPrecondition;

        protected Builder() {
        }

        @Inject
        protected void setGeneNamesProvider(GeneNamesProvider geneNamesProvider) {
            this.geneNamesProvider = geneNamesProvider;
        }

        @Inject
        public void setGeneExpressionPrecondition(GeneExpressionPrecondition precondition) {
            this.geneExpressionPrecondition = precondition;
        }

        public Builder forGeneId(String geneId) {
            this.geneProfile = new GeneProfile();
            geneProfile.setGeneNamesProvider(geneNamesProvider);
            geneProfile.setGeneId(geneId);
            return this;
        }

        public Builder addExpression(Expression expression) {
            checkState(geneProfile != null, "Please invoke forGeneID before create");
            if (geneExpressionPrecondition.apply(expression)) {
                geneProfile.add(expression, geneExpressionPrecondition.getQueryFactorType());
            }
            return this;
        }

        public Builder withCutoff(double cutoff) {
            this.cutoffValue = cutoff;

            return this;
        }

        public GeneProfile create() {
            checkState(geneProfile != null, "Please invoke forGeneID before create");

            return containsExpressions() ? geneProfile : null;
        }

        public boolean containsExpressions() {
//            return !geneProfile.factorValueExpressions.isEmpty();
            return !geneProfile.expressions.isEmpty();
        }

    }
}
