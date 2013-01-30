/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

package uk.ac.ebi.atlas.model.barcharts;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.FactorValue;
import uk.ac.ebi.atlas.model.GeneExpressionPrecondition;
import uk.ac.ebi.atlas.model.caches.BarChartTradersCache;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.SortedMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class BarChartTraderIT {

    private static final String ORGANISM_PART = "ORGANISM_PART";
    @Inject
    private BarChartTradersCache barChartTradersCache;

    @Inject
    private GeneExpressionPrecondition geneExpressionPrecondition;
    private BarChartTrader subject;


    @PostConstruct
    public void initSpringBeans() {
        geneExpressionPrecondition.setQueryFactorType(ORGANISM_PART);
    }

    @Before
    public void initSubject() {
        this.subject = barChartTradersCache.getBarchartTrader("E-MTAB-599");
    }

    @Test
    public void chartDataAllExperimentalFactosTest() {
        SortedMap<Double, Integer> chartData = subject.getChart(null, null);
        //returns
        //[<{0.0=27717, 0.1=24908, 0.2=23690, 0.3=23014, 0.4=22478, 0.5=22094, 0.6=21790, 0.7=21504, 0.8=21280, 0.9=21070, 1.0=20267, 2.0=19147, 3.0=18297, 4.0=17559, 5.0=16934, 6.0=16311, 7.0=15763, 8.0=15252, 9.0=14756, 10.0=14261, 20.0=10555, 30.0=8283, 40.0=6793, 50.0=5830, 60.0=5068, 70.0=4521, 80.0=4074, 90.0=3724, 100.0=3451, 200.0=2038, 300.0=1493, 400.0=1204, 500.0=985, 600.0=844, 700.0=751, 800.0=681, 900.0=627, 1000.0=568, 2000.0=315, 3000.0=229, 4000.0=195, 5000.0=166, 6000.0=144, 7000.0=129, 8000.0=120, 9000.0=115, 10000.0=107, 20000.0=76, 30000.0=63, 40000.0=54, 50000.0=48, 60000.0=39, 70000.0=33, 80000.0=28, 90000.0=26, 100000.0=23, 200000.0=10, 300000.0=9, 400000.0=6, 500000.0=6, 600000.0=5, 700000.0=3, 800000.0=3, 900000.0=3, 1000000.0=2, 2000000.0=1}>

        assertThat(chartData, hasEntry(0.0, 27717));
        assertThat(chartData, hasEntry(2.0, 19147));
        assertThat(chartData, hasEntry(10.0, 14261));

    }

    @Test
    public void chartDataForSomeExperimentalFactorsTest() {
        SortedMap<Double, Integer> chartData = subject.getChart(null, Sets.newHashSet(new FactorValue(ORGANISM_PART, "liver")
                , new FactorValue(ORGANISM_PART, "lung")));
        //returns
        assertThat(chartData, hasEntry(0.0, 21950));
        assertThat(chartData, hasEntry(2.0, 14758));
        assertThat(chartData, hasEntry(10.0, 9321));

    }
}
