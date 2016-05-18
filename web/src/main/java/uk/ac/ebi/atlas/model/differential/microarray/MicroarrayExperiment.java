
package uk.ac.ebi.atlas.model.differential.microarray;


import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;

import java.util.Date;
import java.util.Set;
import java.util.SortedSet;

public class MicroarrayExperiment extends DifferentialExperiment {

    private SortedSet<String> arrayDesignAccessions;

    private boolean isTwoColour = false;

    public MicroarrayExperiment(ExperimentType type, String accession, Date lastUpdate, Set<Contrast> contrasts,
                                String description, boolean hasExtraInfoFile, boolean hasRData,
                                Set<String> species, String kingdom, String ensemblDB, SortedSet<String> arrayDesignAccessions, boolean twoColour,
                                Set<String> pubMedIds, ExperimentDesign experimentDesign) {

        super(type, accession, lastUpdate, contrasts, description, hasExtraInfoFile, hasRData, species, kingdom, ensemblDB, pubMedIds, experimentDesign);
        this.arrayDesignAccessions = arrayDesignAccessions;

        isTwoColour = twoColour;
    }

    public SortedSet<String> getArrayDesignAccessions() {
        return arrayDesignAccessions;
    }

    public boolean isTwoColour() {
        return isTwoColour;
    }
}
