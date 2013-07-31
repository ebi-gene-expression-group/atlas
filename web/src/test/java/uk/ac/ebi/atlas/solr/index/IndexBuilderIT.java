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

package uk.ac.ebi.atlas.solr.index;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.SolrParams;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml"})
public class IndexBuilderIT {

    @Value("#{configuration['bioentity.properties']}")
    private String bioentityPropertyDirectory;

    @Inject
    private BioentityIndex subject;

    @Inject
    private SolrServer embeddedSolrServer;

    @Before
    public void setup() throws ParserConfigurationException, SAXException, IOException {
    }

    @After
    public void cleanup() throws IOException, SolrServerException {
        embeddedSolrServer.deleteByQuery("*:*");
        embeddedSolrServer.shutdown();
    }

    @Test
    public void addDesignElementsShouldSucceed() throws IOException, SolrServerException {
        subject.add(Paths.get(bioentityPropertyDirectory, "anopheles_gambiae.A-AFFY-102.tsv"));

        SolrParams solrQuery = new SolrQuery("*:*");
        QueryResponse queryResponse = embeddedSolrServer.query(solrQuery);
        List<BioentityPropertyDocument> bioentityPropertyDocuments = queryResponse.getBeans(BioentityPropertyDocument.class);
        assertThat(bioentityPropertyDocuments, hasSize(10));

    }

}
