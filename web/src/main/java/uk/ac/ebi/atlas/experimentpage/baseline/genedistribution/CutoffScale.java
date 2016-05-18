
package uk.ac.ebi.atlas.experimentpage.baseline.genedistribution;

import org.springframework.context.annotation.Scope;

import javax.inject.Named;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Named("cutoffScale")
@Scope("singleton")
public class CutoffScale {

    private static final int DEFAULT_NUMBER_OF_FRACTIONAL_DIGITS = 1;

    private static final int MAX_NUMBER_OF_VALUES = 100;

    private static final ConcurrentMap<Integer, Double> MAGNIFIED_SCALE = new ConcurrentHashMap<>();

    private static final int NINTH_PART = 9;

    private static final int BASE_TEN = 10;

    CutoffScale() {

    }

    public SortedSet<Double> getValuesSmallerThan(double expressionLevel) {

        SortedSet<Double> scaledValues = new TreeSet<>();

        for (int i = 0; i < MAX_NUMBER_OF_VALUES; i++) {

            double scaledValue = getNthValue(i);
            if (expressionLevel <= scaledValue) {
                return scaledValues;
            }
            scaledValues.add(scaledValue);
        }

        return scaledValues;
    }

    public double getNthValue(int position) {

        if (!(position >= 0)) {
            throw new IllegalArgumentException("position must be >= 0 ");
        }

        if (position == 0) {
            return 0;
        }

        Double nthValue = MAGNIFIED_SCALE.get(position);

        if (nthValue == null) {

            nthValue = calculateNthScaledValue(position);

            MAGNIFIED_SCALE.put(position, nthValue);

        }

        return nthValue;

    }

    private Double calculateNthScaledValue(int position) {
        Double nthValue;
        int remainder = position % NINTH_PART;

        if (remainder != 0) {
            int power = (position / NINTH_PART) - DEFAULT_NUMBER_OF_FRACTIONAL_DIGITS;
            nthValue = Math.pow(BASE_TEN, power) * remainder;
        } else {
            int power = (position / NINTH_PART) - (DEFAULT_NUMBER_OF_FRACTIONAL_DIGITS + 1);
            nthValue = Math.pow(BASE_TEN, power) * NINTH_PART;
        }

        if (nthValue > 1) {
            nthValue = Math.floor(nthValue);
        }

        nthValue = (Math.floor(nthValue * BASE_TEN)) / BASE_TEN;
        return nthValue;
    }

}
