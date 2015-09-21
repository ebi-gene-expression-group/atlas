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

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.baseline.Factor;

import javax.inject.Inject;
import java.util.SortedMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class BarChartTraderIT {

    private static final String ORGANISM_PART = "ORGANISM_PART";
    private static final String BASELINE_EXPERIMENT_ACCESSION = "E-MTAB-599";

    @Inject
    private BarChartTradersCache barChartTradersCache;

    private BarChartTrader subject;


    @Before
    public void setUp() throws Exception {

        this.subject = barChartTradersCache.getBarchartTrader(BASELINE_EXPERIMENT_ACCESSION);

    }


    @Test
    @Ignore
    public void chartDataAllExperimentalFactorsTest() {
        SortedMap<Double, Integer> chartData = subject.getChart(null, null);
        //returns
        assertThat(chartData, hasEntry(0.0, 28295));
        assertThat(chartData, hasEntry(2.0, 19245));
        assertThat(chartData, hasEntry(10.0, 14268));

    }

    @Test
    @Ignore
    public void chartDataForSomeExperimentalFactorsTest() {
        SortedMap<Double, Integer> chartData = subject.getChart(null, Sets.newHashSet(new Factor(ORGANISM_PART, "liver")
                , new Factor(ORGANISM_PART, "lung")));
        //returns
        assertThat(chartData, hasEntry(0.0, 22357));
        assertThat(chartData, hasEntry(2.0, 14783));
        assertThat(chartData, hasEntry(10.0, 9322));

    }
}
