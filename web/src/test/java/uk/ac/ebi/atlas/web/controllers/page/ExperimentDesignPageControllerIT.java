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
 * For further details of the Gene Expression Atlas project, including source code, * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.web.controllers.page;

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

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItemInArray;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ExperimentDesignPageControllerIT {

    @Value("#{configuration['experiment.experiment-design.path.template']}")
    private String experimentDesignTemplate;

    private static final String EXPERIMENT_ACCESSION = "E-GEOD-38400";
    private static final Set<String> LIBRARIES = Sets.newHashSet("SRR504179", "SRR504180", "SRR504181", "SRR504182", "SRR504183", "SRR504184", "SRR504185", "SRR504186", "SRR504187", "SRR576327", "SRR576328", "SRR576329");

    private ExperimentDesignPageController subject;

    Model model = new BindingAwareModelMap();

    @Before
    public void setUp() throws Exception {
        subject = new ExperimentDesignPageController(experimentDesignTemplate);
    }

    @Test
    public void testExtractExperimentDesign() {

        // given
        subject.extractExperimentDesign(model, EXPERIMENT_ACCESSION, LIBRARIES);

        Gson gson = new Gson();

        // then
        Map<String, Object> map = model.asMap();
        assertThat(((String) map.get("assayHeader")), is("Run"));

        // and
        Type stringIntegerMapType = new TypeToken<Map<String, Integer>>() {
        }.getType();
        Map<String, Integer> samples = gson.fromJson((String) map.get("samples"), stringIntegerMapType);
        assertThat(samples.get("ecotype"), is(1));
        assertThat(samples.get("Organism"), is(2));
        Map<String, Integer> factors = gson.fromJson((String) map.get("factors"), stringIntegerMapType);
        assertThat(factors.get("genotype"), is(3));

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