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

package uk.ac.ebi.atlas.geneindex;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

public class GeneQueryResponse {

    private Multimap<String, String> geneSets = HashMultimap.create();

    public GeneQueryResponse addGeneIds(String queryTerm, Set<String> geneIds){
        checkArgument(StringUtils.isNotBlank(queryTerm));
        checkArgument(!geneSets.containsKey(queryTerm));

        if(!geneIds.isEmpty()){
            geneSets.putAll(queryTerm, geneIds);
        }
        return this;
    }

    public Collection<String> getAllGeneIds(){
        return Sets.newHashSet(geneSets.values());
    }

    public boolean isEmpty() {
        return geneSets.isEmpty();
    }

    public Set<String> getQueryTerms() {
        return geneSets.keySet();
    }

    public boolean containsEntry(String queryTerm, String id) {
        return geneSets.containsEntry(queryTerm, id);
    }

    public Collection<String> getIds(String queryTerm) {
        return geneSets.get(queryTerm);
    }

    public Set<String> getRelatedTerms(String geneId){
        Set<String> relatedTerms = Sets.newHashSet();
        for (Map.Entry<String,Collection<String>> geneSet: geneSets.asMap().entrySet()){
            if(geneSet.getValue().contains(geneId)){
                relatedTerms.add(geneSet.getKey());
            }
        }
        return relatedTerms;
    }

}
