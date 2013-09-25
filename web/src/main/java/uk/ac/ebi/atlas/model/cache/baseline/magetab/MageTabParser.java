package uk.ac.ebi.atlas.model.cache.baseline.magetab;

import java.util.Map;
import java.util.Set;

public interface MageTabParser {

    Set<String> extractSpecies();

    /**
     * @return a Map with key factor type and value factor name
     */
    Map<String, String> getFactorNamesByType();


}
