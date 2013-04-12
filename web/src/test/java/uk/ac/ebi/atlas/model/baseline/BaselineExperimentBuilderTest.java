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

package uk.ac.ebi.atlas.model.baseline;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.ExperimentType;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BaselineExperimentBuilderTest {

    public static final String FACTOR_TYPE = "type";
    public static final String FACTOR_VALUE = "value";
    public static final String SPECIES = "homo sapiens";
    public static final String SPECIES_NAME = "Homo sapiens";
    public static final String FACTOR_NAME = "name";
    public static final String EXPERIMENT_ACCESSION = "accession";
    public static final String DESCRIPTION = "description";
    public static final String DISPLAY_NAME = "displayName";
    public static final String RUN_ACCESSION1 = "run1";
    public static final String RUN_ACCESSION2 = "run2";
    BaselineExperimentBuilder subject;

    @Mock
    ExperimentRun runMock1;

    @Mock
    ExperimentRun runMock2;

    @Mock
    FactorGroup factorGroupMock;

    Factor factor;

    Map<String, String> nameMap = Maps.newHashMap();

    Map<String, String> speciesMap = Maps.newHashMap();

    @Before
    public void setUp() throws Exception {
        subject = new BaselineExperimentBuilder(new ExperimentalFactorsBuilder());

        factor = new Factor(FACTOR_TYPE, FACTOR_VALUE);

        nameMap.put(FACTOR_TYPE, FACTOR_NAME);

        speciesMap.put(SPECIES, SPECIES_NAME);

        when(runMock1.getFactorGroup()).thenReturn(factorGroupMock);
        when(runMock1.getAccession()).thenReturn(RUN_ACCESSION1);
        when(runMock2.getFactorGroup()).thenReturn(factorGroupMock);
        when(runMock2.getAccession()).thenReturn(RUN_ACCESSION2);
        when(factorGroupMock.getFactorByType(FACTOR_TYPE)).thenReturn(factor);
        when(factorGroupMock.iterator()).thenReturn(Sets.newHashSet(factor).iterator());
    }

    @Test
    public void testCreate() throws Exception {

        BaselineExperiment experiment = subject.forSpecies(Sets.newHashSet(SPECIES))
                .withAccession(EXPERIMENT_ACCESSION)
                .withDefaultFilterFactors(Sets.newHashSet(factor))
                .withDefaultQueryType(FACTOR_TYPE)
                .withDescription(DESCRIPTION)
                .withDisplayName(DISPLAY_NAME)
                .withExperimentRuns(Sets.newHashSet(runMock1, runMock2))
                .withExtraInfo(false)
                .withMenuFilterFactorTypes(Sets.newHashSet(FACTOR_TYPE))
                .withFactorNamesByType(nameMap)
                .withSpeciesMapping(speciesMap)
                .create();

        assertThat(experiment.getAccession(), is(EXPERIMENT_ACCESSION));
        assertThat(experiment.getExperimentRunAccessions(), hasItems(RUN_ACCESSION1, RUN_ACCESSION2));
        assertThat(experiment.getDefaultFilterFactors(), hasItem(factor));
        assertThat(experiment.getFactorGroup(RUN_ACCESSION1), is(factorGroupMock));
        assertThat(experiment.getDefaultQueryFactorType(), is(FACTOR_TYPE));
        assertThat(experiment.getDescription(), is(DESCRIPTION));
        assertThat(experiment.getDisplayName(), is(DISPLAY_NAME));
        assertThat(experiment.hasExtraInfoFile(), is(false));
        assertThat(experiment.getSpecies(), hasItem(SPECIES));
        assertThat(experiment.getFirstSpecies(), is(SPECIES));
        assertThat(experiment.getSpeciesMapping(), is(speciesMap));
        assertThat(experiment.getType(), is(ExperimentType.BASELINE));

    }
}