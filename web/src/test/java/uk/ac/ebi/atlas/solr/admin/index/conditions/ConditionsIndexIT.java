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

package uk.ac.ebi.atlas.solr.admin.index.conditions;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentloader.ExperimentDAO;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.cache.differential.DifferentialExperimentsCacheLoader;

import javax.inject.Inject;
import javax.sql.DataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml"})
public class ConditionsIndexIT {

    private static final String DIFFERENTIAL_ACCESION = "E-GEOD-22351";
    @Inject
    private ConditionsIndex subject;

    @Inject
    private DifferentialExperimentsCacheLoader cacheLoader;


    @Inject
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Inject
    private ExperimentDAO experimentDAO;

    @Before
    public void setUp() {
        experimentDAO.addExperiment(DIFFERENTIAL_ACCESION, ExperimentType.DIFFERENTIAL, false);
    }

    @After
    public void tearDown() throws Exception {
        experimentDAO.deleteExperiment(DIFFERENTIAL_ACCESION);
    }


    //TODO: Use embedded (in memory) Solr!
    @Test
    public void testIndex() throws Exception {

//        DifferentialExperiment experiment = cacheLoader.load(DIFFERENTIAL_ACCESION);
//
//        subject.updateConditions(experiment);
    }
}
