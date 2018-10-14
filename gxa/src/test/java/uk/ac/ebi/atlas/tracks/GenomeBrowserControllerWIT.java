package uk.ac.ebi.atlas.tracks;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.ac.ebi.atlas.configuration.TestConfig;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.text.MessageFormat;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GenomeBrowserControllerWIT {
    @Inject
    private DataSource dataSource;

    private static final String URL_TEMPLATE =
            "/experiments/{1}/redirect/genome-browsers/?name={0}&geneId={2}&trackId={3}";

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeAll
    void populateDatabaseTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(new ClassPathResource("fixtures/experiment-fixture.sql"));
        populator.execute(dataSource);
    }

    @AfterAll
    void cleanDatabaseTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(new ClassPathResource("fixtures/experiment-delete.sql"));
        populator.execute(dataSource);
    }

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    void invalidGenomeBrowserForExperimentSpecies() throws Exception {
        this.mockMvc
                .perform(
                        get(MessageFormat.format(
                                URL_TEMPLATE, "ensemblgenomes", "E-MTAB-3827", "ENSG00000102970", "g13")))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("error-page"));
    }

    @Test
    void experimentNotFound() throws Exception {
        this.mockMvc
                .perform(get(MessageFormat.format(URL_TEMPLATE, "ensembl", "E-FOO-BAR", "ENSG00000102970", "g13")))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error-page"));
    }

    @Test
    void miRNAExperimentsDontHaveEnsembleIdsAndCantShowTheGenomeBrowser() throws Exception {
        this.mockMvc
                .perform(get(MessageFormat.format(URL_TEMPLATE, "ensembl", "E-TABM-713", "MIMAT0002177", "g1_g2")))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("error-page"));
    }

    @Test
    void proteomicsExperiment() throws Exception {
        this.mockMvc
                .perform(get(MessageFormat.format(URL_TEMPLATE, "ensembl", "E-PROT-1", "ENSG00000013297", "any")))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://www.ensembl.org/Homo_sapiens/Location/View?g=ENSG00000013297"))
                .andReturn();
    }

    @Test
    void baselinePlantExperimentGramene() throws Exception {
        MvcResult result =
                this.mockMvc.perform(
                        get(MessageFormat.format(URL_TEMPLATE, "gramene", "E-MTAB-4401", "BRADI3G11800", "g1")))
                        .andExpect(status().isFound())
                        .andReturn();

        String redirectedUrl = result.getResponse().getRedirectedUrl();

        assertThat(
                redirectedUrl,
                startsWith(
                        "http://ensembl.gramene.org/Brachypodium_distachyon/Location/View" +
                                "?g=BRADI3G11800;contigviewbottom=url:http"));
        assertThat(
                redirectedUrl,
                endsWith(
                        "/experiments-content/E-MTAB-4401/tracks/E-MTAB-4401.g1.genes.expressions.bedGraph;" +
                                "format=BEDGRAPH"));
    }

    @Test
    void baselinePlantExperimentEnsemblGenomes() throws Exception {
        MvcResult result =
                this.mockMvc.perform(
                        get(MessageFormat.format(URL_TEMPLATE, "ensemblgenomes", "E-MTAB-4401", "BRADI3G11800", "g1")))
                        .andExpect(status().isFound())
                        .andReturn();

        String redirectedUrl = result.getResponse().getRedirectedUrl();

        assertThat(
                redirectedUrl,
                startsWith(
                        "http://plants.ensembl.org/Brachypodium_distachyon/Location/View" +
                        "?g=BRADI3G11800;contigviewbottom=url:http"));
        assertThat(
                redirectedUrl,
                endsWith(
                        "/experiments-content/E-MTAB-4401/tracks/E-MTAB-4401.g1.genes.expressions.bedGraph;" +
                        "format=BEDGRAPH"));
    }

    @Test
    void differentialPlantExperimentGramene() throws Exception {
        MvcResult result =
                this.mockMvc.perform(
                        get(MessageFormat.format(URL_TEMPLATE, "gramene", "E-MTAB-5422", "Zm00001d010885", "g2_g1")))
                .andExpect(status().isFound())
                .andReturn();

        String redirectedUrl = result.getResponse().getRedirectedUrl();

        assertThat(
                redirectedUrl,
                startsWith(
                        "http://ensembl.gramene.org/Zea_mays/Location/View" +
                                "?g=Zm00001d010885;contigviewbottom=url:http"));
        assertThat(
                redirectedUrl,
                containsString(
                        "/experiments-content/E-MTAB-5422/tracks/" +
                                "E-MTAB-5422.g2_g1.genes.log2foldchange.bedGraph=tiling, url:http"));
        assertThat(
                redirectedUrl,
                endsWith(
                        "/experiments-content/E-MTAB-5422/tracks/" +
                                "E-MTAB-5422.g2_g1.genes.pval.bedGraph=pvalue;format=BEDGRAPH"));
    }

    @Test
    void differentialPlantExperimentEnsemblGenomes() throws Exception {
        MvcResult result =
                this.mockMvc.perform(
                        get(MessageFormat.format(
                                URL_TEMPLATE, "ensemblgenomes", "E-MTAB-1913", "OS04G0581000", "g9_g10")))
                        .andExpect(status().isFound())
                        .andReturn();

        String redirectedUrl = result.getResponse().getRedirectedUrl();

        assertThat(
                redirectedUrl,
                startsWith(
                        "http://plants.ensembl.org/Oryza_sativa/Location/View" +
                                "?g=OS04G0581000;contigviewbottom=url:http"));
        assertThat(
                redirectedUrl,
                containsString(
                        "/experiments-content/E-MTAB-1913/tracks/" +
                                "E-MTAB-1913.g9_g10.genes.log2foldchange.bedGraph=tiling, url:http"));
        assertThat(
                redirectedUrl,
                endsWith(
                        "/experiments-content/E-MTAB-1913/tracks/" +
                                "E-MTAB-1913.g9_g10.genes.pval.bedGraph=pvalue;format=BEDGRAPH"));
    }

    @Test
    void baselineWormExperimentWormbaseParaSite() throws Exception {
        MvcResult result =
                this.mockMvc.perform(
                        get(
                                MessageFormat.format(
                                        URL_TEMPLATE, "wormbaseparasite", "E-MTAB-451", "Smp_131110", "g4")))
                        .andExpect(status().isFound())
                        .andReturn();

        String redirectedUrl = result.getResponse().getRedirectedUrl();

        assertThat(
                redirectedUrl,
                startsWith(
                        "http://parasite.wormbase.org/Schistosoma_mansoni_prjea36577/Location/View" +
                                "?g=Smp_131110;contigviewbottom=url:http"));
        assertThat(
                redirectedUrl,
                endsWith(
                        "/experiments-content/E-MTAB-451/tracks/" +
                                "E-MTAB-451.g4.genes.expressions.bedGraph;format=BEDGRAPH"));
    }

    @Test
    void differentialWormExperimentWormbaseParaSite() throws Exception {
        MvcResult result =
                this.mockMvc.perform(
                        get(MessageFormat.format(
                                URL_TEMPLATE, "wormbaseparasite", "E-MEXP-1810", "WBGene00003778", "g3_g1")))
                        .andExpect(status().isFound())
                        .andReturn();

        String redirectedUrl = result.getResponse().getRedirectedUrl();

        assertThat(
                redirectedUrl,
                startsWith("http://parasite.wormbase.org/Caenorhabditis_elegans_prjna13758/Location/View" +
                        "?g=WBGene00003778;contigviewbottom=url:http"));
        assertThat(
                redirectedUrl,
                containsString("/experiments-content/E-MEXP-1810/tracks/" +
                        "E-MEXP-1810.g3_g1.genes.log2foldchange.bedGraph=tiling, url:http"));
        assertThat(
                redirectedUrl,
                endsWith("/experiments-content/E-MEXP-1810/tracks/" +
                        "E-MEXP-1810.g3_g1.genes.pval.bedGraph=pvalue;format=BEDGRAPH"));
    }

    @Test
    void baselineVertebrateExperimentEnsembl() throws Exception {
        MvcResult result =
                this.mockMvc.perform(
                        get(MessageFormat.format(URL_TEMPLATE, "ensembl", "E-MTAB-3827", "ENSG00000102970", "g13")))
                        .andExpect(status().isFound())
                        .andReturn();

        String redirectedUrl = result.getResponse().getRedirectedUrl();

        assertThat(
                redirectedUrl,
                startsWith(
                        "http://www.ensembl.org/Homo_sapiens/Location/View" +
                                "?g=ENSG00000102970;contigviewbottom=url:http"));
        assertThat(
                redirectedUrl,
                endsWith(
                        "/experiments-content/E-MTAB-3827/tracks/" +
                                "E-MTAB-3827.g13.genes.expressions.bedGraph;format=BEDGRAPH"));
    }

    @Test
    void differentialVertebrateExperimentEnsembl() throws Exception {
        MvcResult result =
                this.mockMvc.perform(
                        get(
                                MessageFormat.format(
                                        URL_TEMPLATE, "ensembl", "E-MEXP-1968", "ENSMUSG00000002688", "g1_g2")))
                        .andExpect(status().isFound())
                        .andReturn();

        String redirectedUrl = result.getResponse().getRedirectedUrl();

        assertThat(
                redirectedUrl,
                startsWith(
                        "http://www.ensembl.org/Mus_musculus/Location/View" +
                                "?g=ENSMUSG00000002688;contigviewbottom=url:http"));
        assertThat(
                redirectedUrl,
                containsString(
                        "/experiments-content/E-MEXP-1968/tracks/" +
                                "E-MEXP-1968.g1_g2.genes.log2foldchange.bedGraph=tiling, url:http"));
        assertThat(
                redirectedUrl,
                endsWith(
                        "/experiments-content/E-MEXP-1968/tracks/" +
                                "E-MEXP-1968.g1_g2.genes.pval.bedGraph=pvalue;format=BEDGRAPH"));
    }

    @Test
    void privateExperimentWithoutAccessKey() throws Exception {
        this.mockMvc.perform(
                get(MessageFormat.format(URL_TEMPLATE, "ensembl", "E-MTAB-3871", "ENSG00000043355", "g1")))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void privateExperimentWithAccessKey() throws Exception {
        MvcResult result =
                this.mockMvc.perform(
                        get(
                                MessageFormat.format(
                                        URL_TEMPLATE,
                                        "ensemblgenomes",
                                        "E-MTAB-4106",
                                        "SPAC869.01", "g5_g1") +
                                        "&accessKey=1483a23f-4853-4583-b10c-5ab333bf522f"))
                .andExpect(status().isFound())
                .andReturn();

        assertThat(
                result.getResponse().getRedirectedUrl(),
                containsString(
                        "/experiments-content/E-MTAB-4106/tracks/" +
                                "E-MTAB-4106.g5_g1.genes.log2foldchange.bedGraph" +
                                "?accessKey=1483a23f-4853-4583-b10c-5ab333bf522f"));
    }
}
