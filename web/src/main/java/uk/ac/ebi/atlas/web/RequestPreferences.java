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
import uk.ac.ebi.atlas.model.FactorValue;
import uk.ac.ebi.atlas.utils.NumberUtils;

import javax.inject.Named;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
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

    @Min(value = 0, message = "The expression level cutoff must be greater than 0")
    private Double cutoff = DEFAULT_CUTOFF;

    private SortedSet<String> organismParts;

    //ToDo: this will become factorValues when we remove organismParts
    private SortedSet<String> filterFactorValues;

    private String defaultFactorType;

    private boolean includeGenesExpressedOnNonSelectedFactorValues = true;

    private boolean rankGenesExpressedOnMostFactorsLast = true;

    @Size(max = 900,
            message = "The gene query expression is too long, please limit it to a maximum length of 900 characters")
    private String geneQuery = DEFAULT_GENE_QUERY_STRING;

    private boolean displayLevels;

    private boolean displayGeneDistribution;

    private NumberUtils numberUtils = new NumberUtils();

    //ToDo: this will be removed
    public SortedSet<String> getOrganismParts() {
        return organismParts;
    }

    public String getDefaultFactorType() {
        return defaultFactorType;
    }

    public void setDefaultFactorType(String type) {
        this.defaultFactorType = type;
    }

    public SortedSet<String> getFilterFactorValues() {
        return filterFactorValues;
    }

    public Set<FactorValue> getFilterFactorValuesAsObjects() {
        Set<FactorValue> results = new HashSet<>();

        // Turns a factor specification string into a FactorValue list.
        // Splits at : between type and value
        if (filterFactorValues != null) {
            for (String filter : filterFactorValues) {
                String[] split = filter.split(":");
                if (split.length == 2) {
                    FactorValue factorValue = new FactorValue(split[0], "", split[1]);
                    results.add(factorValue);
                }
            }
        }

        return results;
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

    public boolean isIncludeGenesExpressedOnNonSelectedFactorValues() {
        return this.includeGenesExpressedOnNonSelectedFactorValues;
    }

    public void setIncludeGenesExpressedOnNonSelectedFactorValues(boolean includeGenesExpressedOnNonSelectedFactorValues) {
        this.includeGenesExpressedOnNonSelectedFactorValues = includeGenesExpressedOnNonSelectedFactorValues;
    }

    public boolean isRankGenesExpressedOnMostFactorsLast() {
        return this.rankGenesExpressedOnMostFactorsLast;
    }

    public void setRankGenesExpressedOnMostFactorsLast(boolean rankGenesExpressedOnMostFactorsLast) {
        this.rankGenesExpressedOnMostFactorsLast = rankGenesExpressedOnMostFactorsLast;
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

    public void setFilterFactorValues(SortedSet<String> filterFactorValues) {
        this.filterFactorValues = filterFactorValues;
    }

    public String getGeneQuery() {
        return this.geneQuery;
    }

    public void setGeneQuery(String geneQuery) {
        this.geneQuery = geneQuery;
    }

    public String toString() {
        return Objects.toStringHelper(this.getClass())
                .add("geneQuery", geneQuery)
                .add("organismParts", organismParts)
                .add("cutoff", cutoff)
                .add("defaultFactorType", defaultFactorType)
                .toString();
    }

}
