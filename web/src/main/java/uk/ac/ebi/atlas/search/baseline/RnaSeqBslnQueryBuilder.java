package uk.ac.ebi.atlas.search.baseline;

import oracle.sql.ARRAY;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.search.DatabaseQuery;

import javax.inject.Named;

import static com.google.common.base.Preconditions.checkState;

@Named
@Scope("prototype")
public class RnaSeqBslnQueryBuilder {

    static final String EXPERIMENT = "EXPERIMENT";
    static final String ASSAY_GROUP_ID = "ASSAYGROUPID";
    static final String EXPRESSION = "SUM(RBE.EXPRESSION)";
    static final String NUMBER_GENES_EXPRESSED = "NumberOfGenesExpressed";

    static final String SELECT_QUERY = "SELECT rbe.experiment, rbe.assaygroupid, SUM(rbe.expression), count(distinct IDENTIFIER) as NumberOfGenesExpressed from RNASEQ_BSLN_EXPRESSIONS subpartition( ABOVE_CUTOFF ) rbe ";
    static final String FOR_GENES = "JOIN TABLE(?) identifiersTable ON rbe.IDENTIFIER = identifiersTable.column_value ";
    static final String WHERE_PUBLIC = "WHERE rbe.experiment = (SELECT accession FROM experiment WHERE private = 'F' AND accession = rbe.experiment) ";
    static final String GROUP_BY = "GROUP BY GROUPING SETS (rbe.experiment, (rbe.experiment, rbe.assaygroupid)) ";
    static final String ORDER_BY = "ORDER BY rbe.experiment, rbe.assaygroupid desc";

    private ARRAY geneIds;

    public RnaSeqBslnQueryBuilder withGeneIds(ARRAY geneIds) {
        this.geneIds = geneIds;
        return this;
    }

    public DatabaseQuery<Object> build() {

        checkState(geneIds != null, "Gene ids must be specified!");

        DatabaseQuery<Object> databaseQuery = new DatabaseQuery<>();

        databaseQuery.appendToQueryString(SELECT_QUERY);

        addGeneIds(databaseQuery);

        databaseQuery.appendToQueryString(WHERE_PUBLIC);
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
