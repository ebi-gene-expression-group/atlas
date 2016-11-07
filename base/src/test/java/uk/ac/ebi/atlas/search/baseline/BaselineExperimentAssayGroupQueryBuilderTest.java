
package uk.ac.ebi.atlas.search.baseline;

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
public class BaselineExperimentAssayGroupQueryBuilderTest {

    private BaselineExperimentAssayGroupQueryBuilder subject;

    @Before
    public void setUp() throws Exception {
        subject = new BaselineExperimentAssayGroupQueryBuilder();
    }


    @Test
    public void selectWhereAssayGroups() {
        ARRAY assayGroups = Mockito.mock(ARRAY.class);

        DatabaseQuery<Object> databaseQuery = subject.withExperimentAssayGroups(assayGroups).build();

        MatcherAssert.assertThat(databaseQuery.getQuery(), is("SELECT DISTINCT rbe.experiment, rbe.assaygroupid from RNASEQ_BSLN_EXPRESSIONS rbe " +
                        "JOIN TABLE(?) assayGroups on rbe.EXPERIMENT = assayGroups.EXPERIMENT and rbe.ASSAYGROUPID = assayGroups.CONTRASTID " +
                        "WHERE rbe.expression > 0.5 and rbe.experiment = (SELECT accession FROM experiment WHERE type = 'RNASEQ_MRNA_BASELINE' AND private = 'F' AND accession = rbe.experiment) " +
                        "UNION ALL " +
                        "SELECT DISTINCT rbe.experiment, rbe.assaygroupid from RNASEQ_BSLN_EXPRESSIONS rbe " +
                        "JOIN TABLE(?) assayGroups on rbe.EXPERIMENT = assayGroups.EXPERIMENT and rbe.ASSAYGROUPID = assayGroups.CONTRASTID " +
                        "WHERE rbe.experiment = (SELECT accession FROM experiment WHERE type = 'PROTEOMICS_BASELINE' AND private = 'F' AND accession = rbe.experiment) "
        ));
        MatcherAssert.assertThat(databaseQuery.getParameters(), IsIterableContainingInOrder.contains((Object) assayGroups, (Object) assayGroups));
    }

    @Test
    public void selectWhereGeneIds()  {
        ARRAY geneIds = Mockito.mock(ARRAY.class);

        DatabaseQuery<Object> databaseQuery = subject.withGeneIds(geneIds).build();

        MatcherAssert.assertThat(databaseQuery.getQuery(), is("SELECT DISTINCT rbe.experiment, rbe.assaygroupid from RNASEQ_BSLN_EXPRESSIONS rbe " +
                        "JOIN TABLE(?) identifiersTable ON rbe.IDENTIFIER = identifiersTable.column_value " +
                        "WHERE rbe.expression > 0.5 and rbe.experiment = (SELECT accession FROM experiment WHERE type = 'RNASEQ_MRNA_BASELINE' AND private = 'F' AND accession = rbe.experiment) " +
                        "UNION ALL " +
                        "SELECT DISTINCT rbe.experiment, rbe.assaygroupid from RNASEQ_BSLN_EXPRESSIONS rbe " +
                        "JOIN TABLE(?) identifiersTable ON rbe.IDENTIFIER = identifiersTable.column_value " +
                        "WHERE rbe.experiment = (SELECT accession FROM experiment WHERE type = 'PROTEOMICS_BASELINE' AND private = 'F' AND accession = rbe.experiment) "));
        MatcherAssert.assertThat(databaseQuery.getParameters(), IsIterableContainingInOrder.contains((Object) geneIds, (Object) geneIds));

    }

    @Test
    public void selectWhereAssayGroupsAndGeneIds() {
        ARRAY geneIds = Mockito.mock(ARRAY.class);
        ARRAY assayGroups = Mockito.mock(ARRAY.class);

        DatabaseQuery<Object> databaseQuery = subject.withExperimentAssayGroups(assayGroups)
                .withGeneIds(geneIds)
                .build();

        MatcherAssert.assertThat(databaseQuery.getQuery(), is(
                "SELECT DISTINCT rbe.experiment, rbe.assaygroupid from RNASEQ_BSLN_EXPRESSIONS rbe " +
                        "JOIN TABLE(?) assayGroups on rbe.EXPERIMENT = assayGroups.EXPERIMENT and rbe.ASSAYGROUPID = assayGroups.CONTRASTID " +
                        "JOIN TABLE(?) identifiersTable ON rbe.IDENTIFIER = identifiersTable.column_value " +
                        "WHERE rbe.expression > 0.5 and rbe.experiment = (SELECT accession FROM experiment WHERE type = 'RNASEQ_MRNA_BASELINE' AND private = 'F' AND accession = rbe.experiment) " +
                        "UNION ALL " +
                        "SELECT DISTINCT rbe.experiment, rbe.assaygroupid from RNASEQ_BSLN_EXPRESSIONS rbe " +
                        "JOIN TABLE(?) assayGroups on rbe.EXPERIMENT = assayGroups.EXPERIMENT and rbe.ASSAYGROUPID = assayGroups.CONTRASTID " +
                        "JOIN TABLE(?) identifiersTable ON rbe.IDENTIFIER = identifiersTable.column_value " +
                        "WHERE rbe.experiment = (SELECT accession FROM experiment WHERE type = 'PROTEOMICS_BASELINE' AND private = 'F' AND accession = rbe.experiment) "));
        MatcherAssert.assertThat(databaseQuery.getParameters().size(), is(4));

    }

}
