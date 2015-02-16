package uk.ac.ebi.atlas.search.baseline;

import oracle.sql.ARRAY;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.search.DatabaseQuery;

import javax.inject.Named;

import static com.google.common.base.Preconditions.checkState;

@Named
@Scope("prototype")
public class BaselineExperimentAssayGroupQueryBuilder {

    static final String SELECT_QUERY = "SELECT rbe.experiment, rbe.assaygroupid from RNASEQ_BSLN_EXPRESSIONS subpartition( ABOVE_CUTOFF ) rbe ";
    static final String FOR_ASSAY_GROUPS = "JOIN TABLE(?) assayGroups on rbe.EXPERIMENT = assayGroups.EXPERIMENT and rbe.ASSAYGROUPID = assayGroups.CONTRASTID ";
    static final String FOR_GENES = "JOIN TABLE(?) identifiersTable ON rbe.IDENTIFIER = identifiersTable.column_value ";
    static final String WHERE_PUBLIC = "WHERE rbe.experiment = (SELECT accession FROM experiment WHERE private = 'F' AND accession = rbe.experiment) ";
    static final String GROUP_BY = "GROUP BY rbe.experiment, rbe.assaygroupid";

    private ARRAY experimentAssayGroups;
    private ARRAY geneIds;

    public BaselineExperimentAssayGroupQueryBuilder withExperimentAssayGroups(ARRAY assayGroups) {
        this.experimentAssayGroups = assayGroups;
        return this;
    }

    public BaselineExperimentAssayGroupQueryBuilder withGeneIds(ARRAY geneIds) {
        this.geneIds = geneIds;
        return this;
    }

    public DatabaseQuery<Object> build() {

        checkState(geneIds != null || experimentAssayGroups != null, "Conditions and/or genes must be specified!");

        DatabaseQuery<Object> databaseQuery = new DatabaseQuery<>();

        databaseQuery.appendToQueryString(SELECT_QUERY);

        addAssayGroups(databaseQuery);

        addGeneIds(databaseQuery);

        databaseQuery.appendToQueryString(WHERE_PUBLIC);
        databaseQuery.appendToQueryString(GROUP_BY);

        return databaseQuery;
    }

    // eg: "join table(EXPR_CONTRAST_TABLE( EXPR_CONTRAST('E-MTAB-1733','g17') ) ) ect on rbe.EXPERIMENT = ect.EXPERIMENT and rbe.ASSAYGROUPID != ect.CONTRASTID"
    private void addAssayGroups(DatabaseQuery<Object> databaseQuery) {
        if (experimentAssayGroups != null) {
            databaseQuery.appendToQueryString(FOR_ASSAY_GROUPS);
            databaseQuery.addParameter(experimentAssayGroups);
        }
    }

    // "JOIN TABLE(IDENTIFIERS_TABLE('A', 'B', 'C', 'D', 'E')) identifiersTable ON IDENTIFIER = identifiersTable.column_value"
    protected void addGeneIds(DatabaseQuery<Object> databaseQuery) {
        if (geneIds != null) {
            databaseQuery.appendToQueryString(FOR_GENES);
            databaseQuery.addParameter(geneIds);
        }
    }
}
