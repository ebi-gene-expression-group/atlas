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

    //TODO - should fetch public experiments only
    static final String SELECT_QUERY = "SELECT rbe.experiment, rbe.assaygroupid, SUM(rbe.expression) from RNASEQ_BSLN_EXPRESSIONS subpartition( ABOVE_CUTOFF ) rbe ";
    static final String FOR_GENES = "JOIN TABLE(?) identifiersTable ON rbe.IDENTIFIER = identifiersTable.column_value GROUP BY rbe.experiment, rbe.assaygroupid";

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

        return databaseQuery;
    }

    // "JOIN TABLE(IDENTIFIERS_TABLE('A', 'B', 'C', 'D', 'E')) identifiersTable ON IDENTIFIER = identifiersTable.column_value"
    protected void addGeneIds(DatabaseQuery<Object> databaseQuery) {
        if (geneIds != null) {
            databaseQuery.appendToQueryString(FOR_GENES);
            databaseQuery.addParameter(geneIds);
        }
    }
}
