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

package uk.ac.ebi.atlas.solr.query;

import com.google.common.collect.Multimap;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import java.util.Arrays;

import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml"})
public class FetchGenePagePropertiesIT {

    @Inject
    private SolrClient subject;

    @Value("#{configuration['index.types.genepage']}")
    private String genePagePropertyTypes;

    @Test
    public void testFetchTooltipProperties() throws Exception {

        // given
        Multimap<String, String> properties = subject.fetchGenePageProperties("ENSMUSG00000029816", Arrays.asList(genePagePropertyTypes.split(",")));

        // index.types.genepage=symbol,description,synonym,ortholog,goterm,interproterm,ensfamily_description,ensgene,entrezgene,uniprot,mgi_id,mgi_description,gene_biotype,design_element

        assertThat(properties.size(), Matchers.greaterThan(50));
        assertThat(properties.get("synonym").size(), Matchers.is(2));
        assertThat(properties.get("synonym"), Matchers.hasItems("Dchil", "Osteoactivin"));
        assertThat(properties.get("ortholog"), Matchers.hasItems("ENSRNOG00000008816", "ENSGALG00000010949", "ENSBTAG00000000604", "ENSXETG00000007393", "ENSG00000136235"));
        assertThat(properties.get("goterm"), Matchers.hasItems("heparin binding", "cell adhesion", "integral to plasma membrane", "cytoplasmic membrane-bounded vesicle"));
        assertThat(properties.get("interproterm"), Matchers.hasItems("PKD domain", "PKD/Chitinase domain"));
        assertThat(properties.get("ensfamily_description"), Matchers.hasItems("TRANSMEMBRANE GLYCOPROTEIN NMB PRECURSOR"));
        assertThat(properties.get("ensgene"), Matchers.hasItems("ENSMUSG00000029816"));
        assertThat(properties.get("entrezgene"), Matchers.hasItems("93695"));
        assertThat(properties.get("uniprot"), Matchers.hasItems("Q99P91", "Q3UE75"));
        assertThat(properties.get("mgi_id"), Matchers.hasItems("MGI:1934765"));
        assertThat(properties.get("mgi_description"), Matchers.hasItems("glycoprotein (transmembrane) nmb"));
        assertThat(properties.get("gene_biotype"), Matchers.hasItems("protein_coding"));
        assertThat(properties.get("design_element"), Matchers.hasItems("5548029", "108822_at", "5610568", "5182097", "5246058"));
    }


}
