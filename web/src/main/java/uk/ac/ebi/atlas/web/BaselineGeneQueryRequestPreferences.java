package uk.ac.ebi.atlas.web;

import uk.ac.ebi.atlas.utils.NumberUtils;

/**
 * Created by barrera on 02/07/2014.
 *
 */
public class BaselineGeneQueryRequestPreferences extends ExperimentPageRequestPreferences {

    private static final double DEFAULT_CUTOFF = 0.5d;
    private static final String DEFAULT_GENE_QUERY = "protein_coding";

    private NumberUtils numberUtils = new NumberUtils();

    private String entityIdentifier;

    private String ensemblIdentifiersForMiRNA;

    @Override
    protected String getDefaultGeneQuery() {
        return DEFAULT_GENE_QUERY;
    }

    @Override
    public void setCutoff(Double cutoff) {
        if (cutoff != null) {
            super.setCutoff(numberUtils.round(cutoff));
        } else {
            super.setCutoff(cutoff);
        }
    }

    @Override
    public double getDefaultCutoff() {
        return DEFAULT_CUTOFF;
    }

    public String getEntityIdentifier() {
        return entityIdentifier;
    }

    public void setEntityIdentifier(String entityIdentifier) {
        this.entityIdentifier = entityIdentifier;
    }

    public String getEnsemblIdentifiersForMiRNA() {
        return ensemblIdentifiersForMiRNA;
    }

    public void setEnsemblIdentifiersForMiRNA(String ensemblIdentifiersForMiRNA) {
        this.ensemblIdentifiersForMiRNA = ensemblIdentifiersForMiRNA;
    }
}
