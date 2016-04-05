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

package uk.ac.ebi.atlas.experimentpage.experimentdesign;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.trader.cache.RnaSeqDiffExperimentsCache;
import uk.ac.ebi.atlas.web.DifferentialDesignRequestPreferences;
import uk.ac.ebi.atlas.experimentpage.ExperimentDispatcher;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class DifferentialDesignPageControllerIT {

    @Value("#{configuration['experiment.experiment-design.path.template']}")
    private String experimentDesignTemplate;

    private static final String EXPERIMENT_ACCESSION = "E-GEOD-38400";
    private static final Set<String> LIBRARIES = Sets.newHashSet("SRR504179", "SRR504180", "SRR504181", "SRR504182", "SRR504183", "SRR504184", "SRR504185", "SRR504186", "SRR504187", "SRR576327", "SRR576328", "SRR576329");

    @Inject
    private DifferentialDesignPageController subject;

    @Inject
    private RnaSeqDiffExperimentsCache rnaSeqDiffExperimentsCache;

    private HttpServletRequest requestMock;
    private DifferentialDesignRequestPreferences preferencesMock;

    Model model = new BindingAwareModelMap();


    @Before
    public void initSubject() throws Exception {

        requestMock = mock(HttpServletRequest.class);
        preferencesMock = mock(DifferentialDesignRequestPreferences.class);
        DifferentialExperiment differentialExperiment = rnaSeqDiffExperimentsCache.getExperiment(EXPERIMENT_ACCESSION);
        when(requestMock.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE)).thenReturn(differentialExperiment);
        when(requestMock.getRequestURI()).thenReturn("/gxa/experiments/" + EXPERIMENT_ACCESSION + "/experiment-design");

    }

    @Test
    public void testExtractExperimentDesign() throws IOException {

        // given
        subject.showRnaSeqExperimentDesign(preferencesMock, model, requestMock);

        Gson gson = new Gson();

        // then
        Map<String, Object> map = model.asMap();
        assertThat(((String) map.get("assayHeaders")), is("[\"Run\"]"));

        // and
        String[] samples = gson.fromJson((String) map.get("sampleHeaders"), String[].class);
        assertThat(samples, arrayContaining("age", "developmental stage", "ecotype", "genotype", "organism"));
        String[] factors = gson.fromJson((String) map.get("factorHeaders"), String[].class);
        assertThat(factors, arrayContaining("genotype"));

        // and
        Type listStringArrayType = new TypeToken<List<String[]>>() {
        }.getType();
        List<String[]> data = gson.fromJson((String) map.get("tableData"), listStringArrayType);
        assertThat(data.get(0), hasItemInArray("SRR504179"));
        assertThat(data.get(data.size() - 1), hasItemInArray("SRR576329"));

        // and
        assertThat((String) map.get("runAccessions"), is(gson.toJson(LIBRARIES)));

        // and
        assertThat((String) map.get("experimentAccession"), is(EXPERIMENT_ACCESSION));
    }
}