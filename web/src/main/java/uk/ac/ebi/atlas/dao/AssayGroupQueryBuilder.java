package uk.ac.ebi.atlas.dao;

import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.solr.query.conditions.IndexedAssayGroup;

import javax.inject.Named;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;

@Named
@Scope("prototype")
class AssayGroupQueryBuilder {

    static final String EXPERIMENT = "EXPERIMENT";

    static final int DEFAULT_GENE_SIZE = 1000;
    private String selectPart;
    private String assayGroupOrContrast;
    private Collection<IndexedAssayGroup> indexedAssayGroups;
    private String extraCondition;
    private Collection<String> geneIds;

    private int geneSize = DEFAULT_GENE_SIZE;

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

    public AssayGroupQueryBuilder withGeneIds(Collection<String> geneIds) {
        this.geneIds = geneIds;
        return this;
    }

    public AssayGroupQueryBuilder withGeneBatchSize(int geneBatchSize) {
        geneSize = geneBatchSize;
        return this;
    }

    public AssayGroupQuery build() {

        checkState(CollectionUtils.isNotEmpty(geneIds) || CollectionUtils.isNotEmpty(indexedAssayGroups), "Condition or/and geneIds must be specified!");

        AssayGroupQuery query = new AssayGroupQuery();

        List<String> queryParts = Lists.newArrayList();

        String queryPart = "(" + EXPERIMENT + "=? AND " + assayGroupOrContrast + "=? )";

        StringBuilder queryStringBuilder = new StringBuilder();
        queryStringBuilder.append(selectPart);
        queryStringBuilder.append("WHERE ");

        if (CollectionUtils.isNotEmpty(indexedAssayGroups)) {
            for (IndexedAssayGroup indexedContrast : indexedAssayGroups) {
                queryParts.add(queryPart);
                query.addValue(indexedContrast.getExperimentAccession());
                query.addValue(indexedContrast.getAssayGroupOrContrastId());
            }

            Joiner joiner = Joiner.on(" OR ");
            queryStringBuilder.append("(");
            joiner.appendTo(queryStringBuilder, queryParts)
                    .append(") ");
        }

        if (CollectionUtils.isNotEmpty(geneIds)) {
            if (CollectionUtils.isNotEmpty(indexedAssayGroups)) {
                queryStringBuilder.append("AND ");
            }
            queryStringBuilder.append("(");

            int i = 1;

            for (List<String> sublist : Iterables.partition(geneIds, geneSize)) {

                if (i > 1) {
                    queryStringBuilder.append(" OR ");
                }
                List<String> params = Collections.nCopies(sublist.size(), "?");

                queryStringBuilder.append("IDENTIFIER IN (").append(Joiner.on(", ").join(params)).append(")");
                query.addValues(sublist);

                i++;
            }
            queryStringBuilder.append(") ");

        }

        queryStringBuilder.append(extraCondition);

        query.setQueryString(queryStringBuilder.toString());
        return query;
    }
}
