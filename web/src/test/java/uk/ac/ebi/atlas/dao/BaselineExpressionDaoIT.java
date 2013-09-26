package uk.ac.ebi.atlas.dao;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.baseline.BaselineBioentitiesCount;
import uk.ac.ebi.atlas.solr.query.conditions.IndexedAssayGroup;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class BaselineExpressionDaoIT {

    private static final String E_MTAB_599 = "E-MTAB-599";

    @Inject
    private BaselineExpressionDao subject;


    @Test
    public void testGetExpressions() throws Exception {
        IndexedAssayGroup assayGroup = new IndexedAssayGroup(E_MTAB_599, "g2");

        List<BaselineBioentitiesCount> bioentitiesCounts = subject.getBioentitiesCounts(Lists.newArrayList(assayGroup));
        assertThat(bioentitiesCounts, hasSize(1));
        assertThat(bioentitiesCounts.get(0).getExperimentAccession(), is(E_MTAB_599));
        assertThat(bioentitiesCounts.get(0).getCount(), is(17719));
        assertThat(bioentitiesCounts.get(0).getSpecies(), is("mus musculus"));

    }



}
