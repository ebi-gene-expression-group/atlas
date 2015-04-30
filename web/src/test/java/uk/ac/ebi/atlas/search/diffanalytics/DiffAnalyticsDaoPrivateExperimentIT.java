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

package uk.ac.ebi.atlas.search.diffanalytics;

import com.google.common.base.Optional;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentimport.ExperimentMetadataCRUD;
import uk.ac.ebi.atlas.solr.query.conditions.IndexedAssayGroup;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class DiffAnalyticsDaoPrivateExperimentIT {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-698";

    @Inject
    private DiffAnalyticsDao subject;

    @Inject
    private ExperimentMetadataCRUD experimentMetadataCRUD;

    @Before
    public void setPrivate() throws IOException {
        experimentMetadataCRUD.updateExperiment(EXPERIMENT_ACCESSION, true);
    }

    @Test
    public void getTopExpressionsDoesNotReturnResultsInPrivateExperiments() {
        Collection<String> geneIds = Collections.singleton("ENSMUSG00000050520");
        String species = "";
        List<DiffAnalytics> expressions = subject.fetchTopExpressions(Optional.<Collection<IndexedAssayGroup>>absent(), Optional.of(geneIds), species);
        MatcherAssert.assertThat(expressions, Matchers.hasSize(0));
    }

    @After
    public void setPublic() throws IOException {
        experimentMetadataCRUD.updateExperiment(EXPERIMENT_ACCESSION, false);
    }

}
