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

package uk.ac.ebi.atlas.commands.download;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.commands.context.BaselineRequestContext;
import uk.ac.ebi.atlas.commands.context.BaselineRequestContextBuilder;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.cache.baseline.BaselineExperimentsCache;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.controllers.rest.BaselineQueryDownloadController;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class BaselineProfilesTSVWriterIT {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-513";
    private static String EXPECTED_HEADERS_TEXT =
            "# Expression Atlas version: 0.1.4-SNAPSHOT\n" +
            "# Query: Genes (matching): protein_coding, (specifically) expressed in any ORGANISM_PART above the expression level cutoff: 0.5 in experiment " + EXPERIMENT_ACCESSION + "\n" +
            "# Timestamp: ";

    /*/in factor value(s)*/

    @Inject
    private BaselineProfilesTSVWriter subject;

    @Inject
    private BaselineExperimentsCache baselineExperimentsCache;

    @Inject
    private BaselineRequestContextBuilder baselineRequestContextBuilder;

    private BaselineRequestContext baselineRequestContext;

    private BaselineRequestPreferences requestPreferences = new BaselineRequestPreferences();

    @PostConstruct
    private void initContext(){
        requestPreferences.setQueryFactorType("ORGANISM_PART");
        BaselineExperiment baselineExperiment = baselineExperimentsCache.getExperiment(EXPERIMENT_ACCESSION);
        baselineRequestContext = baselineRequestContextBuilder.forExperiment(baselineExperiment)
                                .withPreferences(requestPreferences).build();
    }


    @Test
    public void headersShouldHaveExpectedFormat(){

        requestPreferences.setExactMatch(false);

        String headersText = subject.buildHeaders();

        assertThat(headersText, startsWith(EXPECTED_HEADERS_TEXT));

    }

}
