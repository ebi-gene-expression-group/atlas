package uk.ac.ebi.atlas.commands;

import com.google.common.collect.Lists;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.dao.BaselineExperimentResult;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.web.GeneQuerySearchRequestParameters;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class BaselineBioentityCountsServiceIT {

    @Inject
    BaselineBioentityCountsService subject;


    public static List<String> getExperimentAccessions(Set<BaselineExperimentResult> results) {
        List<String> names = Lists.newArrayList();
        for (BaselineExperimentResult result: results) {
            names.add(result.getExperimentAccession());
        }
        return names;
    }


    @Test
    public void geneQuery2IDsDifferentSpecies() throws GenesNotFoundException {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery("ENSG00000161547 ENSMUSG00000030105");

        Set<BaselineExperimentResult> results = subject.query(requestParameters);
        List<String> experimentAccessions = getExperimentAccessions(results);

        assertThat(experimentAccessions, contains("E-MTAB-513", "E-MTAB-599"));
    }

    @Test
    public void geneQuery2IDsSameSpecies() throws GenesNotFoundException {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery("ENSG00000161547 ENSG00000211855");

        Set<BaselineExperimentResult> results = subject.query(requestParameters);
        List<String> experimentAccessions = getExperimentAccessions(results);

        assertThat(experimentAccessions, contains("E-MTAB-513"));
    }

    @Test
    public void geneQueryMiRNA() throws GenesNotFoundException {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery("hsa-mir-636");

        Set<BaselineExperimentResult> results = subject.query(requestParameters);
        List<String> experimentAccessions = getExperimentAccessions(results);

        assertThat(experimentAccessions, contains("E-MTAB-513"));
    }

    @Test
    public void geneQueryKeywordWithQuotesZincFinger() throws GenesNotFoundException {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery("\"zinc finger\"");

        Set<BaselineExperimentResult> results = subject.query(requestParameters);
        List<String> experimentAccessions = getExperimentAccessions(results);

        assertThat(experimentAccessions, contains("E-MTAB-513", "E-MTAB-599"));
    }

    @Test
    public void geneQueryKeywordKinase() throws GenesNotFoundException {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery("kinase");
        requestParameters.setExactMatch(false);

        Set<BaselineExperimentResult> results = subject.query(requestParameters);
        List<String> experimentAccessions = getExperimentAccessions(results);

        assertThat(experimentAccessions, contains("E-MTAB-513", "E-MTAB-599"));
    }

    @Test
    @Ignore //TODO: make this performant, currently too slow to run
    public void geneQueryKeywordProteinCoding() throws GenesNotFoundException {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery("protein_coding");

        Set<BaselineExperimentResult> results = subject.query(requestParameters);
        List<String> experimentAccessions = getExperimentAccessions(results);

        assertThat(experimentAccessions, contains("E-MTAB-513", "E-MTAB-599"));

    }

    @Test
    public void conditionPregnant() throws GenesNotFoundException {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setCondition("pregnant");

        Set<BaselineExperimentResult> results = subject.query(requestParameters);

        assertThat(results, hasSize(0));
    }

    List<Factor> femaleFactors = Lists.newArrayList(
            new Factor("ORGANISM_PART", "adipose"),
            new Factor("ORGANISM_PART", "brain"),
            new Factor("ORGANISM_PART", "breast"),
            new Factor("ORGANISM_PART", "colon"),
            new Factor("ORGANISM_PART", "kidney"),
            new Factor("ORGANISM_PART", "lymph node"),
            new Factor("ORGANISM_PART", "ovary"),
            new Factor("ORGANISM_PART", "thyroid"));

    @Test
    public void conditionFemale() throws GenesNotFoundException {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setCondition("female");

        Set<BaselineExperimentResult> results = subject.query(requestParameters);
        List<String> experimentAccessions = getExperimentAccessions(results);

        BaselineExperimentResult eMtab513 = results.iterator().next();

        assertThat(experimentAccessions, contains("E-MTAB-513"));

        assertThat(eMtab513.getDefaultFactorsForSpecificAssayGroupsWithCondition(), contains(femaleFactors.toArray(new Factor[femaleFactors.size()])));
    }

    @Test
    public void conditionsOR() throws GenesNotFoundException {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setCondition("adipose thymus");

        Set<BaselineExperimentResult> results = subject.query(requestParameters);
        List<String> experimentAccessions = getExperimentAccessions(results);

        assertThat(experimentAccessions, contains("E-MTAB-513", "E-MTAB-599"));

        BaselineExperimentResult[] resultsArray = results.toArray(new BaselineExperimentResult[results.size()]);

        BaselineExperimentResult eMtab513 = resultsArray[0];
        assertThat(eMtab513.getDefaultFactorsForSpecificAssayGroupsWithCondition(), contains(new Factor("ORGANISM_PART", "adipose")));

        BaselineExperimentResult eMtab599 = resultsArray[1];
        assertThat(eMtab599.getDefaultFactorsForSpecificAssayGroupsWithCondition(), contains(new Factor("ORGANISM_PART", "thymus")));
    }

    @Test
    public void conditionsAND() throws GenesNotFoundException {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setCondition("liver AND Caucasian");

        Set<BaselineExperimentResult> results = subject.query(requestParameters);
        List<String> experimentAccessions = getExperimentAccessions(results);

        BaselineExperimentResult eMtab513 = results.iterator().next();

        assertThat(experimentAccessions, contains("E-MTAB-513"));

        assertThat(eMtab513.getDefaultFactorsForSpecificAssayGroupsWithCondition(), contains(new Factor("ORGANISM_PART", "liver")));
    }

    @Test
    public void geneQueryGeneIDAndConditionFemale() throws GenesNotFoundException {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery("ENSG00000268458");                   //expressed in ovary
        requestParameters.setCondition("female");

        Set<BaselineExperimentResult> results = subject.query(requestParameters);
        List<String> experimentAccessions = getExperimentAccessions(results);

        BaselineExperimentResult eMtab513 = results.iterator().next();

        assertThat(experimentAccessions, contains("E-MTAB-513"));

        assertThat(eMtab513.getDefaultFactorsForSpecificAssayGroupsWithCondition(), contains(femaleFactors.toArray(new Factor[femaleFactors.size()])));
    }

    @Test
    public void conditionWildType() throws GenesNotFoundException {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setCondition("wild type");

        Set<BaselineExperimentResult> results = subject.query(requestParameters);
        List<String> experimentAccessions = getExperimentAccessions(results);

        BaselineExperimentResult eMtab513 = results.iterator().next();

        assertThat(experimentAccessions, contains("E-MTAB-599"));

        assertThat(eMtab513.getDefaultFactorsForSpecificAssayGroupsWithCondition(), hasSize(0));
    }

}
