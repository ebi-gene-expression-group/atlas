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

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.junit.After;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.EndPoint;
import uk.ac.ebi.atlas.experimentloader.ExperimentDTO;

import javax.naming.directory.SearchResult;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ExperimentLoaderIT {

    @After
    public void cleanup() {
        EndPoint endPoint = new EndPoint("/gxa/updateExperiment?accession=E-MTAB-599&private=false");
        String result = endPoint.getResponseBody().asString();
        assertThat(result, containsString("E-MTAB-599"));
    }


    @Test
    public void testListExperiments() {
        EndPoint endPoint = new EndPoint("/gxa/listExperiments");
        String result = endPoint.getResponseBody().asString();
        assertThat(result, containsString("E-MTAB-599"));
    }

    @Test
    public void testListSelectedExperiments() {
        EndPoint endPoint = new EndPoint("/gxa/listExperiments?accession=E-MTAB-599,E-MTAB-513");
        List<ExperimentDTO> experiments = fromJson(endPoint);
        Collection<String> accessions = Collections2.transform(experiments, new Function<ExperimentDTO, String>(){
            @Override
            public String apply(uk.ac.ebi.atlas.experimentloader.ExperimentDTO experimentDTO) {
                return experimentDTO.getExperimentAccession();
            }
        });
        assertThat(experiments, hasSize(2));
        assertThat(accessions, hasItems("E-MTAB-599", "E-MTAB-513"));
    }

    @Test
    public void testDeleteAndLoadExperimentPublic() {
        EndPoint endPoint = new EndPoint("/gxa/deleteExperiment?accession=E-MTAB-599");
        String result = endPoint.getResponseBody().asString();
        assertThat(result, is("Experiment E-MTAB-599 successfully deleted."));
        endPoint = new EndPoint("/gxa/listExperiments");
        result = endPoint.getResponseBody().asString();
        assertThat(result, not(containsString("E-MTAB-599")));

        endPoint = new EndPoint("/gxa/loadExperiment?accession=E-MTAB-599&type=BASELINE&private=false");
        result = endPoint.getResponseBody().asString();
        assertThat(result, startsWith("Experiment E-MTAB-599 loaded, accessKey:"));
        endPoint = new EndPoint("/gxa/listExperiments?accession=E-MTAB-599");
        List<ExperimentDTO> experiments = fromJson(endPoint);
        assertThat(experiments.get(0).getExperimentAccession(), is("E-MTAB-599"));
        assertThat(experiments.get(0).isPrivate(), is(false));
    }

    @Test
    public void testDeleteAndLoadExperimentPrivate() {
        EndPoint endPoint = new EndPoint("/gxa/deleteExperiment?accession=E-MTAB-599");
        String result = endPoint.getResponseBody().asString();
        assertThat(result, is("Experiment E-MTAB-599 successfully deleted."));

        endPoint = new EndPoint("/gxa/loadExperiment?accession=E-MTAB-599&type=BASELINE&private=true");
        result = endPoint.getResponseBody().asString();
        assertThat(result, startsWith("Experiment E-MTAB-599 loaded, accessKey:"));
        endPoint = new EndPoint("/gxa/listExperiments?accession=E-MTAB-599");
        List<ExperimentDTO> experiments = fromJson(endPoint);
        assertThat(experiments.get(0).getExperimentAccession(), is("E-MTAB-599"));
        assertThat(experiments.get(0).isPrivate(), is(true));
    }

    private List<ExperimentDTO> fromJson(EndPoint endPoint){
        String experimentsString = endPoint.getResponseBody().asString();
        Type t = new TypeToken<List<ExperimentDTO>>() {}.getType();
        return new Gson().fromJson(experimentsString, t);
    }

    @Test
    public void testDeleteNonExisting() {
        EndPoint endPoint = new EndPoint("/gxa/deleteExperiment?accession=E-MTAB-BLA");
        String result = endPoint.getResponseBody().asString();
        assertThat(result, is("Experiment not found for experiment accession: E-MTAB-BLA"));
    }

    @Test
    public void loadShouldFailWhenExperimentHasAlreadyBeenImported() {
        EndPoint endPoint = new EndPoint("/gxa/loadExperiment?accession=E-MTAB-599&type=BASELINE");
        String result = endPoint.getResponseBody().asString();
        assertThat(result, is("Experiment with experimentAccession E-MTAB-599 has been already imported."));
    }

    @Test
    public void testLoadInvalidExperiment() {
        EndPoint endPoint = new EndPoint("/gxa/loadExperiment?accession=E-MTAB-BLA&type=BASELINE");
        String result = endPoint.getResponseBody().asString();
        assertThat(result, containsString("Required file can not be read"));
        endPoint = new EndPoint("/gxa/listExperiments");
        result = endPoint.getResponseBody().asString();
        assertThat(result, not(containsString("E-MTAB-BLA")));
    }

    @Test
    public void testUpdate(){
        EndPoint endPoint = new EndPoint("/gxa/updateExperiment?accession=E-MTAB-599&private=true");
        String result = endPoint.getResponseBody().asString();
        assertThat(result, containsString("E-MTAB-599"));

        endPoint = new EndPoint("/gxa/listExperiments?accession=E-MTAB-599");
        List<ExperimentDTO> experiments = fromJson(endPoint);
        assertThat(experiments.get(0).getExperimentAccession(), is("E-MTAB-599"));
        assertThat(experiments.get(0).isPrivate(), is(true));

        endPoint = new EndPoint("/gxa/updateExperiment?accession=E-MTAB-599&private=false");
        result = endPoint.getResponseBody().asString();
        assertThat(result, containsString("E-MTAB-599"));

        endPoint = new EndPoint("/gxa/listExperiments?accession=E-MTAB-599");
        experiments = fromJson(endPoint);
        assertThat(experiments.get(0).getExperimentAccession(), is("E-MTAB-599"));
        assertThat(experiments.get(0).isPrivate(), is(false));

    }

}