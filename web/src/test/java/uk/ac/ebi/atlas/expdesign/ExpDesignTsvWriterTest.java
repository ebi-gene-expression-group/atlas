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

package uk.ac.ebi.atlas.expdesign;

import au.com.bytecode.opencsv.CSVWriter;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ExpDesignTsvWriterTest {

    private static final String EXPERIMENT_ACCESSION = "EXPERIMENT_ACCESSION";

    private ExpDesignTsvWriter subject;

    @Before
    public void setUp() throws Exception {
        subject = new ExpDesignTsvWriter();
        subject.setPathTemplate("{0}");
    }

    @Test
    public void testForExperimentAccession() throws Exception {
        CSVWriter csvWriter = subject.forExperimentAccession(EXPERIMENT_ACCESSION);
        assertThat(csvWriter, is(not(nullValue())));
        assertThat(subject.getFileAbsolutePath(), is(not(nullValue())));
        assertThat(subject.getFileAbsolutePath(), endsWith(EXPERIMENT_ACCESSION));
        File file = new File(subject.getFileAbsolutePath());
        assertThat(file.exists(), is(true));
        assertThat(file.delete(), is(true));
    }

    @Test
    public void testGetFileAbsolutePath() throws Exception {
        assertThat(subject.getFileAbsolutePath(), is(nullValue()));
    }

    @Test
    public void testGetPathString() throws Exception {
        assertThat(subject.getPathString(EXPERIMENT_ACCESSION), is(EXPERIMENT_ACCESSION));
    }
}