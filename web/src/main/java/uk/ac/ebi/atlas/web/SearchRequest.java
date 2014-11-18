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

package uk.ac.ebi.atlas.web;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang.StringUtils;
import uk.ac.ebi.atlas.solr.query.BioentityPropertyValueTokenizer;

import java.util.List;

public class SearchRequest {

    private String geneQuery = getDefaultGeneQuery();

    private boolean exactMatch = true;

    protected String getDefaultGeneQuery() {
        return StringUtils.EMPTY;
    }

    public String getGeneQuery() {
        return this.geneQuery;
    }

    public void setGeneQuery(String geneQuery) {
        this.geneQuery = tagsToQueryString(geneQuery);
    }

    public String getGeneQueryTagEditor() {
        return queryStringToTags(this.geneQuery);
    }

    boolean areQuotesMatching(String searchText) {
        int numberOfDoubleQuotes = StringUtils.countMatches(searchText, "\"");
        return numberOfDoubleQuotes % 2 == 0;
    }

    public String toString() {
        return Objects.toStringHelper(this.getClass())
                .add("geneQuery", geneQuery)
                .add("exactMatch", exactMatch)
                .toString();
    }

    public boolean isExactMatch() {
        return exactMatch;
    }

    public void setExactMatch(boolean exactMatch) {
        this.exactMatch = exactMatch;
    }

    public boolean hasGeneQuery() {
        return StringUtils.isNotBlank(getGeneQuery());
    }

    public String tagsToQueryString(String geneQuery) {

        String[] tags = geneQuery.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        String resQuery = "";

        if (tags.length == 1) {
            return geneQuery;
        }

        for (String tag : tags) { //for any tag find single word or multiple words
            String[] words = tag.trim().split(" ");

            if (words.length > 1) {  //multi-term
                String res = "\"";
                for (String word : words) {
                    res = res + word + " ";
                }
                res = res.trim() + "\" ";
                resQuery = resQuery + res;
            } else {  //single term
                resQuery = resQuery + tag.trim() + " ";
            }
        }

        return resQuery.trim();
    }

    public String queryStringToTags(String geneQuery) {
        List<String> tags = BioentityPropertyValueTokenizer.splitBySpacePreservingQuotes(geneQuery);

        ImmutableList.Builder<String> builder = ImmutableList.builder();
        for (String tag : tags) {
            if (tag.contains("\"")) {
                String newTag = tag.contains(",") ? tag : tag.replace("\"", "");
                builder.add(newTag);
            } else {
                builder.add(tag);
            }
        }

        return Joiner.on(",").join(builder.build());
    }
}
