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

package uk.ac.ebi.atlas.experimentloader.experimentdesign.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml"})
public class BioontologyClientIT {

    @Inject
    private BioontologyClient subject;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void isValidShouldSucceed() throws Exception {
        boolean valid = subject.isValid("micromole per kilogram");
        assertThat(valid, is(true));
    }

    @Test
    public void isValidShouldReturnFalse() throws Exception {
        boolean valid = subject.isValid("icecream per pizza");
        assertThat(valid, is(false));

        valid = subject.isValid("");
        assertThat(valid, is(false));

        valid = subject.isValid(null);
        assertThat(valid, is(false));
    }

}
