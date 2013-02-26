/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

package uk.ac.ebi.atlas.geneindex;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import java.net.URISyntaxException;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class IndexClientTestIT {

    @Inject
    private SolrClient subject;

    private static final String QUERY = "GO:0008134 \"p53 binding\"";

    private static final String SPECIES = "homo sapiens";

    @Test
    public void testFindGeneIdJsonValidQuery() throws URISyntaxException {
        //given
        Set<String> result = subject.findGeneIds(QUERY, SPECIES);

        //some genes are found
        assertThat(result.iterator().next(), startsWith("ENSG"));
    }


    @Test
    public void testFindGeneIdJsonNotExistingQuery() throws URISyntaxException {
        //given
        String query = "\"NOT THERE\"";

        Set<String> result = subject.findGeneIds(query, SPECIES);

        //no genes are found
        assertThat(result, is(empty()));
    }


}