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
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.baseline.TranscriptProfile;
import uk.ac.ebi.atlas.utils.TsvReaderUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TranscriptProfilesLoaderTest {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-513";
    private static final String A_URL_TEMPLATE_MOCK = "A_URL_TEMPLATE_MOCK ";
    private static final String TRANSCRIPT_ID = "TRANSCRIPT_ID";
    private static final String A_GENE_ID = "A_GENE_ID";
    private static final String[] A_CSV_LINE = new String[]{A_GENE_ID, TRANSCRIPT_ID, "0.11", "1.3242", "0", "0.0003"};

    private CSVReader csvReader;

    @Mock
    private TsvReaderUtils tsvReaderUtilsMock;

    @Mock
    private GeneProfileDao geneProfileDaoMock;

    private TranscriptProfilesLoader subject;

    @Before
    public void setUp() throws Exception {
        csvReader = new CSVReader(new InputStreamReader(new ByteArrayInputStream(("\n" + Joiner.on(",").join(A_CSV_LINE)).getBytes())));
        when(tsvReaderUtilsMock.build(contains(EXPERIMENT_ACCESSION))).thenReturn(csvReader);

        subject = new TranscriptProfilesLoader(tsvReaderUtilsMock, geneProfileDaoMock, A_URL_TEMPLATE_MOCK + "{0}");
    }

    @Test
    public void testLoad() throws Exception {
        subject.load(EXPERIMENT_ACCESSION);
        verify(geneProfileDaoMock).deleteTranscriptProfilesForExperiment(EXPERIMENT_ACCESSION);
        verify(tsvReaderUtilsMock).build(A_URL_TEMPLATE_MOCK + EXPERIMENT_ACCESSION);
        verify(geneProfileDaoMock).addTranscriptProfiles(EXPERIMENT_ACCESSION, Collections.EMPTY_LIST);
        verify(geneProfileDaoMock).addTranscriptProfiles(anyString(), anyListOf(TranscriptProfile.class));
    }

    @Test
    public void testCreateTranscriptProfile() throws Exception {
        TranscriptProfile transcriptProfile = subject.createTranscriptProfile(A_CSV_LINE);
        assertThat(transcriptProfile, is(new TranscriptProfile(A_GENE_ID, TRANSCRIPT_ID, Lists.newArrayList(0.11D, 1.3242D, 0D, 0.0003D))));
    }
}
