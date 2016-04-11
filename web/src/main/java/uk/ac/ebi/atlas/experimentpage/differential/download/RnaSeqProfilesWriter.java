package uk.ac.ebi.atlas.experimentpage.differential.download;

import com.google.common.base.Optional;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.experimentpage.context.GenesNotFoundException;
import uk.ac.ebi.atlas.experimentpage.context.RnaSeqRequestContext;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamFilters;
import uk.ac.ebi.atlas.profiles.differential.rnaseq.RnaSeqProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.writer.ProfilesWriter;
import uk.ac.ebi.atlas.profiles.writer.RnaSeqProfilesTSVWriter;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.PrintWriter;

@Named
@Scope("prototype")
public class RnaSeqProfilesWriter extends ProfilesWriter<RnaSeqProfile, Contrast, DifferentialProfileStreamOptions> {

    private RnaSeqProfileStreamFactory inputStreamFactory;
    private SolrQueryService solrQueryService;

    @Inject
    public RnaSeqProfilesWriter(RnaSeqProfilesTSVWriter tsvWriter,
                                RnaSeqProfileStreamFactory inputStreamFactory,
                                SolrQueryService solrQueryService) {
        super(new DifferentialProfileStreamFilters<RnaSeqProfile>(), tsvWriter);
        this.inputStreamFactory = inputStreamFactory;
        this.solrQueryService = solrQueryService;
    }

    public long write(PrintWriter outputWriter, RnaSeqRequestContext requestContext) throws GenesNotFoundException {
        Optional<GeneQueryResponse> geneQueryResponse = solrQueryService.fetchResponseBasedOnRequestContext(requestContext, requestContext.getFilteredBySpecies());
        ObjectInputStream<RnaSeqProfile> inputStream = inputStreamFactory.create(requestContext);
        return super.write(outputWriter, inputStream, requestContext, requestContext.getExperiment().getContrasts(),
                geneQueryResponse);
    }

}
