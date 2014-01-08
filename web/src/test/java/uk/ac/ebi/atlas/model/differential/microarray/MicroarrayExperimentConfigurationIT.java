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

package uk.ac.ebi.atlas.model.differential.microarray;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;
import uk.ac.ebi.atlas.model.differential.Contrast;

import javax.inject.Inject;
import java.util.Set;
import java.util.SortedSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class MicroarrayExperimentConfigurationIT {


    @Inject
    private ConfigurationTrader configurationTrader;

    private MicroarrayExperimentConfiguration subject;

    @Before
    public void setUp() throws Exception {
        subject = configurationTrader.getMicroarrayExperimentConfiguration("E-GEOD-3779");
    }

    @Test
    public void testGetArrayDesignNames() throws Exception {
        assertThat(subject.getArrayDesignNames(), contains("A-AFFY-23", "A-AFFY-24"));
    }

    @Test
    public void testGetContrasts() throws Exception {
        Set<Contrast> contrasts = subject.getContrasts();
        assertThat(contrasts.size(), is(2));
    }

    @Test
    public void testFirstContrast() throws Exception {
        SortedSet<Contrast> contrasts = Sets.newTreeSet(subject.getContrasts());
        Contrast first = contrasts.first();
        assertThat(first.getId(), is("g3_g4"));
        assertThat(first.getDisplayName(), is("genotype:'p107 -/-' vs 'wild type' on A-AFFY-23"));
        assertThat(first.getReferenceAssayGroup(), hasItems("9859-11, chip MOE430A", "9887-4, chip MOE430A", "9619-6, chip MOE430A"));
        assertThat(first.getTestAssayGroup(), hasItems("9447-4 -/-, chip MOE430A", "9887-6 p107 -/-, chip MOE430A", "9619-3 p107 -/-, chip MOE430A", "9859-12 p107 -/-, chip MOE430A"));
    }

    @Test
    public void testLastContrast() throws Exception {
        SortedSet<Contrast> contrasts = Sets.newTreeSet(subject.getContrasts());
        Contrast last = contrasts.last();
        assertThat(last.getId(), is("g2_g1"));
        assertThat(last.getDisplayName(), is("genotype:'p107 -/-' vs 'wild type' on A-AFFY-24"));
    }
}
