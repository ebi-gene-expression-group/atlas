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

package uk.ac.ebi.atlas.model.baseline;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class TranscriptProfile implements Serializable {

    private String transcriptId;

    private List<Double> expressions;

    public TranscriptProfile(String transcriptId, List<Double> expressions) {
        this.transcriptId = transcriptId;
        this.expressions = expressions;
    }

    public String getTranscriptId() {
        return transcriptId;
    }

    public List<Double> getExpressions() {
        return Collections.unmodifiableList(expressions);
    }

    public double getExpression(int index) {
        return expressions.get(index);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TranscriptProfile other = (TranscriptProfile) obj;

        return Objects.equals(transcriptId, other.transcriptId)
                && Objects.equals(expressions, other.expressions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transcriptId, expressions);
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static TranscriptProfile fromJson(String jsonString) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(jsonString, TranscriptProfile.class);
    }
}
