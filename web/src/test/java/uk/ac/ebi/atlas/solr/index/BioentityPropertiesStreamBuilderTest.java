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

package uk.ac.ebi.atlas.solr.index;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(MockitoJUnitRunner.class)
public class BioentityPropertiesStreamBuilderTest {
    private static final String ANOPHELES_GAMBIAE = "anopheles gambiae";

    private BioentityPropertiesStreamBuilder subject;

    @Mock
    private BioentityPropertiesBuilder bioentityPropertiesBuilderMock;

    @Before
    public void initSubject() {

        subject = new BioentityPropertiesStreamBuilder(bioentityPropertiesBuilderMock);

    }

    @Test
    public void testGetFileName() throws Exception {
        //when
        Path path = Paths.get("directory", "anopheles_gambiae.A-AFFY-102.tsv");
        subject.forPath(path);

        String fileName = subject.getFileName();
        assertThat(fileName, is("anopheles_gambiae.A-AFFY-102.tsv"));
    }

    @Test
    public void testGetSpecies() throws Exception {
        //when
        Path path = Paths.get("directory", "anopheles_gambiae.A-AFFY-102.tsv");
        subject.forPath(path);

        String fileName = subject.getSpecies();
        assertThat(fileName, is(ANOPHELES_GAMBIAE));
    }
}
