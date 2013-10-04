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
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class DiffExpressionDaoIT {

    private static final String E_MTAB_1066 = "E-MTAB-1066";

    @Inject
    private DiffExpressionDao subject;


    @Test
    public void testGetExpressions() throws Exception {
        IndexedAssayGroup indexedContrast1 = new IndexedAssayGroup("E-MTAB-1066", "g2_g3");

        List<DifferentialBioentityExpression> expressions = subject.getExpressions(Lists.newArrayList(indexedContrast1));
        assertThat(expressions.size(), is(22));
        assertThat(expressions.get(0).getBioentityId(), is("FBgn0040393"));
        assertThat(expressions.get(1).getBioentityId(), is("FBgn0017561"));

    }

    @Test
    public void testGetResultCount() throws Exception {
        IndexedAssayGroup indexedContrast1 = new IndexedAssayGroup("E-MTAB-1066", "g2_g3");

        int resultCount = subject.getResultCount(Lists.newArrayList(indexedContrast1));
        assertThat(resultCount, is(22));
    }

    @Test
    public void testGetExpressionsForGene() throws Exception {
        List<DifferentialBioentityExpression> expressions = subject.getExpressions(Sets.newHashSet("AT1G02220"));
        assertThat(expressions, hasSize(2));
        assertThat(expressions.get(0).getBioentityId(), is("AT1G02220"));
        assertThat(expressions.get(0).getExperimentAccession(), is("E-GEOD-38400"));
        assertThat(expressions.get(1).getBioentityId(), is("AT1G02220"));
        assertThat(expressions.get(1).getExperimentAccession(), is("E-TABM-51"));
    }

    @Test
    public void testGetResultCountForGene() throws Exception {
        assertThat(subject.getResultCount("AT1G02220"), is(2));

    }
}
