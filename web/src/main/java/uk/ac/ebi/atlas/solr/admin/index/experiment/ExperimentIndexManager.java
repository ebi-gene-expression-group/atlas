package uk.ac.ebi.atlas.solr.admin.index.experiment;

import uk.ac.ebi.atlas.model.ExperimentTrader;

public class ExperimentIndexManager {

    private ExperimentTrader experimentTrader;

    private FactorIndex factorIndex;


    public void updateProperties(String experimentAccession) {

    }

    public void addProperties(String experimentAccession) {

    }

    public void deleteProperties(String experimentAccession){

    }

    protected ExperimentDesignIndex getIndexer(String experimentAccession) {
        return null;
    }
}
