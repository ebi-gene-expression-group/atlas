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

import java.util.HashSet;
import java.util.Set;

//Not Spring managed because actually requires initialization parameters
//and Spring doesn't support assisted constructor injection
public class GeneSet{

    private Set<BaselineProfile> baselineProfiles = new HashSet<>();

    private String id;

    public GeneSet(String id){
        this.id = id;
    }

    public GeneSet addBaselineProfile(BaselineProfile baselineProfile){
        baselineProfiles.add(baselineProfile);
        return this;
    }

    public BaselineProfile getAverageProfile(){
        BaselineProfile summaryProfile = new BaselineProfile(null, id);

        for(BaselineProfile baselineProfile: baselineProfiles){
            summaryProfile.sumProfile(baselineProfile);
        }

        return summaryProfile.foldProfile(baselineProfiles.size());
    }
}
