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

package uk.ac.ebi.atlas.utils;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.ExperimentalFactors;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.cache.baseline.BaselineExperimentsCache;
import uk.ac.ebi.atlas.model.cache.differential.RnaSeqDiffExperimentsCache;
import uk.ac.ebi.atlas.model.cache.microarray.MicroarrayExperimentsCache;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.web.ApplicationProperties;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentInfoListBuilderTest {

    private static final String FACTOR_TYPE = "FACTOR_TYPE";
    private static final String FACTOR_NAME = "FACTOR_NAME";
    private static final String SPECIES = "SPECIES";
    private static final String ACCESSION = "ACCESSION";
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final String CONTRAST = "CONTRAST";
    private static final String ASSAY_1 = "ASSAY1";
    private static final String ASSAY_2 = "ASSAY2";
    private static final String ARRAY = "ARRAY";
    private static final String MICROARRAY = "MICROARRAY";
    private static final String DIFFERENTIAL = "DIFFERENTIAL";

    @Mock
    private ApplicationProperties applicationPropertiesMock;

    @Mock
    private BaselineExperimentsCache baselineExperimentsCacheMock;

    @Mock
    private RnaSeqDiffExperimentsCache rnaSeqDiffExperimentsCacheMock;

    @Mock
    private MicroarrayExperimentsCache microarrayExperimentsCacheMock;

    @Mock
    private ExperimentalFactors experimentalFactorsMock;

    @Mock
    private BaselineExperiment baselineExperimentMock;

    @Mock
    private DifferentialExperiment differentialExperimentMock;

    @Mock
    private MicroarrayExperiment microarrayExperimentMock;

    private ExperimentInfoListBuilder subject;

    @Before
    public void setUp() throws Exception {

        Factor factor = new Factor(FACTOR_TYPE, FACTOR_NAME);
        when(experimentalFactorsMock.getFactorName(FACTOR_TYPE)).thenReturn(FACTOR_NAME);
        when(experimentalFactorsMock.getAllFactors()).thenReturn(Sets.newTreeSet(Sets.newHashSet(factor)));

        when(baselineExperimentMock.getSpecies()).thenReturn(Sets.newHashSet(SPECIES));
        when(baselineExperimentMock.getAccession()).thenReturn(ACCESSION);
        when(baselineExperimentMock.getDescription()).thenReturn(DESCRIPTION);
        when(baselineExperimentMock.getType()).thenReturn(ExperimentType.BASELINE);

        when(applicationPropertiesMock.getBaselineExperimentsIdentifiers()).thenReturn(Sets.newHashSet(ACCESSION));
        when(applicationPropertiesMock.getDifferentialExperimentsIdentifiers()).thenReturn(Sets.newHashSet(DIFFERENTIAL));
        when(applicationPropertiesMock.getMicroarrayExperimentsIdentifiers()).thenReturn(Sets.newHashSet(MICROARRAY));

        when(baselineExperimentsCacheMock.getExperiment(ACCESSION)).thenReturn(baselineExperimentMock);
        when(rnaSeqDiffExperimentsCacheMock.getExperiment(DIFFERENTIAL)).thenReturn(differentialExperimentMock);
        when(microarrayExperimentsCacheMock.getExperiment(MICROARRAY)).thenReturn(microarrayExperimentMock);

        when(microarrayExperimentMock.getAccession()).thenReturn(MICROARRAY);
        when(microarrayExperimentMock.getAssayAccessions()).thenReturn(Sets.newHashSet(ASSAY_1, ASSAY_2));
        when(microarrayExperimentMock.getContrastIds()).thenReturn(Sets.newTreeSet(Sets.newHashSet(CONTRAST)));
        when(microarrayExperimentMock.getArrayDesignAccessions()).thenReturn(Sets.newTreeSet(Sets.newHashSet(ARRAY)));

        when(differentialExperimentMock.getAccession()).thenReturn(DIFFERENTIAL);
        when(differentialExperimentMock.getAssayAccessions()).thenReturn(Sets.newHashSet(ASSAY_1, ASSAY_2));
        when(differentialExperimentMock.getContrastIds()).thenReturn(Sets.newTreeSet(Sets.newHashSet(CONTRAST)));

        when(baselineExperimentMock.getExperimentalFactors()).thenReturn(experimentalFactorsMock);
        when(baselineExperimentMock.getExperimentRunAccessions()).thenReturn(Sets.newHashSet("RUN"));

        subject = new ExperimentInfoListBuilder(applicationPropertiesMock,
                baselineExperimentsCacheMock,
                rnaSeqDiffExperimentsCacheMock,
                microarrayExperimentsCacheMock);
    }

    @Test
    public void testBuild() throws Exception {
        List<ExperimentInfo> experimentInfos = subject.build();
        assertThat(experimentInfos.size(), is(3));
        assertThat(experimentInfos.get(0).getExperimentAccession(), is(ACCESSION));
        assertThat(experimentInfos.get(1).getExperimentAccession(), is(DIFFERENTIAL));
        assertThat(experimentInfos.get(2).getExperimentAccession(), is(MICROARRAY));
    }

    @Test
    public void testExtractMicrorarryExperiments() throws Exception {
        List<ExperimentInfo> experimentInfos = subject.extractMicrorarryExperiments();
        assertThat(experimentInfos.size(), is(1));
        ExperimentInfo experimentInfo = experimentInfos.get(0);
        assertThat(experimentInfo.getNumberOfAssays(), is(2));
        assertThat(experimentInfo.getNumberOfContrasts(), is(1));
        assertThat(experimentInfo.getArrayDesigns(), contains(ARRAY));
    }

    @Test
    public void testExtractRnaSeqDiffExperiments() throws Exception {
        List<ExperimentInfo> experimentInfos = subject.extractRnaSeqDiffExperiments();
        assertThat(experimentInfos.size(), is(1));
        ExperimentInfo experimentInfo = experimentInfos.get(0);
        assertThat(experimentInfo.getNumberOfAssays(), is(2));
        assertThat(experimentInfo.getNumberOfContrasts(), is(1));
    }

    @Test
    public void testExtractBaselineExperiments() throws Exception {
        List<ExperimentInfo> experimentInfos = subject.extractBaselineExperiments();
        assertThat(experimentInfos.size(), is(1));
        ExperimentInfo experimentInfo = experimentInfos.get(0);
        assertThat(experimentInfo.getExperimentalFactors(), contains(FACTOR_NAME));
        assertThat(experimentInfo.getNumberOfAssays(), is(1));
    }

    @Test
    public void testExtractBasicExperimentInfo() throws Exception {
        ExperimentInfo experimentInfo = subject.extractBasicExperimentInfo(baselineExperimentMock);
        assertThat(experimentInfo.getExperimentAccession(), is(ACCESSION));
        assertThat(experimentInfo.getExperimentDescription(), is(DESCRIPTION));
        assertThat(experimentInfo.getSpecies(), contains(SPECIES));
        assertThat(experimentInfo.getExperimentType(), is(ExperimentType.BASELINE));
    }

    @Test
    public void testExtractExperimentalFactors() throws Exception {
        assertThat(subject.extractExperimentalFactors(experimentalFactorsMock), contains(FACTOR_NAME));
    }
}