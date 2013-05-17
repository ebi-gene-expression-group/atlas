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

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;

@Named
@Scope("request")
public class GeneSetProfilesBuilder {

    private BaselineRequestContext requestContext;

    private Map<String, BaselineProfile> averageProfiles = new HashMap<>();
    private Map<String, Integer> geneSetProfileCounts = new HashMap<>();

    private int totalGeneProfileCount = 0;

    @Inject
    GeneSetProfilesBuilder(BaselineRequestContext requestContext) {
        this.requestContext = requestContext;
    }

    public GeneSetProfilesBuilder sumProfile(BaselineProfile profile) {
        totalGeneProfileCount++;

        for (String queryTerm : requestContext.getGeneQueryResponse().getRelatedTerms(profile.getId())) {

            BaselineProfile sumProfile = averageProfiles.get(queryTerm);
            if (sumProfile == null) {
                sumProfile = new BaselineProfile(queryTerm);
                geneSetProfileCounts.put(queryTerm, 0);
            }
            averageProfiles.put(queryTerm, sumProfile.addAll(profile));

            geneSetProfileCounts.put(queryTerm, geneSetProfileCounts.get(queryTerm) + 1);

        }

        return this;
    }

    public BaselineProfilesList build() {
        BaselineProfilesList baselineProfilesList = new BaselineProfilesList();

        for (BaselineProfile profile : averageProfiles.values()) {
            if (!averageProfiles.isEmpty()) {
                baselineProfilesList.add(profile.foldProfile(geneSetProfileCounts.get(profile.getId())));
            }
        }

        baselineProfilesList.setTotalResultCount(totalGeneProfileCount);

        return baselineProfilesList;
    }


}
