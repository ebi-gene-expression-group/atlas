package uk.ac.ebi.atlas.web.controllers.page.crossexperiment;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class BioentitiesQueryControllerTest {

    @Inject
    private BioentitiesQueryController subject;

    @Test
    public void buildGlobalSearchTerm2GeneQueryTermsAndCondition() throws Exception {
        List<String> geneQueryLists = Lists.newArrayList("id1", "id2");
        String condition = "liver";
        assertThat(subject.buildGlobalSearchTerm(geneQueryLists, condition), is("(id1 OR id2) AND liver"));
    }

    @Test
    public void buildGlobalSearchTerm2GeneQueryTerms() throws Exception {
        List<String> geneQueryLists = Lists.newArrayList("id1", "id2");
        String condition = null;
        assertThat(subject.buildGlobalSearchTerm(geneQueryLists, condition), is("(id1 OR id2)"));
    }

    @Test
    public void buildGlobalSearchTerm1GeneQueryTerm() throws Exception {
        List<String> geneQueryLists = Lists.newArrayList("id1");
        String condition = null;
        assertThat(subject.buildGlobalSearchTerm(geneQueryLists, condition), is("(id1)"));
    }

    @Test
    public void buildGlobalSearchTermConditionOnly() throws Exception {
        List<String> geneQueryLists = Collections.EMPTY_LIST;
        String condition = "liver";
        assertThat(subject.buildGlobalSearchTerm(geneQueryLists, condition), is("liver"));
    }

}
