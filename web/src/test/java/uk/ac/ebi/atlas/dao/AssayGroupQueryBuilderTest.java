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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.solr.query.conditions.IndexedAssayGroup;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(MockitoJUnitRunner.class)
public class AssayGroupQueryBuilderTest {

    private AssayGroupQueryBuilder subject;

    @Before
    public void setUp() throws Exception {
        subject = new AssayGroupQueryBuilder();

    }

    @Test
    public void testBuildIndexedContrastQuery() throws Exception {
        IndexedAssayGroup indexedContrast1 = new IndexedAssayGroup("exp1", "c1");
        IndexedAssayGroup indexedContrast2 = new IndexedAssayGroup("exp2", "c2");

        List<IndexedAssayGroup> indexedContrasts = Lists.newArrayList(indexedContrast1, indexedContrast2);
        AssayGroupQuery query = subject.withAssayGroupOrContrast(DiffExpressionDao.CONTRASTID)
                .withIndexedAssayGroupsOrContrasts(indexedContrasts)
                .withSelectPart(DiffExpressionDao.SELECT_QUERY)
                .withExtraCondition(DiffExpressionDao.ORDER_BY_PVAL).build();


        assertThat(query.getQuery(), is("SELECT IDENTIFIER, NAME, DESIGNELEMENT, ORGANISM, EXPERIMENT, CONTRASTID, PVAL, LOG2FOLD, TSTAT FROM VW_DIFFANALYTICS WHERE ((EXPERIMENT=? AND CONTRASTID=? ) OR (EXPERIMENT=? AND CONTRASTID=? )) order by PVAL"));
        assertThat(query.getParams(), is(new String[]{"exp1", "c1", "exp2", "c2"}));

    }

    @Test
    public void testBuildIndexedContrastCountQuery() throws Exception {
        IndexedAssayGroup indexedContrast1 = new IndexedAssayGroup("exp1", "c1");
        IndexedAssayGroup indexedContrast2 = new IndexedAssayGroup("exp2", "c2");

        List<IndexedAssayGroup> indexedContrasts = Lists.newArrayList(indexedContrast1, indexedContrast2);
        AssayGroupQuery query = subject.withAssayGroupOrContrast(DiffExpressionDao.CONTRASTID)
                .withIndexedAssayGroupsOrContrasts(indexedContrasts)
                .withSelectPart(DiffExpressionDao.COUNT_QUERY)
                .withExtraCondition(DiffExpressionDao.ORDER_BY_PVAL).build();

        assertThat(query.getQuery(), is("SELECT count(1) FROM VW_DIFFANALYTICS WHERE ((EXPERIMENT=? AND CONTRASTID=? ) OR (EXPERIMENT=? AND CONTRASTID=? )) order by PVAL"));
        assertThat(query.getParams(), is(new String[]{"exp1", "c1", "exp2", "c2"}));

    }

    @Test
    public void testBuildIndexedContrastQueryWithEmptyList() throws Exception {

        List<IndexedAssayGroup> indexedContrasts = Lists.newArrayList();
        AssayGroupQuery query = subject.withAssayGroupOrContrast(DiffExpressionDao.CONTRASTID)
                .withIndexedAssayGroupsOrContrasts(indexedContrasts)
                .withSelectPart(DiffExpressionDao.SELECT_QUERY)
                .withExtraCondition(DiffExpressionDao.ORDER_BY_PVAL).build();

        assertThat(query.getQuery(), is("SELECT IDENTIFIER, NAME, DESIGNELEMENT, ORGANISM, EXPERIMENT, CONTRASTID, PVAL, LOG2FOLD, TSTAT FROM VW_DIFFANALYTICS order by PVAL"));
        assertThat(query.getParams().length, is(0));

    }

    @Test
    public void testBuildAssayGroupQuery() throws Exception {
        IndexedAssayGroup indexedContrast1 = new IndexedAssayGroup("exp1", "g1");
        IndexedAssayGroup indexedContrast2 = new IndexedAssayGroup("exp2", "g2");

        List<IndexedAssayGroup> indexedContrasts = Lists.newArrayList(indexedContrast1, indexedContrast2);
        AssayGroupQuery query = subject.withAssayGroupOrContrast(BaselineExpressionDao.ASSAYGROUPID)
                .withIndexedAssayGroupsOrContrasts(indexedContrasts)
                .withSelectPart(BaselineExpressionDao.SELECT_QUERY)
                .withExtraCondition(BaselineExpressionDao.GROUP_BY_EXPERIMENT_ASSAYGROUPID).build();

        assertThat(query.getQuery(), is("SELECT EXPERIMENT, ASSAYGROUPID, COUNT(IDENTIFIER) FROM RNASEQ_BSLN_EXPRESSIONS  subpartition( ABOVE_CUTOFF ) WHERE ((EXPERIMENT=? AND ASSAYGROUPID=? ) OR (EXPERIMENT=? AND ASSAYGROUPID=? )) group by EXPERIMENT, ASSAYGROUPID"));
        assertThat(query.getParams(), is(new String[]{"exp1", "g1", "exp2", "g2"}));

    }

}
