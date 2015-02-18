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

package uk.ac.ebi.atlas.experimentpage.baseline.genedistribution;

import com.google.common.cache.LoadingCache;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BarChartTradersCacheTest {

    public static final String EXPERIMENT_ACCESSION = "experimentAccession";

    private BarChartTradersCache subject;

    @Mock
    private LoadingCache<String, BarChartTrader> barChartTradersMock;

    @Mock
    private BarChartTrader barChartTraderMock;

    @Before
    public void setUp() throws Exception {
        subject = new BarChartTradersCache(barChartTradersMock);
    }

    @Test
    public void testGetBarchartTrader() throws Exception {
        when(barChartTradersMock.get(EXPERIMENT_ACCESSION)).thenReturn(barChartTraderMock);

        assertThat(subject.getBarchartTrader(EXPERIMENT_ACCESSION), is(barChartTraderMock));
    }

    @Test(expected = IllegalStateException.class)
    public void throwException() throws Exception {
        when(barChartTradersMock.get(EXPERIMENT_ACCESSION)).thenThrow(new ExecutionException(new Throwable("Testing error handling")));

        subject.getBarchartTrader(EXPERIMENT_ACCESSION);
    }
}