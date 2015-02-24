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

package uk.ac.ebi.atlas.model.baseline;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.baseline.impl.FactorSet;
import uk.ac.ebi.atlas.trader.cache.BaselineExperimentsCache;

import javax.inject.Inject;
import java.util.SortedSet;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class ExperimentalFactorsIT {

    private static final String BASELINE_EXPERIMENT_ACCESSION = "E-GEOD-26284";

    @Inject
    private BaselineExperimentsCache experimentsCache;

    ExperimentalFactors subject;

    @Before
    public void setUp() throws Exception {
        BaselineExperiment experiment = experimentsCache.getExperiment(BASELINE_EXPERIMENT_ACCESSION);
        subject = experiment.getExperimentalFactors();
    }

    @Test
    public void getFactorsByTypeTest() {
        assertThat(subject.getFactors("RNA").size(), is(3));
        assertThat(subject.getFactors("CELL_LINE").size(), is(23));
        assertThat(subject.getFactors("CELLULAR_COMPONENT").size(), is(6));
    }

    @Test
    public void getNonExistentFactor() {
        assertThat(subject.getFactors("FOOBAR").size(), is(0));
    }

    @Test
    public void getCellLineFilteredFactorsTest() {
        Factor filterFactor1 = new Factor("RNA", "total RNA");
        Factor filterFactor2 = new Factor("CELLULAR_COMPONENT", "whole cell");

        SortedSet<Factor> filteredFactors = subject.getComplementFactors(Sets.newHashSet(filterFactor1, filterFactor2));

        assertThat(filteredFactors.size(), is(5));
        assertThat(filteredFactors.first().getValue(), is("CD34-positive mobilized cell cell line"));
        assertThat(filteredFactors.last().getValue(), is("hMSC-AT cell line"));
    }

    @Test
    public void getMaterialTypeFilteredFactorsTest() {
        Factor filterFactor1 = new Factor("CELL_LINE", "IMR-90");
        Factor filterFactor2 = new Factor("CELLULAR_COMPONENT", "whole cell");

        SortedSet<Factor> filteredFactors = subject.getComplementFactors(Sets.newHashSet(filterFactor1, filterFactor2));

        assertThat(filteredFactors.size(), is(2));
        assertThat(filteredFactors.first().getValue(), is("long polyA RNA"));
        assertThat(filteredFactors.last().getValue(), is("total RNA"));
    }

    @Test
    public void getValidCombinationsForFactorTest() {

        Factor factor = new Factor("RNA", "total RNA");
        assertThat(subject.getCoOccurringFactors(factor).size(), is(10));
        factor = new Factor("RNA", "long polyA RNA");
        assertThat(subject.getCoOccurringFactors(factor).size(), is(21));
        factor = new Factor("CELLULAR_COMPONENT", "whole cell");
        assertThat(subject.getCoOccurringFactors(factor).size(), is(26));
        factor = new Factor("CELL_LINE", "IMR-90");
        assertThat(subject.getCoOccurringFactors(factor).size(), is(5));
        factor = new Factor("CELL_LINE", "CD34-positive mobilized cell cell line");
        assertThat(subject.getCoOccurringFactors(factor).size(), is(2));
    }


    @Test
    public void getCellularComponentFilteredFactorsTest() {
        Factor filterFactor1 = new Factor("CELL_LINE", "IMR-90");
        Factor filterFactor2 = new Factor("RNA", "total RNA");

        SortedSet<Factor> filteredFactors = subject.getComplementFactors(Sets.newHashSet(filterFactor1, filterFactor2));

        assertThat(filteredFactors.size(), is(1));
        assertThat(filteredFactors.first().getValue(), is("whole cell"));
    }

    @Test
    public void getNonDefaultFactors_g59() {
        Factor filterFactor1 = new Factor("RNA", "long non-polyA RNA");
        Factor filterFactor2 = new Factor("CELLULAR_COMPONENT", "whole cell");

        assertThat(subject.getNonDefaultFactors("g59"), contains(filterFactor1, filterFactor2));
    }

    @Test
    public void getNonDefaultFactors_g41() {
        Factor filterFactor1 = new Factor("RNA", "long polyA RNA");
        Factor filterFactor2 = new Factor("CELLULAR_COMPONENT", "cytosol");

        assertThat(subject.getNonDefaultFactors("g41"), contains(filterFactor1, filterFactor2));
    }

    @Test
    public void getNonDefaultFactors_g20() {
        Factor filterFactor1 = new Factor("RNA", "long polyA RNA");
        Factor filterFactor2 = new Factor("CELLULAR_COMPONENT", "cytosol");

        assertThat(subject.getNonDefaultFactors("g20"), contains(filterFactor1, filterFactor2));
    }

    @Test
    public void groupAssayGroupIdsByNonDefaultFilterFactor() {
        Multimap<FactorGroup,String> byFactorGroup = subject.getAssayGroupIdsGroupedByNonDefaultFactors(ImmutableList.of("g80", "g41", "g46"));

        Factor filterFactor1 = new Factor("RNA", "total RNA");
        Factor filterFactor2 = new Factor("CELLULAR_COMPONENT", "whole cell");
        FactorSet factorSet1 = new FactorSet();
        factorSet1.add(filterFactor1);
        factorSet1.add(filterFactor2);

        Factor filterFactor3 = new Factor("RNA", "long polyA RNA");
        Factor filterFactor4 = new Factor("CELLULAR_COMPONENT", "cytosol");
        FactorSet factorSet2 = new FactorSet();
        factorSet2.add(filterFactor3);
        factorSet2.add(filterFactor4);

        assertThat(byFactorGroup.entries(), hasSize(3));
        assertThat(byFactorGroup.asMap(), allOf(
                hasEntry(equalTo((FactorGroup) factorSet1), containsInAnyOrder("g80")),
                hasEntry(equalTo((FactorGroup) factorSet2), containsInAnyOrder("g46", "g41"))
        ));
    }

}
