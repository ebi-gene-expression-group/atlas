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

        query.appendToQueryString(selectPart);
        query.appendToQueryString("WHERE ");

        if (CollectionUtils.isNotEmpty(indexedAssayGroups)) {
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

        addGeneIds(query);

        query.appendToQueryString(extraCondition);

        return query;
    }

    protected void addGeneIds(AssayGroupQuery query) {
        if (CollectionUtils.isNotEmpty(geneIds)) {
            if (CollectionUtils.isNotEmpty(indexedAssayGroups)) {
                query.appendToQueryString("AND ");
            }
            query.appendToQueryString("(");

            int i = 1;

            for (List<String> sublist : Iterables.partition(geneIds, geneSize)) {

                if (i > 1) {
                    query.appendToQueryString(" OR ");
                }
                List<String> params = Collections.nCopies(sublist.size(), "?");

                query.appendToQueryString("IDENTIFIER IN (").appendToQueryString(Joiner.on(", ").join(params)).appendToQueryString(")");
                query.addParameters(sublist);

                i++;
            }
            query.appendToQueryString(") ");

        }
    }
}
