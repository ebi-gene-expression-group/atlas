package uk.ac.ebi.atlas.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.max;

public abstract class Profile<DataColumnDescriptor extends DescribesDataColumns, Expr extends Expression> {
    protected Map<DataColumnDescriptor, Expr> expressionsByCondition = new HashMap<>();

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
        return FluentIterable.from(expressionsByCondition.values()).filter(new Predicate<Expr>() {
            public boolean apply(Expr expr) {
                return expr.getLevel() != 0;
            }
        }).size();
    }

    @Nullable
    public Double getExpressionLevel(DataColumnDescriptor condition) {
        Expression expression = expressionsByCondition.get(condition);
        if (expression != null) {
            return expression.getLevel();
        }
        return null;
    }

    public double getMaxExpressionLevelOn(Collection<DataColumnDescriptor> conditions) {
        checkArgument(!CollectionUtils.isEmpty(conditions));
        double expressionLevel = 0D;

        for (DataColumnDescriptor condition : conditions) {
            Double level = getExpressionLevel(condition);
            if (level != null) {
                expressionLevel = max(expressionLevel, Math.abs(level));
            }
        }
        return expressionLevel;
    }

    public double getAverageExpressionLevelOn(Collection<DataColumnDescriptor> conditions) {
        checkArgument(!CollectionUtils.isEmpty(conditions));

        double expressionLevel = 0D;

        for (DataColumnDescriptor condition : conditions) {
            Double level = getExpressionLevel(condition);
            if (level != null) {
                expressionLevel += Math.abs(level);
            }
        }

        return expressionLevel / conditions.size();
    }

    protected abstract void updateStateAfterAddingExpression(Expr expression);

    public boolean isExpressedOnAnyOf(Collection<DataColumnDescriptor> conditions) {
        for(DataColumnDescriptor dataColumnDescriptor : conditions){
            if(expressionsByCondition.containsKey(dataColumnDescriptor)){
                return true;
            }
        }
        return false;
    }

    public Set<DataColumnDescriptor> getConditions() {
        return Sets.newHashSet(expressionsByCondition.keySet());
    }

    public void add(DataColumnDescriptor condition, Expr expression) {
        expressionsByCondition.put(condition, expression);
        updateStateAfterAddingExpression(expression);
    }

    public Expr getExpression(DataColumnDescriptor condition) {
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
                .add("name", getName())
                .toString();
    }

    public Map<String,String> properties(){
        return ImmutableMap.of("id", id, "name", getName());
    }

    public String[] identifiers(){
        return new String[]{id, getName()};
    }
}
