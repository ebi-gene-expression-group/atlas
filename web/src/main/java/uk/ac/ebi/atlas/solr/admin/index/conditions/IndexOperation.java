package uk.ac.ebi.atlas.solr.admin.index.conditions;

import uk.ac.ebi.atlas.model.Experiment;

public enum IndexOperation {
    ADD {
        @Override
        <T extends Experiment> IndexCommand<T> getIndexCommand(T experiment, ConditionsIndex<T> indexer) {
            return new AddIndexCommand<T>(indexer, experiment);
        }
    },
    UPDATE {
        @Override
        <T extends Experiment> IndexCommand<T> getIndexCommand(T experiment, ConditionsIndex<T> indexer) {
            return new UpdateIndexCommand<T>(indexer, experiment);
        }
    },
    REMOVE {
        @Override
        <T extends Experiment> IndexCommand<T> getIndexCommand(T experiment, ConditionsIndex<T> indexer) {
            return new RemoveConditionsFromIndexCommand<T>(indexer, experiment);
        }
    };

    abstract <T extends Experiment>IndexCommand<T> getIndexCommand(T experiment, ConditionsIndex<T> indexer);
}
