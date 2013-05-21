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

package uk.ac.ebi.atlas.transcript;

import java.util.LinkedHashMap;
import java.util.Map;

public class TranscriptContributions {

    protected static final String OTHERS = "Others";

    private int totalTranscriptsCount;

    private Map<String, Double> transcriptExpressions = new LinkedHashMap<>();

    public int getTotalTranscriptsCount() {
        return totalTranscriptsCount;
    }

    public void setTotalTranscriptsCount(int totalTranscriptsCount) {
        this.totalTranscriptsCount = totalTranscriptsCount;
    }

    public Map<String, Double> getTranscriptExpressions() {
        return transcriptExpressions;
    }

    public void put(String transcriptId, double fpkm) {
        transcriptExpressions.put(transcriptId, fpkm);
    }

}
