package uk.ac.ebi.atlas.search.baseline;

import java.sql.Array;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.search.DatabaseQuery;

import javax.inject.Named;

import static com.google.common.base.Preconditions.checkState;

@Named
@Scope("prototype")
public class BaselineExperimentAssayGroupQueryBuilder {

    static final float FPKM_CUTOFF = 0.5f;
    static final String SELECT_QUERY_RNSEQ = "SELECT DISTINCT rbe.experiment, rbe.assaygroupid from RNASEQ_BSLN_EXPRESSIONS rbe ";
    static final String FOR_ASSAY_GROUPS = "JOIN TABLE(?) assayGroups on rbe.EXPERIMENT = assayGroups.EXPERIMENT and rbe.ASSAYGROUPID = assayGroups.CONTRASTID ";
    static final String FOR_GENES = "JOIN TABLE(?) identifiersTable ON rbe.IDENTIFIER = identifiersTable.column_value ";
    static final String WHERE_PUBLIC_RNASEQ = "WHERE rbe.expression > " + FPKM_CUTOFF + " and rbe.experiment = (SELECT accession FROM experiment WHERE type = 'RNASEQ_MRNA_BASELINE' AND private = 'F' AND accession = rbe.experiment) ";
    static final String UNIONALL = "UNION ALL ";
    static final String SELECT_QUERY_PROTEOMICS = "SELECT DISTINCT rbe.experiment, rbe.assaygroupid from RNASEQ_BSLN_EXPRESSIONS rbe ";
    static final String WHERE_PUBLIC_PROTEOMICS = "WHERE rbe.experiment = (SELECT accession FROM experiment WHERE type = 'PROTEOMICS_BASELINE' AND private = 'F' AND accession = rbe.experiment) ";

    private Array experimentAssayGroups;
    private Array geneIds;

    public BaselineExperimentAssayGroupQueryBuilder withExperimentAssayGroups(Array assayGroups) {
        this.experimentAssayGroups = assayGroups;
        return this;
    }

    public BaselineExperimentAssayGroupQueryBuilder withGeneIds(Array geneIds) {
        this.geneIds = geneIds;
        return this;
    }

    public DatabaseQuery<Object> build() {

        checkState(geneIds != null || experimentAssayGroups != null, "Conditions and/or genes must be specified!");

        DatabaseQuery<Object> databaseQuery = new DatabaseQuery<>();

        databaseQuery.appendToQueryString(SELECT_QUERY_RNSEQ);
        addAssayGroups(databaseQuery);
        addGeneIds(databaseQuery);
        databaseQuery.appendToQueryString(WHERE_PUBLIC_RNASEQ);

        databaseQuery.appendToQueryString(UNIONALL);

        databaseQuery.appendToQueryString(SELECT_QUERY_PROTEOMICS);
        addAssayGroups(databaseQuery);
        addGeneIds(databaseQuery);
        databaseQuery.appendToQueryString(WHERE_PUBLIC_PROTEOMICS);

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
