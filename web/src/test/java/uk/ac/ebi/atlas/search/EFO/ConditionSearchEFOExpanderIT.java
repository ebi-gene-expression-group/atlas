package uk.ac.ebi.atlas.search.EFO;

import org.apache.commons.lang.StringUtils;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class ConditionSearchEFOExpanderIT {


    @Inject
    ConditionSearchEFOExpander subject;

    @Test
    public void cancer() throws Exception {
        String expandedSearch = subject.fetchExpandedTermWithEFOChildren("cancer");

        String[] terms = StringUtils.split(expandedSearch);

        assertThat(terms, arrayWithSize(1024));
        assertThat(terms[0], is("cancer"));
        assertThat(terms[1], startsWith("EFO:"));

    }


    @Test
    public void heart() throws Exception {
        String expandedSearch = subject.fetchExpandedTermWithEFOChildren("heart");

        String[] terms = StringUtils.split(expandedSearch);
        assertThat(terms, arrayWithSize(90));
        assertThat(terms[0], is("heart"));
        assertThat(terms[1], is("UBERON_0000948"));

        assertThat(terms[2], startsWith("EFO:"));
        assertThat(terms[4], startsWith("BTO_"));
    }

    @Test
    public void cancerANDheart() throws Exception {
        String expandedSearch = subject.fetchExpandedTermWithEFOChildren("cancer AND heart");

        String[] terms = StringUtils.split(expandedSearch);
        assertThat(terms[0], is("cancer"));
        assertThat(terms[1], is("AND"));
        assertThat(terms[2], is("heart"));
        assertThat(terms, arrayWithSize(3));
    }

    @Test
    public void cancerANDheartLowerCase() throws Exception {
        String expandedSearch = subject.fetchExpandedTermWithEFOChildren("cancer and heart");

        String[] terms = StringUtils.split(expandedSearch);
        assertThat(terms[0], is("cancer"));
        assertThat(terms[1], is("and"));
        assertThat(terms[2], is("heart"));
        assertThat(terms, arrayWithSize(3));
    }


}