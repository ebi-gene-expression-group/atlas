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

package uk.ac.ebi.atlas.model.baseline;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commands.context.BaselineRequestContext;
import uk.ac.ebi.atlas.model.baseline.impl.FactorSet;
import uk.ac.ebi.atlas.utils.NumberUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

@Named
@Scope("request")
public class AverageBaselineProfileBuilder {

    private String profileId;

    private BaselineRequestContext requestContext;

    private List<BaselineProfile> baselineProfiles;

    @Inject
    AverageBaselineProfileBuilder(BaselineRequestContext requestContext) {
        this.requestContext = requestContext;
    }

    public AverageBaselineProfileBuilder forProfileId(String profileId){
        this.profileId = profileId;
        return this;
    }

    public AverageBaselineProfileBuilder withBaselineProfiles(List<BaselineProfile> baselineProfiles){
        checkArgument(baselineProfiles != null && !baselineProfiles.isEmpty(), "Please provide a non empty profiles list");
        this.baselineProfiles = baselineProfiles;
        return this;
    }

    public BaselineProfile build(){
        BaselineProfile averageBaselineProfile = new BaselineProfile(profileId);
        for (Factor queryFactor : requestContext.getAllQueryFactors()){

            double averageExpressionLevel = averageExpressionLevel(queryFactor);

            BaselineExpression baselineExpression = buildExpressionLevel(queryFactor, averageExpressionLevel);

            averageBaselineProfile.add(queryFactor.getType(), baselineExpression);
        }
        return averageBaselineProfile;
    }

    BaselineExpression buildExpressionLevel(Factor factor, double averageExpressionLevel) {
        FactorGroup factorGroup = new FactorSet().add(factor).addAll(requestContext.getSelectedFilterFactors());
        return new BaselineExpression(averageExpressionLevel, factorGroup);
    }

    double averageExpressionLevel(Factor factor) {
        double totalExpressionLevel = 0D;

        for (BaselineProfile baselineProfile: baselineProfiles){
            totalExpressionLevel += baselineProfile.getExpressionLevel(factor);
        }

        return new NumberUtils().round(totalExpressionLevel / baselineProfiles.size());
    }


}
