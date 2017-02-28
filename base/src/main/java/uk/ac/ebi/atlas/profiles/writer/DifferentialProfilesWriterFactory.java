package uk.ac.ebi.atlas.profiles.writer;

import com.google.common.collect.ImmutableList;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import uk.ac.ebi.atlas.experimentpage.context.DifferentialRequestContext;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialProfile;
import uk.ac.ebi.atlas.search.SearchDescription;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public abstract class DifferentialProfilesWriterFactory<Expr extends DifferentialExpression, Prof extends
        DifferentialProfile<Expr>, R extends DifferentialRequestContext<?,?>> extends
        ProfilesWriterFactory<Contrast, Expr, Prof, R, DifferentialProfilesWriterFactory.DifferentialDownLoadOptions> {

    static class DifferentialDownLoadOptions extends ProfilesWriterFactory.ProfileDownloadOptions {
        public DifferentialDownLoadOptions(String queryDescription) {
            super(queryDescription);
        }
        /*
        There might be options which value to actually download - foldChange, pValue, or tStat
        units will possibly vary, too
         */
    }

    private String tsvFileMastheadTemplate;

    @Value("classpath:/file-templates/download-headers-differential.txt")
    public void setTsvFileMastheadTemplate(Resource tsvFileMastheadResource) {
        try (InputStream inputStream = tsvFileMastheadResource.getInputStream()) {
            tsvFileMastheadTemplate = IOUtils.toString(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ProfilesWriter<Prof> create(Writer responseWriter, R requestContext){
        return create(responseWriter, requestContext, new DifferentialDownLoadOptions(SearchDescription.get(requestContext.getGeneQuery())));
    }

    @Override
    protected Iterable<String> labelsForColumn(R requestContext, DifferentialDownLoadOptions profileDownloadOptions,
                                               Contrast dataColumnDescriptor){
        String name = requestContext.displayNameForColumn(dataColumnDescriptor);
        return ImmutableList.of(name+".foldChange", name+".pValue");
    }

    @Override
    protected Iterable<String> valuesFromColumn(R requestContext, DifferentialDownLoadOptions profileDownloadOptions, Expr expression) {
        return ImmutableList.of(Double.toString(expression.getFoldChange()), Double.toString(expression.getPValue()));
    }

    @Override
    protected String getTsvFileMasthead(R requestContext, DifferentialDownLoadOptions profileDownloadOptions) {
        String geneQuery = profileDownloadOptions.queryDescription;
        String specific = requestContext.isSpecific() ? " specifically" : "";
        String regulation = " " + requestContext.getRegulation().getLabel();
        String selectedContrasts = formatSelectedContrasts(requestContext);
        double pValueCutoff = requestContext.getCutoff();
        double foldChangeCutoff = requestContext.getFoldChangeCutOff();
        String experimentAccession = requestContext.getExperiment().getAccession();
        String timeStamp = new SimpleDateFormat("E, dd-MMM-yyyy HH:mm:ss").format(new Date());
        return MessageFormat.format(tsvFileMastheadTemplate, geneQuery, specific, regulation, selectedContrasts, pValueCutoff, foldChangeCutoff,
                experimentAccession, timeStamp);
    }

    private String formatSelectedContrasts(DifferentialRequestContext requestContext) {
            return MessageFormat.format("<formatSelectedContrasts TODO!> {0} selected contrasts out of {1} " +
                    "</formatSelectedContrasts TODO!>",
                    requestContext.getDataColumnsToReturn().size(), requestContext
                            .getAllDataColumnDescriptors().size() );

    }




}