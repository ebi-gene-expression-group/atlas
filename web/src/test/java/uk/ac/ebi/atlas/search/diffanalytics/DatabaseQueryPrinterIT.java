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
import com.google.common.collect.Sets;
import oracle.sql.ARRAY;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.solr.query.conditions.IndexedAssayGroup;

import javax.inject.Inject;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class DatabaseQueryPrinterIT {

    @Inject
    DiffAnalyticsDao diffAnalyticsDao;

    @Test
    public void oracleArrayToString() throws Exception {
        ARRAY oracleArray = diffAnalyticsDao.createOracleArrayForIdentifiers(ImmutableList.of("A", "B", "C", "D", "E"));
        assertThat(DatabaseQueryPrinter.oracleArrayToString(oracleArray), is("IDENTIFIERS_TABLE('A', 'B', 'C', 'D', 'E')"));
    }

    @Test
    public void printDatabaseQueryWithGeneIds() throws Exception {
        IndexedAssayGroup iag1 = new IndexedAssayGroup("EXP1", "G1");
        DatabaseQuery<Object> databaseQuery = diffAnalyticsDao.buildSelect(Optional.of(Collections.singleton(iag1)), Optional.of(ImmutableList.of("A", "B", "C", "D", "E")));

        assertThat(databaseQuery.print(), is("SELECT IDENTIFIER, NAME, ORGANISM, EXPERIMENT, CONTRASTID, PVAL, LOG2FOLD, TSTAT FROM VW_DIFFANALYTICS JOIN EXPERIMENT on VW_DIFFANALYTICS.EXPERIMENT = EXPERIMENT.ACCESSION AND PRIVATE = 'F' JOIN TABLE(IDENTIFIERS_TABLE('A', 'B', 'C', 'D', 'E')) identifiersTable ON IDENTIFIER = identifiersTable.column_value WHERE ((EXPERIMENT='EXP1' AND CONTRASTID='G1' )) order by abs(LOG2FOLD) desc"));
    }

}
