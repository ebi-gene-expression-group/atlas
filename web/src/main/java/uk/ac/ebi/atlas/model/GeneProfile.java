package uk.ac.ebi.atlas.model;

import org.apache.commons.collections.CollectionUtils;

import java.util.Iterator;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

public class GeneProfile implements Iterable<Expression> {

    private String geneId;

    private double maxExpressionLevel = 0;
    private double minExpressionLevel = Double.MAX_VALUE;

    private SortedMap<String, Expression> expressions = new TreeMap<>();

    private GeneProfile(String geneId) {
        this.geneId = geneId;
    }

    public GeneProfile add(Expression expression){
        this.expressions.put(expression.getOrganismPart(), expression);
        updateProfileExpression(expression.getLevel());
        return this;
    }

    public static Builder forGeneId(String geneId) {
        return new Builder(geneId);
    }

    public String getGeneId() {
        return geneId;
    }

    public int getSpecificity() {
        return expressions.size();
    }

    public Iterator<Expression> iterator(){
        return expressions.values().iterator();
    }

    public double getMaxExpressionLevel(){
        return maxExpressionLevel;
    }

    public double getMinExpressionLevel(){
        return minExpressionLevel;
    }

    private void updateProfileExpression(double level){
        if (maxExpressionLevel < level) {
            maxExpressionLevel = level;
        }
        if (level < minExpressionLevel) {
            minExpressionLevel = level;
        }
    }

    public boolean isExpressedAtMostOn(Set<String> selectedOrganismParts){
        if (CollectionUtils.isEmpty(selectedOrganismParts)){
            return true;
        }
        return selectedOrganismParts.containsAll(getOrganismParts());
    }

    public Set<String> getOrganismParts() {
        return this.expressions.keySet();
    }

    public double getExpressionLevel(String organismPart) {
        Expression expression = expressions.get(organismPart);
        return expression == null ? 0 : expression.getLevel() ;
    }

    public static class Builder {

        private GeneProfile geneProfile;

        private static double DEFAULT_CUTOFF_VALUE = 0D;

        private double cutoffValue = DEFAULT_CUTOFF_VALUE;

        public Builder(String geneId) {
            geneProfile = new GeneProfile(geneId);
        }

        public Builder addExpression(Expression expression) {

            if (expression.isGreaterThan(cutoffValue)) {
                geneProfile.add(expression);
            }

            return this;
        }

        public Builder withCutoff(double cutoff) {
            checkState(geneProfile.expressions.size() == 0, "withCutoff should be invoked before adding any Expression!");
            checkArgument(cutoff >= 0, "cutoff must be >= 0");
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
