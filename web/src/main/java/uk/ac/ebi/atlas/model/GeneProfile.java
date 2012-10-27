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
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.geneannotation.GeneNamesProvider;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

public class GeneProfile implements Iterable<Expression> {

    private static final int FRACTIONAL_DIGITS_FOR_VALUE_LARGER_OR_EQUAL_TO_ONE = 0;
    private static final int FRACTIONAL_DIGITS_FOR_VALUE_SMALLER_THAN_ONE = 1;

    private GeneNamesProvider geneNamesProvider;

    private double maxExpressionLevel = 0;
    private double minExpressionLevel = Double.MAX_VALUE;

    private String geneId;


    private SortedMap<String, Expression> expressions = new TreeMap<>();

    private GeneProfile() {
    }

    public GeneProfile add(Expression expression){
        String organismPart = expression.getOrganismPart();
        if (!StringUtils.isEmpty(organismPart)){
            this.expressions.put(organismPart, expression);
        }
        updateProfileExpression(expression.getLevel());
        return this;
    }

    public GeneProfile setGeneNamesProvider(GeneNamesProvider geneNamesProvider){
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

    //we decided to lazy load rather then have an attribute because
    // not always the name is used
    public String getGeneName(){
        if (geneNamesProvider != null) {
            return geneNamesProvider.getGeneName(geneId);
        }
        return null;
    }

    @Named("geneProfileBuilder")
    @Scope("prototype")
    public static class Builder {

        private GeneNamesProvider geneNamesProvider;

        private GeneProfile geneProfile;

        private static double DEFAULT_CUTOFF_VALUE = 0D;

        private double cutoffValue = DEFAULT_CUTOFF_VALUE;

        //This constructor is only available to allow instantiation of a Builder
        //in the Integration Tests. It will return a builder that has no GeneNamesProvider binding!
        //This should be removed as soon as we start using Spring injection in Integration Tests
//        protected Builder(){
//            this.geneProfile = new GeneProfile();
//        }

        protected Builder() {
        }

        @Inject
        protected void setGeneNamesProvider(GeneNamesProvider geneNamesProvider){
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
