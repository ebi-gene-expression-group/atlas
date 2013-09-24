package uk.ac.ebi.atlas.solr.admin.index.conditions;

import uk.ac.ebi.atlas.model.Experiment;

public abstract class IndexCommand<T extends Experiment>{

    protected ConditionsIndex<T> indexer;
    protected T experiment;

    protected IndexCommand(ConditionsIndex<T> indexer, T experiment) {
        this.indexer = indexer;
        this.experiment = experiment;
    }

    public abstract void execute();
}
