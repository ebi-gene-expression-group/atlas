package uk.ac.ebi.atlas.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.max;
import static java.util.stream.Collectors.toList;

public abstract class Profile<D extends DescribesDataColumns,
                              E extends Expression,
                              P extends Profile<D, E, P>> {

    protected Map<String, E> expressionsByCondition = new HashMap<>();
    private String id;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Profile)) return false;
        Profile<?, ?, ?> profile = (Profile<?, ?, ?>) o;
        return Objects.equal(expressionsByCondition, profile.expressionsByCondition) &&
                Objects.equal(getId(), profile.getId()) &&
                Objects.equal(getName(), profile.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(expressionsByCondition, getId(), getName());
    }

    protected Profile() {}

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

    public boolean hasAllExpressionsEqualZero() {
        return expressionsByCondition.values().stream().noneMatch(expr -> expr.getLevel() != 0);
    }

    public long getSpecificity() {
        return expressionsByCondition.values().stream().filter(expr -> expr.getLevel() != 0).count();
    }

    public long getSpecificity(Collection<D> conditions) {
        List<String> dataColumnDescriptorIds = conditions.stream().map(DescribesDataColumns::getId).collect(toList());

        return expressionsByCondition.entrySet().stream()
                .filter(p -> dataColumnDescriptorIds.contains(p.getKey()) && p.getValue().getLevel() != 0)
                .count();
    }

    public boolean isExpressedAnywhereOn(Collection<D> conditions) {
        return expressionsByCondition.size() > 0 && conditions.stream().anyMatch(this::isExpressedOn);
    }

    private boolean isExpressedOn(D condition) {
        Expression expression = getExpression(condition);
        return expression != null && expression.getLevel() != 0;
    }

    @Nullable
    public Double getExpressionLevel(D condition) {
        Expression expression = getExpression(condition);
        if (expression != null) {
            return expression.getLevel();
        }
        return null;
    }

    public double getMaxExpressionLevelOn(Collection<D> conditions) {
        double expressionLevel = 0D;

        for (D condition : conditions) {
            Double level = getExpressionLevel(condition);
            if (level != null) {
                expressionLevel = max(expressionLevel, Math.abs(level));
            }
        }
        return expressionLevel;
    }

    public double getAverageExpressionLevelOn(Collection<D> conditions) {
        checkArgument(!CollectionUtils.isEmpty(conditions));

        double expressionLevel = 0D;

        for (D condition : conditions) {
            Double level = getExpressionLevel(condition);
            if (level != null) {
                expressionLevel += Math.abs(level);
            }
        }

        return expressionLevel / conditions.size();
    }

    public void add(D condition, E expression) {
        expressionsByCondition.put(condition.getId(), expression);
    }

    public E getExpression(D condition) {
        return expressionsByCondition.get(condition.getId());
    }

    public String getName() {
        if (StringUtils.isBlank(name)) {
            return id;
        }

        return name;
    }

    public P filter(Predicate<E> keepExpressions) {
        P result = createEmptyCopy();

        expressionsByCondition.entrySet().stream()
                .filter(e -> keepExpressions.test(e.getValue()))
                .forEach(e -> result.expressionsByCondition.put(e.getKey(), e.getValue()));

        return result;
    }

    protected abstract P createEmptyCopy();

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("expressionsByCondition", expressionsByCondition.values())
                .add("id", id)
                .add("name", getName())
                .toString();
    }

    public Map<String,String> properties() {
        return ImmutableMap.of("id", id, "name", getName());
    }

    public String[] identifiers() {
        return new String[]{id, getName()};
    }

}
