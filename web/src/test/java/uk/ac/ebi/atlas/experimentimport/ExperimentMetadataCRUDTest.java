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

package uk.ac.ebi.atlas.experimentimport;

import au.com.bytecode.opencsv.CSVWriter;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.dao.ArrayDesignDao;
import uk.ac.ebi.atlas.experimentimport.experimentdesign.ExperimentDesignFileWriter;
import uk.ac.ebi.atlas.experimentimport.experimentdesign.ExperimentDesignFileWriterBuilder;
import uk.ac.ebi.atlas.experimentimport.experimentdesign.magetab.CondensedSdrfParser;
import uk.ac.ebi.atlas.experimentimport.experimentdesign.magetab.MageTabParser;
import uk.ac.ebi.atlas.experimentimport.experimentdesign.magetab.MageTabParserFactory;
import uk.ac.ebi.atlas.experimentimport.experimentdesign.magetab.MageTabParserOutput;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentConfiguration;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperimentConfiguration;
import uk.ac.ebi.atlas.solr.admin.index.conditions.ConditionsIndex;
import uk.ac.ebi.atlas.solr.admin.index.conditions.ConditionsIndexTrader;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentMetadataCRUDTest {

    private static final String EXPERIMENT_ACCESSION = "EXPERIMENT_ACCESSION";
    private static final String EXPERIMENT_ASSAY = "EXPERIMENT_ASSAY";
    private static final String ARRAY_DESIGN = "ARRAY_DESIGN";

    private static final String EFO_0000761 = "EFO_0000761";
    private static final String EFO_0000001 = "EFO_0000001";
    private static final String EFO_0001443 = "snap#SpecificallyDependentContinuant";
    private static final String EFO_0001438 = "snap#Disposition";
    private static final Set<String> EXPANDED_EFO_TERMS = new HashSet<>(Arrays.asList(EFO_0001438, EFO_0001443, EFO_0000001));

    @Mock
    private ExperimentDesignFileWriter experimentDesignFileWriterMock;

    @Mock
    private CSVWriter csvWriterMock;

    @Mock
    private ArrayDesignDao arrayDesignDaoMock;

    @Mock
    private MicroarrayExperimentConfiguration microarrayExperimentConfigurationMock;

    private ExperimentMetadataCRUD subject;

    @Mock
    private ExperimentDesignFileWriterBuilder experimentDesignFileWriterBuilderMock;

    @Mock
    private ExperimentDAO experimentDAOMock;

    @Mock
    private ExperimentTrader experimentTraderMock;

    @Mock
    DifferentialExperiment differentialExperimentMock;

    @Mock
    private ConditionsIndexTrader conditionsIndexTrader;

    @Mock
    private ConditionsIndex conditionsIndex;

    @Mock
    private ExperimentDTOBuilder experimentDTOBuilderMock;

    @Mock
    private ExperimentDesign experimentDesignMock;

    @Mock
    private MageTabParserOutput mageTabParserOutput;

    @Mock
    private ExperimentConfiguration experimentConfiguration;

    @Mock
    private MageTabParserFactory mageTabParserFactory;

    @Mock
    private CondensedSdrfParser condensedSdrfParserMock;

    @Mock
    private MageTabParser mageTabParser;

    @Mock
    private EFOParentsLookupService efoParentsLookupServiceMock;

    @Captor
    ArgumentCaptor<ImmutableSetMultimap<String, String>> termIdsByAssayAccessionCaptor;

    @Before
    public void setUp() throws Exception {

        when(microarrayExperimentConfigurationMock.getArrayDesignAccessions()).thenReturn(Sets.newTreeSet(Sets.newHashSet(ARRAY_DESIGN)));
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

        given(experimentDTOBuilderMock.forExperimentAccession(EXPERIMENT_ACCESSION)).willReturn(experimentDTOBuilderMock);
        given(experimentDTOBuilderMock.withExperimentType(ExperimentType.RNASEQ_MRNA_BASELINE)).willReturn(experimentDTOBuilderMock);
        given(experimentDTOBuilderMock.withPrivate(false)).willReturn(experimentDTOBuilderMock);
        given(experimentDTOBuilderMock.withSpecies(anySet())).willReturn(experimentDTOBuilderMock);
        given(experimentDTOBuilderMock.withPubMedIds(anySet())).willReturn(experimentDTOBuilderMock);
        given(experimentDTOBuilderMock.withTitle(anyString())).willReturn(experimentDTOBuilderMock);

        ImmutableSetMultimap.Builder<String, String> builder = new ImmutableSetMultimap.Builder<>();
        builder.put(EXPERIMENT_ASSAY, EFO_0000761);
        when(experimentDesignMock.getAllOntologyTermIdsByAssayAccession()).thenReturn(builder.build());
        when(efoParentsLookupServiceMock.getAllParents(anySet())).thenReturn(EXPANDED_EFO_TERMS);

        subject = new ExperimentMetadataCRUD(experimentDAOMock,
                experimentDesignFileWriterBuilderMock, experimentTraderMock, experimentDTOBuilderMock, condensedSdrfParserMock, mageTabParserFactory, conditionsIndexTrader, efoParentsLookupServiceMock);
    }

    @Test
    public void generateExperimentDesignShouldUseTheExperimentDesignWriter() throws Exception {

        subject.writeExperimentDesignFile(EXPERIMENT_ACCESSION, ExperimentType.RNASEQ_MRNA_BASELINE, experimentDesignMock);
        verify(experimentDesignFileWriterBuilderMock).forExperimentAccession(EXPERIMENT_ACCESSION);
        verify(experimentDesignFileWriterBuilderMock).withExperimentType(ExperimentType.RNASEQ_MRNA_BASELINE);
        verify(experimentDesignFileWriterBuilderMock).build();
        verify(experimentDesignFileWriterMock).write(experimentDesignMock);
    }

    @Test(expected = IOException.class)
    public void shouldThrowIllegalStateExceptionWhenWritingExperimentDesignFails() throws Exception {
        willThrow(new IOException()).given(experimentDesignFileWriterMock).write(experimentDesignMock);
        subject.writeExperimentDesignFile(EXPERIMENT_ACCESSION, ExperimentType.RNASEQ_MRNA_BASELINE, experimentDesignMock);
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
        subject.importExperiment(EXPERIMENT_ACCESSION, experimentConfiguration, false, Optional.<String>absent());
        verify(conditionsIndex).addConditions(any(Experiment.class), any(SetMultimap.class));
    }

    @Test
    public void importExperimentExpandsOntologyTerms() throws Exception {
        subject.importExperiment(EXPERIMENT_ACCESSION, experimentConfiguration, false, Optional.<String>absent());
        verify(conditionsIndex).addConditions(any(Experiment.class), termIdsByAssayAccessionCaptor.capture());

        ImmutableSetMultimap<String, String> termIdsByAssayAccession =  termIdsByAssayAccessionCaptor.getValue();
        assertThat(termIdsByAssayAccession.get(EXPERIMENT_ASSAY), containsInAnyOrder(EFO_0000001, EFO_0000761, EFO_0001438, EFO_0001443));
    }

    @Test
    public void updateExperimentExpandsOntologyTerms() throws Exception {
        subject.updateExperimentDesign(new ExperimentDTO(EXPERIMENT_ACCESSION, ExperimentType.RNASEQ_MRNA_BASELINE, null, null, null, false));
        verify(conditionsIndex).updateConditions(any(Experiment.class), termIdsByAssayAccessionCaptor.capture());

        ImmutableSetMultimap<String, String> termIdsByAssayAccession =  termIdsByAssayAccessionCaptor.getValue();
        assertThat(termIdsByAssayAccession.get(EXPERIMENT_ASSAY), containsInAnyOrder(EFO_0000001, EFO_0000761, EFO_0001438, EFO_0001443));
    }

}