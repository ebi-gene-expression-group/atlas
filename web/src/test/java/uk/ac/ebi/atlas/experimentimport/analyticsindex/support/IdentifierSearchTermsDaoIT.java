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

package uk.ac.ebi.atlas.experimentimport.analyticsindex.support;

import com.google.common.base.Joiner;
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
    public void fetchSearchTerms() {
        Set<String> properties = subject.fetchSearchTerms("ENSMODG00000012671");

        System.out.println("\"" + Joiner.on("\", \"").join(properties) + "\"");

        assertThat(properties.size(), Matchers.is(27));
        assertThat(properties, contains("oogenesis", "developmental growth", "negative regulation of asymmetric cell division", "IQ motif, EF-hand binding site", "ASP", "neuron migration", "Calmbp1", "Calponin homology domain", "FLJ10549", "forebrain neuroblast division", "ASPM", "positive regulation of neuroblast proliferation", "spindle pole", "maintenance of centrosome location", "protein_coding", "asp (abnormal spindle) homolog, microcephaly associated (Drosophila) [Source:HGNC Symbol;Acc:19048]", "protein binding", "negative regulation of neuron differentiation", "MCPH5", "Calmodulin-regulated spectrin-associated protein, CH domain", "FLJ10517", "binding", "spermatogenesis", "brain development", "Armadillo-type fold", "midbody", "positive regulation of canonical Wnt receptor signaling pathway"));

                ///assertThat(properties.get("synonym").size(), Matchers.is(5));
        //assertThat(properties.get("synonym"), Matchers.hasItems("Calmbp1", "MCPH5", "ASP"));
        //assertThat(properties.get("goterm"), Matchers.hasItems("oogenesis", "developmental growth", "positive regulation of neuroblast proliferation"));
        //assertThat(properties.get("interproterm"), Matchers.hasItems("Calmodulin-regulated spectrin-associated protein, CH domain", "Armadillo-type fold", "IQ motif, EF-hand binding site"));
    }

    @Test
    public void fetchSearchTermsForUnknownGene()  {
        Set<String> properties = subject.fetchSearchTerms("FOOBAR");

        assertThat(properties.size(), is(0));
    }

}
