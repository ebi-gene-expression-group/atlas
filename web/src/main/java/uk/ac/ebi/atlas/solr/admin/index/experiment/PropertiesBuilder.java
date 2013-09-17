package uk.ac.ebi.atlas.solr.admin.index.experiment;

import uk.ac.ebi.atlas.model.Experiment;

import java.util.Collection;

public abstract class PropertiesBuilder<T extends Experiment, M> {

    public abstract Collection<M> buildProperties(T experiment);
}
