package uk.ac.ebi.atlas.commands.download;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Collections2;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import uk.ac.ebi.atlas.commands.context.DifferentialRequestContext;
import uk.ac.ebi.atlas.commands.context.RnaSeqRequestContext;
import uk.ac.ebi.atlas.geneannotation.GeneNamesProvider;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.utils.NumberUtils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Named("differentialProfileWriter")
@Scope("prototype")
public class DifferentialGeneProfilesTSVWriter extends GeneProfilesTSVWriter<RnaSeqProfile, Contrast> {
    private static final Logger LOGGER = Logger.getLogger(DifferentialGeneProfilesTSVWriter.class);

    private RnaSeqRequestContext requestContext;
    private Resource headerTemplateResource;
    private String headerTemplate;


    @Inject
    public void setHeaderTemplateResource(@Value("classpath:/file-templates/download-headers-differential.txt") Resource headerTemplateResource) {
        this.headerTemplateResource = headerTemplateResource;
    }

    @Inject
    public DifferentialGeneProfilesTSVWriter(NumberUtils numberUtils, GeneNamesProvider geneNamesProvider, RnaSeqRequestContext requestContext) {
        super(numberUtils, geneNamesProvider);
        this.requestContext = requestContext;
    }

    @PostConstruct
    void initTemplate() {
        try (InputStream inputStream = headerTemplateResource.getInputStream()) {
            headerTemplate = IOUtils.toString(inputStream);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }
    }

    @Override
    protected List<String> buildColumnNames(SortedSet<Contrast> conditions) {
        List<String> columnNames = new ArrayList<>();
        for (Contrast condition : conditions) {
            columnNames.add(condition.getDisplayName() + ".p-value");
            columnNames.add(condition.getDisplayName() + ".log2foldchange");
        }
        return columnNames;
    }


    @Override
    protected String buildHeaders() {
        String geneQuery = requestContext.getGeneQuery();
        String specific = requestContext.isSpecific() ? " specifically" : "";
        String exactMatch = requestContext.isExactMatch() ? " exactly" : "";
        String regulation = " " + requestContext.getRegulation().getLabel();
        String selectedContrasts = formatSelectedContrasts();
        double cutoff = requestContext.getCutoff();
        String experimentAccession = requestContext.getExperiment().getAccession();
        String timeStamp = new SimpleDateFormat("E, dd-MMM-yyyy HH:mm:ss").format(new Date());
        return MessageFormat.format(headerTemplate, geneQuery, exactMatch, specific, regulation, selectedContrasts, cutoff,
                experimentAccession, timeStamp);

    }

    private String formatSelectedContrasts() {
        if (requestContext.getSelectedQueryFactors().isEmpty()){
            return "any contrast";
        }
        Collection<String> selectedContrasts = Collections2.transform(requestContext.getSelectedQueryFactors(), new Function<Contrast, String>() {
            @Override
            public String apply(Contrast constrast) {
                return constrast.getDisplayName();
            }
        });
        return "contrast" + (selectedContrasts.size() == 1 ? ": '" : "s: '") + Joiner.on(", ").join(selectedContrasts) + "'";
    }

    @Override
    protected String[] buildExpressionsRow(final RnaSeqProfile geneProfile, SortedSet<Contrast> contrasts) {
        String[] expressionLevels = new String[contrasts.size() * 2];
        int i = 0;
        for (Contrast contrast : contrasts) {
            DifferentialExpression expression = geneProfile.getExpression(contrast);
            if (expression != null) {
                expressionLevels[i++] = getValueToString(expression.getLevel());
                expressionLevels[i++] = getValueToString(expression.getFoldChange());
            } else {
                expressionLevels[i++] = "NA";
                expressionLevels[i++] = "NA";
            }
        }
        return expressionLevels;
    }

    protected String getValueToString(double value) {
        if (Double.isInfinite(value)) {
            return "NA";
        }
        return Double.toString(value);
    }

}
