package uk.ac.ebi.atlas.profiles.writer;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Collections2;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.SortedSet;

import static org.apache.commons.lang3.StringUtils.wrap;
import static uk.ac.ebi.atlas.search.SemanticQuery.isEmpty;

public class BaselineProfilesTSVWriter extends GeneProfilesTSVWriter<OldBaselineProfile, Factor, BaselineRequestContext> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaselineProfilesTSVWriter.class);

    private String tsvFileMastheadTemplate;

    public BaselineProfilesTSVWriter(CsvWriterFactory csvWriterFactory, Resource tsvFileMastheadTemplateResource) {
        super(csvWriterFactory);

        try (InputStream inputStream = tsvFileMastheadTemplateResource.getInputStream()) {
            tsvFileMastheadTemplate = IOUtils.toString(inputStream);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }
    }

    @Override
    protected String[] getProfileIdColumnHeaders(BaselineRequestContext options, boolean isGeneSet) {
        if (isGeneSet) {
            return new String[]{"Gene set"};
        }
        return new String[]{"Gene ID", "Gene Name"};
    }

    @Override
    protected String[] getConditionColumnHeaders(Set<Factor> conditions) {
        Set<String> factorValues = Factor.getValues(conditions);
        return factorValues.toArray(new String[factorValues.size()]);
    }

    @Override
    protected String getSecondaryRowHeader(OldBaselineProfile geneProfile) {
       return null;
    }

    @Override
    protected String[] extractConditionLevels(OldBaselineProfile geneProfile, Set<Factor> conditions) {
        String[] expressionLevels = new String[conditions.size()];
        int i = 0;
        for (Factor condition: conditions) {
            BaselineExpression expression = geneProfile.getExpression(condition);

            if (expression != null){
                expressionLevels[i] = expression.getLevelAsString();
            }
            i++;
        }
        return expressionLevels;
    }

    @Override
    protected String getTsvFileMasthead(BaselineRequestContext requestContext, boolean isGeneSet) {
        String responseType = isGeneSet ? "Gene sets" : "Genes";
        String geneQuery = isEmpty(requestContext.getGeneQuery()) ? requestContext.getQueryDescription() : wrap(requestContext.getQueryDescription(), "'");
        String specific = requestContext.isSpecific() ? "specifically " : "";
        String selectedQueryFactors = formatSelectedQueryFactors(requestContext);
        double cutoff = requestContext.getCutoff();
        String experimentAccession = requestContext.getExperiment().getAccession();
        String selectedFilterFactors = formatSelectedFilterFactors(requestContext);
        String timeStamp = new SimpleDateFormat("E, dd-MMM-yyyy HH:mm:ss").format(new Date());
        return MessageFormat.format(tsvFileMastheadTemplate, responseType, geneQuery, specific, selectedQueryFactors, cutoff,
                experimentAccession, selectedFilterFactors, timeStamp);

    }

    private String formatSelectedFilterFactors(final BaselineRequestContext requestContext) {
        SortedSet<Factor> selectedFilterFactors = requestContext.getSelectedFilterFactors();
        if (CollectionUtils.isEmpty(selectedFilterFactors)) {
            return "";
        }
        Collection<String> transformedSelectedFilterFactors = Collections2.transform(selectedFilterFactors, new Function<Factor, String>() {
            @Override
            public String apply(Factor factor) {
                String factorName = requestContext.getExperiment().getExperimentalFactors().getFactorDisplayName(factor.getType());
                return factorName + ": " + factor.getValue();
            }
        });
        return ", filtered by " + Joiner.on(" and ").join(transformedSelectedFilterFactors);
    }

    private String formatSelectedQueryFactors(BaselineRequestContext requestContext) {
        String queryFactorName = requestContext.getExperiment().getExperimentalFactors().getFactorDisplayName(requestContext.getQueryFactorType());
        Set<Factor> selectedQueryFactors = requestContext.getDataColumnsToReturn();
        if (CollectionUtils.isEmpty(selectedQueryFactors)) {
            return "any " + queryFactorName;
        }
        Collection<String> transformedFactors = Collections2.transform(selectedQueryFactors, new Function<Factor, String>() {
            @Override
            public String apply(Factor factor) {
                return factor.getValue();
            }
        });
        return queryFactorName + (transformedFactors.size() == 1 ? ": '" : "s: '") + Joiner.on(", ").join(transformedFactors) + "'";
    }

}
