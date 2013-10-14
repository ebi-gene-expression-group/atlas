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
import uk.ac.ebi.atlas.commands.context.RnaSeqRequestContext;
import uk.ac.ebi.atlas.model.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.differential.rnaseq.RnaSeqProfile;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named("differentialProfileWriter")
@Scope("prototype")
public class RnaSeqProfilesTSVWriter extends DifferentialProfilesTSVWriter<RnaSeqProfile, DifferentialExpression> {

    private RnaSeqRequestContext requestContext;

    @Inject
    public void setRequestContext(RnaSeqRequestContext requestContext) {
        this.requestContext = requestContext;
    }

    @Override
    public RnaSeqRequestContext getRequestContext() {
        return requestContext;
    }

    @Override
    protected List<String> getExpressionColumnsHeaders() {
        return Lists.newArrayList("p-value", "log2foldchange");
    }

    @Override
    protected List<Double> getExpressionLevelData(DifferentialExpression expression) {
        return Lists.newArrayList(expression.getLevel(), expression.getFoldChange());
    }

    @Override
    protected String getSecondaryRowHeader(RnaSeqProfile geneProfile) {
        return "";
    }

}
