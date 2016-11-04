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

import com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.web.FilterFactorsConverter;

import java.util.NavigableMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anySet;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BaselineBarChartControllerTest {

    public static final String EXPERIMENT_ACCESSION = "experimentAccession";
    public static final String SERIALIZED_FILTER_FACTORS = "serializedFilterFactorType:Value";
    public static final String EXPERIMENT_ACCESSKEY = "accessKey";
    @Mock
    private BarChartTradersCache barChartTradersCacheMock;

    @Mock
    private BarChartTrader barChartTraderMock;

    @Mock
    private BarChartExperimentAccessKeyTrader barChartExperimentAccessKeyTrader;

    private BaselineBarChartController subject;


    @Before
    public void setUp() throws Exception {
        subject = new BaselineBarChartController(barChartTradersCacheMock, barChartExperimentAccessKeyTrader);
    }

    @Test
    public void testGetMap() throws Exception {

        NavigableMap<Double, Integer> chartData = Maps.newTreeMap();
        chartData.put(0.1D, 1);
        chartData.put(0.2D, 2);
        chartData.put(0.3D, 3);

        when(barChartTradersCacheMock.getBarchartTrader(EXPERIMENT_ACCESSION)).thenReturn(barChartTraderMock);
        when(barChartTraderMock.getChart(anySet(), anySet())).thenReturn(chartData);

        String json = subject.getMap(EXPERIMENT_ACCESSION, null, "queryFactorType", SERIALIZED_FILTER_FACTORS, EXPERIMENT_ACCESSKEY);
        assertThat(json, is("{\"0.1\":1,\"0.2\":2,\"0.3\":3}"));

    }
}