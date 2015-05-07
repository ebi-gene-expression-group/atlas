package uk.ac.ebi.atlas.experimentpage.baseline.download;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.GenesNotFoundException;
import uk.ac.ebi.atlas.experimentpage.context.LoadGeneIdsIntoRequestContext;
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.profiles.ExpressionProfileInputStream;
import uk.ac.ebi.atlas.profiles.baseline.*;
import uk.ac.ebi.atlas.profiles.writer.BaselineProfilesTSVWriter;
import uk.ac.ebi.atlas.profiles.writer.ProfilesWriter;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.PrintWriter;

@Named
@Scope("prototype")
public class BaselineProfilesWriter extends ProfilesWriter<BaselineProfile, Factor, BaselineProfileStreamOptions> {

    private BaselineProfileInputStreamFactory inputStreamFactory;
    private LoadGeneIdsIntoRequestContext loadGeneIdsIntoRequestContext;

    @Inject
    public BaselineProfilesWriter(BaselineProfileStreamPipelineBuilder pipelineBuilder,
                                  BaselineProfilesTSVWriter tsvWriter,
                                  @Qualifier("baselineProfileInputStreamFactory") BaselineProfileInputStreamFactory inputStreamFactory,
                                  LoadGeneIdsIntoRequestContext loadGeneIdsIntoRequestContext) {
        super(pipelineBuilder, tsvWriter);
        this.inputStreamFactory = inputStreamFactory;
        this.loadGeneIdsIntoRequestContext = loadGeneIdsIntoRequestContext;
    }

    public long write(PrintWriter outputWriter, BaselineRequestContext requestContext) throws GenesNotFoundException {
        loadGeneIdsIntoRequestContext.load(requestContext, requestContext.getFilteredBySpecies());
        ExpressionProfileInputStream<BaselineProfile, BaselineExpression> inputStream = inputStreamFactory.create(requestContext);
        return super.write(outputWriter, inputStream, requestContext, requestContext.getAllQueryFactors());
    }

    public long writeAsGeneSets(PrintWriter outputWriter, BaselineRequestContext requestContext) throws GenesNotFoundException {
        loadGeneIdsIntoRequestContext.load(requestContext, requestContext.getFilteredBySpecies());

        BaselineProfileStreamOptionsWrapperAsGeneSets options = new BaselineProfileStreamOptionsWrapperAsGeneSets(requestContext);

        ExpressionProfileInputStream<BaselineProfile, BaselineExpression> inputStream = inputStreamFactory.create(options);
        return super.write(outputWriter, inputStream, options, options.getAllQueryFactors());
    }

}
