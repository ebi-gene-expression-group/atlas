package uk.ac.ebi.atlas.profiles.writer;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.model.AssayGroup;
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

import static org.apache.commons.lang3.StringUtils.wrap;
import static uk.ac.ebi.atlas.search.SemanticQuery.isEmpty;

@Named
public class BaselineProfilesWriterFactory extends ProfilesWriterFactory<AssayGroup, BaselineExpression,
        BaselineProfile, BaselineRequestContext, BaselineProfilesWriterFactory.BaselineDownloadOptions> {

    static class BaselineDownloadOptions implements ProfilesWriterFactory.ProfileDownloadOptions {
        private final boolean isGeneSet;

        public BaselineDownloadOptions(boolean isGeneSet){
            this.isGeneSet = isGeneSet;
        }

        boolean isGeneSet(){
            return isGeneSet;
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


    public ProfilesWriter<BaselineProfile> create(Writer responseWriter, BaselineRequestContext requestContext,
                                                  boolean isGeneSet){
        return create(responseWriter, requestContext, new BaselineDownloadOptions(isGeneSet));
    }


    @Override
    protected String getTsvFileMasthead(BaselineRequestContext requestContext, BaselineDownloadOptions profileDownloadOptions) {
        String responseType = profileDownloadOptions.isGeneSet() ? "Gene sets" : "Genes";
        String geneQuery = isEmpty(requestContext.getGeneQuery()) ? requestContext.getQueryDescription() : wrap(requestContext.getQueryDescription(), "'");
        String specific = requestContext.isSpecific() ? "specifically " : "";
        String selectedQueryFactors = formatSelectedQueryFactors(requestContext);
        double cutoff = requestContext.getCutoff();
        String experimentAccession = requestContext.getExperimentAccession();
        String selectedFilterFactors = "<formatSelectedFilterFactors TODO I don't think I need this, edit template/>";
        String timeStamp = new SimpleDateFormat("E, dd-MMM-yyyy HH:mm:ss").format(new Date());
        return MessageFormat.format(tsvFileMastheadTemplate, responseType, geneQuery, specific, selectedQueryFactors, cutoff,
                experimentAccession, selectedFilterFactors, timeStamp);
    }

    private String formatSelectedQueryFactors(BaselineRequestContext baselineRequestContext){
        return MessageFormat.format("<formatSelectedQueryFactors TODO!> {0} selected assay groups out of {1} " +
                "</formatSelectedQueryFactors TODO!>",
                baselineRequestContext.getDataColumnsToReturn().size(), baselineRequestContext
                        .getAllDataColumnDescriptors().size() );
    }



    @Override
    protected String[] getProfileIdColumnHeaders(BaselineRequestContext requestContext, BaselineDownloadOptions profileDownloadOption) {
        return profileDownloadOption.isGeneSet()
                ? new String[] {"Gene set ID", "Gene Name"}
                : super.getProfileIdColumnHeaders(requestContext, profileDownloadOption);
    }

}
