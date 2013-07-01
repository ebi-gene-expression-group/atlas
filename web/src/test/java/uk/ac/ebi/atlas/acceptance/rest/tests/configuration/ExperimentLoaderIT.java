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

package uk.ac.ebi.atlas.acceptance.rest.tests.configuration;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.EndPoint;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ExperimentLoaderIT {

    @Test
    public void testListExperiments() {
        EndPoint endPoint = new EndPoint("/gxa/listExperiments");
        String result = endPoint.getResponseBody().asString();
        assertThat(result, containsString("E-MTAB-599"));
    }

    @Test
    public void testDeleteAndLoadExperiment() {
        EndPoint endPoint = new EndPoint("/gxa/deleteExperiment?accession=E-MTAB-599");
        String result = endPoint.getResponseBody().asString();
        assertThat(result, is("Experiment E-MTAB-599 deleted."));
        endPoint = new EndPoint("/gxa/listExperiments");
        result = endPoint.getResponseBody().asString();
        assertThat(result, not(containsString("E-MTAB-599")));

        endPoint = new EndPoint("/gxa/loadExperiment?accession=E-MTAB-599&type=BASELINE");
        result = endPoint.getResponseBody().asString();
        assertThat(result, is("Experiment E-MTAB-599 loaded."));
        endPoint = new EndPoint("/gxa/listExperiments");
        result = endPoint.getResponseBody().asString();
        assertThat(result, containsString("E-MTAB-599"));
    }

    @Test
    public void testDeleteNonExisting() {
        EndPoint endPoint = new EndPoint("/gxa/deleteExperiment?accession=E-MTAB-BLA");
        String result = endPoint.getResponseBody().asString();
        assertThat(result, is("Experiment E-MTAB-BLA not present."));
    }

    @Test
    public void testLoadExisting() {
        EndPoint endPoint = new EndPoint("/gxa/loadExperiment?accession=E-MTAB-599&type=BASELINE");
        String result = endPoint.getResponseBody().asString();
        assertThat(result, is("Experiment with experimentAccession E-MTAB-599 already exists."));
    }

    @Test
    public void testLoadNonExistingDirectory() {
        EndPoint endPoint = new EndPoint("/gxa/loadExperiment?accession=E-MTAB-BLA&type=BASELINE");
        String result = endPoint.getResponseBody().asString();
        assertThat(result, containsString("Required file can not be read"));
        endPoint = new EndPoint("/gxa/listExperiments");
        result = endPoint.getResponseBody().asString();
        assertThat(result, not(containsString("E-MTAB-BLA")));
    }

}