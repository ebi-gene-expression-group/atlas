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

package uk.ac.ebi.atlas.model.differential.microarray;

import org.apache.commons.configuration.XMLConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MicroarrayExperimentConfigurationTest {

    public static final String ARRAYDESIGN = "ARRAYDESIGN";

    @Mock
    XMLConfiguration xmlConfigurationMock;

    MicroarrayExperimentConfiguration subject;

    @Before
    public void setUp() throws Exception {
        List list = new ArrayList<>();
        list.add(ARRAYDESIGN);
        when(xmlConfigurationMock.getList("analytics/array_design")).thenReturn(list);
        subject = new MicroarrayExperimentConfiguration(xmlConfigurationMock, null);
    }

    @Test
    public void testGetArrayDesignNames() throws Exception {
        assertThat(subject.getArrayDesignNames(), hasItem(ARRAYDESIGN));
    }
}