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

package uk.ac.ebi.atlas.trader.cache;

import com.google.common.cache.LoadingCache;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;

import java.io.FileNotFoundException;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class RnaSeqDiffExperimentsCacheTest {

    @Mock
    private LoadingCache<String, DifferentialExperiment> loadingCacheMock;

    @Mock
    private DifferentialExperiment differentialExperimentMock;

    private RnaSeqDiffExperimentsCache subject;

    @Before
    public void setUp() throws Exception {
        subject = new RnaSeqDiffExperimentsCache(loadingCacheMock);
    }

    @Test
    public void testGetExperiment() throws Exception {
        given(loadingCacheMock.get(anyString())).willReturn(differentialExperimentMock);
        assertThat(subject.getExperiment("bla"), is(differentialExperimentMock));
    }

    @Test(expected = ExecutionException.class)
    public void whenGetFromCacheFailsCacheShallThrowExecutionException() throws ExecutionException {
        given(loadingCacheMock.get("")).willThrow(new ExecutionException(new FileNotFoundException()));
        subject.getExperiment("");
    }
}