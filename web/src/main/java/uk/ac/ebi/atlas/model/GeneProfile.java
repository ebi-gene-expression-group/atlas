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

package uk.ac.ebi.atlas.model;

import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

//K is the Condition type (i.e. Factor or Contrast),
//T is the Expression type (Baseline Expression or DifferentialExpression)
public abstract class GeneProfile<K, T extends GeneExpression> {
    private Map<K, T> expressionsByCondition = new HashMap<>();

    private String geneId;

    public GeneProfile(String geneId) {
        this.geneId = geneId;
    }

    public String getGeneId() {
        return geneId;
    }

    public boolean isEmpty() {
        return expressionsByCondition.isEmpty();
    }

    public int getSpecificity() {
        return expressionsByCondition.size();
    }

    public double getExpressionLevel(K condition) {
        GeneExpression expression = expressionsByCondition.get(condition);
        return expression == null ? 0 : expression.getLevel();
    }

    protected abstract void updateProfileExpression(T geneExpression);

    public boolean isExpressedOnAnyOf(Set<K> conditions) {
        checkArgument(CollectionUtils.isNotEmpty(conditions));
        return Sets.intersection(this.expressionsByCondition.keySet(), conditions).size() > 0;
    }

    protected GeneProfile addExpression(K condition, T geneExpression) {
        updateProfileExpression(geneExpression);
        this.expressionsByCondition.put(condition, geneExpression);
        return this;
    }

    public T getExpression(K contrast) {
        return expressionsByCondition.get(contrast);
    }

}
