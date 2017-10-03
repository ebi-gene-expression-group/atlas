package uk.ac.ebi.atlas.model;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
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

public abstract class Profile<DataColumnDescriptor extends DescribesDataColumns,
                              Expr extends Expression,
                              Self extends Profile<DataColumnDescriptor, Expr, Self>>
implements KryoSerializable {

    protected Map<String, Expr> expressionsByCondition = new HashMap<>();
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

    public boolean hasAllExpressionsEqualZero(){
        return expressionsByCondition.values().stream().noneMatch(expr -> expr.getLevel() != 0);
    }

    public long getSpecificity() {
        return expressionsByCondition.values().stream().filter(expr -> expr.getLevel() != 0).count();
    }

    public long getSpecificity(Collection<DataColumnDescriptor> conditions) {
        List<String> dataColumnDescriptorIds = conditions.stream().map(DescribesDataColumns::getId).collect(toList());

        return expressionsByCondition.entrySet().stream()
                .filter(p -> dataColumnDescriptorIds.contains(p.getKey()) && p.getValue().getLevel() != 0)
                .count();
    }


    @Nullable
    public Double getExpressionLevel(DataColumnDescriptor condition) {
        Expression expression = getExpression(condition);
        if (expression != null) {
            return expression.getLevel();
        }
        return null;
    }

    public double getMaxExpressionLevelOn(Collection<DataColumnDescriptor> conditions) {
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

    public void add(DataColumnDescriptor condition, Expr expression) {
        expressionsByCondition.put(condition.getId(), expression);
    }

    public Expr getExpression(DataColumnDescriptor condition) {
        return expressionsByCondition.get(condition.getId());
    }

    public String getName() {
        if (StringUtils.isBlank(name)) {
            return id;
        }

        return name;
    }

    public Self filter(Predicate<Expr> keepExpressions){
        Self result = createEmptyCopy();

        expressionsByCondition.entrySet().stream().filter(e -> keepExpressions.test(e.getValue())).forEach(e -> {
            result.expressionsByCondition.put(e.getKey(), e.getValue());
        });

        return result;
    }

    protected abstract Self createEmptyCopy();

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


    @Override
    public void write(Kryo kryo, Output output) {
        output.writeString(id);
        output.writeString(name);
        kryo.writeObject(output, expressionsByCondition);
    }

    @Override
    public void read(Kryo kryo, Input input) {
        id = input.readString();
        name = input.readString();
        expressionsByCondition = kryo.readObject(input, HashMap.class);
    }
}
