package uk.ac.ebi.atlas.model.caches.magetab;

import java.util.Set;

public interface MageTabLoaderBuilder {

    public MageTabLoaderBuilder forExperimentAccession(String experimentAccession);

    public MageTabLoaderBuilder withRequiredFactorTypes(Set<String> factorTypes);

    public MageTabLoader build();
}
