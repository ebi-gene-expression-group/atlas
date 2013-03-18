package uk.ac.ebi.atlas.commands;

import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;

public interface CommandExecutor<T, K> {

    public T execute(ObjectInputStream<K> inputStream);

}
