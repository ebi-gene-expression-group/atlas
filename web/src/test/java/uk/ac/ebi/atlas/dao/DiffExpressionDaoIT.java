package uk.ac.ebi.atlas.dao;

import com.google.common.collect.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentloader.ExperimentDAO;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.differential.DifferentialBioentityExpression;
import uk.ac.ebi.atlas.solr.query.conditions.IndexedContrast;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml"})
public class DiffExpressionDaoIT {

    private static final String E_MTAB_1066 = "E-MTAB-1066";

    @Inject
    private DiffExpressionDao subject;

    @Inject
    private ExperimentDAO experimentDAO;

    @Before
    public void setUp() throws Exception {
        experimentDAO.addExperiment(E_MTAB_1066, ExperimentType.MICROARRAY, false);

    }

    @After
    public void tearDown() throws Exception {
        experimentDAO.deleteExperiment(E_MTAB_1066);
    }

    @Test
    public void testGetExpressions() throws Exception {
        IndexedContrast indexedContrast1 = new IndexedContrast("E-MTAB-1066", "g2_g3");

        List<DifferentialBioentityExpression> expressions = subject.getExpressions(Lists.newArrayList(indexedContrast1));
        assertThat(expressions.size(), is(50));
        assertThat(expressions.get(0).getBioentityId(), is("FBgn0032801"));
        assertThat(expressions.get(1).getBioentityId(), is("FBgn0040393"));

    }

    @Test
    public void testGetResultCount() throws Exception {
        IndexedContrast indexedContrast1 = new IndexedContrast("E-MTAB-1066", "g2_g3");

        int resultCount = subject.getResultCount(Lists.newArrayList(indexedContrast1));
        assertThat(resultCount, is(129));
    }

}
