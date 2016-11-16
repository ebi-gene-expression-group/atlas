package uk.ac.ebi.atlas.search.baseline;

import java.sql.Array;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.search.DatabaseQuery;

import javax.inject.Named;

import static com.google.common.base.Preconditions.checkState;

@Named
@Scope("prototype")
public class BaselineQueryBuilder {

    static final float FPKM_CUTOFF = 0.5f;
    static final String EXPERIMENT = "EXPERIMENT";
    static final String ASSAY_GROUP_ID = "ASSAYGROUPID";
    static final String EXPRESSION = "SumOfExpressions";
    static final String NUMBER_GENES_EXPRESSED = "NumberOfGenesExpressed";

    static final String SELECT_QUERY_WRAPPER = "SELECT experiment, assaygroupid, SumOfExpressions, NumberOfGenesExpressed from (";

    static final String SELECT_QUERY_RNSEQ = "SELECT rbe.experiment, rbe.assaygroupid, SUM(rbe.expression) as SumOfExpressions, count(distinct IDENTIFIER) as NumberOfGenesExpressed from RNASEQ_BSLN_EXPRESSIONS rbe ";
    static final String SELECT_QUERY_PROTEOMICS = "SELECT rbe.experiment, rbe.assaygroupid, SUM(rbe.expression) as SumOfExpressions, count(distinct IDENTIFIER) as NumberOfGenesExpressed from RNASEQ_BSLN_EXPRESSIONS rbe ";
    static final String FOR_GENES = "JOIN TABLE(?) identifiersTable ON rbe.IDENTIFIER = identifiersTable.column_value ";
    static final String WHERE_PUBLIC_RNASEQ = "WHERE rbe.expression > " + FPKM_CUTOFF + " and rbe.experiment = (SELECT accession FROM experiment WHERE type = 'RNASEQ_MRNA_BASELINE' AND private = 'F' AND accession = rbe.experiment) ";
    static final String WHERE_PUBLIC_PROTEOMICS = "WHERE rbe.experiment = (SELECT accession FROM experiment WHERE type = 'PROTEOMICS_BASELINE' AND private = 'F' AND accession = rbe.experiment) ";
    static final String UNIONALL = "UNION ALL ";

    static final String GROUP_BY = "GROUP BY GROUPING SETS (rbe.experiment, (rbe.experiment, rbe.assaygroupid)) ";
    static final String ORDER_BY = ") ORDER BY experiment, assaygroupid desc";

    private Array geneIds;

    public BaselineQueryBuilder withGeneIds(Array geneIds) {
        this.geneIds = geneIds;
        return this;
    }

    public DatabaseQuery<Object> build() {

        checkState(geneIds != null, "Gene ids must be specified!");

        DatabaseQuery<Object> databaseQuery = new DatabaseQuery<>();

        databaseQuery.appendToQueryString(SELECT_QUERY_WRAPPER);

        databaseQuery.appendToQueryString(SELECT_QUERY_RNSEQ);
        addGeneIds(databaseQuery);
        databaseQuery.appendToQueryString(WHERE_PUBLIC_RNASEQ);
        databaseQuery.appendToQueryString(GROUP_BY);

        databaseQuery.appendToQueryString(UNIONALL);

        databaseQuery.appendToQueryString(SELECT_QUERY_PROTEOMICS);
        addGeneIds(databaseQuery);
        databaseQuery.appendToQueryString(WHERE_PUBLIC_PROTEOMICS);
        databaseQuery.appendToQueryString(GROUP_BY);

        databaseQuery.appendToQueryString(ORDER_BY);

        return databaseQuery;
    }

    // "JOIN TABLE(IDENTIFIERS_TABLE('A', 'B', 'C', 'D', 'E')) identifiersTable ON IDENTIFIER = identifiersTable.column_value"
    protected void addGeneIds(DatabaseQuery<Object> databaseQuery) {
        databaseQuery.appendToQueryString(FOR_GENES);
        databaseQuery.addParameter(geneIds);
    }
}
