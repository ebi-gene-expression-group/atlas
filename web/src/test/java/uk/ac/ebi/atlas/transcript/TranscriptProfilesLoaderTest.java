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

package uk.ac.ebi.atlas.transcript;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.baseline.TranscriptProfile;
import uk.ac.ebi.atlas.utils.CsvReaderFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.contains;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TranscriptProfilesLoaderTest {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-513";
    private static final String A_URL_TEMPLATE_MOCK = "A_URL_TEMPLATE_MOCK ";
    private static final String TRANSCRIPT_ID_1 = "TRANSCRIPT_ID_1";
    private static final String TRANSCRIPT_ID_2 = "TRANSCRIPT_ID_2";
    private static final String A_GENE_ID = "A_GENE_ID";
    private static final String A_GENE_NAME = "A_GENE_NAME";
    private static final TranscriptProfile transcriptProfile1 = new TranscriptProfile(A_GENE_ID, TRANSCRIPT_ID_1, Lists.newArrayList("0.11", "1.3242", "0", "0.0003"));
    private static final TranscriptProfile transcriptProfile2 = new TranscriptProfile(A_GENE_ID, TRANSCRIPT_ID_2, Lists.newArrayList("0.00", "2", "0", "0.0002"));
    private static final String[] TSV_LINE_1 = new String[]{A_GENE_ID, A_GENE_NAME, TRANSCRIPT_ID_1, "0.11", "1.3242", "0", "0.0003"};
    private static final String[] TSV_LINE_2 = new String[]{A_GENE_ID, A_GENE_NAME, TRANSCRIPT_ID_2, "0.00", "2", "0", "0.0002"};

    @Mock
    private CsvReaderFactory csvReaderFactory;


    @Captor
    private ArgumentCaptor<List<TranscriptProfile>> captor;


    @Mock
    private TranscriptProfileDao transcriptProfileDaoMock;

    private TranscriptProfilesLoader subject;

    @Before
    public void setUp() throws Exception {
        CSVReader csvReader = new CSVReader(new InputStreamReader(new ByteArrayInputStream(("\n" + Joiner.on(",").join(TSV_LINE_1) + "\n" + Joiner.on(",").join(TSV_LINE_2)).getBytes())));
        when(csvReaderFactory.createTsvReader(contains(EXPERIMENT_ACCESSION))).thenReturn(csvReader);

        subject = new TranscriptProfilesLoader(csvReaderFactory, transcriptProfileDaoMock, A_URL_TEMPLATE_MOCK + "{0}");
    }

    @Test
    public void testLoad() throws IOException {
        subject.load(EXPERIMENT_ACCESSION);
        verify(transcriptProfileDaoMock).deleteTranscriptProfilesForExperiment(EXPERIMENT_ACCESSION);
        verify(csvReaderFactory).createTsvReader(A_URL_TEMPLATE_MOCK + EXPERIMENT_ACCESSION);

        verify(transcriptProfileDaoMock).loadTranscriptProfiles(eq(EXPERIMENT_ACCESSION), captor.capture());
        assertThat(captor.getValue(), containsInAnyOrder(transcriptProfile1, transcriptProfile2));
    }

    @Test
    public void testCreateTranscriptProfile()  {
        TranscriptProfile transcriptProfile = subject.createTranscriptProfile(TSV_LINE_1);
        assertThat(transcriptProfile, is(transcriptProfile));
    }
}
