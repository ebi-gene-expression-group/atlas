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

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItemInArray;
import static org.hamcrest.Matchers.is;

@RunWith(MockitoJUnitRunner.class)
public class TsvReaderImplTest {

    private static final String PATH_TEMPLATE = "A_PATH_TEMPLATE";
    private static final String EXPERIMENT_ACCESSION = "experimentAccession";

    private TsvReaderImpl subject;

    private InputStreamReader tsvFileInputStreamReaderMock;

    @Before
    public void setUp() throws Exception {

        String data = "#Comment\nColumn1\tColumn2\tColumn3\nData1\tData2\tData3";

        tsvFileInputStreamReaderMock = new InputStreamReader(new ByteArrayInputStream(data.getBytes()));

        subject = new TsvReaderImpl(tsvFileInputStreamReaderMock);
    }

    @Test
    public void testReadAll() {
        List<String[]> result = subject.readAll();
        assertThat(result.size(), is(2));
        assertThat(result.get(0), hasItemInArray("Column1"));
        assertThat(result.get(0), hasItemInArray("Column2"));
        assertThat(result.get(0), hasItemInArray("Column3"));
        assertThat(result.get(1), hasItemInArray("Data1"));
        assertThat(result.get(1), hasItemInArray("Data2"));
        assertThat(result.get(1), hasItemInArray("Data3"));
    }

    @Test
    public void testReadLine() {
        String[] result = subject.readLine(2);
        assertThat(result.length, is(3));
        assertThat(result, hasItemInArray("Data1"));
        assertThat(result, hasItemInArray("Data2"));
        assertThat(result, hasItemInArray("Data3"));
    }

    @Test
    public void testReadAndFilter() {
        TsvReaderImpl.IsCommentPredicate commentPredicatePredicate = new TsvReaderImpl.IsCommentPredicate();
        List<String[]> result = subject.readAndFilter(commentPredicatePredicate);
        assertThat(result.size(), is(1));
        assertThat(result.get(0), hasItemInArray("#Comment"));
    }

    @Test
    public void testIsComment() {
        TsvReaderImpl.IsCommentPredicate commentPredicatePredicate = new TsvReaderImpl.IsCommentPredicate();
        assertThat(commentPredicatePredicate.apply("  #  Xyz"), is(true));
        assertThat(commentPredicatePredicate.apply(" #Xyz"), is(true));
        assertThat(commentPredicatePredicate.apply("#Xyz"), is(true));
    }

    @Test
    public void testIsNotComment() {
        TsvReaderImpl.IsNotCommentPredicate isNotCommentPredicate = new TsvReaderImpl.IsNotCommentPredicate();
        assertThat(isNotCommentPredicate.apply("  #  Xyz"), is(false));
        assertThat(isNotCommentPredicate.apply(" #Xyz"), is(false));
        assertThat(isNotCommentPredicate.apply("#Xyz"), is(false));
        assertThat(isNotCommentPredicate.apply("Xyz"), is(true));
        assertThat(isNotCommentPredicate.apply("Xy#z"), is(true));
        assertThat(isNotCommentPredicate.apply("Xyz # "), is(true));
    }
}
