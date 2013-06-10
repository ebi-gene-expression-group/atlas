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

package uk.ac.ebi.atlas.model.cache.microarray;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.MAGETABInvestigation;
import uk.ac.ebi.atlas.commons.magetab.MageTabLimpopoUtils;
import uk.ac.ebi.atlas.model.ConfigurationTrader;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperimentConfiguration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MicroarrayExperimentLoaderTest {

    private static final String ACCESSION = "accession";
    private static final String ARRAYDESIGNS = "arraydesigns";
    private static final String SPECIES = "species";

    @Mock
    private ConfigurationTrader configurationTraderMock;

    @Mock
    private MicroarrayExperimentConfiguration experimentConfigurationMock;

    @Mock
    private Contrast contrastMock;

    @Mock
    private MageTabLimpopoUtils mageTabLimpopoUtilsMock;

    @Mock
    private MAGETABInvestigation investigationMock;

    @Mock
    private ExperimentDesign experimentDesignMock;

    private MicroarrayExperimentLoader subject;

    @Before
    public void setUp() throws Exception {
        subject = new MicroarrayExperimentLoader(configurationTraderMock, "{0}{1}");
        subject.setMageTabLimpopoUtils(mageTabLimpopoUtilsMock);
        when(configurationTraderMock.getMicroarrayExperimentConfiguration(ACCESSION)).thenReturn(experimentConfigurationMock);
        when(experimentConfigurationMock.getContrasts()).thenReturn(Sets.newHashSet(contrastMock));
        when(experimentConfigurationMock.getArrayDesignNames()).thenReturn(Sets.newTreeSet(Sets.newHashSet(ARRAYDESIGNS)));
        when(mageTabLimpopoUtilsMock.parseInvestigation(ACCESSION)).thenReturn(investigationMock);
        when(mageTabLimpopoUtilsMock.extractSpeciesFromSDRF(investigationMock)).thenReturn(Sets.newHashSet(SPECIES));
    }

    @Test
    public void testLoad() throws Exception {
        MicroarrayExperiment microarrayExperiment = subject.load(ACCESSION, "description", false, experimentDesignMock);
        assertThat(microarrayExperiment.getAccession(), is(ACCESSION));
        assertThat(microarrayExperiment.getArrayDesignAccessions(), hasItem(ARRAYDESIGNS));
        assertThat(microarrayExperiment.getSpecies(), hasItem(SPECIES));
        assertThat(microarrayExperiment.getExperimentDesign(), is(experimentDesignMock));
    }
}