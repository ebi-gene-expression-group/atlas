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

package uk.ac.ebi.atlas.solr.admin.index.analytics;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import java.util.Set;

import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class IdentifierSearchTermsDaoIT {

    @Inject
    private IdentifierSearchTermsDao subject;


    @Test
    public void fetchSearchTerms() throws Exception {
        Set<String> properties = subject.fetchSearchTerms("ENSMODG00000012671");

        assertThat(properties.size(), Matchers.is(24));
        assertThat(properties, contains("oogenesis","positive regulation of neuroblast proliferation","spindle pole","developmental growth","maintenance of centrosome location","negative regulation of asymmetric cell division","ASP","IQ motif, EF-hand binding site","neuron migration","Calmbp1","FLJ10549","Calponin homology domain","forebrain neuroblast division","negative regulation of neuron differentiation","protein binding","MCPH5","Calmodulin-regulated spectrin-associated protein, CH domain","binding","FLJ10517","spermatogenesis","brain development","midbody","Armadillo-type fold","positive regulation of canonical Wnt receptor signaling pathway"));

        ///assertThat(properties.get("synonym").size(), Matchers.is(5));
        //assertThat(properties.get("synonym"), Matchers.hasItems("Calmbp1", "MCPH5", "ASP"));
        //assertThat(properties.get("goterm"), Matchers.hasItems("oogenesis", "developmental growth", "positive regulation of neuroblast proliferation"));
        //assertThat(properties.get("interproterm"), Matchers.hasItems("Calmodulin-regulated spectrin-associated protein, CH domain", "Armadillo-type fold", "IQ motif, EF-hand binding site"));
    }

    @Test
    public void fetchSearchTermsForUnknownGene() throws Exception {
        Set<String> properties = subject.fetchSearchTerms("FOOBAR");

        assertThat(properties.size(), is(0));
    }

}
