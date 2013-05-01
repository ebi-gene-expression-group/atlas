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

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commands.context.MicroarrayRequestContext;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayProfile;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named("microarrayProfileWriter")
@Scope("prototype")
public class MicroarrayProfilesTSVWriter extends DifferentialProfilesTSVWriter<MicroarrayProfile, MicroarrayExpression> {

    private MicroarrayRequestContext requestContext;

    @Inject
    public void setRequestContext(MicroarrayRequestContext requestContext) {
        this.requestContext = requestContext;
    }

    @Override
    public MicroarrayRequestContext getRequestContext() {
        return requestContext;
    }

    @Override
    protected List<String> getExpressionDataLabels(){
        return Lists.newArrayList("p-value", "log2foldchange", "t-statistic");
    }


    @Override
    protected List<Double> getExpressionLevelData(MicroarrayExpression expression){
        return Lists.newArrayList(expression.getLevel(), expression.getFoldChange(), expression.getTstatistic());
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
