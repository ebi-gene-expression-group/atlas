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

package uk.ac.ebi.atlas.dao.diffexpression;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;

import java.util.Iterator;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

public class DatabaseQuery<T> {

    private List<T> params = Lists.newArrayList();
    private StringBuilder queryBuilder = new StringBuilder();

    void addParameter(T value) {
        params.add(value);
    }

    List<T> getParameters() {
        return params;
    }

    String getQuery() {
        return queryBuilder.toString();
    }

    void setQueryString(String query) {
        queryBuilder = new StringBuilder(query);
    }

    DatabaseQuery appendToQueryString(String text) {
        queryBuilder.append(text);
        return this;
    }

    //used for debugging
    public String expand() {
        return substituteQuestionMarksForParameters(queryBuilder.toString(), quote(params));
    }

    protected static String substituteQuestionMarksForParameters(String string, List<?> params) {
        StringBuilder result = new StringBuilder();

        checkArgument(StringUtils.countMatches(string, "?") == params.size(), String.format("Number of question marks (%s) does not match number of params (%s)", StringUtils.countMatches(string, "?"), params.size()));

        Iterable < String > nonParameters = Splitter.on('?').split(string);
        Iterator<?> parameters = params.iterator();

        for (String nonParam : nonParameters) {
            result.append(nonParam);
            if (parameters.hasNext()) {
                result.append(parameters.next().toString());
            }
        }

        return result.toString();
    }

    protected static ImmutableList<String> quote(List<?> params) {
        ImmutableList.Builder<String> builder = ImmutableList.builder();

        for (Object param : params) {
            builder.add("'" + param.toString() + "'");
        }

        return builder.build();
    }

    @Override
    public String toString(){
        return "query: " + getQuery() + ", params: " + params;
    }
}
