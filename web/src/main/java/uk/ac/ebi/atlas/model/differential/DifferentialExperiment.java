package uk.ac.ebi.atlas.model.differential;

import java.util.Collection;

public class DifferentialExperiment {

    private Collection<Contrast> contrasts;

    public DifferentialExperiment(Collection<Contrast> contrasts) {
        this.contrasts = contrasts;
    }

    public Collection<Contrast> getContrasts() {
        return contrasts;
    }
}
