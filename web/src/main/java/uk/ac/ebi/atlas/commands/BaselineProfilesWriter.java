package uk.ac.ebi.atlas.commands;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commands.context.BaselineRequestContext;
import uk.ac.ebi.atlas.commands.download.BaselineProfilesTSVWriter;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.profiles.ProfilesWriter;
import uk.ac.ebi.atlas.profiles.baseline.*;

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
                                  BaselineProfileInputStreamFactory inputStreamFactory,
                                  LoadGeneIdsIntoRequestContext loadGeneIdsIntoRequestContext) {
        super(pipelineBuilder, tsvWriter);
        this.inputStreamFactory = inputStreamFactory;
        this.loadGeneIdsIntoRequestContext = loadGeneIdsIntoRequestContext;
    }

    public long write(PrintWriter outputWriter, BaselineRequestContext requestContext) throws GenesNotFoundException {
        loadGeneIdsIntoRequestContext.load(requestContext, requestContext.getFilteredBySpecies());
        BaselineProfilesInputStream inputStream = inputStreamFactory.create(requestContext);
        return super.write(outputWriter, inputStream, requestContext, requestContext.getAllQueryFactors());
    }

    public long writeAsGeneSets(PrintWriter outputWriter, BaselineRequestContext requestContext) throws GenesNotFoundException {
        loadGeneIdsIntoRequestContext.load(requestContext, requestContext.getFilteredBySpecies());

        BaselineProfileStreamOptionsWrapperAsGeneSets options = new BaselineProfileStreamOptionsWrapperAsGeneSets(requestContext);

        BaselineProfilesInputStream inputStream = inputStreamFactory.create(options);
        return super.write(outputWriter, inputStream, options, options.getAllQueryFactors());
    }

}
