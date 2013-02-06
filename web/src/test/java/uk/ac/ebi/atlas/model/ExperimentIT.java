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
import static org.hamcrest.Matchers.contains;
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
        assertThat(subject.getFactorsByType("MATERIAL_TYPE").size(), is(3));
        assertThat(subject.getFactorsByType("CELL_LINE").size(), is(23));
        assertThat(subject.getFactorsByType("CELLULAR_COMPONENT").size(), is(6));
    }

    @Test
    public void getCellLineFilteredFactorsTest() {
        Factor filterFactor1 = new Factor("MATERIAL_TYPE", "total rna");
        Factor filterFactor2 = new Factor("CELLULAR_COMPONENT", "whole cell");

        SortedSet<Factor> filteredFactors = subject.getFilteredFactors(Sets.newHashSet(filterFactor1, filterFactor2), "CELL_LINE");

        assertThat(filteredFactors.size(), is(5));
        assertThat(filteredFactors.first().getValue(), is("cd34-positive mobilized cell cell line"));
        assertThat(filteredFactors.last().getValue(), is("imr-90"));
    }

    @Test
    public void getMaterialTypeFilteredFactorsTest() {
        Factor filterFactor1 = new Factor("CELL_LINE", "imr-90");
        Factor filterFactor2 = new Factor("CELLULAR_COMPONENT", "whole cell");

        SortedSet<Factor> filteredFactors = subject.getFilteredFactors(Sets.newHashSet(filterFactor1, filterFactor2), "MATERIAL_TYPE");

        assertThat(filteredFactors.size(), is(2));
        assertThat(filteredFactors.first().getValue(), is("long polya rna"));
        assertThat(filteredFactors.last().getValue(), is("total rna"));
    }

    @Test
    public void getValidCombinationsForFactorTest() {

        assertThat(subject.getAllFactorNames().size(), is(3));
        int objectCount = 0;
        for (String factorName : subject.getAllFactorNames()) {
            objectCount += subject.getFactorsByName(factorName).size();
        }
        assertThat(objectCount, is(32));

        Factor factor = new Factor("MATERIAL_TYPE", "RNA type", "total rna");
        assertThat(subject.getValidCombinationsForFactorAndName(factor, "cell line").size(), is(6));
        factor = new Factor("MATERIAL_TYPE", "RNA type", "long polya rna");
        assertThat(subject.getValidCombinationsForFactorAndName(factor, "cell line").size(), is(18));
        factor = new Factor("CELLULAR_COMPONENT", "cellular component", "whole cell");
        assertThat(subject.getValidCombinationsForFactorAndName(factor, "RNA type").size(), is(3));
        factor = new Factor("CELL_LINE", "cell line", "imr-90");
        assertThat(subject.getValidCombinationsForFactorAndName(factor, "cellular component").size(), is(3));
        factor = new Factor("CELL_LINE", "cell line", "cd34-positive mobilized cell cell line");
        assertThat(subject.getValidCombinationsForFactorAndName(factor, "RNA type").size(), is(1));

        for (String factorName : subject.getAllFactorNames()) {
            for (String remainingFactorName : subject.getRemainingFactorNamesForNames(factorName)) {
                for (Factor keyFactor : subject.getFactorsByName(factorName)) {
                    objectCount += subject.getValidCombinationsForFactorAndName(keyFactor, remainingFactorName).size();
                }
            }
        }
        assertThat(objectCount, is(230));
    }

    @Test(expected = IllegalArgumentException.class)
    public void geFilteredFactorsShouldFailIfQueryFactorTypeIsEqualToTheTypeOfOneOfTheFilterFactors() {
        Factor filterFactor1 = new Factor("CELL_LINE", "imr-90");
        Factor filterFactor2 = new Factor("CELLULAR_COMPONENT", "whole cell");

        subject.getFilteredFactors(Sets.newHashSet(filterFactor1, filterFactor2), "CELL_LINE");
    }


    @Test
    public void getCellularComponentFilteredFactorsTest() {
        Factor filterFactor1 = new Factor("CELL_LINE", "imr-90");
        Factor filterFactor2 = new Factor("MATERIAL_TYPE", "total rna");

        SortedSet<Factor> filteredFactors = subject.getFilteredFactors(Sets.newHashSet(filterFactor1, filterFactor2), "CELLULAR_COMPONENT");

        assertThat(filteredFactors.size(), is(1));
        assertThat(filteredFactors.first().getValue(), is("whole cell"));
    }

    @Test
    public void getRemainingFactorNamesForNames() {
        assertThat(subject.getRemainingFactorNamesForNames("RNA type"), contains("cell line", "cellular component"));
        assertThat(subject.getRemainingFactorNamesForNames("cell line", "cellular component").size(), is(1));
        assertThat(subject.getRemainingFactorNamesForNames("cell line", "cellular component"), contains("RNA type"));
    }

}
