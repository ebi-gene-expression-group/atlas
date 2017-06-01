package uk.ac.ebi.atlas.tracks;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"/applicationContext.xml", "/solrContext.xml", "/dbContext.xml"})
public class TracksControllerWIT {
    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void invalidGenomeBrowserForExperimentSpecies() throws Exception {
        this.mockMvc.perform(get("/experiments-content/E-MTAB-3827/redirect/genomeBrowser/Ensembl Plants?geneId=ENSG00000102970&trackId=g13"))
                .andExpect(status().isBadRequest())
                .andExpect(forwardedUrl("error-page"));
    }

    @Test
    public void experimentNotFound() throws Exception {
        this.mockMvc.perform(get("/experiments-content/E-FOO-BAR/redirect/genomeBrowser/Ensembl?geneId=ENSG00000102970&trackId=g13"))
                .andExpect(status().isNotFound())
                .andExpect(forwardedUrl("error-page"));
    }

    @Test
    public void miRNAExperimentsDontHaveEnsembleIdsAndCantShowTheGenomeBrowser() throws Exception {
        this.mockMvc.perform(get("/experiments-content/E-GEOD-13316/redirect/genomeBrowser/Ensembl?geneId=ENSG00000102970&trackId=g13"))
                .andExpect(status().isBadRequest())
                .andExpect(forwardedUrl("error-page"));
    }

    @Test
    public void proteomicsExperiment() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/experiments-content/E-PROT-1/redirect/genomeBrowser/Ensembl?geneId=ENSG00000013297&trackId=g1"))
                .andExpect(status().isFound())
                .andReturn();

        String redirectedUrl = result.getResponse().getRedirectedUrl();

        assertThat(redirectedUrl, is("http://www.ensembl.org/Homo_sapiens/Location/View?g=ENSG00000013297"));
    }

    @Test
    public void baselinePlantExperimentGramene() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/experiments-content/E-GEOD-55482/redirect/genomeBrowser/Gramene?geneId=AT4G08874&trackId=g1"))
                .andExpect(status().isFound())
                .andReturn();

        String redirectedUrl = result.getResponse().getRedirectedUrl();

        assertThat(redirectedUrl, startsWith("http://ensembl.gramene.org/Arabidopsis_thaliana/Location/View?g=AT4G08874;contigviewbottom=url:http"));
        assertThat(redirectedUrl, endsWith("/experiments/E-GEOD-55482/tracks/E-GEOD-55482.g1.genes.expressions.bedGraph;format=BEDGRAPH"));
    }

    @Test
    public void baselinePlantExperimentEnsemblGenomes() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/experiments-content/E-GEOD-55482/redirect/genomeBrowser/Ensembl Plants?geneId=AT4G08874&trackId=g1"))
                .andExpect(status().isFound())
                .andReturn();

        String redirectedUrl = result.getResponse().getRedirectedUrl();

        assertThat(redirectedUrl, startsWith("http://plants.ensembl.org/Arabidopsis_thaliana/Location/View?g=AT4G08874;contigviewbottom=url:http"));
        assertThat(redirectedUrl, endsWith("/experiments/E-GEOD-55482/tracks/E-GEOD-55482.g1.genes.expressions.bedGraph;format=BEDGRAPH"));
    }

    @Test
    public void differentialPlantExperimentGramene() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/experiments-content/E-GEOD-57252/redirect/genomeBrowser/Gramene?geneId=GLYMA11G00580&trackId=g1_g6"))
                .andExpect(status().isFound())
                .andReturn();

        String redirectedUrl = result.getResponse().getRedirectedUrl();

        assertThat(redirectedUrl, startsWith("http://ensembl.gramene.org/Glycine_max/Location/View?g=GLYMA11G00580;contigviewbottom=url:http"));
        assertThat(redirectedUrl, containsString("/experiments/E-GEOD-57252/tracks/E-GEOD-57252.g1_g6.genes.log2foldchange.bedGraph=tiling,url:http"));
        assertThat(redirectedUrl, endsWith("/experiments/E-GEOD-57252/tracks/E-GEOD-57252.g1_g6.genes.pval.bedGraph=pvalue;format=BEDGRAPH"));
    }

    @Test
    public void differentialPlantExperimentEnsemblGenomes() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/experiments-content/E-GEOD-57252/redirect/genomeBrowser/Ensembl Plants?geneId=GLYMA11G00580&trackId=g1_g6"))
                .andExpect(status().isFound())
                .andReturn();

        String redirectedUrl = result.getResponse().getRedirectedUrl();

        assertThat(redirectedUrl, startsWith("http://plants.ensembl.org/Glycine_max/Location/View?g=GLYMA11G00580;contigviewbottom=url:http"));
        assertThat(redirectedUrl, containsString("/experiments/E-GEOD-57252/tracks/E-GEOD-57252.g1_g6.genes.log2foldchange.bedGraph=tiling,url:http"));
        assertThat(redirectedUrl, endsWith("/experiments/E-GEOD-57252/tracks/E-GEOD-57252.g1_g6.genes.pval.bedGraph=pvalue;format=BEDGRAPH"));
    }

    @Test
    public void baselineWormExperimentWormbaseParaSite() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/experiments-content/E-MTAB-2812/redirect/genomeBrowser/Wormbase ParaSite?geneId=WBGene00009892&trackId=g31"))
                .andExpect(status().isFound())
                .andReturn();

        String redirectedUrl = result.getResponse().getRedirectedUrl();

        assertThat(redirectedUrl, startsWith("http://parasite.wormbase.org/Caenorhabditis_elegans_prjna13758/Location/View?g=WBGene00009892;contigviewbottom=url:http"));
        assertThat(redirectedUrl, endsWith("/experiments/E-MTAB-2812/tracks/E-MTAB-2812.g31.genes.expressions.bedGraph;format=BEDGRAPH"));
    }

    @Test
    public void differentialWormExperimentWormbaseParaSite() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/experiments-content/E-MEXP-1810/redirect/genomeBrowser/Wormbase ParaSite?geneId=WBGene00003778&trackId=g3_g1"))
                .andExpect(status().isFound())
                .andReturn();

        String redirectedUrl = result.getResponse().getRedirectedUrl();

        assertThat(redirectedUrl, startsWith("http://parasite.wormbase.org/Caenorhabditis_elegans_prjna13758/Location/View?g=WBGene00003778;contigviewbottom=url:http"));
        assertThat(redirectedUrl, containsString("/experiments/E-MEXP-1810/tracks/E-MEXP-1810.g3_g1.genes.log2foldchange.bedGraph=tiling,url:http"));
        assertThat(redirectedUrl, endsWith("/experiments/E-MEXP-1810/tracks/E-MEXP-1810.g3_g1.genes.pval.bedGraph=pvalue;format=BEDGRAPH"));
    }

    @Test
    public void baselineVertebrateExperimentEnsembl() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/experiments-content/E-MTAB-3827/redirect/genomeBrowser/Ensembl?geneId=ENSG00000102970&trackId=g13"))
                .andExpect(status().isFound())
                .andReturn();

        String redirectedUrl = result.getResponse().getRedirectedUrl();

        assertThat(redirectedUrl, startsWith("http://www.ensembl.org/Homo_sapiens/Location/View?g=ENSG00000102970;contigviewbottom=url:http"));
        assertThat(redirectedUrl, endsWith("/experiments/E-MTAB-3827/tracks/E-MTAB-3827.g13.genes.expressions.bedGraph;format=BEDGRAPH"));
    }

    @Test
    public void differentialVertebrateExperimentEnsembl() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/experiments-content/E-GEOD-22351/redirect/genomeBrowser/Ensembl?geneId=ENSMUSG00000029816&trackId=g1_g2"))
                .andExpect(status().isFound())
                .andReturn();

        String redirectedUrl = result.getResponse().getRedirectedUrl();

        assertThat(redirectedUrl, startsWith("http://www.ensembl.org/Mus_musculus/Location/View?g=ENSMUSG00000029816;contigviewbottom=url:http"));
        assertThat(redirectedUrl, containsString("/experiments/E-GEOD-22351/tracks/E-GEOD-22351.g1_g2.genes.log2foldchange.bedGraph=tiling,url:http"));
        assertThat(redirectedUrl, endsWith("/experiments/E-GEOD-22351/tracks/E-GEOD-22351.g1_g2.genes.pval.bedGraph=pvalue;format=BEDGRAPH"));
    }

}