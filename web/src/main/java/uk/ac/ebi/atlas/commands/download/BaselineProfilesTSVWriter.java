package uk.ac.ebi.atlas.commands.download;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import uk.ac.ebi.atlas.commands.context.BaselineRequestContext;
import uk.ac.ebi.atlas.geneannotation.GeneNamesProvider;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.ExperimentalFactors;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.utils.NumberUtils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Named("geneProfileWriter")
@Scope("prototype")
public class BaselineProfilesTSVWriter extends GeneProfilesTSVWriter<BaselineProfile, Factor> {

    private static final Logger LOGGER = Logger.getLogger(BaselineProfilesTSVWriter.class);

    private BaselineRequestContext requestContext;

    private Resource headerTemplateResource;

    private String headerTemplate;

    @Inject
    public BaselineProfilesTSVWriter(NumberUtils numberUtils,
                                     GeneNamesProvider geneNamesProvider) {
        super(numberUtils, geneNamesProvider);
    }

    @Inject
    public void setHeaderTemplateResource(@Value("classpath:/file-templates/download-headers-baseline.txt") Resource headerTemplateResource) {
        this.headerTemplateResource = headerTemplateResource;
    }

    @Inject
    public void setRequestContext(BaselineRequestContext requestContext) {
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
    protected List<String> buildColumnNames(SortedSet<Factor> conditions) {
        SortedSet<String> values = Factor.getValues(conditions);
        return Lists.newArrayList(values);
    }

    @Override
    protected String buildHeaders() {
        String geneQuery = "'" + requestContext.getGeneQuery() + "'";
        String specific = requestContext.isSpecific() ? "specifically " : "";
        String exactMatch = requestContext.isExactMatch() ? " exactly matching" : "";
        String selectedQueryFactors = formatSelectedQueryFactors();
        double cutoff = requestContext.getCutoff();
        String experimentAccession = requestContext.getExperiment().getAccession();
        String selectedFilterFactors = formatSelectedFilterFactors();
        String timeStamp = new SimpleDateFormat("E, dd-MMM-yyyy HH:mm:ss").format(new Date());
        return MessageFormat.format(headerTemplate, exactMatch, geneQuery, specific, selectedQueryFactors, cutoff,
                                    experimentAccession, selectedFilterFactors, timeStamp);

    }

    private String formatSelectedFilterFactors() {
        Set<Factor> selectedFilterFactors = requestContext.getSelectedFilterFactors();
        if (CollectionUtils.isEmpty(selectedFilterFactors)){
            return "";
        }
        Collection<String> transformedSelectedFilterFactors = Collections2.transform(selectedFilterFactors, new Function<Factor, String>() {
            @Override
            public String apply(Factor factor) {
                String factorName = requestContext.getExperiment().getExperimentalFactors().getFactorName(factor.getType());
                return factorName + ": " + factor.getValue();
            }
        });
        return ", filtered by " + Joiner.on(" and ").join(transformedSelectedFilterFactors);
    }


    protected String formatSelectedQueryFactors() {
        String queryFactorName = requestContext.getExperiment().getExperimentalFactors().getFactorName(requestContext.getQueryFactorType());
        Set<Factor> selectedQueryFactors = requestContext.getSelectedQueryFactors();
        if (CollectionUtils.isEmpty(selectedQueryFactors)){
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
