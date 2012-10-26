package uk.ac.ebi.atlas.model;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.math.util.MathUtils;

import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

public class GeneProfile implements Iterable<Expression> {

    public static final int FRACTIONAL_DIGITS_FOR_VALUE_LARGER_OR_EQUAL_TO_ONE = 0;
    public static final int FRACTIONAL_DIGITS_FOR_VALUE_SMALLER_THAN_ONE = 1;

    private String geneId;

    private double maxExpressionLevel = 0;
    private double minExpressionLevel = Double.MAX_VALUE;

    private SortedMap<String, Expression> expressions = new TreeMap<>();

    private GeneProfile(String geneId) {
        this.geneId = geneId;
    }

    public GeneProfile add(Expression expression){
        String organismPart = expression.getOrganismPart();
        if (!StringUtils.isEmpty(organismPart)){
            this.expressions.put(organismPart, expression);
        }
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

    public double getRoundedExpressionLevel(String organismPart){
        return roundedValue(getExpressionLevel(organismPart));
    }

    public Double getRoundedMaxExpressionLevel(){
        return roundedValue(getMaxExpressionLevel());
    }

    public Double getRoundedMinExpressionLevel(){
        return roundedValue(getMinExpressionLevel());
    }

    private double roundedValue(double value){
        return MathUtils.round(value, value >= 1 ? FRACTIONAL_DIGITS_FOR_VALUE_LARGER_OR_EQUAL_TO_ONE
                : FRACTIONAL_DIGITS_FOR_VALUE_SMALLER_THAN_ONE);
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
