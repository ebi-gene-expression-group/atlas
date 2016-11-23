package uk.ac.ebi.atlas.experimentpage.differential.download;

import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.experimentpage.context.RnaSeqRequestContext;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamFilters;
import uk.ac.ebi.atlas.profiles.differential.rnaseq.RnaSeqProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.writer.ProfilesWriter;
import uk.ac.ebi.atlas.profiles.writer.RnaSeqProfilesTSVWriter;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
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

    public long write(PrintWriter outputWriter, RnaSeqRequestContext requestContext) throws IOException {
        GeneQueryResponse geneQueryResponse = solrQueryService.fetchResponse(requestContext.getGeneQuery(), requestContext
                .getFilteredBySpecies());
        ObjectInputStream<RnaSeqProfile> inputStream = inputStreamFactory.create(
                requestContext.getExperiment(), requestContext);
        return super.write(outputWriter, inputStream, requestContext, requestContext.getExperiment().getContrasts(),
                geneQueryResponse);
    }

}
