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

package uk.ac.ebi.atlas.commons.readers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.readers.impl.TsvReaderImpl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TsvReaderBuilderTest {

    public static final String TSV_FILE_PATH_TEMPLATE = "tsvFilePathTemplate";
    private TsvReaderBuilder subject;

    @Mock
    private TsvReaderImpl tsvReaderMock;

    @Before
    public void setUp() throws Exception {
        subject = new TsvReaderBuilder(tsvReaderMock);
    }

    @Test
    public void testForTsvFilePathTemplate() throws Exception {

        subject.forTsvFilePathTemplate(TSV_FILE_PATH_TEMPLATE);

        verify(tsvReaderMock).setPathTemplate(TSV_FILE_PATH_TEMPLATE);
    }

    @Test
    public void testBuild() throws Exception {

        assertThat(subject.build(), is((TsvReader) tsvReaderMock));

    }
}