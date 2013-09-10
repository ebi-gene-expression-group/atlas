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

package uk.ac.ebi.atlas.solr.query;

import com.google.common.collect.Multimap;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;

import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml"})
public class FetchTooltipPropertiesIT {

    @Inject
    private SolrQueryService subject;


    @Test
    public void testFetchTooltipProperties() throws Exception {

        // given
        Multimap<String, String> properties = subject.fetchTooltipProperties("ENSMODG00000012671");

        assertThat(properties.size(), Matchers.is(24));
        assertThat(properties.get("synonym").size(), Matchers.is(5));
        assertThat(properties.get("synonym"), Matchers.hasItems("Calmbp1", "MCPH5", "ASP"));
        assertThat(properties.get("goterm"), Matchers.hasItems("oogenesis", "developmental growth", "positive regulation of neuroblast proliferation"));
        assertThat(properties.get("interproterm"), Matchers.hasItems("Calmodulin-regulated spectrin-associated protein, CH domain", "Armadillo-type fold", "IQ motif, EF-hand binding site"));
    }


}
