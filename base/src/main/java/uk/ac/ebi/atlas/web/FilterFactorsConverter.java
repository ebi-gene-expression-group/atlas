package uk.ac.ebi.atlas.web;

import uk.ac.ebi.atlas.model.experiment.baseline.Factor;

@Deprecated
public class FilterFactorsConverter {

    private FilterFactorsConverter() {
    }

    public static String prettyPrint(Iterable<Factor> factors) {
        if (!factors.iterator().hasNext()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        for (Factor factor : factors) {
            sb.append(factor.getValue()).append(", ");
        }

        sb.delete(sb.length() - 2, sb.length());
        return sb.toString();
    }

}
