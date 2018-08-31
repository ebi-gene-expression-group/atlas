
package uk.ac.ebi.atlas.experimentpage.baseline.genedistribution;

/*
I wish someone put a comment in there.
The cutoff values are, like: 0.1, 0.2, 0.3 ... 0.9, 1, 2, 3, 4 , 5 ... 10, 20, 30, 40, ...
 */
public abstract class CutoffScale {
    private static final int DEFAULT_NUMBER_OF_FRACTIONAL_DIGITS = 1;
    private static final int NINTH_PART = 9;
    private static final int BASE_TEN = 10;

    CutoffScale() {

    }

    public static class Logarithmic {
        private final int size = 200;
        private final double initial = 1e-6;
        private final double step = 1.1;

        public double[] get() {
            double[] result = new double[size + 1];
            result[0] = 0.0;

            double current = initial;
            for (int i = 1; i < result.length; i++) {
                result[i] = current;
                current *= step;
            }
            return result;
        }

    }

    public static class Scaled {

        private final int size = 100;

        public double[] get() {
            double[] result = new double[size];
            for (int i = 0; i < result.length; i++) {
                result[i] = calculateNthScaledValue(i);
            }
            return result;
        }

        private double calculateNthScaledValue(int position) {
            double nthValue;
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

}
