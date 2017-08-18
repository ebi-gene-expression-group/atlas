package uk.ac.ebi.atlas.commons.streams;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.function.Predicate;

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
            if (getAcceptanceCriteria().test(object)) {
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

