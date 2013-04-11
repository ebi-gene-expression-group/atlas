/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.acceptance.rest.tests;


import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.EndPoint;

import java.text.MessageFormat;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;


public class ExternalImageControllerIT {

    private static final String EXTRA_INFO_EXPERIMENT_ACCESSION = "E-GEOD-26284";
    private static final String RNASEQ_EXPERIMENT_ACCESSION = "E-GEOD-38400";
    private static final String MICROARRAY_EXPERIMENT_ACCESSION = "E-MTAB-1066";

    private static final String EXTRA_INFO_IMAGE_URL = "/gxa/external-resources/".concat(EXTRA_INFO_EXPERIMENT_ACCESSION).concat("/extra-info.png");

    private static final String RNASEQ_MA_PLOT_IMAGE_URL_TEMPLATE = "/gxa/external-resources/".concat(RNASEQ_EXPERIMENT_ACCESSION).concat("/{0}/ma-plot.png");

    private static final String MICROARRAY_MA_PLOT_IMAGE_URL_TEMPLATE = "/gxa/external-resources/".concat(MICROARRAY_EXPERIMENT_ACCESSION).concat("/{0}/{1}/ma-plot.png");


    @Test
    public void responseForExtraInfoImageShouldBeNonEmpty() {
        EndPoint subject = new EndPoint(EXTRA_INFO_IMAGE_URL);
        responseAssertions(subject);
    }

    @Test
    public void responseForMicroarrayMaPlotImageShouldBeNonEmpty() {
        String url = MessageFormat.format(MICROARRAY_MA_PLOT_IMAGE_URL_TEMPLATE, "A-AFFY-35", "g2_g3");
        responseAssertions(new EndPoint(url));

        url = MessageFormat.format(MICROARRAY_MA_PLOT_IMAGE_URL_TEMPLATE, "A-AFFY-35", "g2_g1");
        responseAssertions(new EndPoint(url));

    }

    @Test
    public void responseForDifferentContrastsShouldBeDifferent() {
        String url = MessageFormat.format(MICROARRAY_MA_PLOT_IMAGE_URL_TEMPLATE, "A-AFFY-35", "g2_g3");
        byte[] responseBodyForContrast1 = new EndPoint(url).getResponse().getBody().asByteArray();
        assertThat(responseBodyForContrast1, is(notNullValue()));

        url = MessageFormat.format(MICROARRAY_MA_PLOT_IMAGE_URL_TEMPLATE, "A-AFFY-35", "g2_g1");
        byte[] responseBodyForContrast2 = new EndPoint(url).getResponse().getBody().asByteArray();
        assertThat(responseBodyForContrast2, is(notNullValue()));

        assertThat(responseBodyForContrast1.length, is(not(responseBodyForContrast2.length)));

    }

    @Test
    public void responseForRnaSeqMaPlotImageShouldBeNonEmpty() {
        String url = MessageFormat.format(RNASEQ_MA_PLOT_IMAGE_URL_TEMPLATE, "g1_g4");
        responseAssertions(new EndPoint(url));

        url = MessageFormat.format(RNASEQ_MA_PLOT_IMAGE_URL_TEMPLATE, "g1_g2");
        responseAssertions(new EndPoint(url));

        url = MessageFormat.format(RNASEQ_MA_PLOT_IMAGE_URL_TEMPLATE, "g1_g3");
        responseAssertions(new EndPoint(url));
    }

    private ResponseBody responseAssertions(EndPoint endPoint) {
        Response response = endPoint.getResponse();

        // http status code OK
        assertThat(response.getStatusCode(), is(200));

        // unicode encoded plain text
        assertThat(response.getContentType(), is("image/png"));

        // filename of attachment should be ending in -gene-expression-profiles.tsv
        assertThat(response.asByteArray().length, is(greaterThan(1)));

        return response.body();
    }

}