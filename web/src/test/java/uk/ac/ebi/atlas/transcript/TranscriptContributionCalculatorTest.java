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

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.baseline.TranscriptProfile;
import uk.ac.ebi.atlas.model.cache.baseline.BaselineExperimentsCache;

import java.util.Comparator;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(MockitoJUnitRunner.class)
public class TranscriptContributionCalculatorTest {

    public static final String GENE_ID = "geneId";

    @Mock
    private GeneProfileDao geneProfileDaoMock;

    @Mock
    private BaselineExperimentsCache experimentsCacheMock;

    private TranscriptContributionCalculator subject;

    private List<TranscriptProfile> transcriptProfiles;

    private TranscriptProfile profile1 = new TranscriptProfile(GENE_ID, "T1", Lists.newArrayList(1d, 1d, 3d));
    private TranscriptProfile profile2 = new TranscriptProfile(GENE_ID, "T2", Lists.newArrayList(1d, 2d, 3d));
    private TranscriptProfile profile3 = new TranscriptProfile(GENE_ID, "T3", Lists.newArrayList(1d, 3d, 3d));
    private TranscriptProfile profile4 = new TranscriptProfile(GENE_ID, "T4", Lists.newArrayList(1d, 4d, 3d));

    @Before
    public void initSubject() throws Exception {

        subject = new TranscriptContributionCalculator(geneProfileDaoMock, experimentsCacheMock);
    }

    @Test
    public void testCreateTopThreeTranscriptsMapWithMoreThan3Transcripts() throws Exception {
        //given
        transcriptProfiles = Lists.newArrayList(profile4, profile3, profile2, profile1);

        //when
        TranscriptsContribution topThreeTranscriptsMap = subject.createTranscriptsContribution(transcriptProfiles, 1);

        //then
        assertThat(topThreeTranscriptsMap.getTranscriptPercentageRates().keySet(), contains("T4", "T3", "T2", TranscriptContributionCalculator.OTHERS));
        assertThat(topThreeTranscriptsMap.getTranscriptPercentageRates().values(), containsInAnyOrder(40d, 30d, 20d, 10d));

    }

    @Test
    public void testCreateTopThreeTranscriptsMapWith2Transcripts() throws Exception {
        //given
        transcriptProfiles = Lists.newArrayList(profile4, profile2);

        //when
        TranscriptsContribution topThreeTranscriptsMap = subject.createTranscriptsContribution(transcriptProfiles, 1);

        //then
        assertThat(topThreeTranscriptsMap.getTranscriptPercentageRates().keySet(), containsInAnyOrder("T4", "T2"));
        assertThat(topThreeTranscriptsMap.getTranscriptPercentageRates().values(), containsInAnyOrder(66.7d, 33.3d));
    }

    @Test
    public void testGetReverseTranscriptProfileComparator() throws Exception {
        //when
        Comparator<TranscriptProfile> comparator = subject.getReverseTranscriptProfileComparator(1);

        //then
        assertThat(comparator.compare(profile2, profile3), greaterThan(0));
        assertThat(comparator.compare(profile2, profile2), is(0));
    }
}
