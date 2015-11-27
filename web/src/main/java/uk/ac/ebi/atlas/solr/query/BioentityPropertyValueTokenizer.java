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

package uk.ac.ebi.atlas.solr.query;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.utils.StringUtil;

import javax.inject.Named;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Named
@Scope("singleton")
public class BioentityPropertyValueTokenizer {

    private static final String SPLIT_BY_SPACE_PRESERVING_DOUBLE_QUOTES_REGEXP = "\"([^\"]*)\"|(\\S+)";

    private static final Pattern SPLITTING_PATTERN = Pattern.compile(SPLIT_BY_SPACE_PRESERVING_DOUBLE_QUOTES_REGEXP);

    public static List<String> splitBySpacePreservingQuotes(String geneQuery) {
        List<String> results = Lists.newArrayList();

        if (geneQuery == null) {
            return results;
        }

        Matcher m = SPLITTING_PATTERN.matcher(geneQuery);
        while (m.find()) {
            if (m.group(1) != null) {
                // quoted
                results.add("\"" + m.group(1) + "\"");
            } else {
                // plain
                results.add(m.group(2));
            }
        }

        return results;
    }

    public static String joinQuotingPhrases(Iterable<String> strings) {
        return Joiner.on(" ").join(StringUtil.quotePhrases(strings));
    }


    //use static method instead
    @Deprecated
    public List<String> split(String geneQuery) {
        return splitBySpacePreservingQuotes(geneQuery);
    }

}