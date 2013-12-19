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

package uk.ac.ebi.atlas.commons.streams;

import java.io.IOException;
import java.util.Enumeration;

/**
 * Concatenates multiple ObjectInputStreams. When one stream ends, continues
 * from the next stream.
 *
 * (This code was adapted from JAVA's SequenceInputStream class).
 *
 * @param <T>
 */
public class SequenceObjectInputStream<T> implements ObjectInputStream<T> {

    Enumeration<ObjectInputStream<T>> e;
    ObjectInputStream<T> in;

    public SequenceObjectInputStream(Enumeration<ObjectInputStream<T>> e) {
        this.e = e;
        try {
            nextStream();
        } catch (IOException ex) {
            // This should never happen
            throw new Error("panic");
        }
    }

    /**
     * Continues reading in the next stream if an EOF is reached.
     */
    final void nextStream() throws IOException {
        if (in != null) {
            in.close();
        }

        if (e.hasMoreElements()) {
            in = e.nextElement();
            if (in == null)
                throw new NullPointerException();
        } else in = null;

    }

    @Override
    public T readNext() {
        if (in == null) {
            return null;
        }
        T c = in.readNext();
        if (c == null) {
            try {
                nextStream();
            } catch (IOException ex) {
                throw new IllegalStateException("Next stream failed.", ex);
            }
            return readNext();
        }
        return c;
    }

    @Override
    public void close() throws IOException {
        do {
            nextStream();
        } while (in != null);
    }
}