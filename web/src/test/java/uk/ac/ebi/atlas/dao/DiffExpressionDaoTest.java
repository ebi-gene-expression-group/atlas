package uk.ac.ebi.atlas.dao;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.ContrastTrader;
import uk.ac.ebi.atlas.solr.query.conditions.IndexedContrast;

import javax.sql.DataSource;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(MockitoJUnitRunner.class)
public class DiffExpressionDaoTest {

    private DiffExpressionDao subject;

    @Mock
    private ContrastTrader contrastTraderMock;

    @Mock
    private DataSource dataSourceMock;

    @Before
    public void setUp() throws Exception {
        subject = new DiffExpressionDao(dataSourceMock, contrastTraderMock);

    }

    @Test
    public void testBuildIndexedContrastQuery() throws Exception {
        IndexedContrast indexedContrast1 = new IndexedContrast("exp1", "c1");
        IndexedContrast indexedContrast2 = new IndexedContrast("exp2", "c2");

        List<IndexedContrast> indexedContrasts = Lists.newArrayList(indexedContrast1, indexedContrast2);
        DiffExpressionDao.IndexedContrastQuery query = subject.buildIndexedContrastQuery(indexedContrasts, DiffExpressionDao.SELECT_QUERY);

        assertThat(query.getQuery(), is("SELECT IDENTIFIER, NAME, DESIGNELEMENT, ORGANISM, EXPERIMENT, CONTRASTID, PVAL, LOG2FOLD, TSTAT FROM VW_DIFFANALYTICS WHERE ((EXPERIMENT=? AND CONTRASTID=? ) OR (EXPERIMENT=? AND CONTRASTID=? )) order by PVAL"));
        assertThat(query.getValues(), is(new String[]{"exp1", "c1", "exp2", "c2"}));

    }

    @Test
    public void testBuildIndexedContrastCountQuery() throws Exception {
        IndexedContrast indexedContrast1 = new IndexedContrast("exp1", "c1");
        IndexedContrast indexedContrast2 = new IndexedContrast("exp2", "c2");

        List<IndexedContrast> indexedContrasts = Lists.newArrayList(indexedContrast1, indexedContrast2);
        DiffExpressionDao.IndexedContrastQuery query = subject.buildIndexedContrastQuery(indexedContrasts, DiffExpressionDao.COUNT_QUERY);

        assertThat(query.getQuery(), is("SELECT count(1) FROM VW_DIFFANALYTICS WHERE ((EXPERIMENT=? AND CONTRASTID=? ) OR (EXPERIMENT=? AND CONTRASTID=? )) order by PVAL"));
        assertThat(query.getValues(), is(new String[]{"exp1", "c1", "exp2", "c2"}));

    }

    @Test
    public void testBuildIndexedContrastQueryWithEmptyList() throws Exception {

        List<IndexedContrast> indexedContrasts = Lists.newArrayList();
        DiffExpressionDao.IndexedContrastQuery query = subject.buildIndexedContrastQuery(indexedContrasts, DiffExpressionDao.SELECT_QUERY);

        assertThat(query.getQuery(), is("SELECT IDENTIFIER, NAME, DESIGNELEMENT, ORGANISM, EXPERIMENT, CONTRASTID, PVAL, LOG2FOLD, TSTAT FROM VW_DIFFANALYTICS order by PVAL"));
        assertThat(query.getValues().length, is(0));

    }


}
