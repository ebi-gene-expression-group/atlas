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
import uk.ac.ebi.atlas.experimentloader.ExperimentDAO;
import uk.ac.ebi.atlas.experimentloader.ExperimentDTO;
import uk.ac.ebi.atlas.model.ConfigurationTrader;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperimentConfiguration;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MicroarrayExperimentsCacheLoaderTest {

    private static final String ACCESSION = "accession";
    private static final String ARRAYDESIGNS = "arraydesigns";
    private static final String SPECIES = "species";
    private static final String ACCESS_KEY = "AN_UUID";

    @Mock
    private ConfigurationTrader configurationTraderMock;

    @Mock
    private MicroarrayExperimentConfiguration experimentConfigurationMock;

    @Mock
    ExperimentDTO experimentDTOMock;

    @Mock
    private Contrast contrastMock;

    @Mock
    private MageTabLimpopoUtils mageTabLimpopoUtilsMock;

    @Mock
    private MAGETABInvestigation investigationMock;

    @Mock
    private ExperimentDAO experimentDAOMock;

    @Mock
    private ExperimentDesign experimentDesignMock;

    private MicroarrayExperimentsCacheLoader subject;


    @Before
    public void setUp() throws Exception {
        subject = new MicroarrayExperimentsCacheLoader(configurationTraderMock, "{0}{1}");
        subject.setMageTabLimpopoUtils(mageTabLimpopoUtilsMock);
        when(experimentDTOMock.getExperimentAccession()).thenReturn(ACCESSION);
        when(configurationTraderMock.getMicroarrayExperimentConfiguration(ACCESSION)).thenReturn(experimentConfigurationMock);
        when(experimentConfigurationMock.getContrasts()).thenReturn(Sets.newHashSet(contrastMock));
        when(experimentConfigurationMock.getArrayDesignNames()).thenReturn(Sets.newTreeSet(Sets.newHashSet(ARRAYDESIGNS)));
        when(mageTabLimpopoUtilsMock.parseInvestigation(ACCESSION)).thenReturn(investigationMock);
        when(mageTabLimpopoUtilsMock.extractSpeciesFromSDRF(investigationMock)).thenReturn(Sets.newHashSet(SPECIES));

        ExperimentDTO experimentDTO = new ExperimentDTO(ACCESSION, ExperimentType.MICROARRAY, new Date(), false, ACCESS_KEY);
        when(experimentDAOMock.findPublicExperiment(ACCESSION)).thenReturn(experimentDTO);
    }

    @Test
    public void testLoad() throws Exception {
        MicroarrayExperiment microarrayExperiment = subject.load(experimentDTOMock, "description", false, experimentDesignMock);
        assertThat(microarrayExperiment.getAccession(), is(ACCESSION));
        assertThat(microarrayExperiment.getArrayDesignAccessions(), hasItem(ARRAYDESIGNS));
        assertThat(microarrayExperiment.getSpecies(), hasItem(SPECIES));
        assertThat(microarrayExperiment.getExperimentDesign(), is(experimentDesignMock));
    }
}