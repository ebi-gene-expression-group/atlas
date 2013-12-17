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

package uk.ac.ebi.atlas.dao;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.differential.DifferentialBioentityExpression;
import uk.ac.ebi.atlas.solr.query.conditions.IndexedAssayGroup;

import javax.inject.Inject;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class DiffExpressionDaoIT {

    @Inject
    private DiffExpressionDao subject;


    @Test
    public void testGetTopExpressions() throws Exception {
        IndexedAssayGroup indexedContrast1 = new IndexedAssayGroup("E-MTAB-1066", "g2_g3");

        Collection<IndexedAssayGroup> contrasts = Lists.newArrayList(indexedContrast1);
        Collection<String> geneIds = new HashSet<>();

        List<DifferentialBioentityExpression> expressions = subject.getTopExpressions(Optional.of(contrasts), Optional.of(geneIds));
        assertThat(expressions.size(), is(16));
        assertThat(expressions.get(0).getBioentityId(), is("FBgn0040393"));
        assertThat(expressions.get(1).getBioentityId(), is("FBgn0017561"));

    }

    @Test
    public void testGetResultCount() throws Exception {
        IndexedAssayGroup indexedContrast1 = new IndexedAssayGroup("E-MTAB-1066", "g2_g3");

        Collection<IndexedAssayGroup> indexedAssayGroups = Lists.newArrayList(indexedContrast1);
        Collection<String> strings = new HashSet<>();
        int resultCount = subject.getResultCount(Optional.of(indexedAssayGroups), Optional.of(strings));
        assertThat(resultCount, is(16));
    }

    @Test
    public void testGetTopExpressionsForGene() throws Exception {
        IndexedAssayGroup indexedContrast1 = new IndexedAssayGroup("E-MTAB-1066", "g2_g3");

        Collection<String> geneIds = Sets.newHashSet("AT1G02220");
        List<DifferentialBioentityExpression> expressions = subject.getTopExpressions(Optional.<Collection<IndexedAssayGroup>>absent(), Optional.of(geneIds));
        assertThat(expressions, hasSize(2));
        assertThat(expressions.get(0).getBioentityId(), is("AT1G02220"));
        assertThat(expressions.get(0).getExperimentAccession(), is("E-GEOD-38400"));
        assertThat(expressions.get(1).getBioentityId(), is("AT1G02220"));
        assertThat(expressions.get(1).getExperimentAccession(), is("E-TABM-51"));
    }

    @Test
    public void testGetResultCountForGene() throws Exception {
        Collection<IndexedAssayGroup> assayGroups = new HashSet<>();
        Collection<String> geneIds = Sets.newHashSet("AT1G02220");
        assertThat(subject.getResultCount(Optional.of(assayGroups), Optional.of(geneIds)), is(2));

    }
}
