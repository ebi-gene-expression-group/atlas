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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.baseline.BaselineBioentitiesCount;
import uk.ac.ebi.atlas.solr.query.conditions.IndexedAssayGroup;

import javax.inject.Inject;
import java.util.SortedSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class BaselineExpressionDaoIT {

    private static final String E_MTAB_599 = "E-MTAB-599";

    @Inject
    private BaselineExpressionDao subject;


    @Test
    public void testGetExpressions() throws Exception {
        IndexedAssayGroup assayGroup = new IndexedAssayGroup(E_MTAB_599, "g2");

        SortedSet<BaselineBioentitiesCount> bioentitiesCounts = subject.getBioentitiesCounts(Lists.newArrayList(assayGroup));
        assertThat(bioentitiesCounts, hasSize(1));
        assertThat(bioentitiesCounts.first().getExperimentAccession(), is(E_MTAB_599));
        assertThat(bioentitiesCounts.first().getCount(), is(2856));
        assertThat(bioentitiesCounts.first().getSpecies(), is("Mus musculus"));

    }



}
