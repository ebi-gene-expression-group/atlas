package uk.ac.ebi.atlas.model;

import uk.ac.ebi.atlas.geneannotation.GeneNamesProvider;

public class GeneProfileBuilderConcreteFactory extends GeneProfileBuilderFactory {

    @Override
    protected GeneProfile.Builder createNew() {

        return new GeneProfile.Builder();

    }
}
