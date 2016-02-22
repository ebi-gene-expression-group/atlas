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

public class SearchRequest {

    private GeneQuery geneQuery = getDefaultGeneQuery();
    private SemanticQuery semanticQuery = getDefaultSemanticQuery();

    private boolean exactMatch = true;

    protected GeneQuery getDefaultGeneQuery() {
        return GeneQuery.EMPTY;
    }

    protected SemanticQuery getDefaultSemanticQuery() {
        return new SemanticQuery();
    }

    public GeneQuery getGeneQuery() {
        return this.geneQuery;
    }

    public SemanticQuery getSemanticQuery() {
        return this.semanticQuery;
    }

    public void setGeneQuery(GeneQuery geneQuery) {
        this.geneQuery = geneQuery;
    }

    public void setSemanticQuery(SemanticQuery semanticQuery) {
        this.semanticQuery = semanticQuery;
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
        return !geneQuery.isEmpty();
    }

    public boolean hasSemanticQuery() {
        return !semanticQuery.isEmpty();
    }
}
