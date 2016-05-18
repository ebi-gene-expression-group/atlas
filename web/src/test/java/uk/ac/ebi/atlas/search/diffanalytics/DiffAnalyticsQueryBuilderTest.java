
package uk.ac.ebi.atlas.search.diffanalytics;

import oracle.sql.ARRAY;
import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.search.DatabaseQuery;

import static org.hamcrest.Matchers.is;

@RunWith(MockitoJUnitRunner.class)
public class DiffAnalyticsQueryBuilderTest {

    private DiffAnalyticsQueryBuilder subject;

    @Before
    public void setUp() throws Exception {
        subject = new DiffAnalyticsQueryBuilder();

    }

    @Test
    public void selectWhereContrasts() throws Exception {
        ARRAY indexedContrasts = Mockito.mock(ARRAY.class);

        DatabaseQuery<Object> databaseQuery = subject.withExperimentContrasts(indexedContrasts).buildSelect();

        MatcherAssert.assertThat(databaseQuery.getQuery(), is("SELECT IDENTIFIER, NAME, ORGANISM, EXPERIMENT, CONTRASTID, PVAL, LOG2FOLD, TSTAT FROM VW_DIFFANALYTICS JOIN EXPERIMENT on VW_DIFFANALYTICS.EXPERIMENT = EXPERIMENT.ACCESSION AND PRIVATE = 'F' JOIN TABLE(?) exprContrast ON VW_DIFFANALYTICS.EXPERIMENT = exprContrast.EXPERIMENT AND VW_DIFFANALYTICS.CONTRASTID = exprContrast.CONTRASTID order by abs(LOG2FOLD) desc"));
    }

    @Test
    public void countWhereContrasts() throws Exception {
        ARRAY indexedContrasts = Mockito.mock(ARRAY.class);

        DatabaseQuery<Object> databaseQuery = subject.withExperimentContrasts(indexedContrasts).buildCount();

        MatcherAssert.assertThat(databaseQuery.getQuery(), is("SELECT count(1) FROM VW_DIFFANALYTICS JOIN EXPERIMENT on VW_DIFFANALYTICS.EXPERIMENT = EXPERIMENT.ACCESSION AND PRIVATE = 'F' JOIN TABLE(?) exprContrast ON VW_DIFFANALYTICS.EXPERIMENT = exprContrast.EXPERIMENT AND VW_DIFFANALYTICS.CONTRASTID = exprContrast.CONTRASTID order by abs(LOG2FOLD) desc"));
    }


    @Test
    public void selectWhereGeneIds() throws Exception {
        ARRAY geneIds = Mockito.mock(ARRAY.class);

        DatabaseQuery<Object> databaseQuery = subject.withGeneIds(geneIds).buildSelect();

        MatcherAssert.assertThat(databaseQuery.getQuery(), is("SELECT IDENTIFIER, NAME, ORGANISM, EXPERIMENT, CONTRASTID, PVAL, LOG2FOLD, TSTAT " +
                "FROM VW_DIFFANALYTICS JOIN EXPERIMENT on VW_DIFFANALYTICS.EXPERIMENT = EXPERIMENT.ACCESSION AND PRIVATE = 'F' JOIN TABLE(?) identifiersTable ON IDENTIFIER = identifiersTable.column_value order by abs(LOG2FOLD) desc"));
        MatcherAssert.assertThat(databaseQuery.getParameters(), IsIterableContainingInOrder.contains((Object) geneIds));

    }

    @Test
    public void selectWhereContrastsAndGeneIds() throws Exception {
        ARRAY geneIds = Mockito.mock(ARRAY.class);
        ARRAY indexedContrasts = Mockito.mock(ARRAY.class);

        DatabaseQuery<Object> databaseQuery = subject.withExperimentContrasts(indexedContrasts)
                .withGeneIds(geneIds)
                .buildSelect();

        MatcherAssert.assertThat(databaseQuery.getQuery(), is("SELECT IDENTIFIER, NAME, ORGANISM, EXPERIMENT, CONTRASTID, PVAL, LOG2FOLD, TSTAT FROM VW_DIFFANALYTICS JOIN EXPERIMENT on VW_DIFFANALYTICS.EXPERIMENT = EXPERIMENT.ACCESSION AND PRIVATE = 'F' JOIN TABLE(?) identifiersTable ON IDENTIFIER = identifiersTable.column_value JOIN TABLE(?) exprContrast ON VW_DIFFANALYTICS.EXPERIMENT = exprContrast.EXPERIMENT AND VW_DIFFANALYTICS.CONTRASTID = exprContrast.CONTRASTID order by abs(LOG2FOLD) desc"));
        MatcherAssert.assertThat(databaseQuery.getParameters().size(), is(2));

    }


    @Test
    public void countWhereContrastsAndGeneIds() throws Exception {
        ARRAY geneIds = Mockito.mock(ARRAY.class);
        ARRAY indexedContrasts = Mockito.mock(ARRAY.class);

        DatabaseQuery<Object> databaseQuery = subject.withExperimentContrasts(indexedContrasts)
                .withGeneIds(geneIds)
                .buildCount();

        MatcherAssert.assertThat(databaseQuery.getQuery(), is("SELECT count(1) FROM VW_DIFFANALYTICS JOIN EXPERIMENT on VW_DIFFANALYTICS.EXPERIMENT = EXPERIMENT.ACCESSION AND PRIVATE = 'F' JOIN TABLE(?) identifiersTable ON IDENTIFIER = identifiersTable.column_value JOIN TABLE(?) exprContrast ON VW_DIFFANALYTICS.EXPERIMENT = exprContrast.EXPERIMENT AND VW_DIFFANALYTICS.CONTRASTID = exprContrast.CONTRASTID order by abs(LOG2FOLD) desc"));

    }

}
