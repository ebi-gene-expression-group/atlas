package uk.ac.ebi.atlas.dao;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import oracle.sql.ARRAY;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.solr.query.conditions.IndexedAssayGroup;

import javax.inject.Named;
import java.util.Collection;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;

@Named
@Scope("prototype")
class DifferentialGeneQueryBuilder {

    static final String EXPERIMENT = "EXPERIMENT";
    static final String CONTRASTID = "CONTRASTID";

    private String selectPart;
    private Collection<IndexedAssayGroup> indexedAssayGroups;
    private String suffix;
    private ARRAY geneIds;

    public DifferentialGeneQueryBuilder withSelectPart(String selectPart) {
        this.selectPart = selectPart;
        return this;
    }

    public DifferentialGeneQueryBuilder withAssayGroups(Collection<IndexedAssayGroup> indexedAssayGroups) {
        this.indexedAssayGroups = indexedAssayGroups;
        return this;
    }

    // eg: "order by PVAL"
    public DifferentialGeneQueryBuilder withSuffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    public DifferentialGeneQueryBuilder withGeneIds(ARRAY geneIds) {
        this.geneIds = geneIds;
        return this;
    }

    public Query<Object> build() {

        checkState(geneIds != null || CollectionUtils.isNotEmpty(indexedAssayGroups), "Condition or/and geneIds must be specified!");

        Query<Object> query = new Query<>();

        query.appendToQueryString(selectPart);

        addGeneIds(query);

        addContrasts(query);

        query.appendToQueryString(suffix);

        return query;
    }

    // eg: "((EXPERIMENT=? AND CONTRASTID=? ) OR (EXPERIMENT=? AND CONTRASTID=? ))"
    private void addContrasts(Query<Object> query) {
        if (CollectionUtils.isNotEmpty(indexedAssayGroups)) {
            query.appendToQueryString("WHERE ");

            List<String> queryParts = Lists.newArrayList();

            String queryPart = "(" + EXPERIMENT + "=? AND " + CONTRASTID + "=? )";

            for (IndexedAssayGroup indexedContrast : indexedAssayGroups) {
                queryParts.add(queryPart);
                query.addParameter(indexedContrast.getExperimentAccession());
                query.addParameter(indexedContrast.getAssayGroupOrContrastId());
            }

            query.appendToQueryString("(");
            Joiner joiner = Joiner.on(" OR ");
            String queryPartsString = joiner.join(queryParts);
            query.appendToQueryString(queryPartsString).appendToQueryString(") ");
        }
    }

    // "JOIN TABLE(?) identifiersTable ON IDENTIFIER = identifiersTable.column_value"
    protected void addGeneIds(Query<Object> query) {
        if (geneIds != null) {
            query.appendToQueryString("JOIN TABLE(?) identifiersTable ON IDENTIFIER = identifiersTable.column_value ");
            query.addParameter(geneIds);
        }
    }
}
