package uk.ac.ebi.atlas.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;

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
    public Double getKnownExpressionLevel(Condition condition) {
        Expression expression = expressionsByCondition.get(condition);
        if (expression != null && expression.isKnown()) {
            return expression.getLevel();
        }
        return null;
    }

    protected boolean isKnownLevel(Condition condition){
        Expression expression = expressionsByCondition.get(condition);
        return expression != null && expression.isKnown();
    }

    protected abstract void addExpression(T expression);

    public boolean isExpressedOnAnyOf(Set<Condition> conditions) {
        checkArgument(CollectionUtils.isNotEmpty(conditions));
        return Sets.intersection(expressionsByCondition.keySet(), conditions).size() > 0;
    }

    public Set<Condition> getConditions() {
        return Sets.newHashSet(expressionsByCondition.keySet());
    }

    protected Profile addExpression(Condition condition, T expression) {
        expressionsByCondition.put(condition, expression);
        addExpression(expression);
        return this;
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
}
