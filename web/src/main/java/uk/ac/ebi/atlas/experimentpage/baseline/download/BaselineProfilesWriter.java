package uk.ac.ebi.atlas.experimentpage.baseline.download;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.GenesNotFoundException;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileInputStreamFactory;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamFilters;
import uk.ac.ebi.atlas.profiles.writer.BaselineProfilesTSVWriter;
import uk.ac.ebi.atlas.profiles.writer.ProfilesWriter;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.PrintWriter;
import java.io.Writer;

@Named
@Scope("prototype")
public class BaselineProfilesWriter extends ProfilesWriter<BaselineProfile, Factor, BaselineRequestContext> {

    private BaselineProfileInputStreamFactory inputStreamFactory;
    private SolrQueryService solrQueryService;

    @Inject
    public BaselineProfilesWriter(BaselineProfilesTSVWriter tsvWriter,
                                  @Qualifier("baselineProfileInputStreamFactory") BaselineProfileInputStreamFactory inputStreamFactory,
                                  SolrQueryService solrQueryService) {
        super(new BaselineProfileStreamFilters(), tsvWriter);
        this.inputStreamFactory = inputStreamFactory;
        this.solrQueryService = solrQueryService;
    }

    public long write(Writer outputWriter, BaselineRequestContext requestContext) throws GenesNotFoundException {
        GeneQueryResponse geneQueryResponse = solrQueryService.fetchResponseBasedOnRequestContext(requestContext, requestContext.getFilteredBySpecies());
        return super.write(outputWriter, inputStreamFactory.create(requestContext), requestContext, requestContext
                .getAllQueryFactors(),geneQueryResponse, false);
    }

    /* Unused, Alfonso says there was once an idea for having the download button give you exactly what's on the page
    . Didn't happen. You can consider deleting this path of the code.*/
    public long writeAsGeneSets(Writer outputWriter, BaselineRequestContext requestContext) throws GenesNotFoundException {
        GeneQueryResponse geneQueryResponse = solrQueryService.fetchResponseBasedOnRequestContext(requestContext, requestContext.getFilteredBySpecies());

        return super.write(outputWriter, inputStreamFactory.create(requestContext), requestContext, requestContext
                .getAllQueryFactors(),geneQueryResponse, true);
    }

}
