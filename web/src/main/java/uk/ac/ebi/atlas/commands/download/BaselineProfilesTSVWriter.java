package uk.ac.ebi.atlas.commands.download;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import uk.ac.ebi.atlas.commands.context.BaselineRequestContext;
import uk.ac.ebi.atlas.geneannotation.GeneNamesProvider;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
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
        String geneQuery = requestContext.getGeneQuery();
        String specific = requestContext.isSpecific() ? "(specifically) " : "";
        String exactMatch = requestContext.isExactMatch() ? "exactly " : "";
        double cutoff = requestContext.getCutoff();
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("E, dd-MMM-yyyy HH:mm:ss");
        return MessageFormat.format(headerTemplate, exactMatch, geneQuery, specific, formatQueryFactors(), cutoff, ft.format(dNow));

    }


    protected String formatQueryFactors() {
        Set<Factor> selectedQueryFactors = requestContext.getSelectedQueryFactors();
        Collection<String> transformedFactors = Collections2.transform(selectedQueryFactors, new Function<Factor, String>() {
            @Override
            public String apply(Factor factor) {
                return factor.getValue();
            }
        });

        return Joiner.on(",").join(transformedFactors);
    }

}
