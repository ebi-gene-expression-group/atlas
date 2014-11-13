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

import com.google.common.base.Objects;
import org.apache.commons.lang.StringUtils;

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
        if (!areQuotesMatching(geneQuery)) {
            this.geneQuery = geneQuery + "\"";
        } else {
            this.geneQuery = geneQuery;
        }
    }

    public String getGeneQueryTagEditor() {
        return this.geneQuery+ "hello";
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
}
