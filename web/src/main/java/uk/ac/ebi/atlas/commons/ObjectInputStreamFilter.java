package uk.ac.ebi.atlas.commons;

import com.google.common.base.Predicate;

import java.io.IOException;

public abstract class ObjectInputStreamFilter<T> implements ObjectInputStream{

    private ObjectInputStream<T> inputStream;

    public ObjectInputStreamFilter(ObjectInputStream<T> inputStream){
        this.inputStream = inputStream;
    }

    @Override
    public T readNext() {
        if (inputStream.readNext() == null){
            return null;
        }
        T object = inputStream.readNext();
        if (getAcceptanceCriteria().apply(object)){
            return object;
        }
        return readNext();
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
    }

    protected abstract Predicate<T> getAcceptanceCriteria();
}

