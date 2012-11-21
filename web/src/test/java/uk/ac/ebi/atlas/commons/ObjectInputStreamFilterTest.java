/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

package uk.ac.ebi.atlas.commons;

import com.google.common.base.Predicate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStreamFilter;

import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ObjectInputStreamFilterTest {

    @Mock
    private ObjectInputStream<Object> inputStreamMock;

    @Mock
    private Predicate<Object> predicateMock;

    private Object object1 = new Object();
    private Object object2 = new Object();
    private Object object3 = new Object();


    private ObjectInputStreamFilter<Object> subject;

    @Before
    public void initSubject() throws Exception {
        subject = new ObjectInputStreamFilter<Object>(inputStreamMock){

            @Override
            protected Predicate<Object> getAcceptanceCriteria() {
                return predicateMock;
            }
        };
    }

    @Test
    public void testReadNext() throws Exception {
        //given
        given(predicateMock.apply(anyObject())).willReturn(false)
                                                .willReturn(true)
                                                .willReturn(false);
        //and
        given(inputStreamMock.readNext()).willReturn(object1)
                                         .willReturn(object2)
                                         .willReturn(object3)
                                         .willReturn(null);

        //then
        assertThat(subject.readNext(), is(object2));

        //and reading again
        assertThat(subject.readNext(), is(nullValue()));

    }

    @Test
    public void closeShouldCloseTheWrappedInputStream() throws IOException {
        //when
        subject.close();

        verify(inputStreamMock).close();
    }
}
