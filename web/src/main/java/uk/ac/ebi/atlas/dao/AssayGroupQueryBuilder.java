package uk.ac.ebi.atlas.dao;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.solr.query.conditions.IndexedAssayGroup;

import javax.inject.Named;
import java.util.Collection;
import java.util.List;

@Named
@Scope("prototype")
class AssayGroupQueryBuilder {

    static final String EXPERIMENT = "EXPERIMENT";

    private String selectPart;
    private String assayGroupOrContrast;
    private Collection<IndexedAssayGroup> indexedAssayGroups;
    private String extraCondition;

    public AssayGroupQueryBuilder withSelectPart(String selectPart) {
        this.selectPart = selectPart;
        return this;
    }

    public AssayGroupQueryBuilder withAssayGroupOrContrast(String assayGroupOrContrast) {
        this.assayGroupOrContrast = assayGroupOrContrast;
        return this;
    }

    public AssayGroupQueryBuilder withIndexedAssayGroupsOrContrasts(Collection<IndexedAssayGroup> indexedAssayGroups) {
        this.indexedAssayGroups = indexedAssayGroups;
        return this;
    }

    public AssayGroupQueryBuilder withExtraCondition(String extraCondition) {
        this.extraCondition = extraCondition;
        return this;
    }

    public AssayGroupQuery build() {
        AssayGroupQuery query = new AssayGroupQuery();

        List<String> queryParts = Lists.newArrayList();

        String queryPart = "(" + EXPERIMENT + "=? AND " + assayGroupOrContrast + "=? )";

        for (IndexedAssayGroup indexedContrast : indexedAssayGroups) {
            queryParts.add(queryPart);
            query.addValue(indexedContrast.getExperimentAccession());
            query.addValue(indexedContrast.getAssayGroupOrContrastId());
        }

        Joiner joiner = Joiner.on(" OR ");

        StringBuilder queryStringBuilder = new StringBuilder();
        queryStringBuilder.append(selectPart);

        if (!indexedAssayGroups.isEmpty()) {

            queryStringBuilder.append("WHERE (");
            joiner.appendTo(queryStringBuilder, queryParts)
                    .append(") ");
        }

        queryStringBuilder.append(extraCondition);

        query.setQueryString(queryStringBuilder.toString());
        return query;
    }
}
