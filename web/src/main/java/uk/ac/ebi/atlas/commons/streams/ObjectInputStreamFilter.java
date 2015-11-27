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

package uk.ac.ebi.atlas.commons.streams;

import com.google.common.base.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public abstract class ObjectInputStreamFilter<T> implements ObjectInputStream<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectInputStreamFilter.class);

    private ObjectInputStream<T> inputStream;

    protected ObjectInputStreamFilter(ObjectInputStream<T> inputStream) {
        this.inputStream = inputStream;
    }

    public ObjectInputStream<T> getWrappedInputStream() {
        return inputStream;
    }

    @Override
    public T readNext() {

        T object;
        while ((object = inputStream.readNext()) != null) {
            if (getAcceptanceCriteria().apply(object)) {
                return object;
            }
        }
        return null;
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
        LOGGER.debug("<close> close invoked on ObjectInputStreamFilter");
    }

    protected abstract Predicate<T> getAcceptanceCriteria();


}

