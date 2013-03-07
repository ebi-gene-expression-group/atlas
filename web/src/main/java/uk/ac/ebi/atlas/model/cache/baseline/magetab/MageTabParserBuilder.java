package uk.ac.ebi.atlas.model.cache.baseline.magetab;

import java.io.IOException;
import java.util.Set;

public interface MageTabParserBuilder {

    public MageTabParserBuilder forExperimentAccession(String experimentAccession);

    public MageTabParserBuilder withRequiredFactorTypes(Set<String> factorTypes);

    public MageTabParserBuilder withProcessedRunAccessions(Set<String> processedRunAccessions);

    public MageTabParser build() throws IOException, uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;

}
