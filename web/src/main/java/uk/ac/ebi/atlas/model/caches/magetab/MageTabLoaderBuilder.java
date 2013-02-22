package uk.ac.ebi.atlas.model.caches.magetab;

import java.io.IOException;
import java.util.Set;

public interface MageTabLoaderBuilder {

    public MageTabLoaderBuilder forExperimentAccession(String experimentAccession);

    public MageTabLoaderBuilder withRequiredFactorTypes(Set<String> factorTypes);

    public MageTabLoaderBuilder withProcessedRunAccessions(Set<String> processedRunAccessions);

    public MageTabLoader build() throws IOException, uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;

}
