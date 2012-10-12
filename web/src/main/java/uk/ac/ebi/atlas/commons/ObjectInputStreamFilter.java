package uk.ac.ebi.atlas.commons;

import com.google.common.base.Predicate;
import org.apache.log4j.Logger;

import java.io.IOException;

public abstract class ObjectInputStreamFilter<T> implements ObjectInputStream<T> {
    private static final Logger logger = Logger.getLogger(ObjectInputStreamFilter.class);

    private ObjectInputStream<T> inputStream;

    public ObjectInputStreamFilter(ObjectInputStream<T> inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public T readNext() {

        T object;
        while ((object = inputStream.readNext())!=null){
            if (getAcceptanceCriteria().apply(object)) {
                return object;
            }
        }
        return null;
    }

    private Object findNextAcceptableObject(){
        return null;
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
        logger.info("<close> close invoked on ObjectInputStreamFilter");
    }

    protected abstract Predicate<T> getAcceptanceCriteria();


}

