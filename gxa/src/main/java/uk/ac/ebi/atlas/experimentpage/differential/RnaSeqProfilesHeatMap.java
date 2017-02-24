package uk.ac.ebi.atlas.experimentpage.differential;

import uk.ac.ebi.atlas.experimentpage.context.RnaSeqRequestContext;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.experiment.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.profiles.differential.rnaseq.RnaSeqProfileStreamFactory;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class RnaSeqProfilesHeatMap extends DifferentialProfilesHeatMap<DifferentialExpression,
        DifferentialExperiment, RnaSeqProfile, RnaSeqRequestContext> {

    @Inject
    public RnaSeqProfilesHeatMap(RnaSeqProfileStreamFactory inputStreamFactory,
                                 SolrQueryService solrQueryService) {
        super(inputStreamFactory,solrQueryService);
    }

}
