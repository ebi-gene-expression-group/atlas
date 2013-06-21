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

package uk.ac.ebi.atlas.configuration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.ExperimentType;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentCheckerTest {

    private static final String EXPERIMENT_ACCESSION = "EXPERIMENT_ACCESSION";
    private static final String BASELINE_TYPE = "BASELINE";
    private static final String NON_EXISTING_TYPE = "NON-EXISTING-TYPE";

    @Mock
    private ConfigurationDao configurationDaoMock;

    @Mock
    private ExperimentConfiguration experimentConfigurationMock;

    private ExperimentChecker subject;

    @Before
    public void setUp() throws Exception {

        when(configurationDaoMock.getExperimentConfiguration(EXPERIMENT_ACCESSION)).thenReturn(null);

        subject = new ExperimentChecker(configurationDaoMock);
    }

    @Test
    public void testCheckAccessionAndType() throws Exception {
        assertThat(subject.checkAccessionAndType(EXPERIMENT_ACCESSION, BASELINE_TYPE), is(ExperimentType.BASELINE));
    }

    @Test(expected = IllegalStateException.class)
    public void testCheckAccessionAndTypeEmptyAccession() throws Exception {
        subject.checkAccessionAndType("", BASELINE_TYPE);
    }

    @Test(expected = IllegalStateException.class)
    public void testCheckAccessionAndTypeUnknownType() throws Exception {
        subject.checkAccessionAndType(EXPERIMENT_ACCESSION, NON_EXISTING_TYPE);
    }

    @Test(expected = IllegalStateException.class)
    public void testCheckAccessionAndTypeExistingAccession() throws Exception {
        when(configurationDaoMock.getExperimentConfiguration(EXPERIMENT_ACCESSION)).thenReturn(experimentConfigurationMock);
        subject.checkAccessionAndType(EXPERIMENT_ACCESSION, NON_EXISTING_TYPE);
    }
}