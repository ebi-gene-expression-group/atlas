package uk.ac.ebi.atlas.search.diffanalytics;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.web.OldGeneQuery;
import uk.ac.ebi.atlas.web.GeneQuerySearchRequestParameters;

import javax.inject.Inject;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class DiffAnalyticsTSVWriterHeaderIT {

    @Inject
    private DiffAnalyticsTSVWriter subject;

    @Test
    public void headerTextShouldContainThreeRows(){
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();

        String[] headerRows = subject.getTsvFileMasthead(requestParameters).split("\n");

        assertThat(headerRows.length, is(3));
    }

    @Test
    public void thirdHeaderLineShouldDescribeTimestamp(){
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();

        String[] headerRows = subject.getTsvFileMasthead(requestParameters).split("\n");

        assertThat(headerRows[2], startsWith("# Timestamp: "));
        assertThat(headerRows[2].length(), greaterThan("# Timestamp: ".length()));
    }

    @Test
    public void queryDescriptionWithGeneQuery(){
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery(OldGeneQuery.create("TEST"));

        String[] headerRows = subject.getTsvFileMasthead(requestParameters).split("\n");

        assertThat(headerRows[1], is("# Query: Genes matching: 'TEST', specifically up/down differentially expressed, given the False Discovery Rate cutoff: 0.05"));
    }

    @Test
    public void queryDescriptionWithCondition(){
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setCondition("LIVER");

        String[] headerRows = subject.getTsvFileMasthead(requestParameters).split("\n");

        assertThat(headerRows[1], is("# Query: specifically up/down differentially expressed in condition matching 'LIVER', given the False Discovery Rate cutoff: 0.05"));
    }

    @Test
    public void queryDescriptionWithOrganism(){
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setOrganism("Mus musculus");

        String[] headerRows = subject.getTsvFileMasthead(requestParameters).split("\n");

        assertThat(headerRows[1], is("# Query: specifically up/down differentially expressed in organism 'Mus musculus', given the False Discovery Rate cutoff: 0.05"));
    }

    @Test
    public void queryDescriptionWithOrganismAndCondition(){
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setCondition("LIVER");
        requestParameters.setOrganism("Mus musculus");

        String[] headerRows = subject.getTsvFileMasthead(requestParameters).split("\n");

        assertThat(headerRows[1], is("# Query: specifically up/down differentially expressed in condition matching 'LIVER' and in organism 'Mus musculus', given the False Discovery Rate cutoff: 0.05"));
    }

    @Test
    public void queryDescriptionWithGeneQueryAndCondition(){
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery(OldGeneQuery.create("TEST"));
        requestParameters.setCondition("LIVER");

        String[] headerRows = subject.getTsvFileMasthead(requestParameters).split("\n");

        assertThat(headerRows[1], is("# Query: Genes matching: 'TEST', specifically up/down differentially expressed in condition matching 'LIVER', given the False Discovery Rate cutoff: 0.05"));
    }

    @Test
    public void firstHeaderLineShouldDescribeAtlasVersion(){
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();

        String[] headerRows = subject.getTsvFileMasthead(requestParameters).split("\n");

        assertThat(headerRows[0], startsWith("# Expression Atlas version: "));
        assertThat(headerRows[0].length(), greaterThan("# Expression Atlas version: ".length()));
    }
}
