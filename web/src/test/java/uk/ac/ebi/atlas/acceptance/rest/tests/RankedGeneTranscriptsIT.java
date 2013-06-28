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

public class RankedGeneTranscriptsIT {

    private static final String E_MTAB_513_URL_TEMPLATE = "/gxa/json/transcripts/E-MTAB-513";

    private static final String E_MTAB_599_URL_TEMPLATE = "/gxa/json/transcripts/E-MTAB-599";

    private static final String E_GEOD_26284_URL_TEMPLATE = "/gxa/json/transcripts/E-GEOD-26284";


    @Test
    public void testEMTAB513RankedGeneTranscriptsOneTranscript() {
        EndPoint subject = new EndPoint(E_MTAB_513_URL_TEMPLATE, "geneId=ENSG00000211855&factorType=ORGANISM_PART&factorValue=leukocyte&selectedFilterFactorsJson=[]");

        ResponseBody responseBody = responseAssertions(subject);

        assertThat(responseBody.asString(), is("{\"totalTranscriptsCount\":1,\"expressedTranscriptsCount\":1,\"transcriptExpressions\":{\"ENST00000390503\":7332.0}}"));


    }

    @Test
    public void testEMTAB513RankedGeneTranscriptsTwoTranscripts() {
        EndPoint subject = new EndPoint(E_MTAB_513_URL_TEMPLATE, "geneId=ENSG00000134588&factorType=ORGANISM_PART&factorValue=testis&selectedFilterFactorsJson=[]");

        ResponseBody responseBody = responseAssertions(subject);

        assertThat(responseBody.asString(), is("{\"totalTranscriptsCount\":4,\"expressedTranscriptsCount\":2,\"transcriptExpressions\":{\"ENST00000417459\":0.7,\"ENST00000370832\":0.4}}"));


    }

    @Test
    public void testEMTAB513RankedGeneTranscriptsThreeTranscripts() {
        EndPoint subject = new EndPoint(E_MTAB_513_URL_TEMPLATE, "geneId=ENSG00000175305&factorType=ORGANISM_PART&factorValue=brain&selectedFilterFactorsJson=[]");

        ResponseBody responseBody = responseAssertions(subject);

        assertThat(responseBody.asString(), is("{\"totalTranscriptsCount\":8,\"expressedTranscriptsCount\":3,\"transcriptExpressions\":{\"ENST00000396133\":1.0,\"ENST00000520509\":0.6,\"ENST00000308108\":0.5}}"));


    }

    @Test
    public void testEMTAB513RankedGeneTranscriptsFourTranscripts() {
        EndPoint subject = new EndPoint(E_MTAB_513_URL_TEMPLATE, "geneId=ENSG00000112812&factorType=ORGANISM_PART&factorValue=lung&selectedFilterFactorsJson=[]");

        ResponseBody responseBody = responseAssertions(subject);

        assertThat(responseBody.asString(), is("{\"totalTranscriptsCount\":20,\"expressedTranscriptsCount\":4,\"transcriptExpressions\":{\"ENST00000377456\":1.0,\"ENST00000421826\":0.6,\"ENST00000492575\":0.6,\"Others\":0.1}}"));


    }


    @Test
    public void testEMTAB599RankedGeneTranscriptsOneTranscript() {
        EndPoint subject = new EndPoint(E_MTAB_599_URL_TEMPLATE, "geneId=ENSMUSG00000064356&factorType=ORGANISM_PART&factorValue=liver&selectedFilterFactorsJson=[]");

        ResponseBody responseBody = responseAssertions(subject);

        assertThat(responseBody.asString(), is("{\"totalTranscriptsCount\":1,\"expressedTranscriptsCount\":1,\"transcriptExpressions\":{\"ENSMUST00000082407\":1151225.0}}"));


    }

    @Test
    public void testEMTAB599RankedGeneTranscriptsTwoTranscripts() {
        EndPoint subject = new EndPoint(E_MTAB_599_URL_TEMPLATE, "geneId=ENSMUSG00000021789&factorType=ORGANISM_PART&factorValue=lung&selectedFilterFactorsJson=[]");

        ResponseBody responseBody = responseAssertions(subject);

        assertThat(responseBody.asString(), is("{\"totalTranscriptsCount\":2,\"expressedTranscriptsCount\":2,\"transcriptExpressions\":{\"ENSMUST00000022314\":905.0,\"ENSMUST00000170719\":584.0}}"));


    }

    @Test
    public void testEMTAB599RankedGeneTranscriptsThreeTranscripts() {
        EndPoint subject = new EndPoint(E_MTAB_599_URL_TEMPLATE, "geneId=ENSMUSG00000078688&factorType=ORGANISM_PART&factorValue=liver&selectedFilterFactorsJson=[]");

        ResponseBody responseBody = responseAssertions(subject);

        assertThat(responseBody.asString(), is("{\"totalTranscriptsCount\":3,\"expressedTranscriptsCount\":3,\"transcriptExpressions\":{\"ENSMUST00000107490\":199.0,\"ENSMUST00000148940\":93.0,\"ENSMUST00000074700\":19.0}}"));


    }

    @Test
    public void testEMTAB599RankedGeneTranscriptsFourTranscripts() {
        EndPoint subject = new EndPoint(E_MTAB_599_URL_TEMPLATE, "geneId=ENSMUSG00000022212&factorType=ORGANISM_PART&factorValue=hippocampus&selectedFilterFactorsJson=[]");

        ResponseBody responseBody = responseAssertions(subject);

        assertThat(responseBody.asString(), is("{\"totalTranscriptsCount\":7,\"expressedTranscriptsCount\":4,\"transcriptExpressions\":{\"ENSMUST00000163767\":111.0,\"ENSMUST00000074225\":41.0,\"ENSMUST00000165262\":35.0,\"Others\":14.0}}"));


    }

    @Test
    public void testEGEOD26284RankedGeneTranscripts() {
        EndPoint subject = new EndPoint(E_GEOD_26284_URL_TEMPLATE, "geneId=ENSG00000142185&factorType=CELL_LINE" +
                "&factorValue=CD34-positive mobilized cell cell line" +
                "&selectedFilterFactorsJson=[{\"type\":\"RNA\",\"value\":\"total RNA\"},{\"type\":\"CELLULAR_COMPONENT\",\"value\":\"whole cell\"}]");

        ResponseBody responseBody = responseAssertions(subject);

        assertThat(responseBody.asString(), is("{\"totalTranscriptsCount\":7,\"expressedTranscriptsCount\":4,\"transcriptExpressions\":{\"ENST00000490982\":0.5,\"ENST00000397928\":0.4,\"ENST00000397932\":0.3,\"Others\":0.1}}"));


        subject = new EndPoint(E_GEOD_26284_URL_TEMPLATE, "geneId=ENSG00000131652&factorType=CELL_LINE" +
                "&factorValue=IMR-90" +
                "&selectedFilterFactorsJson=[{\"type\":\"RNA\",\"value\":\"total RNA\"},{\"type\":\"CELLULAR_COMPONENT\",\"value\":\"whole cell\"}]");

        responseBody = responseAssertions(subject);

        assertThat(responseBody.asString(), is("{\"totalTranscriptsCount\":10,\"expressedTranscriptsCount\":2,\"transcriptExpressions\":{\"ENST00000326266\":5.0,\"ENST00000253952\":1.0}}"));

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
