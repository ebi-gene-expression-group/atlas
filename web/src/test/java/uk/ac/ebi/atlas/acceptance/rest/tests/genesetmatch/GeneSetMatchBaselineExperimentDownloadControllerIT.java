
package uk.ac.ebi.atlas.acceptance.rest.tests.genesetmatch;


import com.jayway.restassured.response.ResponseBody;
import org.junit.Ignore;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.EndPoint;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

@Ignore
public class GeneSetMatchBaselineExperimentDownloadControllerIT {

    private EndPoint subject = new EndPoint("/gxa/experiments/E-GEOD-30352.tsv?serializedFilterFactors=ORGANISM:Homo sapiens" +
            "&queryFactorType=ORGANISM_PART&heatmapMatrixSize=50&displayLevels=true&displayGeneDistribution=true" +
            "&geneQuery=\"Alpha-1-acid glycoprotein\" react_1619&_exactMatch=on&geneSetMatch=true" +
            "&_geneSetMatch=on&_queryFactorValues=1&specific=true&_specific=on&cutoff=0.5");


    @Test
    public void verifyFirstLine() {

        List<String> firstLine = subject.getRowValues(3);

        assertThat(firstLine,
                contains("Gene set", "cerebellum", "frontal lobe", "heart", "kidney", "liver", "prefrontal cortex", "temporal lobe", "testis")
        );

    }

    @Test
    public void verifyFirstProfile() {

        List<String> secondLine = subject.getRowValues(4);

        assertThat(secondLine,
                contains("\"Alpha-1-acid glycoprotein\"", "", "", "0.5", "0.5", "6451", "", "", "3" ));

    }

    @Test
    public void verifySecondProfile() {

        List<String> secondLine = subject.getRowValues(5);

        assertThat(secondLine,
                contains("react_1619", "4", "4", "12", "22", "17", "4", "6", "14" ));

    }

    @Test
    public void verifyLenghtOfDocument() {
        ResponseBody body = subject.getResponseBody();

        String[] lines = body.asString().split("\n");
        assertThat(lines.length, is(6));
    }

}