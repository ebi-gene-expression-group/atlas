package uk.ac.ebi.atlas.commands;

public interface Command<T> {

    public T execute(String experimentAccession) throws GenesNotFoundException;

}
