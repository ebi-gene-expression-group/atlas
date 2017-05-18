package uk.ac.ebi.atlas.profiles.writer;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfile;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Named
public class BaselineProfilesWriterFactory<Unit extends ExpressionUnit.Absolute> extends ProfilesWriterFactory<AssayGroup, BaselineExpression,
        BaselineProfile, BaselineRequestContext<Unit>, BaselineProfilesWriterFactory.BaselineDownloadOptions> {

    static class BaselineDownloadOptions extends ProfilesWriterFactory.ProfileDownloadOptions {
        public final boolean isGeneSet;

        public BaselineDownloadOptions(String queryDescription,
                                       boolean isGeneSet){
            super(queryDescription);
            this.isGeneSet = isGeneSet;
        }

    }

    private final String tsvFileMastheadTemplate;

    @Inject
    public BaselineProfilesWriterFactory(@Value("classpath:/file-templates/download-headers-baseline.txt")
                                             Resource tsvFileMastheadTemplateResource) {
        try (InputStream inputStream = tsvFileMastheadTemplateResource.getInputStream()) {
            this.tsvFileMastheadTemplate = IOUtils.toString(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public ProfilesWriter<BaselineProfile> create(Writer responseWriter,
                                                  BaselineRequestContext requestContext,
                                                  String queryDescription, boolean isGeneSet){
        return create(responseWriter, requestContext, new BaselineDownloadOptions(queryDescription, isGeneSet));
    }


    @Override
    protected String getTsvFileMasthead(BaselineRequestContext requestContext, BaselineDownloadOptions profileDownloadOptions) {
        String responseType = profileDownloadOptions.isGeneSet ? "Gene sets" : "Genes";
        String geneQuery = profileDownloadOptions.queryDescription;
        String specific = requestContext.isSpecific() ? "specifically " : "";
        String selectedQueryFactors = selectedAssayGroups(requestContext);
        double cutoff = requestContext.getCutoff();
        String experimentAccession = requestContext.getExperimentAccession();
        String timeStamp = new SimpleDateFormat("E, dd-MMM-yyyy HH:mm:ss").format(new Date());
        return MessageFormat.format(tsvFileMastheadTemplate, responseType, geneQuery, specific, cutoff, experimentAccession,selectedQueryFactors, timeStamp);
    }

    private String selectedAssayGroups(BaselineRequestContext requestContext){
        if(requestContext.getDataColumnsToReturn().size() == requestContext.getAllDataColumns().size()){
            return MessageFormat.format("{0} (all)", requestContext.getDataColumnsToReturn().size() );
        } else {
            return MessageFormat.format("{0}/{1} ", requestContext.getDataColumnsToReturn().size() ,
                    requestContext.getAllDataColumns().size());
        }
    }



    @Override
    protected String[] getProfileIdColumnHeaders(BaselineRequestContext requestContext, BaselineDownloadOptions profileDownloadOption) {
        return profileDownloadOption.isGeneSet
                ? new String[] {"Gene set ID", "Gene Name"}
                : super.getProfileIdColumnHeaders(requestContext, profileDownloadOption);
    }

}
