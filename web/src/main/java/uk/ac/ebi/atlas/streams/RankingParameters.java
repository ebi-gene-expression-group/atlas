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

package uk.ac.ebi.atlas.streams;

import org.springframework.context.annotation.Scope;

import javax.inject.Named;

@Named
@Scope("request")
public class RankingParameters {

    private boolean specific;

    private Integer heatmapMatrixSize;

    public RankingParameters(){

    }

    public boolean isSpecific() {
        return specific;
    }

    public Integer getHeatmapMatrixSize() {
        return heatmapMatrixSize;
    }


    public void setSpecific(boolean specific) {
        this.specific = specific;
    }

    public void setHeatmapMatrixSize(Integer heatmapMatrixSize) {
        this.heatmapMatrixSize = heatmapMatrixSize;
    }

    @Override
    public String toString() {
        return super.toString() + " " + this.specific + " " + this.heatmapMatrixSize;
    }
}