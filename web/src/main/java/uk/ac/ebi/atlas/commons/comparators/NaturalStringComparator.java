package uk.ac.ebi.atlas.commons.comparators;

import java.util.Comparator;

/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 17/07/15.
 */

public class NaturalStringComparator implements Comparator<String> {
    @Override
    public int compare(String o1, String o2) {
        return o1.compareTo(o2);
    }
}
