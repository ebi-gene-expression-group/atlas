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
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ExperimentDesignPageControllerIT {

    private static final String PATH_TEMPLATE = "web/src/test/resources/magetab/{0}/ExpDesign-{0}.tsv";
    private static final String EXPERIMENT_ACCESSION = "E-GEOD-38400";
    private static final Set<String> LIBRARIES = Sets.newHashSet("SRR504179", "SRR504180", "SRR504181", "SRR504182", "SRR504183", "SRR504184", "SRR504185", "SRR504186", "SRR504187", "SRR576327", "SRR576328", "SRR576329");

    private ExperimentDesignPageController subject;

    Model model = new BindingAwareModelMap();

    @Before
    public void setUp() throws Exception {
        subject = new ExperimentDesignPageController(PATH_TEMPLATE);
    }

    @Test
    public void testExtractExperimentDesign() {

        // given
        subject.extractExperimentDesign(model, EXPERIMENT_ACCESSION, LIBRARIES);

        Gson gson = new Gson();

        // then
        Map<String, Object> map = model.asMap();
        assertThat(((String) map.get("assayHeader")), is("Run"));

        Type type = new TypeToken<Map<String, Integer>>() {
        }.getType();
        Map<String, Integer> samples = gson.fromJson((String) map.get("samples"), type);
        assertThat(samples.get("Organism"), is(1));
    }
}