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
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.baseline.TranscriptProfile;
import uk.ac.ebi.atlas.utils.TsvReaderUtils;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TranscriptProfilesLoaderTest {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-513";
    @Mock
    private CSVReader csvReaderMock;
    @Mock
    private TsvReaderUtils tsvReaderUtilsMock;
    @Mock
    private GeneProfileDao geneProfileDaoMock;

    private TranscriptProfilesLoader subject;
    private static final String TRANSCRIPT_ID = "TRANSCRIPT_ID";
    public static final String A_GENE_ID = "A_GENE_ID";
    private static final String[] A_CSV_LINE = new String[]{A_GENE_ID, TRANSCRIPT_ID, "0.11", "1.3242", "0", "0.0003"};

    @Before
    public void setUp() throws Exception {
        when(tsvReaderUtilsMock.build(contains(EXPERIMENT_ACCESSION))).thenReturn(csvReaderMock);

        subject = new TranscriptProfilesLoader(tsvReaderUtilsMock, geneProfileDaoMock, "A_URL_TEMPLATE_MOCK {0}");
    }

    @Test
    public void testLoad() throws Exception {
        subject.load(EXPERIMENT_ACCESSION);
        verify(csvReaderMock, times(2)).readNext();
        verify(csvReaderMock).close();
    }

    @Test
    public void testCreateTranscriptProfile() throws Exception {
        TranscriptProfile transcriptProfile = subject.createTranscriptProfile(A_CSV_LINE);
        assertThat(transcriptProfile, is(new TranscriptProfile(A_GENE_ID, TRANSCRIPT_ID, Lists.newArrayList(0.11D, 1.3242D, 0D, 0.0003D))));
    }
}
