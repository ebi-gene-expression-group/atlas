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
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.geneindex.GeneQueryResponse;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@Named
@Scope("request")
public class GeneSetProfilesBuilder {

    private Comparator<BaselineProfile> baselineProfileComparator;

    private ObjectInputStream<BaselineProfile> inputStream;

    private int totalGeneProfileCount = 0;

    private Map<String, GeneSet> geneSets = new HashMap();

    private GeneQueryResponse geneQueryResponse;

    private GeneSetFactory geneSetFactory;

    @Inject
    public GeneSetProfilesBuilder(GeneSetFactory geneSetFactory){
        this.geneSetFactory = geneSetFactory;
    }

    public GeneSetProfilesBuilder forGeneQueryResponse(GeneQueryResponse geneQueryResponse){
        this.geneQueryResponse = geneQueryResponse;

        for (String geneSetId: geneQueryResponse.getQueryTerms()){
            GeneSet geneSet = geneSetFactory.createGeneSet(geneSetId);
            geneSets.put(geneSetId, geneSet);
        }
        return this;
    }

    public GeneSetProfilesBuilder withBaselineComparator(Comparator<BaselineProfile> baselineProfileComparator){
        this.baselineProfileComparator = baselineProfileComparator;
        return this;
    }

    public GeneSetProfilesBuilder withInputStream(ObjectInputStream<BaselineProfile> inputStream){
        this.inputStream = inputStream;
        return this;
    }

    GeneSetProfilesBuilder addProfile(BaselineProfile profile){
        totalGeneProfileCount++;

        for (String geneSetId: geneQueryResponse.getRelatedQueryTerms(profile.getId())){
            geneSets.get(geneSetId).addBaselineProfile(profile);
        }

        return this;
    }

    public BaselineProfilesList build(){

        BaselineProfile geneProfile;
        while ((geneProfile = inputStream.readNext()) != null) {
            addProfile(geneProfile);
        }

        BaselineProfilesList baselineProfilesList = new BaselineProfilesList();

        for(GeneSet geneSet: geneSets.values()){
            BaselineProfile averageProfile = geneSet.getAverageProfile();
            if (!averageProfile.isEmpty()){
                baselineProfilesList.add(averageProfile);
            }
        }

        baselineProfilesList.setTotalResultCount(totalGeneProfileCount);

        if (baselineProfileComparator != null){
            Collections.sort(baselineProfilesList, baselineProfileComparator);
        }

        return baselineProfilesList;
    }


}
