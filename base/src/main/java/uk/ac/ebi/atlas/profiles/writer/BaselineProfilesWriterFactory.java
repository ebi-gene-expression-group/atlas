package uk.ac.ebi.atlas.profiles.writer;

import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfile;
import uk.ac.ebi.atlas.search.SearchDescription;
import uk.ac.ebi.atlas.search.SemanticQuery;

import javax.inject.Named;
import java.io.Writer;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.apache.commons.lang3.StringUtils.wrap;

@Named
public class BaselineProfilesWriterFactory<Unit extends ExpressionUnit.Absolute> extends ProfilesWriterFactory<AssayGroup, BaselineExpression,
        BaselineProfile, BaselineRequestContext<Unit>> {


    @Override
    protected String getTsvFileMasthead(BaselineRequestContext<Unit> requestContext, String queryDescription) {
        String specific = requestContext.isSpecific() ? "specifically " : "";
        String selectedQueryFactors = selectedAssayGroups(requestContext);
        double cutoff = requestContext.getCutoff();
        String experimentAccession = requestContext.getExperimentAccession();
        String timeStamp = new SimpleDateFormat("E, dd-MMM-yyyy HH:mm:ss").format(new Date());
        return MessageFormat.format(
                "# Expression Atlas\n" +
                        "# Query: Genes matching: {0}, {1}expressed above the expression level cutoff: {2} {3} in experiment {4}\n" +
                        "# Selected columns: {5}\n" +
                        "# Timestamp: {6}", queryDescription, specific, cutoff,requestContext.getExpressionUnit(), experimentAccession, selectedQueryFactors, timeStamp);
    }

    private String selectedAssayGroups(BaselineRequestContext requestContext) {
        if (requestContext.getDataColumnsToReturn().size() == requestContext.getAllDataColumns().size()) {
            return MessageFormat.format("{0} (all)", requestContext.getDataColumnsToReturn().size());
        } else {
            return MessageFormat.format("{0}/{1} ", requestContext.getDataColumnsToReturn().size(),
                    requestContext.getAllDataColumns().size());
        }
    }

    public ProfilesWriter<BaselineProfile> create(Writer responseWriter, BaselineRequestContext<Unit> baselineRequestContext, int coexpressionsRequested){
        return create(responseWriter, baselineRequestContext, describe(baselineRequestContext.getGeneQuery(), coexpressionsRequested));
    }


    private String describe(SemanticQuery geneQuery, int coexpressedGenes) {
        return coexpressedGenes == 0 ? wrap(SearchDescription.get(geneQuery), "'") :
                geneQuery.description() + " with " + coexpressedGenes + " similarly expressed genes";
    }
}
