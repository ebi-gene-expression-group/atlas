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

        System.out.println("\"" + Joiner.on("\", \"").join(expandedSearch) + "\"");

        assertThat(expandedSearch.terms(), hasSize(44));

        assertThat(expandedSearch, containsInAnyOrder("sex", "EFO_0004820", "Orphanet_753", "EFO_0003941", "Orphanet_752", "Orphanet_90783", "Orphanet_90787", "Orphanet_90786", "Orphanet_90776", "Orphanet_325697", "Orphanet_325109", "Orphanet_2282", "Orphanet_325061", "Orphanet_325546", "Orphanet_325345", "NCBITaxon_7130", "Orphanet_325706", "EFO_0001752", "Orphanet_325665", "Orphanet_2983", "EFO_0000695", "EFO_0004714", "EFO_0003955", "Orphanet_98086", "Orphanet_1422", "EFO_0001271", "Orphanet_90796", "CHEBI_50112", "Orphanet_325713", "Orphanet_98087", "Orphanet_325632", "Orphanet_393", "Orphanet_2975", "Orphanet_325690", "Orphanet_2973", "Orphanet_168558", "Orphanet_85112", "Orphanet_2138", "Orphanet_325511", "EFO_0005639", "EFO_0005638", "Orphanet_325638", "EFO_0004696", "Orphanet_325357"));
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