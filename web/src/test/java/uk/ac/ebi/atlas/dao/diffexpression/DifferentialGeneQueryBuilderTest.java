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

package uk.ac.ebi.atlas.dao.diffexpression;

import com.google.common.collect.Lists;
import oracle.sql.ARRAY;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.dao.diffexpression.DiffExpressionDao;
import uk.ac.ebi.atlas.dao.diffexpression.DifferentialGeneQueryBuilder;
import uk.ac.ebi.atlas.dao.diffexpression.Query;
import uk.ac.ebi.atlas.solr.query.conditions.IndexedAssayGroup;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class DifferentialGeneQueryBuilderTest {

    private DifferentialGeneQueryBuilder subject;

    @Before
    public void setUp() throws Exception {
        subject = new DifferentialGeneQueryBuilder();

    }

    @Test
    public void selectWhereContrasts() throws Exception {
        IndexedAssayGroup indexedContrast1 = new IndexedAssayGroup("exp1", "c1");
        IndexedAssayGroup indexedContrast2 = new IndexedAssayGroup("exp2", "c2");

        List<IndexedAssayGroup> indexedContrasts = Lists.newArrayList(indexedContrast1, indexedContrast2);
        Query<Object> query = subject.withAssayGroups(indexedContrasts).buildSelect();

        assertThat(query.getQuery(), is("SELECT IDENTIFIER, NAME, DESIGNELEMENT, ORGANISM, EXPERIMENT, CONTRASTID, PVAL, LOG2FOLD, TSTAT FROM VW_DIFFANALYTICS WHERE ((EXPERIMENT=? AND CONTRASTID=? ) OR (EXPERIMENT=? AND CONTRASTID=? )) order by PVAL"));
        assertThat(query.getParameters(), contains(new Object[]{"exp1", "c1", "exp2", "c2"}));

    }

    @Test
    public void countWhereContrasts() throws Exception {
        IndexedAssayGroup indexedContrast1 = new IndexedAssayGroup("exp1", "c1");
        IndexedAssayGroup indexedContrast2 = new IndexedAssayGroup("exp2", "c2");

        List<IndexedAssayGroup> indexedContrasts = Lists.newArrayList(indexedContrast1, indexedContrast2);
        Query<Object> query = subject.withAssayGroups(indexedContrasts).buildCount();

        assertThat(query.getQuery(), is("SELECT count(1) FROM VW_DIFFANALYTICS WHERE ((EXPERIMENT=? AND CONTRASTID=? ) OR (EXPERIMENT=? AND CONTRASTID=? )) order by PVAL"));
        assertThat(query.getParameters(), contains(new Object[]{"exp1", "c1", "exp2", "c2"}));

    }


    @Test
    public void selectWhereGeneIds() throws Exception {

        List<IndexedAssayGroup> indexedContrasts = Lists.newArrayList();
        ARRAY geneIds = mock(ARRAY.class);

        Query<Object> query = subject.withAssayGroups(indexedContrasts)
                .withGeneIds(geneIds)
                .buildSelect();

        assertThat(query.getQuery(), is("SELECT IDENTIFIER, NAME, DESIGNELEMENT, ORGANISM, EXPERIMENT, CONTRASTID, PVAL, LOG2FOLD, TSTAT " +
                "FROM VW_DIFFANALYTICS JOIN TABLE(?) identifiersTable ON IDENTIFIER = identifiersTable.column_value order by PVAL"));
        assertThat(query.getParameters(), contains((Object) geneIds));

    }

    @Test
    public void selectWhereContrastsAndGeneIds() throws Exception {
        ARRAY geneIds = mock(ARRAY.class);

        IndexedAssayGroup indexedContrast1 = new IndexedAssayGroup("exp1", "g1");
        IndexedAssayGroup indexedContrast2 = new IndexedAssayGroup("exp2", "g2");

        List<IndexedAssayGroup> indexedContrasts = Lists.newArrayList(indexedContrast1, indexedContrast2);
        Query<Object> query = subject.withAssayGroups(indexedContrasts)
                .withGeneIds(geneIds)
                .buildSelect();

        assertThat(query.getQuery(), is("SELECT IDENTIFIER, NAME, DESIGNELEMENT, ORGANISM, EXPERIMENT, CONTRASTID, PVAL, LOG2FOLD, TSTAT " +
                "FROM VW_DIFFANALYTICS JOIN TABLE(?) identifiersTable ON IDENTIFIER = identifiersTable.column_value WHERE ((EXPERIMENT=? AND CONTRASTID=? ) OR (EXPERIMENT=? AND CONTRASTID=? )) " +
                "order by PVAL"));
        assertThat(query.getParameters().size(), is(5));

    }


    @Test
    public void countWhereContrastsAndGeneIds() throws Exception {
        ARRAY geneIds = mock(ARRAY.class);

        IndexedAssayGroup indexedContrast1 = new IndexedAssayGroup("exp1", "c1");
        IndexedAssayGroup indexedContrast2 = new IndexedAssayGroup("exp2", "c2");

        List<IndexedAssayGroup> indexedContrasts = Lists.newArrayList(indexedContrast1, indexedContrast2);
        Query<Object> query = subject.withAssayGroups(indexedContrasts)
                .withGeneIds(geneIds)
                .buildCount();

        assertThat(query.getQuery(), is("SELECT count(1) FROM VW_DIFFANALYTICS JOIN TABLE(?) identifiersTable ON IDENTIFIER = identifiersTable.column_value " +
                "WHERE ((EXPERIMENT=? AND CONTRASTID=? ) OR (EXPERIMENT=? AND CONTRASTID=? )) order by PVAL"));

    }

}
