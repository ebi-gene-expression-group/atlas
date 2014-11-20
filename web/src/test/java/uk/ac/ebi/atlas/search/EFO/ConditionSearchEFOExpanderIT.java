package uk.ac.ebi.atlas.search.EFO;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.Matchers.arrayWithSize;
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

        assertThat(terms, arrayWithSize(35));
        assertThat(terms[0], is("cancer"));
        assertThat(terms[1], startsWith("EFO_"));

    }

    @Test
    public void inflammatoryBowelDisease() throws Exception {
        String expandedSearch = subject.fetchExpandedTermWithEFOChildren("Inflammatory Bowel Disease");

        String[] terms = StringUtils.split(expandedSearch);

        assertThat(terms, arrayWithSize(5));
        assertThat(terms[0], is("Inflammatory"));
        assertThat(terms[1], startsWith("Bowel"));
        assertThat(terms[2], startsWith("Disease"));
        assertThat(terms[3], startsWith("Orphanet_"));

    }


    @Test
    public void heart() throws Exception {
        String expandedSearch = subject.fetchExpandedTermWithEFOChildren("heart");

        String[] terms = StringUtils.split(expandedSearch);
        assertThat(terms, arrayWithSize(49));
        assertThat(terms[0], is("heart"));
        assertThat(terms[1], is("EFO_0003777"));

        assertThat(terms[2], startsWith("Orphanet_"));
        assertThat(terms[4], startsWith("Orphanet_"));
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