
package uk.ac.ebi.atlas.profiles.differential.viewmodel;

import org.apache.commons.math.util.MathUtils;

public class FoldChangeRounder {

    private FoldChangeRounder(){};

    public static double round(double number) {
        return MathUtils.round(number, 1);
    }

}