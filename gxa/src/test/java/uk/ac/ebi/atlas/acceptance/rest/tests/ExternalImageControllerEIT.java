package uk.ac.ebi.atlas.acceptance.rest.tests;


import uk.ac.ebi.atlas.acceptance.rest.EndPoint;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import org.junit.Test;

import java.text.MessageFormat;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;


public class ExternalImageControllerEIT {

    private static final String EXTRA_INFO_EXPERIMENT_ACCESSION = "E-MTAB-2812";
    private static final String RNASEQ_EXPERIMENT_ACCESSION = "E-GEOD-54705";
    private static final String MICROARRAY_EXPERIMENT_ACCESSION = "E-GEOD-34130";

    private static final String EXTRA_INFO_IMAGE_URL = "/gxa/external-resources/".concat(EXTRA_INFO_EXPERIMENT_ACCESSION).concat("/extra-info.png");
    private static final String RNASEQ_MA_PLOT_IMAGE_URL_TEMPLATE = "/gxa/external-resources/".concat(RNASEQ_EXPERIMENT_ACCESSION).concat("/{0}/ma-plot.png");
    private static final String MICROARRAY_MA_PLOT_IMAGE_URL_TEMPLATE = "/gxa/external-resources/".concat(MICROARRAY_EXPERIMENT_ACCESSION).concat("/{0}/{1}/ma-plot.png");
    private static final String GSEA_PLOT_IMAGE_URL_TEMPLATE = "/gxa/external-resources/".concat(RNASEQ_EXPERIMENT_ACCESSION).concat("/{0}/gsea_{1}.png");

    @Test
    public void responseForExtraInfoImageShouldBeNonEmpty() {
        EndPoint subject = new EndPoint(EXTRA_INFO_IMAGE_URL);
        responseAssertions(subject);
    }

    @Test
    public void responseForGseaPlotsShouldBeNonEmpty() {
        String goUrl = MessageFormat.format(GSEA_PLOT_IMAGE_URL_TEMPLATE, "g6_g2", "go");
        responseAssertions(new EndPoint(goUrl));

        String interproUrl = MessageFormat.format(GSEA_PLOT_IMAGE_URL_TEMPLATE, "g6_g2", "interpro");
        responseAssertions(new EndPoint(interproUrl));

        String reactomeUrl = MessageFormat.format(GSEA_PLOT_IMAGE_URL_TEMPLATE, "g6_g2", "reactome");
        responseAssertions(new EndPoint(reactomeUrl));

    }

    @Test
    public void responseForMicroarrayMaPlotImageShouldBeNonEmpty() {
        String url = MessageFormat.format(MICROARRAY_MA_PLOT_IMAGE_URL_TEMPLATE, "A-AFFY-2", "g9_g3");
        responseAssertions(new EndPoint(url));

        url = MessageFormat.format(MICROARRAY_MA_PLOT_IMAGE_URL_TEMPLATE, "A-AFFY-2", "g2_g1");
        responseAssertions(new EndPoint(url));

    }

    @Test
    public void responseForDifferentContrastsShouldBeDifferent() {
        String url = MessageFormat.format(MICROARRAY_MA_PLOT_IMAGE_URL_TEMPLATE, "A-AFFY-2", "g2_g3");
        byte[] responseBodyForContrast1 = new EndPoint(url).getResponse().getBody().asByteArray();
        assertThat(responseBodyForContrast1, is(notNullValue()));

        url = MessageFormat.format(MICROARRAY_MA_PLOT_IMAGE_URL_TEMPLATE, "A-AFFY-2", "g2_g1");
        byte[] responseBodyForContrast2 = new EndPoint(url).getResponse().getBody().asByteArray();
        assertThat(responseBodyForContrast2, is(notNullValue()));

        assertThat(responseBodyForContrast1.length, is(not(responseBodyForContrast2.length)));

    }

    @Test
    public void responseForRnaSeqMaPlotImageShouldBeNonEmpty() {
        String url = MessageFormat.format(RNASEQ_MA_PLOT_IMAGE_URL_TEMPLATE, "g5_g2");
        responseAssertions(new EndPoint(url));

        url = MessageFormat.format(RNASEQ_MA_PLOT_IMAGE_URL_TEMPLATE, "g3_g2");
        responseAssertions(new EndPoint(url));

        url = MessageFormat.format(RNASEQ_MA_PLOT_IMAGE_URL_TEMPLATE, "g6_g2");
        responseAssertions(new EndPoint(url));
    }

    private ResponseBody responseAssertions(EndPoint endPoint) {
        Response response = endPoint.getResponse();

        // http status code OK
        assertThat(response.getStatusCode(), is(200));

        // unicode encoded plain text
        assertThat(response.getContentType(), is("image/png"));

        // filename of attachment should be ending in -query-results.tsv
        assertThat(response.asByteArray().length, is(greaterThan(1)));

        return response.body();
    }

}
