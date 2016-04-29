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

package uk.ac.ebi.atlas.profiles.baseline;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.Factor;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class BaselineProfilesInputStreamIT {

    public static final String EXPERIMENT_ACCESSION = "E-MTAB-513";

    @Resource(name = "baselineProfileInputStreamFactory")
    private BaselineProfileInputStreamFactory inputStreamFactory;

    private ObjectInputStream<BaselineProfile> subject;

    String queryFactorType = "ORGANISM_PART";
    double defaultCutoff = 0.5;
    Set<Factor> noFilterFactors = Collections.emptySet();

    @Before
    public void setUp() throws Exception {
        setUp(defaultCutoff);
    }

    public void setUp(double cutoff) throws Exception {
        subject = inputStreamFactory.createBaselineProfileInputStream(EXPERIMENT_ACCESSION, queryFactorType, cutoff,
                noFilterFactors);
    }



    @Test
    public void readNextShouldReturnFalseGivenAllExpressionLevelsHaveBeenRead() throws Exception {
        long countProfiles = 0;
        while (subject.readNext() != null) {
            ++countProfiles;
        }

        assertThat(countProfiles, greaterThan(10L));


        setUp(5);

        long countProfiles2 = 0;
        while (subject.readNext() != null) {
            ++countProfiles2;
        }

        assertThat(countProfiles2, greaterThan(10L));

        assertTrue(countProfiles>countProfiles2);
    }


    @Test(expected = IllegalStateException.class)
    public void givenTheReaderHasBeenClosedReadNextShouldThrowIllegalStateException() throws Exception {
        //given
        subject.close();
        //when
        subject.readNext();
    }


    @Test
    public void closingTwiceShouldNotThrowException() throws Exception {
        //given
        subject.close();
        //when
        subject.close();
    }

}
