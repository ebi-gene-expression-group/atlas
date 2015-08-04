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

package uk.ac.ebi.atlas.model;

import com.google.common.collect.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;

import javax.inject.Inject;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class ExperimentConfigurationIT {

    private static final String ARRAY_DESIGN = "ARRAY_DESIGN";

    @Inject
    private ConfigurationTrader configurationTrader;

    private ExperimentConfiguration subject;

    @Test
    public void testGetContrast() throws Exception {
        subject = configurationTrader.getExperimentConfiguration("E-GEOD-22351");
        Contrast contrast = subject.getContrast("g1_g2", ARRAY_DESIGN);
        assertThat(contrast.getDisplayName(), is("genotype:\'expressing human TDP-43\' vs \'non transgenic\'"));
        assertThat(Sets.newHashSet(contrast.getReferenceAssayGroup()), containsInAnyOrder("SRR057596", "SRR057598", "SRR057597"));
        assertThat(Sets.newHashSet(contrast.getTestAssayGroup()), containsInAnyOrder("SRR057599", "SRR057600", "SRR057601"));
        assertThat(contrast.getArrayDesignAccession(), is(ARRAY_DESIGN));
    }

    @Test
    public void testGetContrastWithCommaInName() throws Exception {
        subject = configurationTrader.getExperimentConfiguration("TEST-MICROARRAY");
        Contrast contrast = subject.getContrast("g2_g1", ARRAY_DESIGN);
        assertThat(contrast.getDisplayName(), is("'Apical anaerobic' vs. 'Conventional'"));
    }

   @Test
    public void testGetAssayGroups() throws Exception {
        subject = configurationTrader.getExperimentConfiguration("E-MTAB-513");
        AssayGroups assayGroups = subject.getAssayGroups();

        assertThat(assayGroups.getAssayGroupIds(), hasSize(16));
        assertThat(assayGroups, hasItem(new AssayGroup("g7", "ERR030882")));

    }

    @Test
    public void testGetBaselineAssayAccessions(){
        subject = configurationTrader.getExperimentConfiguration("E-MTAB-513");
        Set<String> assayAccessions = subject.getAssayAccessions();

        assertThat(assayAccessions, hasSize(16));
        assertThat(assayAccessions, hasItems("ERR030876","ERR030887"));
    }

    @Test
    public void testGetDifferentialAssayAccessions(){
        subject = configurationTrader.getExperimentConfiguration("E-GEOD-22351");
        Set<String> assayAccessions = subject.getAssayAccessions();

        assertThat(assayAccessions, hasSize(6));
        assertThat(assayAccessions, hasItems("SRR057596","SRR057598","SRR057600"));
    }

    @Test
    public void testGetExperimentType() {
        subject = configurationTrader.getExperimentConfiguration("E-GEOD-22351");
        assertThat(subject.getExperimentType(), is(ExperimentType.RNASEQ_MRNA_DIFFERENTIAL));
    }

}
