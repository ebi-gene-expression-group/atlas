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

import com.google.common.collect.Lists;
import oracle.sql.ARRAY;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.solr.query.conditions.IndexedAssayGroup;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class DiffAnalyticsQueryBuilderTest {

    private DiffAnalyticsQueryBuilder subject;

    @Before
    public void setUp() throws Exception {
        subject = new DiffAnalyticsQueryBuilder();

    }

    @Test
    public void selectWhereContrasts() throws Exception {
        IndexedAssayGroup indexedContrast1 = new IndexedAssayGroup("exp1", "c1");
        IndexedAssayGroup indexedContrast2 = new IndexedAssayGroup("exp2", "c2");

        List<IndexedAssayGroup> indexedContrasts = Lists.newArrayList(indexedContrast1, indexedContrast2);
        DatabaseQuery<Object> databaseQuery = subject.withAssayGroups(indexedContrasts).buildSelect();

        MatcherAssert.assertThat(databaseQuery.getQuery(), Matchers.is("SELECT IDENTIFIER, NAME, DESIGNELEMENT, ORGANISM, EXPERIMENT, CONTRASTID, PVAL, LOG2FOLD, TSTAT FROM VW_DIFFANALYTICS JOIN EXPERIMENT on VW_DIFFANALYTICS.EXPERIMENT = EXPERIMENT.ACCESSION AND PRIVATE = 'F' WHERE ((EXPERIMENT=? AND CONTRASTID=? ) OR (EXPERIMENT=? AND CONTRASTID=? )) order by abs(LOG2FOLD) desc"));
        MatcherAssert.assertThat(databaseQuery.getParameters(), IsIterableContainingInOrder.contains(new Object[]{"exp1", "c1", "exp2", "c2"}));

    }

    @Test
    public void countWhereContrasts() throws Exception {
        IndexedAssayGroup indexedContrast1 = new IndexedAssayGroup("exp1", "c1");
        IndexedAssayGroup indexedContrast2 = new IndexedAssayGroup("exp2", "c2");

        List<IndexedAssayGroup> indexedContrasts = Lists.newArrayList(indexedContrast1, indexedContrast2);
        DatabaseQuery<Object> databaseQuery = subject.withAssayGroups(indexedContrasts).buildCount();

        MatcherAssert.assertThat(databaseQuery.getQuery(), Matchers.is("SELECT count(1) FROM VW_DIFFANALYTICS JOIN EXPERIMENT on VW_DIFFANALYTICS.EXPERIMENT = EXPERIMENT.ACCESSION AND PRIVATE = 'F' WHERE ((EXPERIMENT=? AND CONTRASTID=? ) OR (EXPERIMENT=? AND CONTRASTID=? )) order by abs(LOG2FOLD) desc"));
        MatcherAssert.assertThat(databaseQuery.getParameters(), IsIterableContainingInOrder.contains(new Object[]{"exp1", "c1", "exp2", "c2"}));

    }


    @Test
    public void selectWhereGeneIds() throws Exception {

        List<IndexedAssayGroup> indexedContrasts = Lists.newArrayList();
        ARRAY geneIds = Mockito.mock(ARRAY.class);

        DatabaseQuery<Object> databaseQuery = subject.withAssayGroups(indexedContrasts)
                .withGeneIds(geneIds)
                .buildSelect();

        MatcherAssert.assertThat(databaseQuery.getQuery(), Matchers.is("SELECT IDENTIFIER, NAME, DESIGNELEMENT, ORGANISM, EXPERIMENT, CONTRASTID, PVAL, LOG2FOLD, TSTAT " +
                "FROM VW_DIFFANALYTICS JOIN EXPERIMENT on VW_DIFFANALYTICS.EXPERIMENT = EXPERIMENT.ACCESSION AND PRIVATE = 'F' JOIN TABLE(?) identifiersTable ON IDENTIFIER = identifiersTable.column_value order by abs(LOG2FOLD) desc"));
        MatcherAssert.assertThat(databaseQuery.getParameters(), IsIterableContainingInOrder.contains((Object) geneIds));

    }

    @Test
    public void selectWhereContrastsAndGeneIds() throws Exception {
        ARRAY geneIds = Mockito.mock(ARRAY.class);

        IndexedAssayGroup indexedContrast1 = new IndexedAssayGroup("exp1", "g1");
        IndexedAssayGroup indexedContrast2 = new IndexedAssayGroup("exp2", "g2");

        List<IndexedAssayGroup> indexedContrasts = Lists.newArrayList(indexedContrast1, indexedContrast2);
        DatabaseQuery<Object> databaseQuery = subject.withAssayGroups(indexedContrasts)
                .withGeneIds(geneIds)
                .buildSelect();

        MatcherAssert.assertThat(databaseQuery.getQuery(), Matchers.is("SELECT IDENTIFIER, NAME, DESIGNELEMENT, ORGANISM, EXPERIMENT, CONTRASTID, PVAL, LOG2FOLD, TSTAT " +
                "FROM VW_DIFFANALYTICS JOIN EXPERIMENT on VW_DIFFANALYTICS.EXPERIMENT = EXPERIMENT.ACCESSION AND PRIVATE = 'F' JOIN TABLE(?) identifiersTable ON IDENTIFIER = identifiersTable.column_value WHERE ((EXPERIMENT=? AND CONTRASTID=? ) OR (EXPERIMENT=? AND CONTRASTID=? )) " +
                "order by abs(LOG2FOLD) desc"));
        MatcherAssert.assertThat(databaseQuery.getParameters().size(), Matchers.is(5));

    }


    @Test
    public void countWhereContrastsAndGeneIds() throws Exception {
        ARRAY geneIds = Mockito.mock(ARRAY.class);

        IndexedAssayGroup indexedContrast1 = new IndexedAssayGroup("exp1", "c1");
        IndexedAssayGroup indexedContrast2 = new IndexedAssayGroup("exp2", "c2");

        List<IndexedAssayGroup> indexedContrasts = Lists.newArrayList(indexedContrast1, indexedContrast2);
        DatabaseQuery<Object> databaseQuery = subject.withAssayGroups(indexedContrasts)
                .withGeneIds(geneIds)
                .buildCount();

        MatcherAssert.assertThat(databaseQuery.getQuery(), Matchers.is("SELECT count(1) FROM VW_DIFFANALYTICS JOIN EXPERIMENT on VW_DIFFANALYTICS.EXPERIMENT = EXPERIMENT.ACCESSION AND PRIVATE = 'F' JOIN TABLE(?) identifiersTable ON IDENTIFIER = identifiersTable.column_value " +
                "WHERE ((EXPERIMENT=? AND CONTRASTID=? ) OR (EXPERIMENT=? AND CONTRASTID=? )) order by abs(LOG2FOLD) desc"));

    }

}
