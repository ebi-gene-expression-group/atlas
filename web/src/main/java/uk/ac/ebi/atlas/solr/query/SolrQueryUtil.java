package uk.ac.ebi.atlas.solr.query;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

public class SolrQueryUtil {

    public static String limitSpeciesNameToTwoWords(String species) {

        String[] words = StringUtils.split(species);

        if (ArrayUtils.getLength(words) > 2) {
            return words[0].concat(" ").concat(words[1]);
        }
        return species;
    }
}
