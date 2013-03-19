package uk.ac.ebi.atlas.commands;

public interface CommandExecutor<T> {

    public T execute(String experimentAccession) throws GenesNotFoundException;

}
