package uk.ac.ebi.atlas.commons.streams;

import java.io.IOException;
import java.util.function.Predicate;

public class ObjectInputStreams {
    protected ObjectInputStreams() {
        throw new UnsupportedOperationException();
    }

    public static <X> ObjectInputStream<X> filter(final ObjectInputStream<X> unfiltered, final Predicate<X> keep) {
        return new ObjectInputStream<X>() {
            @Override
            public X readNext() {
                X result;
                do {
                    result = unfiltered.readNext();
                    if (result == null) {
                        return null;
                    }
                } while (!keep.test(result));
                return result;
            }

            @Override
            public void close() throws IOException {
                unfiltered.close();
            }
        };
    }
}
