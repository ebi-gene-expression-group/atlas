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

package uk.ac.ebi.atlas.model;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;

import javax.inject.Inject;
import java.util.SortedSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ExperimentIT {

    @Inject
    private ExperimentsCache experimentsCache;

    private Experiment subject;

    @Before
    public void initSubject() {

        subject = experimentsCache.getExperiment("E-GEOD-26284");

    }

    @Test
    public void getFactorsByTypeTest() {
        ExperimentalFactors experimentalFactors = subject.getExperimentalFactors();

        assertThat(experimentalFactors.getFactorsByType("MATERIAL_TYPE").size(), is(3));
        assertThat(experimentalFactors.getFactorsByType("CELL_LINE").size(), is(23));
        assertThat(experimentalFactors.getFactorsByType("CELLULAR_COMPONENT").size(), is(6));
    }

    @Test
    public void getCellLineFilteredFactorsTest() {
        Factor filterFactor1 = new Factor("MATERIAL_TYPE", "total RNA");
        Factor filterFactor2 = new Factor("CELLULAR_COMPONENT", "whole cell");

        ExperimentalFactors experimentalFactors = subject.getExperimentalFactors();
        SortedSet<Factor> filteredFactors = experimentalFactors.getFilteredFactors(Sets.newHashSet(filterFactor1, filterFactor2));

        assertThat(filteredFactors.size(), is(5));
        assertThat(filteredFactors.first().getValue(), is("CD34-positive mobilized cell cell line"));
        assertThat(filteredFactors.last().getValue(), is("hMSC-AT cell line"));
    }

    @Test
    public void getMaterialTypeFilteredFactorsTest() {
        Factor filterFactor1 = new Factor("CELL_LINE", "IMR-90");
        Factor filterFactor2 = new Factor("CELLULAR_COMPONENT", "whole cell");

        ExperimentalFactors experimentalFactors = subject.getExperimentalFactors();
        SortedSet<Factor> filteredFactors = experimentalFactors.getFilteredFactors(Sets.newHashSet(filterFactor1, filterFactor2));

        assertThat(filteredFactors.size(), is(2));
        assertThat(filteredFactors.first().getValue(), is("long polyA RNA"));
        assertThat(filteredFactors.last().getValue(), is("total RNA"));
    }

    @Test
    public void getValidCombinationsForFactorTest() {

        ExperimentalFactors experimentalFactors = subject.getExperimentalFactors();
        assertThat(experimentalFactors.getAllFactorNames().size(), is(3));
        int objectCount = 0;
        for (String factorName : experimentalFactors.getAllFactorNames()) {
            objectCount += experimentalFactors.getFactorsByName(factorName).size();
        }
        assertThat(objectCount, is(32));

        Factor factor = new Factor("MATERIAL_TYPE", "RNA type", "total RNA");
        assertThat(experimentalFactors.getCoOccurringFactors(factor).size(), is(10));
        factor = new Factor("MATERIAL_TYPE", "RNA type", "long polyA RNA");
        assertThat(experimentalFactors.getCoOccurringFactors(factor).size(), is(21));
        factor = new Factor("CELLULAR_COMPONENT", "cellular component", "whole cell");
        assertThat(experimentalFactors.getCoOccurringFactors(factor).size(), is(26));
        factor = new Factor("CELL_LINE", "cell line", "IMR-90");
        assertThat(experimentalFactors.getCoOccurringFactors(factor).size(), is(5));
        factor = new Factor("CELL_LINE", "cell line", "CD34-positive mobilized cell cell line");
        assertThat(experimentalFactors.getCoOccurringFactors(factor).size(), is(2));
    }


    @Test
    public void getCellularComponentFilteredFactorsTest() {
        Factor filterFactor1 = new Factor("CELL_LINE", "IMR-90");
        Factor filterFactor2 = new Factor("MATERIAL_TYPE", "total RNA");

        ExperimentalFactors experimentalFactors = subject.getExperimentalFactors();
        SortedSet<Factor> filteredFactors = experimentalFactors.getFilteredFactors(Sets.newHashSet(filterFactor1, filterFactor2));

        assertThat(filteredFactors.size(), is(1));
        assertThat(filteredFactors.first().getValue(), is("whole cell"));
    }

}
