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

package uk.ac.ebi.atlas.experimentloader;

import au.com.bytecode.opencsv.CSVWriter;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.dao.ArrayDesignDao;
import uk.ac.ebi.atlas.experimentloader.experimentdesign.ExperimentDesignFileWriter;
import uk.ac.ebi.atlas.experimentloader.experimentdesign.ExperimentDesignFileWriterBuilder;
import uk.ac.ebi.atlas.experimentloader.experimentdesign.impl.MageTabParser;
import uk.ac.ebi.atlas.experimentloader.experimentdesign.impl.MageTabParserFactory;
import uk.ac.ebi.atlas.experimentloader.experimentdesign.impl.MageTabParserOutput;
import uk.ac.ebi.atlas.model.*;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperimentConfiguration;
import uk.ac.ebi.atlas.solr.admin.index.conditions.ConditionsIndex;
import uk.ac.ebi.atlas.solr.admin.index.conditions.ConditionsIndexTrader;
import uk.ac.ebi.atlas.transcript.TranscriptProfileDao;

import java.io.IOException;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentMetadataCRUDTest {

    private static final String EXPERIMENT_ACCESSION = "EXPERIMENT_ACCESSION";
    private static final String TEST_EXCEPTION = "TEST_EXCEPTION";
    private static final String ARRAY_DESIGN = "ARRAY_DESIGN";
    private static final String ACCESS_KEY = "AN_UUID";

    @Mock
    private ExperimentDesignFileWriter experimentDesignFileWriterMock;

    @Mock
    private CSVWriter csvWriterMock;

    @Mock
    private ArrayDesignDao arrayDesignDaoMock;

    @Mock
    private ConfigurationTrader configurationTraderMock;

    @Mock
    private MicroarrayExperimentConfiguration microarrayExperimentConfigurationMock;

    private ExperimentMetadataCRUD subject;

    @Mock
    private ExperimentDesignFileWriterBuilder experimentDesignFileWriterBuilderMock;

    @Mock
    private ExperimentDAO experimentDAOMock;

    @Mock
    private TranscriptProfileDao transcriptProfileDaoMock;

    @Mock
    private ExperimentTrader experimentTraderMock;

    @Mock
    DifferentialExperiment differentialExperimentMock;

    @Mock
    private ConditionsIndexTrader conditionsIndexTrader;

    @Mock
    private ConditionsIndex conditionsIndex;

    @Mock
    private ExperimentDTOBuilder exparimentDTOBuilderMock;

    @Mock
    private ExperimentDesign experimentDesignMock;

    @Mock
    private MageTabParserOutput mageTabParserOutput;

    @Mock
    private ExperimentConfiguration experimentConfiguration;

    @Mock
    private MageTabParserFactory mageTabParserFactory;

    @Mock
    private MageTabParser mageTabParser;

    @Before
    public void setUp() throws Exception {

        when(configurationTraderMock.getMicroarrayExperimentConfiguration(EXPERIMENT_ACCESSION)).thenReturn(microarrayExperimentConfigurationMock);
        when(configurationTraderMock.getExperimentConfiguration(EXPERIMENT_ACCESSION)).thenReturn(microarrayExperimentConfigurationMock);
        when(microarrayExperimentConfigurationMock.getArrayDesignNames()).thenReturn(Sets.newTreeSet(Sets.newHashSet(ARRAY_DESIGN)));
        when(experimentConfiguration.getExperimentType()).thenReturn(ExperimentType.RNASEQ_MRNA_BASELINE);

        given(experimentDesignFileWriterBuilderMock.forExperimentAccession(EXPERIMENT_ACCESSION)).willReturn(experimentDesignFileWriterBuilderMock);
        given(experimentDesignFileWriterBuilderMock.withExperimentType(ExperimentType.RNASEQ_MRNA_BASELINE)).willReturn(experimentDesignFileWriterBuilderMock);
        given(experimentDesignFileWriterBuilderMock.withExperimentType(ExperimentType.RNASEQ_MRNA_DIFFERENTIAL)).willReturn(experimentDesignFileWriterBuilderMock);
        given(experimentDesignFileWriterBuilderMock.build()).willReturn(experimentDesignFileWriterMock);

        given(mageTabParserFactory.create(Mockito.any(ExperimentType.class))).willReturn(mageTabParser);
        given(mageTabParser.parse(EXPERIMENT_ACCESSION)).willReturn(mageTabParserOutput);
        given(mageTabParserOutput.getExperimentDesign()).willReturn(experimentDesignMock);

        given(conditionsIndexTrader.getIndex(any(Experiment.class))).willReturn(conditionsIndex);
        given(experimentTraderMock.getPublicExperiment(EXPERIMENT_ACCESSION)).willReturn(differentialExperimentMock);

        given(exparimentDTOBuilderMock.forExperimentAccession(EXPERIMENT_ACCESSION)).willReturn(exparimentDTOBuilderMock);
        given(exparimentDTOBuilderMock.withExperimentType(ExperimentType.RNASEQ_MRNA_BASELINE)).willReturn(exparimentDTOBuilderMock);
        given(exparimentDTOBuilderMock.withPrivate(false)).willReturn(exparimentDTOBuilderMock);
        given(exparimentDTOBuilderMock.withSpecies(anySet())).willReturn(exparimentDTOBuilderMock);


        subject = new ExperimentMetadataCRUD(configurationTraderMock, experimentDAOMock,
                experimentDesignFileWriterBuilderMock, experimentTraderMock, exparimentDTOBuilderMock, mageTabParserFactory, conditionsIndexTrader);
    }

    @Test
    public void generateExperimentDesignShouldUseTheExperimentDesignWriter() throws Exception {

        subject.generateExperimentDesignFile(EXPERIMENT_ACCESSION, ExperimentType.RNASEQ_MRNA_BASELINE, experimentDesignMock);
        verify(experimentDesignFileWriterBuilderMock).forExperimentAccession(EXPERIMENT_ACCESSION);
        verify(experimentDesignFileWriterBuilderMock).withExperimentType(ExperimentType.RNASEQ_MRNA_BASELINE);
        verify(experimentDesignFileWriterBuilderMock).build();
        verify(experimentDesignFileWriterMock).write(experimentDesignMock);
    }

    @Test(expected = IOException.class)
    public void shouldThrowIllegalStateExceptionWhenWritingExperimentDesignFails() throws Exception {
        willThrow(new IOException()).given(experimentDesignFileWriterMock).write(experimentDesignMock);
        subject.generateExperimentDesignFile(EXPERIMENT_ACCESSION, ExperimentType.RNASEQ_MRNA_BASELINE, experimentDesignMock);
    }


    @Test
    public void updateExperimentShouldDelegateToDAO() throws Exception {
        subject.updateExperiment(EXPERIMENT_ACCESSION, true);
        verify(experimentDAOMock).updateExperiment(EXPERIMENT_ACCESSION, true);
    }

    @Test
    public void updateExperimentDesignShouldRemoveExperimentFromCache() throws Exception {
        subject.updateExperimentDesign(new ExperimentDTO(EXPERIMENT_ACCESSION, ExperimentType.RNASEQ_MRNA_BASELINE, null, null, null, false));
        verify(experimentTraderMock).removeExperimentFromCache(EXPERIMENT_ACCESSION, ExperimentType.RNASEQ_MRNA_BASELINE);
        verify(conditionsIndex).updateConditions(any(Experiment.class), any(SetMultimap.class));
    }

    @Test
    public void importExperimentShouldAddExperimentToIndex() throws Exception {
        subject.loadExperiment(EXPERIMENT_ACCESSION, experimentConfiguration, false);
        verify(conditionsIndex).addConditions(any(Experiment.class), any(SetMultimap.class));
    }
}