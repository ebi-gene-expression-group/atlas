package uk.ac.ebi.atlas.commands;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commands.context.BaselineRequestContext;
import uk.ac.ebi.atlas.commands.context.RequestContext;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.streams.InputStreamFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.PrintWriter;

@Named
@Scope("request")
public class WriteBaselineProfilesCommand extends AbstractCommand<Long, BaselineProfile> implements Command<Long> {

    protected static final Logger LOGGER = Logger.getLogger(WriteBaselineProfilesCommand.class);

    private BaselineProfilesTSVWriter baselineProfilesTSVWriter;
    private InputStreamFactory inputStreamFactory;

    @Inject
    public void setInputStreamFactory(InputStreamFactory inputStreamFactory) {
        this.inputStreamFactory = inputStreamFactory;
    }

    @Inject
    public WriteBaselineProfilesCommand(BaselineProfilesTSVWriter baselineProfilesTSVWriter, BaselineRequestContext requestContext) {
        super(requestContext);
        this.baselineProfilesTSVWriter = baselineProfilesTSVWriter;
    }

    @Override
    protected ObjectInputStream<BaselineProfile> createInputStream(String experimentAccession) {
        return inputStreamFactory.createBaselineProfileInputStream(experimentAccession);
    }

    @Override
    protected Long execute(ObjectInputStream<BaselineProfile> inputStream, RequestContext requestContext) {
        try {
            return baselineProfilesTSVWriter.apply(inputStream, requestContext.getAllQueryFactors());
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException("IOException when invoking ObjectInputStream.close()");
        }
    }

    public void setResponseWriter(PrintWriter responseWriter) {
        baselineProfilesTSVWriter.setResponseWriter(responseWriter);
    }
}
