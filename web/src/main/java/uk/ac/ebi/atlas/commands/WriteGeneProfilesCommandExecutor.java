package uk.ac.ebi.atlas.commands;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commands.context.BaselineRequestContext;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.Factor;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.PrintWriter;

@Named
@Scope("request")
public class WriteGeneProfilesCommandExecutor implements CommandExecutor<Long, BaselineProfile> {

    protected static final Logger logger = Logger.getLogger(WriteGeneProfilesCommandExecutor.class);

    private GeneProfilesTSVWriter geneProfileWriter;

    private BaselineRequestContext requestContext;

    @Inject
    public WriteGeneProfilesCommandExecutor(GeneProfilesTSVWriter geneProfileWriter, BaselineRequestContext requestContext) {
        this.geneProfileWriter = geneProfileWriter;
        this.requestContext = requestContext;
    }

    @Override
    public Long execute(ObjectInputStream<BaselineProfile> inputStream) {
        try {
            return geneProfileWriter.apply(inputStream, Factor.getValues(requestContext.getAllQueryFactors()), requestContext.getAllQueryFactors());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("IOException when invoking ObjectInputStream.close()");
        }
    }


    public void setResponseWriter(PrintWriter responseWriter) {
        geneProfileWriter.setResponseWriter(responseWriter);
    }


}
