package uk.ac.ebi.atlas.experimentpage.differential.download;

import uk.ac.ebi.atlas.profiles.differential.microarray.MicroarrayProfileStreamFactory;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import com.google.common.collect.Sets;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.experimentpage.context.MicroarrayRequestContext;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamFilters;
import uk.ac.ebi.atlas.profiles.differential.microarray.MicroarrayProfilesTsvInputStream;
import uk.ac.ebi.atlas.profiles.writer.MicroarrayProfilesTSVWriter;
import uk.ac.ebi.atlas.profiles.writer.ProfilesWriter;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

@Named
@Scope("prototype")
public class MicroarrayProfilesWriter extends ProfilesWriter<MicroarrayProfile, Contrast, DifferentialProfileStreamOptions> {

    private MicroarrayProfileStreamFactory inputStreamFactory;
    private SolrQueryService solrQueryService;

    @Inject
    public MicroarrayProfilesWriter(MicroarrayProfilesTSVWriter tsvWriter,
                                    MicroarrayProfileStreamFactory inputStreamFactory,
                                    SolrQueryService solrQueryService) {
        super(new DifferentialProfileStreamFilters<MicroarrayProfile>(), tsvWriter);
        this.inputStreamFactory = inputStreamFactory;
        this.solrQueryService = solrQueryService;
    }

    public long write(PrintWriter outputWriter, MicroarrayRequestContext requestContext, String arrayDesign) throws IOException {
        GeneQueryResponse geneQueryResponse = solrQueryService.fetchResponse(requestContext.getGeneQuery(),"");
        MicroarrayProfilesTsvInputStream inputStream =
                inputStreamFactory.create(requestContext.getExperiment(),requestContext, arrayDesign);
        Set<Contrast> contrasts = Sets.newHashSet(inputStream.getOrderedContrastsPresentInStream());
        return super.write(outputWriter, inputStream, requestContext, contrasts,geneQueryResponse);
    }

}
