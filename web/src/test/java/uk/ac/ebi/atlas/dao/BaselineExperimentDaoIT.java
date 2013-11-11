package uk.ac.ebi.atlas.dao;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class BaselineExperimentDaoIT {

    private static final String E_MTAB_599 = "E-MTAB-599";

    @Inject
    private BaselineExperimentDao subject;

    @Test
    public void testIsExperimentWithCondition() throws Exception {
        assertThat(subject.isExperimentWithCondition(E_MTAB_599, Lists.newArrayList("g6")), is(true));
        assertThat(subject.isExperimentWithCondition(E_MTAB_599, Lists.newArrayList("g777")), is(false));
    }

    @Test
    public void testIsExperimentWithGenes() throws Exception {
        assertThat(subject.isExperimentWithGenes(E_MTAB_599, Lists.newArrayList("ENSMUSG00000093014")), is(true));
        assertThat(subject.isExperimentWithCondition(E_MTAB_599, Lists.newArrayList("NOT_THERE")), is(false));
    }

    @Test
    public void testIsExperimentWithConditionsAndGenes() throws Exception {
        assertThat(subject.isExperimentWithConditionsAndGenes(E_MTAB_599, Lists.newArrayList("g6"), Lists.newArrayList("ENSMUSG00000093014")), is(true));
        assertThat(subject.isExperimentWithConditionsAndGenes(E_MTAB_599, Lists.newArrayList("g777"), Lists.newArrayList("ENSMUSG00000093487")), is(false));
    }


}
