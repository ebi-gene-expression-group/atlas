package uk.ac.ebi.atlas.solr.admin.index.experiment;

import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;

import java.util.Collection;

public class FactorPropertiesBuilder extends PropertiesBuilder<BaselineExperiment, FactorProperty> {
    @Override
    public Collection<FactorProperty> buildProperties(BaselineExperiment experiment) {
        return null;
    }
}
