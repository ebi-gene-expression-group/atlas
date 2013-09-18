package uk.ac.ebi.atlas.solr.admin.index.conditions;

import uk.ac.ebi.atlas.model.Experiment;

import java.util.Collection;

public abstract class ConditionsBuilder<T extends Experiment> {

    public abstract Collection buildProperties(T experiment);
}
