package uk.ac.ebi.atlas.solr.admin.index.conditions;

import uk.ac.ebi.atlas.model.Experiment;

public class RemoveConditionsFromIndexCommand<T extends Experiment> extends IndexCommand<T> {

    protected RemoveConditionsFromIndexCommand(ConditionsIndex<T> indexer, T experiment) {
        super(indexer, experiment);
    }

    @Override
    public void execute() {
        indexer.removeConditions(experiment.getAccession());
    }
}
