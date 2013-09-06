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
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.commands.GenesNotFoundException;

import javax.inject.Inject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml"})
public class SolrClientIT {

    @Inject
    private SolrClient subject;

    @Test
    public void testQuerySolrForProperties() throws SolrServerException {

        //when
        Multimap<String, String> multimap = subject.fetchProperties("ENSG00000109819", new String[]{"goterm"});

        // then
        assertThat(multimap.get("goterm"), hasItems("RNA splicing", "cellular response to oxidative stress", "cellular glucose homeostasis"));

    }

    @Test
    public void testFetchGeneIdentifiersFromSolr() throws SolrServerException, GenesNotFoundException {

        // given
        GeneQueryResponse geneQueryResponse = subject.findGeneIdsOrSets("aspm", false, "homo sapiens", false);

        // then
        assertThat(geneQueryResponse.getAllGeneIds(), contains("ENSG00000066279"));

    }

    @Test
    public void testFetchGeneIdentifiersFromSolrMany() throws SolrServerException, GenesNotFoundException {

        // given
        GeneQueryResponse geneQueryResponse = subject.findGeneIdsOrSets("protein", false, "homo sapiens", false);

        // then
        assertThat(geneQueryResponse.getAllGeneIds().size(), lessThan(200000));
        assertThat(geneQueryResponse.getAllGeneIds(), hasItems("ENSG00000126773", "ENSG00000183878"));

    }

}