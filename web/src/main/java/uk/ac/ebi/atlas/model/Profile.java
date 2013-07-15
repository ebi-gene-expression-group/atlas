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
public abstract class Profile<K, T extends Expression> {
    private Map<K, T> expressionsByCondition = new HashMap<>();

    private String id;

    protected Profile(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public boolean isEmpty() {
        return expressionsByCondition.isEmpty();
    }

    public int getSpecificity() {
        return expressionsByCondition.size();
    }

    public Double getExpressionLevel(K condition) {
        Expression expression = expressionsByCondition.get(condition);
        if (expression != null) {
            return expression.getLevel();
        }
        return null;
    }

    protected abstract void updateProfileExpression(T expression);

    public boolean isExpressedOnAnyOf(Set<K> conditions) {
        checkArgument(CollectionUtils.isNotEmpty(conditions));
        return Sets.intersection(expressionsByCondition.keySet(), conditions).size() > 0;
    }

    public Set<K> getConditions() {
        return Sets.newHashSet(expressionsByCondition.keySet());
    }

    protected Profile addExpression(K condition, T expression) {
        updateProfileExpression(expression);
        expressionsByCondition.put(condition, expression);
        return this;
    }

    public T getExpression(K condition) {
        return expressionsByCondition.get(condition);
    }

}
