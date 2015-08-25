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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import java.util.Set;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class IdentifierSearchTermsDaoIT {

    @Inject
    private IdentifierSearchTermsDAO subject;

    @Test
    public void fetchSearchTerms() {
        Set<String> properties = subject.fetchSearchTerms("ENSMODG00000012671");

        assertThat(properties.size(), is(69));
        //System.out.println("\"" + Joiner.on("\", \"").join(properties) + "\"");
        assertThat(properties, containsInAnyOrder("GO:0005515", "oogenesis", "GO:0005516", "developmental growth", "GO:0090263", "Calmbp1", "GO:0007283", "FLJ10549", "forebrain neuroblast division", "GO:0072687", "calmodulin binding", "GO:0048589", "F6VH23", "GO:0051445", "cytoplasm", "ASPM", "positive regulation of neuroblast proliferation", "neuronal stem cell maintenance", "positive regulation of canonical Wnt signaling pathway", "IPR027417", "maintenance of centrosome location", "GO:0030496", "IPR016024", "GO:0021873", "asp (abnormal spindle) homolog, microcephaly associated (Drosophila) [Source:HGNC Symbol;Acc:HGNC:19048]", "GO:0000922", "negative regulation of neuron differentiation", "GO:0007420", "binding", "cerebral cortex development", "microtubule", "GO:0021987", "GO:0048477", "negative regulation of asymmetric cell division", "ASP", "IQ motif, EF-hand binding site", "neuron migration", "Calponin homology domain", "IPR001715", "IPR000048", "GO:0005737", "GO:0051661", "IPR022613", "GO:0090306", "mitotic spindle pole", "GO:0097150", "spindle pole", "P-loop containing nucleoside triphosphate hydrolase", "GO:0005874", "male gonad development", "GO:0002052", "GO:0045769", "protein_coding", "protein binding", "MCPH5", "GO:0045665", "GO:0005488", "Calmodulin-regulated spectrin-associated protein, CH domain", "FLJ10517", "spermatogenesis", "GO:0097431", "brain development", "GO:0001764", "Armadillo-type fold", "midbody", "spindle assembly involved in meiosis", "regulation of meiotic cell cycle", "GO:0008584", "meiotic spindle"));

        //assertThat(properties.get("synonym").size(), is(5));
        //assertThat(properties.get("synonym"), hasItems("Calmbp1", "MCPH5", "ASP"));
        //assertThat(properties.get("goterm"), hasItems("oogenesis", "developmental growth", "positive regulation of neuroblast proliferation"));
        //assertThat(properties.get("interproterm"), hasItems("Calmodulin-regulated spectrin-associated protein, CH domain", "Armadillo-type fold", "IQ motif, EF-hand binding site"));
    }

    @Test
    public void fetchSearchTermsForUnknownGene()  {
        Set<String> properties = subject.fetchSearchTerms("FOOBAR");

        assertThat(properties.size(), is(0));
    }

}
