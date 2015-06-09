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

package uk.ac.ebi.atlas.search;

import com.google.common.collect.ImmutableList;
import oracle.sql.ARRAY;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.solr.query.conditions.IndexedAssayGroup;

import javax.inject.Inject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml", "classpath:oracleUcpContext.xml"})
public class DatabaseQueryPrinterIT {

    @Inject
    OracleObjectFactory subject;

    @Test
    public void oracleArrayToString() throws Exception {
        ARRAY oracleArray = subject.createOracleArrayForIdentifiers(ImmutableList.of("A", "B", "C", "D", "E"));
        assertThat(DatabaseQueryPrinter.oracleArrayToString(oracleArray), is("IDENTIFIERS_TABLE('A', 'B', 'C', 'D', 'E')"));
    }

    @Test
    public void oracleObjectArrayToString() throws Exception {
        ARRAY oracleArray = subject.createOracleArrayForIndexedAssayGroup(ImmutableList.of(new IndexedAssayGroup("EXP1", "G1"), new IndexedAssayGroup("EXP2", "G2")));
        assertThat(DatabaseQueryPrinter.oracleArrayToString(oracleArray), is("EXPR_CONTRAST_TABLE(EXPR_CONTRAST('EXP1', 'G1'), EXPR_CONTRAST('EXP2', 'G2'))"));
    }

    @Test
    public void printDatabaseQueryWithGeneIds() throws Exception {
        ARRAY experimentContrasts = subject.createOracleArrayForIndexedAssayGroup(ImmutableList.of(new IndexedAssayGroup("EXP1", "G1"), new IndexedAssayGroup("EXP2", "G2")));
        ARRAY geneIds = subject.createOracleArrayForIdentifiers(ImmutableList.of("A", "B", "C", "D", "E"));

        DatabaseQuery<Object> databaseQuery = new DatabaseQuery<>();

        databaseQuery.appendToQueryString("JOIN TABLE(?) exprContrast ON VW_DIFFANALYTICS.EXPERIMENT = exprContrast.EXPERIMENT AND VW_DIFFANALYTICS.CONTRASTID = exprContrast.CONTRASTID ");
        databaseQuery.addParameter(experimentContrasts);

        databaseQuery.appendToQueryString("JOIN TABLE(?) identifiersTable ON IDENTIFIER = identifiersTable.column_value ");
        databaseQuery.addParameter(geneIds);

        assertThat(databaseQuery.print(), is("JOIN TABLE(EXPR_CONTRAST_TABLE(EXPR_CONTRAST('EXP1', 'G1'), EXPR_CONTRAST('EXP2', 'G2'))) exprContrast ON VW_DIFFANALYTICS.EXPERIMENT = exprContrast.EXPERIMENT AND VW_DIFFANALYTICS.CONTRASTID = exprContrast.CONTRASTID JOIN TABLE(IDENTIFIERS_TABLE('A', 'B', 'C', 'D', 'E')) identifiersTable ON IDENTIFIER = identifiersTable.column_value "));
    }

}
