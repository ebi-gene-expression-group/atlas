package uk.ac.ebi.atlas.search.diffanalytics;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.search.SemanticQuery;

import javax.inject.Inject;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:oracleContext.xml"})
public class DiffAnalyticsTSVWriterHeaderIT {

    @Inject
    private DiffAnalyticsTSVWriter subject;

    @Test
    public void headerTextShouldContainThreeRows(){
        SemanticQuery geneQuery = SemanticQuery.create();
        SemanticQuery conditionQuery = SemanticQuery.create("");
        String species = "";

        String[] headerRows = subject.getTsvFileMasthead(geneQuery, conditionQuery, species).split("\n");

        assertThat(headerRows.length, is(3));
    }

    @Test
    public void thirdHeaderLineShouldDescribeTimestamp(){
        SemanticQuery geneQuery = SemanticQuery.create();
        SemanticQuery conditionQuery = SemanticQuery.create("");
        String species = "";

        String[] headerRows = subject.getTsvFileMasthead(geneQuery, conditionQuery, species).split("\n");

        assertThat(headerRows[2], startsWith("# Timestamp: "));
        assertThat(headerRows[2].length(), greaterThan("# Timestamp: ".length()));
    }

    @Test
    public void queryDescriptionWithGeneQuery(){
        SemanticQuery geneQuery = SemanticQuery.create("TEST");
        SemanticQuery conditionQuery = SemanticQuery.create("");
        String species = "";

        String[] headerRows = subject.getTsvFileMasthead(geneQuery, conditionQuery, species).split("\n");

        assertThat(headerRows[1], is("# Query: Genes matching: 'TEST', specifically up/down differentially expressed, given the False Discovery Rate cutoff: 0.05"));
    }

    @Test
    public void queryDescriptionWithCondition(){
        SemanticQuery geneQuery = SemanticQuery.create();
        SemanticQuery conditionQuery = SemanticQuery.create("LIVER");
        String species = "";

        String[] headerRows = subject.getTsvFileMasthead(geneQuery, conditionQuery, species).split("\n");

        assertThat(headerRows[1], is("# Query: specifically up/down differentially expressed in condition matching 'LIVER', given the False Discovery Rate cutoff: 0.05"));
    }

    @Test
    public void queryDescriptionWithOrganism(){
        SemanticQuery geneQuery = SemanticQuery.create();
        SemanticQuery conditionQuery = SemanticQuery.create("");
        String species = "Mus musculus";

        String[] headerRows = subject.getTsvFileMasthead(geneQuery, conditionQuery, species).split("\n");

        assertThat(headerRows[1], is("# Query: specifically up/down differentially expressed in organism 'Mus musculus', given the False Discovery Rate cutoff: 0.05"));
    }

    @Test
    public void queryDescriptionWithOrganismAndCondition(){
        SemanticQuery geneQuery = SemanticQuery.create();
        SemanticQuery conditionQuery = SemanticQuery.create("LIVER");
        String species = "Mus musculus";

        String[] headerRows = subject.getTsvFileMasthead(geneQuery, conditionQuery, species).split("\n");

        assertThat(headerRows[1], is("# Query: specifically up/down differentially expressed in condition matching 'LIVER' and in organism 'Mus musculus', given the False Discovery Rate cutoff: 0.05"));
    }

    @Test
    public void queryDescriptionWithGeneQueryAndCondition(){
        SemanticQuery geneQuery = SemanticQuery.create("TEST");
        SemanticQuery conditionQuery = SemanticQuery.create("LIVER");
        String species = "";

        String[] headerRows = subject.getTsvFileMasthead(geneQuery, conditionQuery, species).split("\n");

        assertThat(headerRows[1], is("# Query: Genes matching: 'TEST', specifically up/down differentially expressed in condition matching 'LIVER', given the False Discovery Rate cutoff: 0.05"));
    }

    @Test
    public void firstHeaderLineShouldDescribeAtlasVersion(){
        SemanticQuery geneQuery = SemanticQuery.create("TEST");
        SemanticQuery conditionQuery = SemanticQuery.create("LIVER");
        String species = "";

        String[] headerRows = subject.getTsvFileMasthead(geneQuery, conditionQuery, species).split("\n");

        assertThat(headerRows[0], startsWith("# Expression Atlas version: "));
        assertThat(headerRows[0].length(), greaterThan("# Expression Atlas version: ".length()));
    }
}
