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

package uk.ac.ebi.atlas.transcript;

import com.google.common.collect.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.baseline.TranscriptProfile;
import uk.ac.ebi.atlas.model.baseline.TranscriptProfiles;

import javax.inject.Inject;
import javax.sql.DataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class GeneProfileDaoIT {

    @Inject
    private GeneProfileDao subject;

    @Inject
    DataSource dataSource;

    private TranscriptProfiles testData;

    @Before
    public void setup() {
        TranscriptProfile profile = new TranscriptProfile("identifier", Lists.newArrayList(1.0, 2.0, 0.0));
        testData = new TranscriptProfiles(Lists.newArrayList(profile));
    }

    @After
    public void tearDown() {
        JdbcTemplate template = new JdbcTemplate(dataSource);
        template.execute("delete from experiment_transcripts");
    }

    @Test
    public void testGetTranscriptProfiles() throws Exception {

    }

    @Test
    public void testAddTranscriptProfiles() throws Exception {

        subject.addTranscriptProfiles("accession", "geneId", testData);

    }
}