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

package uk.ac.ebi.atlas.geneindex;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class SolrClientIT {

    private static final String HOMO_SAPIENS_SPECIES = "homo sapiens";
    private static final String MUS_MUSCULUS_SPECIES = "mus musculus";

    @Inject
    private SolrClient subject;

    @Before
    public void setUp() throws Exception {

    }


    @Test
    public void findGeneNameSuggestionsShouldSupportSingleTermQueries() {

        List<String> properties = subject.findGeneIdSuggestions("p", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(15));
        assertThat(properties, hasItems("p", "p2rx1", "p2rx2", "p2rx3"));
    }

    @Test
    public void findGeneNameSuggestionsShouldNotContainSpeciesTerms() {

        List<String> properties = subject.findGeneIdSuggestions("mus", MUS_MUSCULUS_SPECIES);

        assertThat(properties, containsInAnyOrder("musk", "mus81", "mustn1"));
    }

    @Test
    public void findGeneNameSuggestionsShouldNotSupportMultitermQueries() {

        List<String> properties = subject.findGeneIdSuggestions("En p", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(0));
    }

    @Test
    public void findGenePropertySuggestionsShouldSupportMultiTermQueries() {

        List<String> properties = subject.findGenePropertySuggestions("p b", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(15));
        assertThat(properties, hasItems("p b", "p binding", "protein binding"));
    }
}
