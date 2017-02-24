package uk.ac.ebi.atlas.experimentpage.differential;

import uk.ac.ebi.atlas.experimentpage.context.MicroarrayRequestContext;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.profiles.differential.microarray.MicroarrayProfileStreamFactory;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;

import javax.inject.Inject;
import javax.inject.Named;
@Named
public class MicroarrayProfilesHeatMap extends DifferentialProfilesHeatMap<MicroarrayExpression,
        MicroarrayExperiment, MicroarrayProfile, MicroarrayRequestContext> {

    @Inject
    public MicroarrayProfilesHeatMap( MicroarrayProfileStreamFactory inputStreamFactory,
                                     SolrQueryService solrQueryService) {
        super(inputStreamFactory,solrQueryService);
    }

}
