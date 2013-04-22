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

    //This will be the new format when we will do better json marshalling
    //private static final String RESULT_1 = "{\"totalTranscriptCount\":1,\"transcriptExpressions\":{\"ENST00000390503\":7332.0}}";

    @Test
    public void responseForExtraInfoImageShouldBeNonEmpty() {
        EndPoint subject = new EndPoint(E_MTAB_513_URL_TEMPLATE, "geneId=ENSG00000211855&factorType=ORGANISM_PART&factorValue=leukocyte&selectedFilterFactorsJson=[]");

        ResponseBody responseBody = responseAssertions(subject);

        assertThat(responseBody.asString(), is("[\"1\",\"{\\\"ENST00000390503\\\":100.0}\"]"));


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
