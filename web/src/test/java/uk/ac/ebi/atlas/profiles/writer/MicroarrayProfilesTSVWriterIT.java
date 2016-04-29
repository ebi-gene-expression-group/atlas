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

package uk.ac.ebi.atlas.profiles.writer;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentpage.context.MicroarrayRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.MicroarrayRequestContextBuilder;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.trader.cache.MicroarrayExperimentsCache;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;

import javax.inject.Inject;

import java.util.regex.Pattern;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class MicroarrayProfilesTSVWriterIT {

    private static final String MICROARRAY_EXPERIMENT_ACCESSION = "E-GEOD-13316";

    @Inject
    private MicroarrayProfilesTSVWriter subject;

    @Inject
    private MicroarrayExperimentsCache microarrayExperimentsCache;

    @Inject
    private MicroarrayRequestContextBuilder microarrayRequestContextBuilder;

    private MicroarrayRequestPreferences requestPreferences = new MicroarrayRequestPreferences();

    private MicroarrayExperiment microarrayExperiment;

    @Before
    public void setUp() throws Exception {

        microarrayExperiment = microarrayExperimentsCache.getExperiment(MICROARRAY_EXPERIMENT_ACCESSION);

    }

    @Test
    public void secondHeaderLineShouldDescribeQueryAlsoWhenSelectingContrasts() {

        requestPreferences.setQueryFactorValues(Sets.newTreeSet(Sets.newHashSet("g2_g1")));

        MicroarrayRequestContext requestContext = microarrayRequestContextBuilder.forExperiment(microarrayExperiment)
                .withPreferences(requestPreferences).build();

        String[] headerRows = subject.getTsvFileMasthead(requestContext, false).split("\n");

        assertTrue("Get a vaguely correct line back",
                Pattern.matches("# Query: Genes.*differentially.*"+MICROARRAY_EXPERIMENT_ACCESSION,headerRows[1]));
        assertThat(headerRows[2], startsWith("# Timestamp: "));
    }


}
