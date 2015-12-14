package uk.ac.ebi.atlas.search.EFO;

import com.google.common.base.Joiner;
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
import static org.hamcrest.Matchers.*;
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
        ConditionQuery expandedSearch = subject.addEfoAccessions(ConditionQuery.create("cancer"));

        assertThat(expandedSearch.size(), is(greaterThanOrEqualTo(45)));
        assertThat(expandedSearch, hasItem("cancer"));
        assertThat(expandedSearch, hasItem(Matchers.startsWith("EFO_")));
    }

    @Test
    public void anatomyBasicComponent() throws Exception {
        ConditionQuery expandedSearch = subject.addEfoAccessions(ConditionQuery.create("\"anatomy basic component\""));

        System.out.println("\"" + Joiner.on("\", \"").join(expandedSearch) + "\"");

        assertThat(expandedSearch.size(), is(2));
        assertThat(expandedSearch, hasItem("anatomy basic component"));
        assertThat(expandedSearch, hasItem("EFO_0000786"));
    }

    @Test
    public void inflammatoryBowelDisease() throws Exception {
        ConditionQuery expandedSearch = subject.addEfoAccessions(ConditionQuery.create("\"Inflammatory Bowel Disease\""));

        assertThat(expandedSearch.size(), is(greaterThanOrEqualTo(3)));
        assertThat(expandedSearch, hasItem("Inflammatory Bowel Disease"));
        assertThat(expandedSearch, hasItem(startsWith("Orphanet_238569"))); //Autosomal recessive early-onset inflammatory bowel disease
        assertThat(expandedSearch, hasItem(startsWith("EFO_0003767")));  //inflammatory bowel disease
    }

    @Test
    public void adipose() throws Exception {
        ConditionQuery expandedSearch = subject.addEfoAccessions(ConditionQuery.create("adipose"));

        assertThat(expandedSearch.size(), is(greaterThanOrEqualTo(1)));
        assertThat(expandedSearch, hasItem("adipose"));
        assertThat(expandedSearch, hasItem("UBERON_0001013")); //adipose tissue
    }


    @Test
    public void thymus() throws Exception {
        ConditionQuery expandedSearch = subject.addEfoAccessions(ConditionQuery.create("thymus"));

        assertThat(expandedSearch.size(), is(greaterThanOrEqualTo(1)));
        assertThat(expandedSearch, hasItem("thymus"));
        assertThat(expandedSearch, hasItem("UBERON_0002370")); //thymus
    }

    @Test
    public void adiposeOrThymus() throws Exception {
        ConditionQuery expandedSearch = subject.addEfoAccessions(ConditionQuery.create("adipose thymus"));

        assertThat(expandedSearch.size(), is(greaterThanOrEqualTo(1)));
        assertThat(expandedSearch, hasItem("adipose"));
        assertThat(expandedSearch, hasItem("UBERON_0001013")); //adipose tissue
        assertThat(expandedSearch, hasItem("thymus"));
        assertThat(expandedSearch, hasItem("UBERON_0002370")); //thymus
    }

    @Test
    public void sex() throws Exception {
        ConditionQuery expandedSearch = subject.addEfoAccessions(ConditionQuery.create("sex"));
        // System.out.println("\"" + Joiner.on("\", \"").join(expandedSearch) + "\"");
        assertThat(expandedSearch.terms().size(), is(greaterThanOrEqualTo(44)));
        assertThat(expandedSearch, hasItem("sex"));
        assertThat(expandedSearch, hasItem("EFO_0004820"));
        assertThat(expandedSearch, hasItem("Orphanet_753"));
        assertThat(expandedSearch, hasItem("EFO_0003941"));
        assertThat(expandedSearch, hasItem("Orphanet_325697"));
        assertThat(expandedSearch, hasItem("Orphanet_325706"));
        assertThat(expandedSearch, hasItem("Orphanet_325690"));
        assertThat(expandedSearch, hasItem("Orphanet_325357"));
    }

    @Test
    public void heart() throws Exception {
        ConditionQuery expandedSearch = subject.addEfoAccessions(ConditionQuery.create("heart"));

        assertThat(expandedSearch.size(), is(greaterThanOrEqualTo(49)));
        assertThat(expandedSearch, hasItem("heart"));
        assertThat(expandedSearch, hasItem("EFO_0003777"));
        assertThat(expandedSearch, hasItem(startsWith("Orphanet_")));
    }

    @Test
    public void cancerANDheart() throws Exception {
        ConditionQuery expandedSearch = subject.addEfoAccessions(ConditionQuery.create("cancer AND heart"));

        assertThat(expandedSearch.size(), is((3)));
        assertThat(expandedSearch.terms().get(0), is("cancer"));
        assertThat(expandedSearch.terms().get(1), is("AND"));
        assertThat(expandedSearch.terms().get(2), is("heart"));

    }

    @Test
    public void cancerANDheartLowerCase() throws Exception {
        ConditionQuery expandedSearch = subject.addEfoAccessions(ConditionQuery.create("cancer and heart"));

        assertThat(expandedSearch.size(), is(greaterThanOrEqualTo(3)));

        assertThat(expandedSearch.terms().get(0), is("cancer"));
        assertThat(expandedSearch.terms().get(1), is("and"));
        assertThat(expandedSearch.terms().get(2), is("heart"));

    }

}