package uk.ac.ebi.atlas.commons;

import com.google.common.base.Predicate;

import java.io.IOException;

public abstract class ObjectInputStreamFilter<T> implements ObjectInputStream<T> {

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
    }

    protected abstract Predicate<T> getAcceptanceCriteria();


}

