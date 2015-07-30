package uk.ac.ebi.atlas.experimentpage.differential;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.differential.Contrast;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class GseaPlotsBuilderTestIT {

    private static final AssayGroup G1 = new AssayGroup("g1", "C3", "C1", "C2");
    private static final AssayGroup G2 = new AssayGroup("g2", "WT3", "WT1", "WT2");
    private static final AssayGroup G3 = new AssayGroup("g3", "K1", "K2", "K3");
    private static final AssayGroup G4 = new AssayGroup("g4", "K1", "K2", "K3");
    private static final Contrast G1_G2 = new Contrast("g1_g2", "A-AFFY-35", G2, G1, "cycC");
    private static final Contrast G3_G4 = new Contrast("g3_g4", "A-AFFY-35", G4, G3, "cdk8");

    private static final ImmutableSortedSet<Contrast> CONTRASTS = ImmutableSortedSet.of(G1_G2, G3_G4);

    @Inject
    GseaPlotsBuilder subject; //= new GseaPlotsBuilder("condensedSdrf/{0}/{0}.{1}.{2}.gsea_class_non_dir_both.png");

    @Test
    public void createMap() {
        ImmutableMap<String, GseaPlots> gseaPlots = subject.createMapByContrastId("E-GEOD-11758", CONTRASTS);

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        String jsonGseaPlots = gson.toJson(gseaPlots);

        //System.out.println(jsonGseaPlots);

        assertThat(jsonGseaPlots, is("{\n" +
                "  \"g3_g4\": {\n" +
                "    \"go\": true,\n" +
                "    \"interpro\": true,\n" +
                "    \"reactome\": false\n" +
                "  },\n" +
                "  \"g1_g2\": {\n" +
                "    \"go\": true,\n" +
                "    \"interpro\": true,\n" +
                "    \"reactome\": true\n" +
                "  }\n" +
                "}"));

    }

}