package uk.ac.ebi.atlas.commons.streams;

import java.io.IOException;
import java.util.Iterator;

public class ObjectInputStreamTest {

    public static <T> ObjectInputStream<T> convert(final Iterable<T> iterable){
        final Iterator<T> it = iterable.iterator();
        return new ObjectInputStream<T>() {
            @Override
            public T readNext() {
                return it.hasNext() ? it.next() : null;
            }

            @Override
            public void close() throws IOException {

            }
        };
    }
}
