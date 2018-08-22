package uk.ac.ebi.atlas.profiles.writer;

import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfile;

import javax.inject.Named;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Named
public class BaselineProfilesWriterFactory<U extends ExpressionUnit.Absolute>
             extends ProfilesWriterFactory<
                        AssayGroup, BaselineExpression, BaselineProfile, BaselineRequestContext<U>> {

    @Override
    protected String getTsvFileMasthead(BaselineRequestContext<U> requestContext, String queryDescription) {
        String specific = requestContext.isSpecific() ? "specifically " : "";
        String selectedQueryFactors = selectedAssayGroups(requestContext);
        double cutoff = requestContext.getCutoff();
        String experimentAccession = requestContext.getExperimentAccession();
        String timeStamp = new SimpleDateFormat("E, dd-MMM-yyyy HH:mm:ss").format(new Date());
        return MessageFormat.format(
                "# Expression Atlas\n" +
                "# Query: Genes matching: {0}, {1} expressed above the expression level cutoff: {2} {3} " +
                        "in experiment {4}\n" +
                "# Selected columns: {5}\n" +
                "# Timestamp: {6}",
                queryDescription,
                specific,
                cutoff,
                requestContext.getExpressionUnit(),
                experimentAccession,
                selectedQueryFactors,
                timeStamp);
    }

    private String selectedAssayGroups(BaselineRequestContext requestContext) {
        if (requestContext.getDataColumnsToReturn().size() == requestContext.getAllDataColumns().size()) {
            return MessageFormat.format("{0} (all)", requestContext.getDataColumnsToReturn().size());
        } else {
            return MessageFormat.format("{0}/{1} ", requestContext.getDataColumnsToReturn().size(),
                    requestContext.getAllDataColumns().size());
        }
    }
}
