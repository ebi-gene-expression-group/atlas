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

package uk.ac.ebi.atlas.profiles.writer;

import com.google.common.collect.Lists;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.experimentpage.context.MicroarrayRequestContext;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamOptions;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named("microarrayProfileWriter")
@Scope("prototype")
public class MicroarrayProfilesTSVWriter extends DifferentialProfilesTSVWriter<MicroarrayProfile, MicroarrayExpression> {

    private static final String DESIGN_ELEMENT = "Design Element";

    private MicroarrayRequestContext requestContext;

    @Inject
    public MicroarrayProfilesTSVWriter(CsvWriterFactory csvWriterFactory) {
        super(csvWriterFactory);
    }

    @Inject
    public void setRequestContext(MicroarrayRequestContext requestContext) {
        this.requestContext = requestContext;
    }

    @Override
    public MicroarrayRequestContext getRequestContext() {
        return requestContext;
    }

    @Override
    protected List<String> getExpressionColumnsHeaders(){
        return Lists.newArrayList("p-value", "log2foldchange", "t-statistic");
    }

    @Override
    protected List<Double> getExpressionLevelData(MicroarrayExpression expression){
        return Lists.newArrayList(expression.getPValue(), expression.getFoldChange(), expression.getTstatistic());
    }

    @Override
    protected String[] getProfileIdColumnHeaders(DifferentialProfileStreamOptions options, boolean isGeneSet) {
        return (String[]) ArrayUtils.addAll(super.getProfileIdColumnHeaders(options, isGeneSet), new String[]{DESIGN_ELEMENT});
    }

    @Override
    protected String getSecondaryRowHeader(MicroarrayProfile geneProfile) {
        return geneProfile.getDesignElementName();
    }
}
