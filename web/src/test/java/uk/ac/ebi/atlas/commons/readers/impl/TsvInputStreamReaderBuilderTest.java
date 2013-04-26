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

package uk.ac.ebi.atlas.commons.readers.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.nio.file.NoSuchFileException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@RunWith(MockitoJUnitRunner.class)
public class TsvInputStreamReaderBuilderTest {

    public static final String EXPERIMENT_ACCESSION = "experimentAccession";
    private TsvInputStreamReaderBuilder subject;

    @Before
    public void setUp() throws Exception {
        subject = new TsvInputStreamReaderBuilder();
    }

    @Test
    public void testWithPathTemplate() throws Exception {
        subject.withPathTemplate("{0}");
        assertThat(subject.getPathString(EXPERIMENT_ACCESSION), is(EXPERIMENT_ACCESSION));
    }

    @Test
    public void testForExperimentAccession() throws Exception {
        subject.withPathTemplate("{0}").forExperimentAccession(EXPERIMENT_ACCESSION);
        assertThat(subject.getPathString(EXPERIMENT_ACCESSION), is(EXPERIMENT_ACCESSION));
        assertThat(subject.getFileSystemPath().toString(), is(EXPERIMENT_ACCESSION));
    }

    @Test(expected = NoSuchFileException.class)
    public void testBuild() throws Exception {
        subject.withPathTemplate("{0}").forExperimentAccession(EXPERIMENT_ACCESSION).build();
    }

    @Test
    public void testGetFileSystemPath() throws Exception {
        assertThat(subject.getFileSystemPath(), is(nullValue()));
        subject.withPathTemplate("{0}").forExperimentAccession(EXPERIMENT_ACCESSION);
        assertThat(subject.getFileSystemPath().toString(), is(EXPERIMENT_ACCESSION));
    }
}