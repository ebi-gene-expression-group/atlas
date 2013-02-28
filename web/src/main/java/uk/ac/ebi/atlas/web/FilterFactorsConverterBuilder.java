package uk.ac.ebi.atlas.web;

import uk.ac.ebi.atlas.model.caches.ExperimentsCache;

import javax.inject.Inject;
import javax.inject.Named;

import static com.google.common.base.Preconditions.checkState;

@Named
public class FilterFactorsConverterBuilder {

    private ExperimentsCache experimentsCache;

    private String accession;
    @Inject
    public FilterFactorsConverterBuilder(ExperimentsCache experimentsCache) {
        this.experimentsCache = experimentsCache;
    }

    public FilterFactorsConverterBuilder forExperimentAccession(String accession) {
        this.accession = accession;
        return this;
    }

    public FilterFactorsConverter build() {
        checkState(accession != null);
        return new FilterFactorsConverter(experimentsCache.getExperiment(accession));
    }
}
