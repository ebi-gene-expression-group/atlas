package uk.ac.ebi.atlas.search.diffanalytics;

import java.sql.Array;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.search.DatabaseQuery;

import javax.inject.Named;

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
    static final String EXPERIMENT = "EXPERIMENT";

    static final String SELECT_QUERY = "SELECT " +
            IDENTIFIER + ", " +
            NAME  + ", " +
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

    private Array experimentContrasts;
    private Array geneIds;
    private String species;

    public DiffAnalyticsQueryBuilder withExperimentContrasts(Array experimentContrasts) {
        this.experimentContrasts = experimentContrasts;
        return this;
    }

    public DiffAnalyticsQueryBuilder withGeneIds(Array geneIds) {
        this.geneIds = geneIds;
        return this;
    }

    public DiffAnalyticsQueryBuilder withSpecies(String species) {
        this.species = species;
        return this;
    }

    public DatabaseQuery<Object> buildSelect() {
        return build(SELECT_QUERY + JOIN_PUBLIC_EXPERIMENTS_ONLY);
    }

    public DatabaseQuery<Object> buildCount() {
        return build(COUNT_QUERY + JOIN_PUBLIC_EXPERIMENTS_ONLY);
    }

    private DatabaseQuery<Object> build(String selectPart) {

        checkState(geneIds != null || experimentContrasts != null, "Conditions and/or genes must be specified!");

        DatabaseQuery<Object> databaseQuery = new DatabaseQuery<>();

        databaseQuery.appendToQueryString(selectPart);

        addGeneIds(databaseQuery);

        addContrasts(databaseQuery);

        addSpecie(databaseQuery, species);

        databaseQuery.appendToQueryString(ORDER_BY_LOG2FOLD);

        return databaseQuery;
    }

    // eg: "JOIN TABLE(?) exprContrast ON VW_DIFFANALYTICS.EXPERIMENT = exprContrast.EXPERIMENT AND VW_DIFFANALYTICS.CONTRASTID = exprContrast.CONTRASTID"
    private void addContrasts(DatabaseQuery<Object> databaseQuery) {
        if (experimentContrasts != null) {
            databaseQuery.appendToQueryString("JOIN TABLE(?) exprContrast ON VW_DIFFANALYTICS.EXPERIMENT = exprContrast.EXPERIMENT AND VW_DIFFANALYTICS.CONTRASTID = exprContrast.CONTRASTID ");
            databaseQuery.addParameter(experimentContrasts);
        }
    }

    // "JOIN TABLE(?) identifiersTable ON IDENTIFIER = identifiersTable.column_value"
    protected void addGeneIds(DatabaseQuery<Object> databaseQuery) {
        if (geneIds != null) {
            databaseQuery.appendToQueryString("JOIN TABLE(?) identifiersTable ON IDENTIFIER = identifiersTable.column_value ");
            databaseQuery.addParameter(geneIds);
        }
    }

    private void addSpecie(DatabaseQuery<Object> databaseQuery, String species) {
       if (StringUtils.isNotBlank(species)) {
            databaseQuery.appendToQueryString("WHERE VW_DIFFANALYTICS.ORGANISM= \'" + species + "\'");
        }
    }
}
