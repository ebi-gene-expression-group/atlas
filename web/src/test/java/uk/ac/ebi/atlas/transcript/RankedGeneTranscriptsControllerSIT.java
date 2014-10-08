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

package uk.ac.ebi.atlas.transcript;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.EndPoint;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RankedGeneTranscriptsControllerSIT {

    private static final String E_MTAB_513_URL_TEMPLATE = "/gxa/json/transcripts/E-MTAB-513";

    private static final String E_MTAB_599_URL_TEMPLATE = "/gxa/json/transcripts/E-MTAB-599";

    @Test
    public void testEMTAB513RankedGeneTranscriptsOneTranscript() {
        EndPoint subject = new EndPoint(E_MTAB_513_URL_TEMPLATE, "geneId=ENSG00000211855&factorType=ORGANISM_PART&factorValue=leukocyte&serializedFilterFactors=");

        ResponseBody responseBody = responseAssertions(subject);

        assertThat(responseBody.asString(), is("{\"totalTranscriptsCount\":1,\"expressedTranscriptsCount\":1,\"transcriptExpressions\":{\"ENST00000390503\":7332.0}}"));


    }

    @Test
    public void transcriptInMultiFactorExperiment() {
        EndPoint subject = new EndPoint("/gxa/json/transcripts/E-GEOD-30352", "geneId=ENSMODG00000013804&factorType=ORGANISM_PART&factorValue=liver&serializedFilterFactors=ORGANISM:Monodelphis domestica");

        ResponseBody responseBody = responseAssertions(subject);

        assertThat(responseBody.asString(), is("{\"totalTranscriptsCount\":1,\"expressedTranscriptsCount\":1,\"transcriptExpressions\":{\"ENSMODT00000017575\":91.0}}"));

    }

    @Test
    public void testEMTAB513RankedGeneTranscriptsTwoTranscripts() {
        EndPoint subject = new EndPoint(E_MTAB_513_URL_TEMPLATE, "geneId=ENSG00000134588&factorType=ORGANISM_PART&factorValue=testis&serializedFilterFactors=");

        ResponseBody responseBody = responseAssertions(subject);

        assertThat(responseBody.asString(), is("{\"totalTranscriptsCount\":4,\"expressedTranscriptsCount\":2,\"transcriptExpressions\":{\"ENST00000417459\":0.7,\"ENST00000370832\":0.4}}"));


    }

    @Test
    public void testEMTAB599RankedGeneTranscriptsOneTranscript() {
        EndPoint subject = new EndPoint(E_MTAB_599_URL_TEMPLATE, "geneId=ENSMUSG00000064356&factorType=ORGANISM_PART&factorValue=liver");

        ResponseBody responseBody = responseAssertions(subject);

        assertThat(responseBody.asString(), is("{\"totalTranscriptsCount\":1,\"expressedTranscriptsCount\":1,\"transcriptExpressions\":{\"ENSMUST00000082407\":1151225.0}}"));


    }

    @Test
    public void testEMTAB599RankedGeneTranscriptsTwoTranscripts() {
        EndPoint subject = new EndPoint(E_MTAB_599_URL_TEMPLATE, "geneId=ENSMUSG00000021789&factorType=ORGANISM_PART&factorValue=lung");

        ResponseBody responseBody = responseAssertions(subject);

        assertThat(responseBody.asString(), is("{\"totalTranscriptsCount\":2,\"expressedTranscriptsCount\":2,\"transcriptExpressions\":{\"ENSMUST00000022314\":905.0,\"ENSMUST00000170719\":584.0}}"));


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
