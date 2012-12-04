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

package uk.ac.ebi.atlas.web;

import com.google.common.base.Objects;
import org.hibernate.validator.constraints.Range;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.utils.NumberUtils;

import javax.inject.Named;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.SortedSet;

@Named("requestPreferences")
@Scope("prototype")
public class RequestPreferences {

    static final int DEFAULT_NUMBER_OF_RANKED_GENES = 50;
    static final double DEFAULT_CUTOFF = 0.5d;
    private static final String DEFAULT_GENE_QUERY_STRING = "protein_coding";

    @NotNull
    @Range(min = 0, max = 1000)
    private Integer heatmapMatrixSize = DEFAULT_NUMBER_OF_RANKED_GENES;

    @NotNull
    @Min(0)
    private Double cutoff = DEFAULT_CUTOFF;

    private SortedSet<String> organismParts;

    private String geneQuery = DEFAULT_GENE_QUERY_STRING;

    private boolean displayLevels;

    private boolean displayGeneDistribution;

    private NumberUtils numberUtils = new NumberUtils();

    public SortedSet<String> getOrganismParts() {
        return organismParts;
    }

    public boolean isDisplayGeneDistribution() {
        return displayGeneDistribution;
    }

    public void setDisplayGeneDistribution(boolean displayGeneDistribution) {
        this.displayGeneDistribution = displayGeneDistribution;
    }

    public Integer getHeatmapMatrixSize() {
        return this.heatmapMatrixSize;
    }

    public void setHeatmapMatrixSize(Integer heatmapMatrixSize) {
        this.heatmapMatrixSize = heatmapMatrixSize != null ? heatmapMatrixSize : DEFAULT_NUMBER_OF_RANKED_GENES;
    }

    public Double getCutoff() {
        return this.cutoff;
    }

    public void setCutoff(Double cutoff) {
        this.cutoff = cutoff != null ? numberUtils.round(cutoff) : DEFAULT_CUTOFF;
    }

    public void setDisplayLevels(boolean displayLevels) {
        this.displayLevels = displayLevels;
    }

    public boolean getDisplayLevels() {
        return displayLevels;
    }

    public void setOrganismParts(SortedSet<String> organismParts) {
        this.organismParts = organismParts;
    }

    public String getGeneQuery() {
        return this.geneQuery;
    }

    public void setGeneQuery(String geneQuery) {
        this.geneQuery = geneQuery;
    }

    public String toString(){
        return Objects.toStringHelper(this.getClass())
                .add("geneQuery", geneQuery)
                .add("organismParts", organismParts)
                .add("cutoff", cutoff)
                .toString();
    }

}
