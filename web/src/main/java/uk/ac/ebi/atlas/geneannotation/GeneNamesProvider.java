/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

package uk.ac.ebi.atlas.geneannotation;


import javax.annotation.Resource;
import javax.inject.Named;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

@Named("geneNamesProvider")
public class GeneNamesProvider {

    Map<String, String> geneNames;

    public GeneNamesProvider() {
    }


    @Resource(name = "geneNames")
    public void setGeneNames(ConcurrentMap<String, String> geneNames) {
        this.geneNames = geneNames;
    }

    public String getGeneName(String ensGeneId) {
        String value = geneNames.get(ensGeneId);
        return value == null ? ensGeneId : value;
    }

}