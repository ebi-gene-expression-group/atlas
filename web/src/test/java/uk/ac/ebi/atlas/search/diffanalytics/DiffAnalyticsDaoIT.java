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
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import oracle.sql.ARRAY;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.solr.query.conditions.IndexedAssayGroup;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class DiffAnalyticsDaoIT {

    @Inject
    private DiffAnalyticsDao subject;


    @Test
    public void getTopExpressionsForContrast() throws Exception {
        IndexedAssayGroup indexedContrast1 = new IndexedAssayGroup("E-MTAB-1066", "g2_g3");

        Collection<IndexedAssayGroup> contrasts = Lists.newArrayList(indexedContrast1);
        Collection<String> geneIds = new HashSet<>();

        List<DiffAnalytics> expressions = subject.fetchTopExpressions(Optional.of(contrasts), Optional.of(geneIds));
        assertThat(expressions.size(), is(6));
        assertThat(expressions.get(0).getBioentityId(), is("FBgn0040393"));
        assertThat(expressions.get(1).getBioentityId(), is("FBgn0030746"));

    }

    @Test
    public void getResultCountForContrast() throws Exception {
        IndexedAssayGroup indexedContrast1 = new IndexedAssayGroup("E-MTAB-1066", "g2_g3");

        Collection<IndexedAssayGroup> indexedAssayGroups = Lists.newArrayList(indexedContrast1);
        Collection<String> strings = new HashSet<>();
        int resultCount = subject.fetchResultCount(Optional.of(indexedAssayGroups), Optional.of(strings));
        assertThat(resultCount, is(6));
    }

    @Test
    public void getTopExpressionsForGene() throws Exception {
        Collection<String> geneIds = Sets.newHashSet("AT1G02220");
        List<DiffAnalytics> expressions = subject.fetchTopExpressions(Optional.<Collection<IndexedAssayGroup>>absent(), Optional.of(geneIds));
        assertThat(expressions, Matchers.hasSize(1));
        assertThat(expressions.get(0).getBioentityId(), is("AT1G02220"));
        assertThat(expressions.get(0).getExperimentAccession(), is("E-GEOD-38400"));
    }

    @Test
    public void getResultCountForGene() throws Exception {
        Collection<IndexedAssayGroup> assayGroups = new HashSet<>();
        Collection<String> geneIds = Sets.newHashSet("AT1G02220");
        assertThat(subject.fetchResultCount(Optional.of(assayGroups), Optional.of(geneIds)), is(1));
    }

    @Test
    public void createOracleArrayForIndexedAssayGroup() throws SQLException {
        IndexedAssayGroup iag1 = new IndexedAssayGroup("EXP1", "G1");
        IndexedAssayGroup iag2 = new IndexedAssayGroup("EXP2", "G2");

        ARRAY array = subject.createOracleArrayForIndexedAssayGroup(ImmutableList.of(iag1, iag2));

        assertThat(array.getOracleArray().length, is(2));
    }

}
