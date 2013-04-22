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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

public class RankedGeneTranscriptsIT {

    private static final String E_MTAB_513_URL_TEMPLATE = "/gxa/json/transcripts/E-MTAB-513";

    private static final String E_MTAB_599_URL_TEMPLATE = "/gxa/json/transcripts/E-MTAB-599";

    //This will be the new format when we will do better json marshalling
    //private static final String RESULT_1 = "{\"totalTranscriptCount\":1,\"transcriptExpressions\":{\"ENST00000390503\":7332.0}}";

    @Test
    public void testEMTAB513RankedGeneTranscriptsOneTranscript() {
        EndPoint subject = new EndPoint(E_MTAB_513_URL_TEMPLATE, "geneId=ENSG00000211855&factorType=ORGANISM_PART&factorValue=leukocyte&selectedFilterFactorsJson=[]");

        ResponseBody responseBody = responseAssertions(subject);

        assertThat(responseBody.asString(), is("[\"1\",\"{\\\"ENST00000390503\\\":100.0}\"]"));


    }

    @Test
    public void testEMTAB513RankedGeneTranscriptsTwoTranscripts() {
        EndPoint subject = new EndPoint(E_MTAB_513_URL_TEMPLATE, "geneId=ENSG00000134588&factorType=ORGANISM_PART&factorValue=testis");

        ResponseBody responseBody = responseAssertions(subject);

        assertThat(responseBody.asString(), is("[\"3\",\"{\\\"ENST00000417459\\\":63.6,\\\"ENST00000370832\\\":36.4}\"]"));


    }

    @Test
    public void testEMTAB513RankedGeneTranscriptsThreeTranscripts() {
        EndPoint subject = new EndPoint(E_MTAB_513_URL_TEMPLATE, "geneId=ENSG00000175305&factorType=ORGANISM_PART&factorValue=brain");

        ResponseBody responseBody = responseAssertions(subject);

        assertThat(responseBody.asString(), is("[\"8\",\"{\\\"ENST00000396133\\\":47.6,\\\"ENST00000520509\\\":28.6,\\\"ENST00000308108\\\":23.799999999999997}\"]"));


    }

    @Test
    public void testEMTAB513RankedGeneTranscriptsFourTranscripts() {
        EndPoint subject = new EndPoint(E_MTAB_513_URL_TEMPLATE, "geneId=ENSG00000112812&factorType=ORGANISM_PART&factorValue=lung");

        ResponseBody responseBody = responseAssertions(subject);

        assertThat(responseBody.asString(), is("[\"20\",\"{\\\"ENST00000377456\\\":43.5,\\\"ENST00000421826\\\":26.1,\\\"ENST00000492575\\\":26.1,\\\"OTHERS\\\":4.300000000000011}\"]"));


    }


    @Test
    public void testEMTAB599RankedGeneTranscriptsOneTranscript() {
        EndPoint subject = new EndPoint(E_MTAB_599_URL_TEMPLATE, "geneId=ENSMUSG00000064356&factorType=ORGANISM_PART&factorValue=liver");

        ResponseBody responseBody = responseAssertions(subject);

        assertThat(responseBody.asString(), is("[\"1\",\"{\\\"ENSMUST00000082407\\\":100.0}\"]"));


    }

    @Test
    public void testEMTAB599RankedGeneTranscriptsTwoTranscripts() {
        EndPoint subject = new EndPoint(E_MTAB_599_URL_TEMPLATE, "geneId=ENSMUSG00000021789&factorType=ORGANISM_PART&factorValue=lung");

        ResponseBody responseBody = responseAssertions(subject);

        assertThat(responseBody.asString(), is("[\"2\",\"{\\\"ENSMUST00000022314\\\":60.8,\\\"ENSMUST00000170719\\\":39.2}\"]"));


    }

    @Test
    public void testEMTAB599RankedGeneTranscriptsThreeTranscripts() {
        EndPoint subject = new EndPoint(E_MTAB_599_URL_TEMPLATE, "geneId=ENSMUSG00000078688&factorType=ORGANISM_PART&factorValue=liver");

        ResponseBody responseBody = responseAssertions(subject);

        assertThat(responseBody.asString(), is("[\"3\",\"{\\\"ENSMUST00000107490\\\":64.0,\\\"ENSMUST00000148940\\\":29.9,\\\"ENSMUST00000074700\\\":6.099999999999994}\"]"));


    }

    @Test
    public void testEMTAB599RankedGeneTranscriptsFourTranscripts() {
        EndPoint subject = new EndPoint(E_MTAB_599_URL_TEMPLATE, "geneId=ENSMUSG00000022212&factorType=ORGANISM_PART&factorValue=hippocampus");

        ResponseBody responseBody = responseAssertions(subject);

        assertThat(responseBody.asString(), is("[\"7\",\"{\\\"ENSMUST00000163767\\\":60.5,\\\"ENSMUST00000074225\\\":18.4,\\\"ENSMUST00000165262\\\":15.8,\\\"OTHERS\\\":5.299999999999997}\"]"));


    }

    private ResponseBody responseAssertions(EndPoint endPoint) {
        Response response = endPoint.getResponse();

        // http status code OK
        assertThat(response.getStatusCode(), is(200));

        // unicode encoded plain text
        assertThat(response.getContentType(), is("application/json"));

        return response.body();
    }

}
