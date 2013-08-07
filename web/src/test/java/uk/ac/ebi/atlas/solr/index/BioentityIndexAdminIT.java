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
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.SolrParams;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CountDownLatch;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS) //this will shutdown spring context, otherwise things like singletons remain initialized between different test classes :(
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml"})
public class BioentityIndexAdminIT implements Observer{

    @Inject
    private BioentityIndexAdmin subject;

    @Inject
    private BioentityIndexMonitor bioentityIndexMonitor;

    private CountDownLatch updateEventLatch; // used to react to event

    private static SolrServer embeddedSolrServer;

    @Inject
    public void setEmbeddedSolrServer(EmbeddedSolrServer embeddedSolrServer){
        BioentityIndexAdminIT.embeddedSolrServer = embeddedSolrServer;
    }

    @Before
    public void init(){
        updateEventLatch = new CountDownLatch(1);
        bioentityIndexMonitor.addObserver(this);
    }

    @After
    public void cleanupData() throws IOException, SolrServerException {
        embeddedSolrServer.deleteByQuery("*:*");
        embeddedSolrServer.commit();
    }

    @AfterClass
    public static void shutDown() {
        embeddedSolrServer.shutdown();
    }

    @Test(timeout = 3000) //expect the indexing to happen in less than 2 seconds
    public void rebuildIndexShouldSucceed() throws Exception {
        subject.rebuildIndex();

        updateEventLatch.await();

        SolrParams solrQuery = new SolrQuery("*:*").setRows(1000);
        QueryResponse queryResponse = embeddedSolrServer.query(solrQuery);
        List<BioentityProperty> bioentityProperties = queryResponse.getBeans(BioentityProperty.class);
        assertThat(bioentityProperties, hasSize(620));

        BioentityIndexMonitor.Status status = bioentityIndexMonitor.getStatus();
        assertThat(status, is(BioentityIndexMonitor.Status.COMPLETED));
    }

    @Override
    public void update(Observable observable, Object argument) {
        assertTrue(observable == bioentityIndexMonitor);

        updateEventLatch.countDown();
    }
}
