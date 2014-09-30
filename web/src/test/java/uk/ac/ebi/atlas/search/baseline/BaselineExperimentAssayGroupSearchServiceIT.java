package uk.ac.ebi.atlas.search.baseline;

import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentpage.context.GenesNotFoundException;
import uk.ac.ebi.atlas.model.baseline.Factor;

import javax.inject.Inject;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class BaselineExperimentAssayGroupSearchServiceIT {

    @Inject
    BaselineExperimentAssayGroupSearchService subject;

    public static List<String> getExperimentAccessions(Set<BaselineExperimentAssayGroup> results) {
        List<String> names = Lists.newArrayList();
        for (BaselineExperimentAssayGroup result: results) {
            names.add(result.getExperimentAccession());
        }
        return names;
    }

    @Test
    public void geneQuery2IDsDifferentSpecies() throws GenesNotFoundException {
        String geneQuery = "ENSG00000161547 ENSMUSG00000030105";
        String condition = "";
        String species = "";
        boolean isExactMatch = true;

        Set<BaselineExperimentAssayGroup> results = subject.query(geneQuery, condition, species, isExactMatch);
        List<String> experimentAccessions = getExperimentAccessions(results);

        assertThat(experimentAccessions, contains("E-MTAB-513", "E-MTAB-599", "E-MTAB-1733", "E-GEOD-30352"));
    }

    @Test
    public void geneQuery2IDsSameSpecies() throws GenesNotFoundException {
        String geneQuery = "ENSG00000161547 ENSG00000211855";
        String condition = "";
        String species = "";
        boolean isExactMatch = true;

        Set<BaselineExperimentAssayGroup> results = subject.query(geneQuery, condition, species, isExactMatch);
        List<String> experimentAccessions = getExperimentAccessions(results);

        assertThat(experimentAccessions, contains("E-MTAB-513", "E-MTAB-1733"));
    }

    @Test
    public void geneQueryMiRNA() throws GenesNotFoundException {
        String geneQuery = "hsa-mir-636";
        String condition = "";
        String species = "";
        boolean isExactMatch = true;

        Set<BaselineExperimentAssayGroup> results = subject.query(geneQuery, condition, species, isExactMatch);
        List<String> experimentAccessions = getExperimentAccessions(results);

        assertThat(experimentAccessions, contains("E-MTAB-513", "E-MTAB-1733"));
    }

    @Test
    public void geneQueryKeywordWithQuotesZincFinger() throws GenesNotFoundException {
        String geneQuery = "\"zinc finger\"";
        String condition = "";
        String species = "";
        boolean isExactMatch = true;

        Set<BaselineExperimentAssayGroup> results = subject.query(geneQuery, condition, species, isExactMatch);
        List<String> experimentAccessions = getExperimentAccessions(results);

        assertThat(experimentAccessions, contains("E-MTAB-513", "E-MTAB-599", "E-MTAB-1733", "E-GEOD-30352", "E-GEOD-30352", "E-GEOD-30352"));
    }

    @Test
    public void geneQueryKeywordKinase() throws GenesNotFoundException {
        String geneQuery = "kinase";
        String condition = "";
        String species = "";
        boolean isExactMatch = false;

        Set<BaselineExperimentAssayGroup> results = subject.query(geneQuery, condition, species, isExactMatch);
        List<String> experimentAccessions = getExperimentAccessions(results);

        assertThat(experimentAccessions, contains(  "E-GEOD-26284", "E-GEOD-26284", "E-GEOD-26284", "E-GEOD-26284", "E-GEOD-26284", "E-GEOD-26284", "E-GEOD-26284", "E-GEOD-26284", "E-GEOD-26284", "E-GEOD-26284",
                                                    "E-MTAB-513", "E-MTAB-599", "E-MTAB-1733",
                                                    "E-GEOD-30352", "E-GEOD-30352", "E-GEOD-30352", "E-GEOD-30352", "E-GEOD-30352", "E-GEOD-30352", "E-GEOD-30352", "E-GEOD-30352", "E-GEOD-30352"));
    }

    @Test
    public void geneQueryKeywordProteinCoding() throws GenesNotFoundException {
        String geneQuery = "protein_coding";
        String condition = "";
        String species = "";
        boolean isExactMatch = true;

        Set<BaselineExperimentAssayGroup> results = subject.query(geneQuery, condition, species, isExactMatch);
        List<String> experimentAccessions = getExperimentAccessions(results);

        assertThat(experimentAccessions, hasSize(22));
    }

    @Test
    public void conditionPregnant() throws GenesNotFoundException {
        String geneQuery = "";
        String condition = "pregnant";
        String species = "";
        boolean isExactMatch = true;

        Set<BaselineExperimentAssayGroup> results = subject.query(geneQuery, condition, species, isExactMatch);

        assertThat(results, hasSize(0));
    }

    @Test
    public void conditionSpecimen() throws GenesNotFoundException {
        String geneQuery = "";
        String condition = "frozen specimen";
        String species = "";
        boolean isExactMatch = true;

        Set<BaselineExperimentAssayGroup> results = subject.query(geneQuery, condition, species, isExactMatch);
        List<String> experimentAccessions = getExperimentAccessions(results);

        BaselineExperimentAssayGroup eMtab1733 = results.iterator().next();

        assertThat(experimentAccessions, contains("E-MTAB-1733"));
        assertThat(eMtab1733.getSpecies(), is("Homo sapiens"));
    }

    @Test
    public void conditionsOR() throws GenesNotFoundException {
        String geneQuery = "";
        String condition = "adipose thymus";
        String species = "";
        boolean isExactMatch = true;

        Set<BaselineExperimentAssayGroup> results = subject.query(geneQuery, condition, species, isExactMatch);
        List<String> experimentAccessions = getExperimentAccessions(results);

        assertThat(experimentAccessions, contains("E-GEOD-26284", "E-MTAB-513", "E-MTAB-599", "E-MTAB-1733"));

        BaselineExperimentAssayGroup[] resultsArray = results.toArray(new BaselineExperimentAssayGroup[results.size()]);

        BaselineExperimentAssayGroup eMtab599 = resultsArray[2];
        assertThat(eMtab599.getDefaultFactorsForSpecificAssayGroupsWithCondition(), contains(new Factor("ORGANISM_PART", "thymus", "UBERON:0002370")));

        BaselineExperimentAssayGroup eMtab1733 = resultsArray[3];
        assertThat(eMtab1733.getDefaultFactorsForSpecificAssayGroupsWithCondition(), contains(new Factor("ORGANISM_PART", "adipose tissue", "UBERON:0001013")));

    }

    @Test
    public void conditionsAND() throws GenesNotFoundException {
        String geneQuery = "";
        String condition = "heart AND frozen specimen";
        String species = "";
        boolean isExactMatch = true;

        Set<BaselineExperimentAssayGroup> results = subject.query(geneQuery, condition, species, isExactMatch);
        List<String> experimentAccessions = getExperimentAccessions(results);

        BaselineExperimentAssayGroup eMtab1733 = results.iterator().next();

        assertThat(experimentAccessions, contains("E-MTAB-1733"));

        assertThat(eMtab1733.getDefaultFactorsForSpecificAssayGroupsWithCondition(), contains(new Factor("ORGANISM_PART", "heart", "UBERON:0000948")));
    }

    @Test
    public void resultsInMoreThanOneSlice() throws GenesNotFoundException {
        String geneQuery = "AHI1";
        String condition = "";
        String species = "";
        boolean isExactMatch = true;

        Set<BaselineExperimentAssayGroup> results = subject.query(geneQuery, condition, species, isExactMatch);
        List<String> experimentAccessions = getExperimentAccessions(results);

        BaselineExperimentAssayGroup first = results.iterator().next();

        assertThat(experimentAccessions, contains("E-GEOD-26284", "E-GEOD-26284", "E-GEOD-26284", "E-GEOD-26284", "E-GEOD-26284", "E-GEOD-26284", "E-GEOD-26284", "E-GEOD-26284", "E-GEOD-26284", "E-GEOD-26284",
                                                    "E-MTAB-513", "E-MTAB-599", "E-MTAB-1733"));

        assertThat(first.getFilterFactors(), contains(new Factor("RNA", "long non-polyA RNA"), new Factor("CELLULAR_COMPONENT", "cytosol")));
    }

    @Test
    public void resultsInMoreThanOneSliceWithSelectedSpecie() throws GenesNotFoundException {
        String geneQuery = "AHI1";
        String condition = "";
        String species = "homo sapiens";
        boolean isExactMatch = true;

        Set<BaselineExperimentAssayGroup> results = subject.query(geneQuery, condition, species, isExactMatch);
        List<String> experimentAccessions = getExperimentAccessions(results);

        BaselineExperimentAssayGroup first = results.iterator().next();

        assertThat(experimentAccessions, contains("E-GEOD-26284", "E-GEOD-26284", "E-GEOD-26284", "E-GEOD-26284", "E-GEOD-26284", "E-GEOD-26284", "E-GEOD-26284", "E-GEOD-26284", "E-GEOD-26284", "E-GEOD-26284",
                "E-MTAB-513", "E-MTAB-1733"));

        assertThat(first.getFilterFactors(), contains(new Factor("RNA", "long non-polyA RNA"), new Factor("CELLULAR_COMPONENT", "cytosol")));
    }

    @Test
    public void buildResults_GEOD26284() {
        ImmutableSetMultimap<String, String> assayGroupsWithExpressionByExperiment = new ImmutableSetMultimap.Builder<String, String>()
                .putAll("E-GEOD-26284", "g13", "g7", "g3")
                .build();
        String species = "";
        Set<BaselineExperimentAssayGroup> baselineExperimentAssayGroups = subject.buildResults(assayGroupsWithExpressionByExperiment, true, species);

        assertThat(baselineExperimentAssayGroups, hasSize(2));

        Iterator<BaselineExperimentAssayGroup> iterator = baselineExperimentAssayGroups.iterator();
        BaselineExperimentAssayGroup searchResult1 = iterator.next();
        BaselineExperimentAssayGroup searchResult2 = iterator.next();

        assertThat(searchResult1.getFilterFactors(), containsInAnyOrder(new Factor("RNA", "long non-polyA RNA"), new Factor("CELLULAR_COMPONENT", "nucleus")));
        assertThat(searchResult1.getDefaultFactorsForSpecificAssayGroupsWithCondition(), containsInAnyOrder(new Factor("CELL_LINE", "HeLa-S3")));

        assertThat(searchResult2.getFilterFactors(), containsInAnyOrder(new Factor("RNA", "long polyA RNA"), new Factor("CELLULAR_COMPONENT", "nucleus")));
        assertThat(searchResult2.getDefaultFactorsForSpecificAssayGroupsWithCondition(), containsInAnyOrder(new Factor("CELL_LINE", "HeLa-S3"), new Factor("CELL_LINE", "IMR-90")));
    }

    @Test
    public void buildResults_MTAB513() {
        ImmutableSetMultimap<String, String> assayGroupsWithExpressionByExperiment = new ImmutableSetMultimap.Builder<String, String>()
                .putAll("E-MTAB-513", "g12", "g14", "g16", "g8", "g2", "g4", "g9")
                .build();
        String species = "";
        Set<BaselineExperimentAssayGroup> baselineExperimentAssayGroups = subject.buildResults(assayGroupsWithExpressionByExperiment, true, species);
        BaselineExperimentAssayGroup searchResult = baselineExperimentAssayGroups.iterator().next();

        assertThat(baselineExperimentAssayGroups, hasSize(1));
        assertThat(searchResult.getFilterFactors().size(), is(0));

    }

    @Test
    public void geneQueryGeneIDAndConditionHeart() throws GenesNotFoundException {
        String geneQuery = "ENSG00000129170";                       //expressed in ovary
        String condition = "heart";
        String species = "";
        boolean isExactMatch = true;

        Set<BaselineExperimentAssayGroup> results = subject.query(geneQuery, condition, species, isExactMatch);
        List<String> experimentAccessions = getExperimentAccessions(results);

        assertThat(experimentAccessions, contains("E-MTAB-599", "E-MTAB-1733"));

        BaselineExperimentAssayGroup[] resultsArray = results.toArray(new BaselineExperimentAssayGroup[results.size()]);

        BaselineExperimentAssayGroup eMtab599 = resultsArray[0];
        assertThat(eMtab599.getDefaultFactorsForSpecificAssayGroupsWithCondition(), contains(new Factor("ORGANISM_PART", "heart", "UBERON:0000948")));

        BaselineExperimentAssayGroup eMtab1733 = resultsArray[1];
        assertThat(eMtab1733.getDefaultFactorsForSpecificAssayGroupsWithCondition(), contains(new Factor("ORGANISM_PART", "heart", "UBERON:0000948")));
    }

    @Test
    public void geneQueryGeneIDAndConditionHeartWithSelectedSpecie() throws GenesNotFoundException {
        String geneQuery = "ENSG00000129170";                       //expressed in ovary
        String condition = "heart";
        String species = "mus musculus";
        boolean isExactMatch = true;

        Set<BaselineExperimentAssayGroup> results = subject.query(geneQuery, condition, species, isExactMatch);
        List<String> experimentAccessions = getExperimentAccessions(results);

        assertThat(experimentAccessions, contains("E-MTAB-599"));

        BaselineExperimentAssayGroup[] resultsArray = results.toArray(new BaselineExperimentAssayGroup[results.size()]);

        BaselineExperimentAssayGroup eMtab599 = resultsArray[0];
        assertThat(eMtab599.getDefaultFactorsForSpecificAssayGroupsWithCondition(), contains(new Factor("ORGANISM_PART", "heart", "UBERON:0000948")));

    }

    @Test
    public void conditionWildType() throws GenesNotFoundException {
        String geneQuery = "";
        String condition = "wild type";
        String species = "";
        boolean isExactMatch = true;

        Set<BaselineExperimentAssayGroup> results = subject.query(geneQuery, condition, species, isExactMatch);
        List<String> experimentAccessions = getExperimentAccessions(results);

        BaselineExperimentAssayGroup eMtab513 = results.iterator().next();

        assertThat(experimentAccessions, contains("E-MTAB-599", "E-GEOD-30352"));

        assertThat(eMtab513.getDefaultFactorsForSpecificAssayGroupsWithCondition(), hasSize(0));
    }

    @Test
    public void conditionRenalGlomerulusQueryWithNoResults() throws GenesNotFoundException {
        String geneQuery = "";
        String condition = "renal glomerulus";
        String species = "";
        boolean isExactMatch = true;

        Set<BaselineExperimentAssayGroup> results = subject.query(geneQuery, condition, species, isExactMatch);

        assertThat(results.size(), is(0));
    }

    @Test
    public void geneQueryASPMWithResults() throws GenesNotFoundException {
        String geneQuery = "ASPM";
        String condition = "";
        String species = "";
        boolean isExactMatch = true;

        Set<BaselineExperimentAssayGroup> results = subject.query(geneQuery, condition, species, isExactMatch);
        List<String> experimentAccessions = getExperimentAccessions(results);

        assertThat(experimentAccessions, contains("E-MTAB-599", "E-MTAB-1733"));

        assertThat(results.size(), is(2));
    }

    @Test
    public void geneQueryASPMAndConditionRenalGlomerulusWithNoResults() throws GenesNotFoundException {
        String geneQuery = "ASPM";
        String condition = "renal glomerulus";
        String species = "";
        boolean isExactMatch = true;

        Set<BaselineExperimentAssayGroup> results = subject.query(geneQuery, condition, species, isExactMatch);

        assertThat(results.size(), is(0));
    }

    @Test
    public void conditionAdultQueryWithResults() throws GenesNotFoundException {
        String geneQuery = "";
        String condition = "adult";
        String species = "";
        boolean isExactMatch = true;

        Set<BaselineExperimentAssayGroup> results = subject.query(geneQuery, condition, species, isExactMatch);
        List<String> experimentAccessions = getExperimentAccessions(results);

        assertThat(results.size(), is(4));

        assertThat(experimentAccessions, contains("E-MTAB-599", "E-MTAB-1733", "E-GEOD-30352", "E-GEOD-30352"));
    }

    @Test
    public void geneQueryASPMAndConditionAdultsWithResults() throws GenesNotFoundException {
        String geneQuery = "ASPM";
        String condition = "adult";
        String species = "";
        boolean isExactMatch = true;

        Set<BaselineExperimentAssayGroup> results = subject.query(geneQuery, condition, species, isExactMatch);
        List<String> experimentAccessions = getExperimentAccessions(results);

        assertThat(results.size(), is(2));
        assertThat(experimentAccessions, contains("E-MTAB-599", "E-MTAB-1733"));
    }

    @Test
    public void geneQueryASPMAndConditionAdultAndSpeciesHomoSapiensWithResults() throws GenesNotFoundException {
        String geneQuery = "ASPM";
        String condition = "adult";
        String species = "homo sapiens";
        boolean isExactMatch = true;

        Set<BaselineExperimentAssayGroup> results = subject.query(geneQuery, condition, species, isExactMatch);
        List<String> experimentAccessions = getExperimentAccessions(results);

        assertThat(results.size(), is(1));
        assertThat(experimentAccessions, contains("E-MTAB-1733"));
    }

}
