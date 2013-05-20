package uk.ac.ebi.atlas.commands;

public interface Command<T> {

    T execute(String experimentAccession) throws GenesNotFoundException;

}
