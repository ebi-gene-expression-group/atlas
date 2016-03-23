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

package uk.ac.ebi.atlas.search.baseline;

import oracle.sql.ARRAY;
import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.search.DatabaseQuery;

import static org.hamcrest.Matchers.is;

@RunWith(MockitoJUnitRunner.class)
public class BaselineQueryBuilderTest {

    private BaselineQueryBuilder subject;

    @Before
    public void setUp() throws Exception {
        subject = new BaselineQueryBuilder();
    }

    @Test
    public void selectWhereGeneIds()  {
        ARRAY geneIds = Mockito.mock(ARRAY.class);

        DatabaseQuery<Object> databaseQuery = subject.withGeneIds(geneIds).build();

        MatcherAssert.assertThat(databaseQuery.getQuery(), is("SELECT experiment, assaygroupid, SumOfExpressions, NumberOfGenesExpressed from (SELECT rbe.experiment, rbe.assaygroupid, SUM(rbe.expression) as SumOfExpressions, count(distinct IDENTIFIER) as NumberOfGenesExpressed from RNASEQ_BSLN_EXPRESSIONS rbe " +
                "JOIN TABLE(?) identifiersTable ON rbe.IDENTIFIER = identifiersTable.column_value WHERE rbe.expression > 0.5 and rbe.experiment = (SELECT accession FROM experiment WHERE type = 'RNASEQ_MRNA_BASELINE' AND private = 'F' AND accession = rbe.experiment) GROUP BY GROUPING SETS (rbe.experiment, (rbe.experiment, rbe.assaygroupid)) UNION ALL " +
                "SELECT rbe.experiment, rbe.assaygroupid, SUM(rbe.expression) as SumOfExpressions, count(distinct IDENTIFIER) as NumberOfGenesExpressed from RNASEQ_BSLN_EXPRESSIONS rbe " +
                "JOIN TABLE(?) identifiersTable ON rbe.IDENTIFIER = identifiersTable.column_value WHERE rbe.experiment = (SELECT accession FROM experiment WHERE type = 'PROTEOMICS_BASELINE' AND private = 'F' AND accession = rbe.experiment) GROUP BY GROUPING SETS (rbe.experiment, (rbe.experiment, rbe.assaygroupid)) " +
                ") ORDER BY experiment, assaygroupid desc"));
        MatcherAssert.assertThat(databaseQuery.getParameters(), IsIterableContainingInOrder.contains((Object) geneIds, (Object) geneIds));

    }
}
