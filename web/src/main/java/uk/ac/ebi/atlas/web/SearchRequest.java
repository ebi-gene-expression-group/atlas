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

import javax.validation.constraints.Size;

public class SearchRequest {

    private static final int GENE_QUERY_MAX_LENGTH = 900;

    @Size(max = GENE_QUERY_MAX_LENGTH,
            message = "The gene query expression is too long, please limit it to a maximum length of 900 characters")
    private String geneQuery = getDefaultGeneQuery();

    private boolean exactMatch = true;

    private boolean geneSetMatch;

    protected String getDefaultGeneQuery() {
        return StringUtils.EMPTY;
    }

    public boolean isGeneSetMatch() {
        return geneSetMatch;
    }

    public void setGeneSetMatch(boolean geneSetMatch) {
        this.geneSetMatch = geneSetMatch;
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

    boolean areQuotesMatching(String searchText) {
        int numberOfDoubleQuotes = StringUtils.countMatches(searchText, "\"");
        return numberOfDoubleQuotes % 2 == 0;
    }

    public String toString() {
        return Objects.toStringHelper(this.getClass())
                .add("geneQuery", geneQuery)
                .add("exactMatch", exactMatch)
                .add("geneSetMatch", geneSetMatch)
                .toString();
    }

    public boolean isExactMatch() {
        return exactMatch;
    }

    public void setExactMatch(boolean exactMatch) {
        this.exactMatch = exactMatch;
    }
}
