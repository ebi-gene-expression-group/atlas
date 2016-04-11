package uk.ac.ebi.atlas.experimentpage.baseline.download;

import com.google.common.base.Optional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.GenesNotFoundException;
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.profiles.ExpressionProfileInputStream;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileInputStreamFactory;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamPipelineBuilder;
import uk.ac.ebi.atlas.profiles.writer.BaselineProfilesTSVWriter;
import uk.ac.ebi.atlas.profiles.writer.ProfilesWriter;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.PrintWriter;

@Named
@Scope("prototype")
public class BaselineProfilesWriter extends ProfilesWriter<BaselineProfile, Factor, BaselineRequestContext> {

    private BaselineProfileInputStreamFactory inputStreamFactory;
    private SolrQueryService solrQueryService;

    @Inject
    public BaselineProfilesWriter(BaselineProfileStreamPipelineBuilder pipelineBuilder,
                                  BaselineProfilesTSVWriter tsvWriter,
                                  @Qualifier("baselineProfileInputStreamFactory") BaselineProfileInputStreamFactory inputStreamFactory,
                                  SolrQueryService solrQueryService) {
        super(pipelineBuilder, tsvWriter);
        this.inputStreamFactory = inputStreamFactory;
        this.solrQueryService = solrQueryService;
    }

    public long write(PrintWriter outputWriter, BaselineRequestContext requestContext) throws GenesNotFoundException {
        Optional<GeneQueryResponse> geneQueryResponse = solrQueryService.fetchResponseBasedOnRequestContext(requestContext,
                requestContext.getFilteredBySpecies());
        return super.write(outputWriter, inputStreamFactory.create(requestContext), requestContext, requestContext.getAllQueryFactors(),geneQueryResponse);
    }

    public long writeAsGeneSets(PrintWriter outputWriter, BaselineRequestContext requestContext) throws GenesNotFoundException {
        Optional<GeneQueryResponse> geneQueryResponse = solrQueryService.fetchResponseBasedOnRequestContext(requestContext, requestContext.getFilteredBySpecies());

        return super.write(outputWriter, inputStreamFactory.create(requestContext), requestContext, requestContext.getAllQueryFactors(),geneQueryResponse);
    }

}
