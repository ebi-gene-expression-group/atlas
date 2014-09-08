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
        assertThat(subject.getBaselinePaneHeaderResultsMessage(), is("7 results"));

        List<BaselineBioEntitiesSearchResult> baselineCounts = subject.getBaselineCounts();

        assertThat(baselineCounts, hasSize(8)); //including geneset description

        assertThat(baselineCounts.get(1).getExperimentAccession(), is("E-MTAB-513"));
        assertThat(baselineCounts.get(1).getExperimentName(), is("Illumina Body Map"));
        assertThat(baselineCounts.get(1).getSpecies(), is("Homo sapiens"));
        assertThat(baselineCounts.get(1).getHref(), endsWith("E-MTAB-513?_specific=on&queryFactorType=ORGANISM_PART&queryFactorValues=&geneQuery=IPR027417&exactMatch=true"));

        assertThat(baselineCounts.get(2).getExperimentAccession(), is("E-MTAB-599"));
        assertThat(baselineCounts.get(2).getExperimentName(), is("Six tissues"));
        assertThat(baselineCounts.get(2).getSpecies(), is("Mus musculus"));
        assertThat(baselineCounts.get(2).getHref(), endsWith("E-MTAB-599?_specific=on&queryFactorType=ORGANISM_PART&queryFactorValues=&geneQuery=IPR027417&exactMatch=true"));

        assertThat(baselineCounts.get(3).getExperimentAccession(), is("E-MTAB-1733"));
        assertThat(baselineCounts.get(3).getExperimentName(), is("Twenty seven tissues"));
        assertThat(baselineCounts.get(3).getSpecies(), is("Homo sapiens"));
        assertThat(baselineCounts.get(3).getHref(), endsWith("E-MTAB-1733?_specific=on&queryFactorType=ORGANISM_PART&queryFactorValues=&geneQuery=IPR027417&exactMatch=true"));

        assertThat(baselineCounts.get(4).getExperimentAccession(), is("E-GEOD-30352"));
        assertThat(baselineCounts.get(4).getExperimentName(), is("Vertebrate tissues - Homo sapiens"));
        assertThat(baselineCounts.get(4).getSpecies(), is("Multi-species"));
        assertThat(baselineCounts.get(4).getHref(), endsWith("E-GEOD-30352?_specific=on&queryFactorType=ORGANISM_PART&queryFactorValues=&geneQuery=IPR027417&exactMatch=true&serializedFilterFactors=ORGANISM:Homo%20sapiens"));

        assertThat(baselineCounts.get(5).getExperimentAccession(), is("E-GEOD-30352"));
        assertThat(baselineCounts.get(5).getExperimentName(), is("Vertebrate tissues - Mus musculus"));
        assertThat(baselineCounts.get(5).getSpecies(), is("Multi-species"));
        assertThat(baselineCounts.get(5).getHref(), endsWith("E-GEOD-30352?_specific=on&queryFactorType=ORGANISM_PART&queryFactorValues=&geneQuery=IPR027417&exactMatch=true&serializedFilterFactors=ORGANISM:Mus%20musculus"));

        assertThat(baselineCounts.get(6).getExperimentAccession(), is("E-GEOD-30352"));
        assertThat(baselineCounts.get(6).getExperimentName(), is("Vertebrate tissues - Pan paniscus"));
        assertThat(baselineCounts.get(6).getSpecies(), is("Multi-species"));
        assertThat(baselineCounts.get(6).getHref(), endsWith("E-GEOD-30352?_specific=on&queryFactorType=ORGANISM_PART&queryFactorValues=&geneQuery=IPR027417&exactMatch=true&serializedFilterFactors=ORGANISM:Pan%20paniscus"));

        assertThat(baselineCounts.get(7).getExperimentAccession(), is("E-GEOD-30352"));
        assertThat(baselineCounts.get(7).getExperimentName(), is("Vertebrate tissues - Pan troglodytes"));
        assertThat(baselineCounts.get(7).getSpecies(), is("Multi-species"));
        assertThat(baselineCounts.get(7).getHref(), endsWith("E-GEOD-30352?_specific=on&queryFactorType=ORGANISM_PART&queryFactorValues=&geneQuery=IPR027417&exactMatch=true&serializedFilterFactors=ORGANISM:Pan%20troglodytes"));
    }

}
