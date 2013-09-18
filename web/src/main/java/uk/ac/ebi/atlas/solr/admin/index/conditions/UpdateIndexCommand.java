package uk.ac.ebi.atlas.solr.admin.index.conditions;

import uk.ac.ebi.atlas.model.Experiment;

public class UpdateIndexCommand<T extends Experiment> extends IndexCommand<T> {

    public UpdateIndexCommand(ConditionsIndex<T> indexer, T experiment) {
        super(indexer, experiment);
    }

    @Override
    public void execute() {
        indexer.updateConditions(experiment);
    }
}
