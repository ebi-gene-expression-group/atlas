
package uk.ac.ebi.atlas.solr.query.builders;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Collections2;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import uk.ac.ebi.atlas.model.baseline.BioentityPropertyName;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * This is a builder, keep always in mind that builders are potentially stateful.
 * If you need to build different query strings within the same client process
 * you must use a new instance of builder for each query.
 */
public abstract class SolrQueryBuilder<T extends SolrQueryBuilder<T>> {

    public static final String PROPERTY_NAME_FIELD = "property_name";

    private static final String BIOENTITY_TYPE_FIELD = "bioentity_type";

    public static final String SPECIES_FIELD = "species";

    protected StringBuilder queryStringBuilder = new StringBuilder();

    public T withSpecies(String species){
        if (StringUtils.isNotBlank(species)){
            queryStringBuilder.append(" AND " + SPECIES_FIELD + ":\"").append(species).append("\"");
        }
        return getThis();
    }

    public T withBioentityTypes(Set<String> bioentityTypes){
        checkArgument(CollectionUtils.isNotEmpty(bioentityTypes));

        Collection<String> bioentityTypeConditions = transformToConditions(BIOENTITY_TYPE_FIELD, Lists.newArrayList(bioentityTypes));

        queryStringBuilder.append(" AND (");
        Joiner.on(" OR ").appendTo(queryStringBuilder, bioentityTypeConditions).append(")");
        return getThis();
    }

    public T withPropertyNames(BioentityPropertyName... propertyNames){
        return withPropertyNames(mapBioentityPropertyNames(propertyNames));
    }

    public T withPropertyNames(String... propertyNames){
        checkArgument(org.apache.commons.lang3.ArrayUtils.isNotEmpty(propertyNames));

        Collection<String> propertyNameConditions = transformToConditions(PROPERTY_NAME_FIELD, Lists.newArrayList(propertyNames));

        queryStringBuilder.append(" AND (");
        Joiner.on(" OR ").appendTo(queryStringBuilder, propertyNameConditions).append(")");
        return getThis();
    }

    private String[] mapBioentityPropertyNames(BioentityPropertyName... propertyNames) {
        return FluentIterable.from(propertyNames).transform(new Function<BioentityPropertyName, String>() {
            @Nullable
            @Override
            public String apply(BioentityPropertyName bioentityPropertyName) {
                return bioentityPropertyName.name;
            }
        }).toArray(String.class);
    }

    private <V> Collection<String> transformToConditions(final String fieldName, List<V> values){
        return FluentIterable.from(values).transform(new Function<V, String>() {
            @Override
            public String apply(V bioEntityType) {
                return fieldName.concat(":\"").concat(bioEntityType.toString()).concat("\"");
            }
        }).toList();

    }

    protected abstract T getThis();

}
