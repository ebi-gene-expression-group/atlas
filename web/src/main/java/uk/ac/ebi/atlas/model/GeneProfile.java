package uk.ac.ebi.atlas.model;

import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commands.RequestContext;
import uk.ac.ebi.atlas.geneannotation.GeneNamesProvider;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

public class GeneProfile extends GeneExpressions {

    private String geneId;

    private GeneNamesProvider geneNamesProvider;

    private double maxExpressionLevel = 0;
    private double minExpressionLevel = Double.MAX_VALUE;

    private SortedMap<Factor, Expression> expressions = new TreeMap<>();

    private GeneProfile() {
    }

    @Override
    public void addExpression(Expression expression) {
        throw new UnsupportedOperationException("Please use the builder!");
    }

    private GeneProfile add(Expression expression, String queryFactorType) {

        updateProfileExpression(expression.getLevel());

        expressions.put(expression.getFactor(queryFactorType), expression);
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
        return expressions.values().size();
    }

    @Override
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

    public boolean isExpressedOnAnyOf(Set<Factor> factors) {
        checkArgument(CollectionUtils.isNotEmpty(factors));
        return Sets.intersection(this.getAllFactors(), factors).size() > 0;
    }

    public double getAverageExpressionLevelOn(Set<Factor> factors) {
        double expressionLevel = 0D;

        if (CollectionUtils.isEmpty(factors)) {
            return expressionLevel;
        }

        for (Factor factor : factors) {
            expressionLevel += getExpressionLevel(factor);
        }
        return expressionLevel / factors.size();
    }

    public Set<Factor> getAllFactors() {
        return Collections.unmodifiableSet(this.expressions.keySet());
    }

    public double getExpressionLevel(Factor factor) {
        Expression expression = expressions.get(factor);
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


    public double getExpressionLevelFoldChangeOn(Set<Factor> selectedFactors, Set<Factor> allFactors) {
        double averageExpressionLevelOnRemaining = getAverageExpressionLevelOn(Sets.difference(allFactors, selectedFactors));
        double averageExpressionLevelOnSelected = getAverageExpressionLevelOn(selectedFactors);
        if (allFactors.isEmpty() || averageExpressionLevelOnRemaining == 0) {
            return averageExpressionLevelOnSelected;
        }
        return averageExpressionLevelOnSelected / averageExpressionLevelOnRemaining;
    }

    @Named("geneProfileBuilder")
    @Scope("prototype")
    public static class Builder {

        private GeneNamesProvider geneNamesProvider;

        private GeneProfile geneProfile;

        private GeneExpressionPrecondition geneExpressionPrecondition;

        private RequestContext requestContext;

        protected Builder() {
        }

        @Inject
        public void setRequestContext(RequestContext requestContext) {
            this.requestContext = requestContext;
        }

        @Inject
        protected void setGeneNamesProvider(GeneNamesProvider geneNamesProvider) {
            this.geneNamesProvider = geneNamesProvider;
        }

        @Inject
        public void setGeneExpressionPrecondition(GeneExpressionPrecondition precondition) {
            this.geneExpressionPrecondition = precondition;
        }

        @PostConstruct
        protected void initPreconditions() {
            geneExpressionPrecondition.setCutoff(requestContext.getCutoff());
            geneExpressionPrecondition.setFilterFactors(requestContext.getSelectedFilterFactors());
        }

        public Builder forGeneId(String geneId) {
            this.geneProfile = new GeneProfile();
            geneProfile.setGeneNamesProvider(geneNamesProvider);
            geneProfile.setGeneId(geneId);
            return this;
        }

        public Builder addExpression(Expression expression) {
            checkState(geneProfile != null, "Please invoke forGeneID before create");
            if (apply(expression)) {
                geneProfile.add(expression, requestContext.getQueryFactorType());
            }
            return this;
        }

        public GeneProfile create() {
            checkState(geneProfile != null, "Please invoke forGeneID before create");

            return containsExpressions() && isOverExpressedInSelectedFactors() ? geneProfile : null;
        }

        private boolean containsExpressions() {
            return !geneProfile.expressions.isEmpty();
        }

        protected boolean isOverExpressedInSelectedFactors() {
            Set<Factor> selectedQueryFactors = requestContext.getSelectedQueryFactors();

            if (!requestContext.isSpecific() || selectedQueryFactors.isEmpty()) {
                return true;
            }

            double averageOnSelected = geneProfile.getAverageExpressionLevelOn(selectedQueryFactors);
            Set<Factor> remainingFactors = Sets.newHashSet(requestContext.getAllQueryFactors());
            remainingFactors.removeAll(selectedQueryFactors);

            double averageOnRest = geneProfile.getAverageExpressionLevelOn(remainingFactors);

            return (averageOnSelected / averageOnRest) >= 1;
        }

        //ToDo: from GeneExpressionPrecondition
        public boolean apply(Expression expression) {

            return expression.isGreaterThan(requestContext.getCutoff())
                    && checkFilterFactors(expression);
        }

        protected boolean checkFilterFactors(Expression expression) {
            return (CollectionUtils.isEmpty(requestContext.getSelectedFilterFactors())
                    || expression.containsAll(requestContext.getSelectedFilterFactors()));
        }
    }
}
