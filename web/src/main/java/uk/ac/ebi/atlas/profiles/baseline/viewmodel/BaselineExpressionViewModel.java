package uk.ac.ebi.atlas.profiles.baseline.viewmodel;

import com.google.common.base.Optional;
import uk.ac.ebi.atlas.model.baseline.Quartiles;

public class BaselineExpressionViewModel {

    private final String factorName;
    private final String color;
    private final double value;
    private final String svgPathId;
    private Quartiles quartiles;

    public BaselineExpressionViewModel(String factorName, String color, double value, String svgPathId,
                                       Optional<Quartiles> quartiles) {
        this.factorName = factorName;
        this.color = color;
        this.value = value;
        this.svgPathId = svgPathId;

        if (quartiles.isPresent()) {
            this.quartiles = quartiles.get();
        }
    }

}
