package uk.ac.ebi.atlas.search.baseline;

import com.google.common.collect.Lists;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.commands.GenesNotFoundException;
import uk.ac.ebi.atlas.dao.BaselineExperimentResult;
import uk.ac.ebi.atlas.model.baseline.Factor;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class BaselineExpressionSearchServiceIT {

    @Inject
    BaselineExpressionSearchService subject;

    public static List<String> getExperimentAccessions(Set<BaselineExperimentResult> results) {
        List<String> names = Lists.newArrayList();
        for (BaselineExperimentResult result: results) {
            names.add(result.getExperimentAccession());
        }
        return names;
    }

    @Test
    public void geneQuery2IDsDifferentSpecies() throws GenesNotFoundException {
        String geneQuery = "ENSG00000161547 ENSMUSG00000030105";
        String condition = "";
        boolean isExactMatch = true;

        Set<BaselineExperimentResult> results = subject.query(geneQuery, condition, isExactMatch);
        List<String> experimentAccessions = getExperimentAccessions(results);

        assertThat(experimentAccessions, contains("E-MTAB-513", "E-MTAB-599", "E-MTAB-1733", "E-GEOD-30352"));
    }

    @Test
    public void geneQuery2IDsSameSpecies() throws GenesNotFoundException {
        String geneQuery = "ENSG00000161547 ENSG00000211855";
        String condition = "";
        boolean isExactMatch = true;

        Set<BaselineExperimentResult> results = subject.query(geneQuery, condition, isExactMatch);
        List<String> experimentAccessions = getExperimentAccessions(results);

        assertThat(experimentAccessions, contains("E-MTAB-513", "E-MTAB-1733"));
    }

    @Test
    public void geneQueryMiRNA() throws GenesNotFoundException {
        String geneQuery = "hsa-mir-636";
        String condition = "";
        boolean isExactMatch = true;

        Set<BaselineExperimentResult> results = subject.query(geneQuery, condition, isExactMatch);
        List<String> experimentAccessions = getExperimentAccessions(results);

        assertThat(experimentAccessions, contains("E-MTAB-513", "E-MTAB-1733"));
    }

    @Test
    public void geneQueryKeywordWithQuotesZincFinger() throws GenesNotFoundException {
        String geneQuery = "\"zinc finger\"";
        String condition = "";
        boolean isExactMatch = true;

        Set<BaselineExperimentResult> results = subject.query(geneQuery, condition, isExactMatch);
        List<String> experimentAccessions = getExperimentAccessions(results);

        assertThat(experimentAccessions, contains("E-MTAB-513", "E-MTAB-599", "E-MTAB-1733", "E-GEOD-30352"));
    }

    @Test
    public void geneQueryKeywordKinase() throws GenesNotFoundException {
        String geneQuery = "kinase";
        String condition = "";
        boolean isExactMatch = false;

        Set<BaselineExperimentResult> results = subject.query(geneQuery, condition, isExactMatch);
        List<String> experimentAccessions = getExperimentAccessions(results);

        assertThat(experimentAccessions, contains("E-GEOD-26284", "E-MTAB-513", "E-MTAB-599", "E-MTAB-1733", "E-GEOD-30352"));
    }

    @Test
    @Ignore //TODO: make this performant, currently too slow to run
    public void geneQueryKeywordProteinCoding() throws GenesNotFoundException {
        String geneQuery = "protein_coding";
        String condition = "";
        boolean isExactMatch = true;

        Set<BaselineExperimentResult> results = subject.query(geneQuery, condition, isExactMatch);
        List<String> experimentAccessions = getExperimentAccessions(results);

        assertThat(experimentAccessions, contains("E-MTAB-1733", "E-MTAB-599"));
    }

    @Test
    public void conditionPregnant() throws GenesNotFoundException {
        String geneQuery = "";
        String condition = "pregnant";
        boolean isExactMatch = true;

        Set<BaselineExperimentResult> results = subject.query(geneQuery, condition, isExactMatch);

        assertThat(results, hasSize(0));
    }

    @Test
    public void conditionSpecimen() throws GenesNotFoundException {
        String geneQuery = "";
        String condition = "frozen specimen";
        boolean isExactMatch = true;

        Set<BaselineExperimentResult> results = subject.query(geneQuery, condition, isExactMatch);
        List<String> experimentAccessions = getExperimentAccessions(results);

        BaselineExperimentResult eMtab1733 = results.iterator().next();

        assertThat(experimentAccessions, contains("E-MTAB-1733"));
        assertThat(eMtab1733.getSpecies(), is("Homo sapiens"));
    }

    @Test
    public void conditionsOR() throws GenesNotFoundException {
        String geneQuery = "";
        String condition = "adipose thymus";
        boolean isExactMatch = true;

        Set<BaselineExperimentResult> results = subject.query(geneQuery, condition, isExactMatch);
        List<String> experimentAccessions = getExperimentAccessions(results);

        assertThat(experimentAccessions, contains("E-GEOD-26284", "E-MTAB-513", "E-MTAB-599", "E-MTAB-1733"));

        BaselineExperimentResult[] resultsArray = results.toArray(new BaselineExperimentResult[results.size()]);

        BaselineExperimentResult eMtab599 = resultsArray[2];
        assertThat(eMtab599.getDefaultFactorsForSpecificAssayGroupsWithCondition(), contains(new Factor("ORGANISM_PART", "thymus", "UBERON:0002370")));

        BaselineExperimentResult eMtab1733 = resultsArray[3];
        assertThat(eMtab1733.getDefaultFactorsForSpecificAssayGroupsWithCondition(), contains(new Factor("ORGANISM_PART", "adipose tissue", "UBERON:0001013")));

    }

    @Test
    public void conditionsAND() throws GenesNotFoundException {
        String geneQuery = "";
        String condition = "heart AND frozen specimen";
        boolean isExactMatch = true;

        Set<BaselineExperimentResult> results = subject.query(geneQuery, condition, isExactMatch);
        List<String> experimentAccessions = getExperimentAccessions(results);

        BaselineExperimentResult eMtab1733 = results.iterator().next();

        assertThat(experimentAccessions, contains("E-MTAB-1733"));

        assertThat(eMtab1733.getDefaultFactorsForSpecificAssayGroupsWithCondition(), contains(new Factor("ORGANISM_PART", "heart", "UBERON:0000948")));
    }

    @Test
    public void geneQueryGeneIDAndConditionHeart() throws GenesNotFoundException {
        String geneQuery = "ENSG00000129170";                       //expressed in ovary
        String condition = "heart";
        boolean isExactMatch = true;

        Set<BaselineExperimentResult> results = subject.query(geneQuery, condition, isExactMatch);
        List<String> experimentAccessions = getExperimentAccessions(results);

        assertThat(experimentAccessions, contains("E-MTAB-599", "E-MTAB-1733"));

        BaselineExperimentResult[] resultsArray = results.toArray(new BaselineExperimentResult[results.size()]);

        BaselineExperimentResult eMtab599 = resultsArray[0];
        assertThat(eMtab599.getDefaultFactorsForSpecificAssayGroupsWithCondition(), contains(new Factor("ORGANISM_PART", "heart", "UBERON:0000948")));

        BaselineExperimentResult eMtab1733 = resultsArray[1];
        assertThat(eMtab1733.getDefaultFactorsForSpecificAssayGroupsWithCondition(), contains(new Factor("ORGANISM_PART", "heart", "UBERON:0000948")));
    }

    @Test
    public void conditionWildType() throws GenesNotFoundException {
        String geneQuery = "";
        String condition = "wild type";
        boolean isExactMatch = true;

        Set<BaselineExperimentResult> results = subject.query(geneQuery, condition, isExactMatch);
        List<String> experimentAccessions = getExperimentAccessions(results);

        BaselineExperimentResult eMtab513 = results.iterator().next();

        assertThat(experimentAccessions, contains("E-MTAB-599", "E-GEOD-30352"));

        assertThat(eMtab513.getDefaultFactorsForSpecificAssayGroupsWithCondition(), hasSize(0));
    }

}
