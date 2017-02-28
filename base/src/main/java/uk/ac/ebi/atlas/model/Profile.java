package uk.ac.ebi.atlas.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.max;

//Condition is the Condition type (i.e. Factor or Contrast),
//T is the Expression type (Baseline Expression or DifferentialExpression)
public abstract class Profile<Condition, T extends Expression> {
    protected Map<Condition, T> expressionsByCondition = new HashMap<>();

    private String id;

    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Profile)) return false;
        Profile<?, ?> profile = (Profile<?, ?>) o;
        return Objects.equal(expressionsByCondition, profile.expressionsByCondition) &&
                Objects.equal(getId(), profile.getId()) &&
                Objects.equal(getName(), profile.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(expressionsByCondition, getId(), getName());
    }

    protected Profile(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public boolean isEmpty() {
        return expressionsByCondition.isEmpty();
    }

    public int getSpecificity() {
        return expressionsByCondition.size();
    }

    @Nullable
    public Double getExpressionLevel(Condition condition) {
        Expression expression = expressionsByCondition.get(condition);
        if (expression != null) {
            return expression.getLevel();
        }
        return null;
    }

    public double getMaxExpressionLevelOn(Set<Condition> conditions) {
        checkArgument(!CollectionUtils.isEmpty(conditions));
        double expressionLevel = 0D;

        for (Condition condition : conditions) {
            Double level = getExpressionLevel(condition);
            if (level != null) {
                expressionLevel = max(expressionLevel, Math.abs(level));
            }
        }
        return expressionLevel;
    }

    public double getAverageExpressionLevelOn(Set<Condition> conditions) {
        checkArgument(!CollectionUtils.isEmpty(conditions));

        double expressionLevel = 0D;

        for (Condition condition : conditions) {
            Double level = getExpressionLevel(condition);
            if (level != null) {
                expressionLevel += Math.abs(level);
            }
        }

        return expressionLevel / conditions.size();
    }

    protected abstract void updateStateAfterAddingExpression(T expression);

    public boolean isExpressedOnAnyOf(Set<Condition> conditions) {
        checkArgument(CollectionUtils.isNotEmpty(conditions));
        return Sets.intersection(expressionsByCondition.keySet(), conditions).size() > 0;
    }

    public Set<Condition> getConditions() {
        return Sets.newHashSet(expressionsByCondition.keySet());
    }

    public void add(Condition condition, T expression) {
        expressionsByCondition.put(condition, expression);
        updateStateAfterAddingExpression(expression);
    }

    public T getExpression(Condition condition) {
        return expressionsByCondition.get(condition);
    }

    public String getName() {
        if (StringUtils.isBlank(name)) {
            return id;
        }
        return name;
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("expressionsByCondition", expressionsByCondition.values())
                .add("id", id)
                .add("name", name)
                .toString();
    }

    public Map<String,String> properties(){
        Map<String,String>  result = new HashMap<>();
        result.put("id", id);
        result.put("name", name);
        return result;
    }

    public String[] identifiers(){
        return new String[]{id, name};
    }
}
