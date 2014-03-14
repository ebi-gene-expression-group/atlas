package uk.ac.ebi.atlas.search.diffanalytics;

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
public class DiffAnalyticsQueryBuilder {

    static final String CONTRASTID = "CONTRASTID";
    static final String PVALUE = "PVAL";
    static final String LOG_2_FOLD = "LOG2FOLD";
    static final String TSTAT = "TSTAT";
    static final String IDENTIFIER = "IDENTIFIER";
    static final String NAME = "NAME";
    static final String ORGANISM = "ORGANISM";
    static final String DESIGNELEMENT = "DESIGNELEMENT";
    static final String EXPERIMENT = "EXPERIMENT";

    static final String SELECT_QUERY = "SELECT " +
            IDENTIFIER + ", " +
            NAME  + ", " +
            DESIGNELEMENT  + ", " +
            ORGANISM + ", " +
            EXPERIMENT + ", " +
            CONTRASTID + ", " +
            PVALUE + ", " +
            LOG_2_FOLD + ", " +
            TSTAT +
            " FROM VW_DIFFANALYTICS ";

    static final String COUNT_QUERY = "SELECT count(1) FROM VW_DIFFANALYTICS ";

    static final String JOIN_PUBLIC_EXPERIMENTS_ONLY = "JOIN EXPERIMENT on VW_DIFFANALYTICS.EXPERIMENT = EXPERIMENT.ACCESSION AND PRIVATE = 'F' ";
    static final String ORDER_BY_LOG2FOLD = "order by abs(LOG2FOLD) desc";

    private Collection<IndexedAssayGroup> indexedAssayGroups;
    private ARRAY geneIds;

    public DiffAnalyticsQueryBuilder withAssayGroups(Collection<IndexedAssayGroup> indexedAssayGroups) {
        //TODO: make a set, as sometimes we get the same assay group multiple times, eg: when running GeneQueryDifferentialSerivceIT.conditionPregnant()
        this.indexedAssayGroups = indexedAssayGroups;
        return this;
    }

    public DiffAnalyticsQueryBuilder withGeneIds(ARRAY geneIds) {
        this.geneIds = geneIds;
        return this;
    }

    public DatabaseQuery<Object> buildSelect() {
        return build(SELECT_QUERY + JOIN_PUBLIC_EXPERIMENTS_ONLY);
    }

    public DatabaseQuery<Object> buildCount() {
        return build(COUNT_QUERY + JOIN_PUBLIC_EXPERIMENTS_ONLY);
    }

    private DatabaseQuery<Object> build(String selectPart) {

        checkState(geneIds != null || CollectionUtils.isNotEmpty(indexedAssayGroups), "Condition or/and geneIds must be specified!");

        DatabaseQuery<Object> databaseQuery = new DatabaseQuery<>();

        databaseQuery.appendToQueryString(selectPart);

        addGeneIds(databaseQuery);

        addContrasts(databaseQuery);

        databaseQuery.appendToQueryString(ORDER_BY_LOG2FOLD);

        return databaseQuery;
    }

    // eg: "((EXPERIMENT=? AND CONTRASTID=? ) OR (EXPERIMENT=? AND CONTRASTID=? ))"
    private void addContrasts(DatabaseQuery<Object> databaseQuery) {
        if (CollectionUtils.isNotEmpty(indexedAssayGroups)) {
            databaseQuery.appendToQueryString("WHERE ");

            List<String> queryParts = Lists.newArrayList();

            String queryPart = "(" + EXPERIMENT + "=? AND " + CONTRASTID + "=? )";

            for (IndexedAssayGroup indexedContrast : indexedAssayGroups) {
                queryParts.add(queryPart);
                databaseQuery.addParameter(indexedContrast.getExperimentAccession());
                databaseQuery.addParameter(indexedContrast.getAssayGroupOrContrastId());
            }

            databaseQuery.appendToQueryString("(");
            Joiner joiner = Joiner.on(" OR ");
            String queryPartsString = joiner.join(queryParts);
            databaseQuery.appendToQueryString(queryPartsString).appendToQueryString(") ");
        }
    }

    // "JOIN TABLE(?) identifiersTable ON IDENTIFIER = identifiersTable.column_value"
    protected void addGeneIds(DatabaseQuery<Object> databaseQuery) {
        if (geneIds != null) {
            databaseQuery.appendToQueryString("JOIN TABLE(?) identifiersTable ON IDENTIFIER = identifiersTable.column_value ");
            databaseQuery.addParameter(geneIds);
        }
    }
}
