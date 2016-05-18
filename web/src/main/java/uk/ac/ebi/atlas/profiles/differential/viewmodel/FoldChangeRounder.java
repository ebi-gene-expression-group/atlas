
package uk.ac.ebi.atlas.profiles.differential.viewmodel;

import org.apache.commons.math.util.MathUtils;
import org.springframework.context.annotation.Scope;

import javax.inject.Named;
import java.text.NumberFormat;

@Named
@Scope("singleton")
public class FoldChangeRounder {

    private final NumberFormat format1Dp = NumberFormat.getNumberInstance();

    public FoldChangeRounder() {
        format1Dp.setGroupingUsed(false);
        format1Dp.setMaximumFractionDigits(1);
    }

    public String format(double number) {
        return format1Dp.format(number);
    }

    public double round(double number) {
        return MathUtils.round(number, 1);
    }

}