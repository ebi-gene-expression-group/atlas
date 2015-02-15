package uk.ac.ebi.atlas.search.EFO;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.search.ConditionQuery;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class ConditionSearchEFOExpanderIT {


    @Inject
    ConditionSearchEFOExpander subject;

    @Test
    public void cancer() throws Exception {
        ConditionQuery expandedSearch = subject.getIds(ConditionQuery.create("cancer"));

        assertThat(expandedSearch.size(), is(greaterThanOrEqualTo(45)));
        assertThat(expandedSearch, hasItem("cancer"));
        assertThat(expandedSearch, hasItem(Matchers.startsWith("EFO_")));
    }

    @Test
    public void inflammatoryBowelDisease() throws Exception {
        ConditionQuery expandedSearch = subject.getIds(ConditionQuery.create("Inflammatory Bowel Disease"));

        assertThat(expandedSearch.size(), is(greaterThanOrEqualTo(3)));
        assertThat(expandedSearch, hasItem("Inflammatory Bowel Disease"));
        assertThat(expandedSearch, hasItem(startsWith("Orphanet_")));
        assertThat(expandedSearch, hasItem(startsWith("EFO_")));
    }

    @Test
    public void adiposeThymus() throws Exception {
        ConditionQuery expandedSearch = subject.getIds(ConditionQuery.create("\"adipose thymus\""));

        assertThat(expandedSearch.size(), is(greaterThanOrEqualTo(1)));
        assertThat(expandedSearch, hasItem("adipose thymus"));
    }

    @Test
    public void heart() throws Exception {
        ConditionQuery expandedSearch = subject.getIds(ConditionQuery.create("heart"));

        assertThat(expandedSearch.size(), is(greaterThanOrEqualTo(49)));
        assertThat(expandedSearch, hasItem("heart"));
        assertThat(expandedSearch, hasItem("EFO_0003777"));
        assertThat(expandedSearch, hasItem(startsWith("Orphanet_")));
    }

    @Test
    public void cancerANDheart() throws Exception {
        ConditionQuery expandedSearch = subject.getIds(ConditionQuery.create("cancer AND heart"));

        assertThat(expandedSearch.size(), is((3)));
        assertThat(expandedSearch.terms().get(0), is("cancer"));
        assertThat(expandedSearch.terms().get(1), is("AND"));
        assertThat(expandedSearch.terms().get(2), is("heart"));

    }

    @Test
    public void cancerANDheartLowerCase() throws Exception {
        ConditionQuery expandedSearch = subject.getIds(ConditionQuery.create("cancer and heart"));

        assertThat(expandedSearch.size(), is(greaterThanOrEqualTo(3)));

        assertThat(expandedSearch.terms().get(0), is("cancer"));
        assertThat(expandedSearch.terms().get(1), is("and"));
        assertThat(expandedSearch.terms().get(2), is("heart"));

    }

    @Test
    public void quotes() {
        ConditionQuery expandedSearchWithoutQuotes = subject.getIds(ConditionQuery.create("anatomy basic component"));
        ConditionQuery expandedSearchWithQuotes = subject.getIds(ConditionQuery.create("\"anatomy basic component\""));

        assertThat(expandedSearchWithQuotes.equals(expandedSearchWithoutQuotes), is(true));
    }
}