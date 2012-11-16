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

import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import org.hibernate.validator.constraints.Range;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.utils.NumberUtils;

import javax.inject.Named;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.SortedSet;
import java.util.regex.Pattern;

@Named("requestPreferences")
@Scope("prototype")
public class RequestPreferences {

    static final int DEFAULT_NUMBER_OF_RANKED_GENES = 50;
    static final double DEFAULT_CUTOFF = 0.5d;
    static final Pattern commaOrSpaceSeparatorPattern = Pattern.compile("\\s*(,+|\\s)+\\s*");

    @NotNull
    @Range(min = 0, max = 1000)
    private Integer heatmapMatrixSize = DEFAULT_NUMBER_OF_RANKED_GENES;

    @NotNull
    @Min(0)
    private Double cutoff = DEFAULT_CUTOFF;

    private SortedSet<String> organismParts;

    private String geneIDsString;

    private Set<String> geneIDs;

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
        this.cutoff = cutoff !=null ? numberUtils.round(cutoff) : DEFAULT_CUTOFF;
    }

    public void setDisplayLevels(boolean displayLevels){
        this.displayLevels = displayLevels;
    }

    public boolean getDisplayLevels(){
        return displayLevels;
    }

    public void setOrganismParts(SortedSet<String> organismParts) {
        this.organismParts = organismParts;
    }

    public String getGeneIDsString() {
        return this.geneIDsString;
    }

    public void setGeneIDsString(String geneIDsString) {
        this.geneIDsString = geneIDsString;
        if (!Strings.isNullOrEmpty(geneIDsString)) {
            setGeneIDs(commaOrSpaceSeparatorPattern.split(geneIDsString));
        }
    }

    private void setGeneIDs(String[] geneIDs){
        this.geneIDs = Sets.newHashSet(geneIDs);
    }

    public Set<String> getGeneIDs() {
        return geneIDs;
    }

}
