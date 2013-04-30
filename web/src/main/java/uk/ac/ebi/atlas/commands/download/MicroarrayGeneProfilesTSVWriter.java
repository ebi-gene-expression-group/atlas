/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.commands.download;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Collections2;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import uk.ac.ebi.atlas.commands.context.MicroarrayRequestContext;
import uk.ac.ebi.atlas.geneannotation.GeneNamesProvider;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.utils.NumberUtils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Named("microarrayProfileWriter")
@Scope("prototype")
public class MicroarrayGeneProfilesTSVWriter extends GeneProfilesTSVWriter<MicroarrayProfile, Contrast> {

    private static final Logger LOGGER = Logger.getLogger(DifferentialGeneProfilesTSVWriter.class);

    private MicroarrayRequestContext requestContext;

    private Resource headerTemplateResource;

    private String headerTemplate;

    @Inject
    public MicroarrayGeneProfilesTSVWriter(NumberUtils numberUtils, GeneNamesProvider geneNamesProvider) {
        super(numberUtils, geneNamesProvider);
    }

    @Inject
    public void setHeaderTemplateResource(@Value("classpath:/file-templates/download-headers-differential.txt") Resource headerTemplateResource) {
        this.headerTemplateResource = headerTemplateResource;
    }

    @Inject
    public void setRequestContext(MicroarrayRequestContext requestContext) {
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
    protected String buildHeaders() {
        String geneQuery = requestContext.getGeneQuery();
        String specific = requestContext.isSpecific() ? "(specifically) " : "";
        String exactMatch = requestContext.isExactMatch() ? "exactly " : "";
        double cutoff = requestContext.getCutoff();
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("E, dd-MMM-yyyy HH:mm:ss");
        return MessageFormat.format(headerTemplate, exactMatch, geneQuery, specific, formatContrasts(), cutoff, ft.format(dNow));

    }

    protected String formatContrasts() {
        Set<Contrast> selectedContrasts = requestContext.getSelectedQueryFactors();
        if (CollectionUtils.isEmpty(selectedContrasts)) {
            return StringUtils.EMPTY;
        }
        Collection<String> transformedContrasts = Collections2.transform(selectedContrasts, new Function<Contrast, String>() {
            @Override
            public String apply(Contrast contrast) {
                return contrast.getDisplayName();
            }
        });

        return Joiner.on(",").join(transformedContrasts);
    }

    @Override
    protected List<String> buildColumnNames(SortedSet<Contrast> conditions) {
        List<String> columnNames = new ArrayList<>();
        for (Contrast condition : conditions) {
            columnNames.add(condition.getDisplayName() + ".p-value");
            columnNames.add(condition.getDisplayName() + ".log2foldchange");
            columnNames.add(condition.getDisplayName() + ".t-statistic");
        }

        return columnNames;
    }

    @Override
    protected String[] buildExpressionsRow(final MicroarrayProfile geneProfile, SortedSet<Contrast> contrasts) {
        String[] expressionLevels = new String[contrasts.size() * 3];
        int i = 0;
        for (Contrast contrast : contrasts) {
            MicroarrayExpression expression = geneProfile.getExpression(contrast);
            if (expression != null) {
                expressionLevels[i++] = getValueToString(expression.getLevel());
                expressionLevels[i++] = getValueToString(expression.getFoldChange());
                expressionLevels[i++] = getValueToString(expression.getTstatistic());
            } else {
                expressionLevels[i++] = "NA";
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

    @Override
    protected String getSecondColumnName() {
        return HeaderBuilder.DESIGN_ELEMENT;
    }

    @Override
    protected String getSecondColumnValue(MicroarrayProfile geneProfile) {
        return geneProfile.getDesignElementName();
    }
}
