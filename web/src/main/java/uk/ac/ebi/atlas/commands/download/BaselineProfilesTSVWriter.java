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
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import uk.ac.ebi.atlas.commands.context.BaselineRequestContext;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.Factor;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.SortedSet;

@Named("geneProfileWriter")
@Scope("prototype")
public class BaselineProfilesTSVWriter extends GeneProfilesTSVWriter<BaselineProfile, Factor> {

    private static final Logger LOGGER = Logger.getLogger(BaselineProfilesTSVWriter.class);
    private static final String GENE_SET_COLUMN_NAME = "Gene set";

    private BaselineRequestContext requestContext;

    private Resource tsvFileMastheadTemplateResource;

    private String tsvFileMastheadTemplate;

    @Value("classpath:/file-templates/download-headers-baseline.txt")
    public void setTsvFileMastheadTemplateResource(Resource tsvFileMastheadTemplateResource) {
        this.tsvFileMastheadTemplateResource = tsvFileMastheadTemplateResource;
    }

    @Inject
    public void setRequestContext(BaselineRequestContext requestContext) {
        this.requestContext = requestContext;
    }

    @PostConstruct
    void initTsvFileMastheadTemplate() {
        try (InputStream inputStream = tsvFileMastheadTemplateResource.getInputStream()) {
            tsvFileMastheadTemplate = IOUtils.toString(inputStream);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }
    }

    @Override
    protected String[] getProfileIdColumnHeaders() {
        if(requestContext.isGeneSetMatch()){
            return new String[]{GENE_SET_COLUMN_NAME};
        }
        return new String[]{HeaderBuilder.GENE_NAME_COLUMN_NAME, HeaderBuilder.GENE_ID_COLUMN_NAME};
    }

    @Override
    protected String[] getConditionColumnHeaders(SortedSet<Factor> conditions) {
        SortedSet<String> factorValues = Factor.getValues(conditions);
        return factorValues.toArray(new String[factorValues.size()]);
    }

    @Override
    protected String getSecondaryRowHeader(BaselineProfile geneProfile) {
        return geneProfile.getId();
    }

    @Override
    protected String getTsvFileMasthead() {
        String responseType = requestContext.isGeneSetMatch() ? "Gene sets" : "Genes";
        String geneQuery = requestContext.getGeneQuery();
        String specific = requestContext.isSpecific() ? "specifically " : "";
        String exactMatch = requestContext.isExactMatch() ? " exactly" : "";
        String selectedQueryFactors = formatSelectedQueryFactors();
        double cutoff = requestContext.getCutoff();
        String experimentAccession = requestContext.getExperiment().getAccession();
        String selectedFilterFactors = formatSelectedFilterFactors();
        String timeStamp = new SimpleDateFormat("E, dd-MMM-yyyy HH:mm:ss").format(new Date());
        return MessageFormat.format(tsvFileMastheadTemplate, responseType, geneQuery, exactMatch, specific, selectedQueryFactors, cutoff,
                experimentAccession, selectedFilterFactors, timeStamp);

    }

    private String formatSelectedFilterFactors() {
        Set<Factor> selectedFilterFactors = requestContext.getSelectedFilterFactors();
        if (CollectionUtils.isEmpty(selectedFilterFactors)) {
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
