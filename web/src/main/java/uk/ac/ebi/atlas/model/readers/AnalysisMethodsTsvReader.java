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

package uk.ac.ebi.atlas.model.readers;

import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.common.collect.SortedSetMultimap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Named("analysisMethodsTsvReader")
@Scope("singleton")
public class AnalysisMethodsTsvReader extends AbstractTsvReader {

    private static final String LIBRARIES_TITLE = "Processed libraries";

    @Inject
    public AnalysisMethodsTsvReader(
                        @Value("#{configuration['experiment.analysis-method.path.template']}")
                        String pathTemplate){
        super(pathTemplate);
    }

    public Set<String> readProcessedLibraries(String experimentAccession) {
        SortedSetMultimap<String,String> allComments = readAllCommentsAsMap(experimentAccession);
        String[] processedLibraries = allComments.get("Libraries").first().split(",");
        return Sets.newHashSet(trim(processedLibraries));
    }

    protected String[] trim(String[] strings) {
        for (int i = 0; i < strings.length; i++) {
            strings[i] = strings[i].trim();
        }
        return strings;
    }

}
