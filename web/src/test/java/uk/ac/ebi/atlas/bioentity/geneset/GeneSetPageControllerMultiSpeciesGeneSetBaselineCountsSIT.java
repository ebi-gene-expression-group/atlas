package uk.ac.ebi.atlas.bioentity.geneset;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BaselineBioEntitiesSearchResult;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntitiesPage;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GeneSetPageControllerMultiSpeciesGeneSetBaselineCountsSIT extends SeleniumFixture {

    private static final String GENESET = "IPR027417";

    private BioEntitiesPage subject;

    @Override
    protected void getStartingPage() {
        subject = new BioEntitiesPage(driver, "genesets/" + GENESET);
        subject.get();
    }

    @Test
    public void baselineResults() {
        assertThat(subject.getBaselinePaneHeaderResultsMessage(), is("30 results"));

        List<BaselineBioEntitiesSearchResult> baselineCounts = subject.getAllBaselineResults();

        assertThat(baselineCounts, hasSize(30));

        BaselineBioEntitiesSearchResult result1 = baselineCounts.get(10);
        assertThat(result1.getExperimentAccession(), is("E-PROT-1"));
        assertThat(result1.getExperimentName(), is("Human Proteome Map - adult"));
        assertThat(result1.getSpecies(), is("Homo sapiens"));
        assertThat(result1.getHref(), endsWith("E-PROT-1?_specific=on&queryFactorType=ORGANISM_PART&queryFactorValues=&geneQuery=IPR027417&exactMatch=true&serializedFilterFactors=DEVELOPMENTAL_STAGE:adult"));

        BaselineBioEntitiesSearchResult result2 = baselineCounts.get(11);
        assertThat(result2.getExperimentAccession(), is("E-PROT-1"));
        assertThat(result2.getExperimentName(), is("Human Proteome Map - fetus"));
        assertThat(result2.getSpecies(), is("Homo sapiens"));
        assertThat(result2.getHref(), endsWith("E-PROT-1?_specific=on&queryFactorType=ORGANISM_PART&queryFactorValues=&geneQuery=IPR027417&exactMatch=true&serializedFilterFactors=DEVELOPMENTAL_STAGE:fetus"));

        BaselineBioEntitiesSearchResult result3 = baselineCounts.get(12);
        assertThat(result3.getExperimentAccession(), is("E-MTAB-513"));
        assertThat(result3.getExperimentName(), is("Illumina Body Map"));
        assertThat(result3.getSpecies(), is("Homo sapiens"));
        assertThat(result3.getHref(), endsWith("E-MTAB-513?_specific=on&queryFactorType=ORGANISM_PART&queryFactorValues=&geneQuery=IPR027417&exactMatch=true"));

        BaselineBioEntitiesSearchResult result4 = baselineCounts.get(23);
        assertThat(result4.getExperimentAccession(), is("E-MTAB-2836"));
        assertThat(result4.getExperimentName(), is("Thirty two tissues"));
        assertThat(result4.getSpecies(), is("Homo sapiens"));
        assertThat(result4.getHref(), endsWith("E-MTAB-2836?_specific=on&queryFactorType=ORGANISM_PART&queryFactorValues=&geneQuery=IPR027417&exactMatch=true"));

        BaselineBioEntitiesSearchResult result5 = baselineCounts.get(24);
        assertThat(result5.getExperimentAccession(), is("E-MTAB-1733"));
        assertThat(result5.getExperimentName(), is("Twenty seven tissues"));
        assertThat(result5.getSpecies(), is("Homo sapiens"));
        assertThat(result5.getHref(), endsWith("E-MTAB-1733?_specific=on&queryFactorType=ORGANISM_PART&queryFactorValues=&geneQuery=IPR027417&exactMatch=true"));

        BaselineBioEntitiesSearchResult result6 = baselineCounts.get(25);
        assertThat(result6.getExperimentAccession(), is("E-GEOD-30352"));
        assertThat(result6.getExperimentName(), is("Vertebrate tissues"));
        assertThat(result6.getSpecies(), is("Homo sapiens"));
        assertThat(result6.getHref(), endsWith("E-GEOD-30352?_specific=on&queryFactorType=ORGANISM_PART&queryFactorValues=&geneQuery=IPR027417&exactMatch=true&serializedFilterFactors=ORGANISM:Homo%20sapiens"));

        BaselineBioEntitiesSearchResult result7 = baselineCounts.get(26);
        assertThat(result7.getExperimentAccession(), is("E-MTAB-599"));
        assertThat(result7.getExperimentName(), is("Six tissues"));
        assertThat(result7.getSpecies(), is("Mus musculus"));
        assertThat(result7.getHref(), endsWith("E-MTAB-599?_specific=on&queryFactorType=ORGANISM_PART&queryFactorValues=&geneQuery=IPR027417&exactMatch=true"));

        BaselineBioEntitiesSearchResult result8 = baselineCounts.get(27);
        assertThat(result8.getExperimentAccession(), is("E-GEOD-30352"));
        assertThat(result8.getExperimentName(), is("Vertebrate tissues"));
        assertThat(result8.getSpecies(), is("Mus musculus"));
        assertThat(result8.getHref(), endsWith("E-GEOD-30352?_specific=on&queryFactorType=ORGANISM_PART&queryFactorValues=&geneQuery=IPR027417&exactMatch=true&serializedFilterFactors=ORGANISM:Mus%20musculus"));

        BaselineBioEntitiesSearchResult result9 = baselineCounts.get(28);
        assertThat(result9.getExperimentAccession(), is("E-GEOD-30352"));
        assertThat(result9.getExperimentName(), is("Vertebrate tissues"));
        assertThat(result9.getSpecies(), is("Pan paniscus"));
        assertThat(result9.getHref(), endsWith("E-GEOD-30352?_specific=on&queryFactorType=ORGANISM_PART&queryFactorValues=&geneQuery=IPR027417&exactMatch=true&serializedFilterFactors=ORGANISM:Pan%20paniscus"));

        BaselineBioEntitiesSearchResult result10 = baselineCounts.get(29);
        assertThat(result10.getExperimentAccession(), is("E-GEOD-30352"));
        assertThat(result10.getExperimentName(), is("Vertebrate tissues"));
        assertThat(result10.getSpecies(), is("Pan troglodytes"));
        assertThat(result10.getHref(), endsWith("E-GEOD-30352?_specific=on&queryFactorType=ORGANISM_PART&queryFactorValues=&geneQuery=IPR027417&exactMatch=true&serializedFilterFactors=ORGANISM:Pan%20troglodytes"));
    }

}
