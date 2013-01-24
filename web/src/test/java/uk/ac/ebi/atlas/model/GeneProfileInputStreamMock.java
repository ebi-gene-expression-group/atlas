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

package uk.ac.ebi.atlas.model;

import com.google.common.collect.Sets;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GeneProfileInputStreamMock implements ObjectInputStream<GeneProfile> {

    private Iterator<GeneProfile> geneProfilesIterator;

    //This will create a stream containing n Gene Profiles
    //Each Gene Profile will contain an increasing number of Expressions (1, 2, 3, ....)
    //For each Gene Profile, each Expression will have an increasing Expression Level value (1, 2, 3, ...)
    //To make the test more complete the profiles are streamed in an order that is the reverse
    //of the final order required by the user stories, profiles with higher selectivity will be streamed last.
    public GeneProfileInputStreamMock(int streamSize) {

        GeneExpressionPrecondition geneExpressionPreconditionMock = mock(GeneExpressionPrecondition.class);
        when(geneExpressionPreconditionMock.apply(any(Expression.class))).thenReturn(true);
        when(geneExpressionPreconditionMock.getQueryFactorType()).thenReturn("factor_type");

        List<GeneProfile> geneProfiles = new ArrayList<GeneProfile>();

        for (int i = streamSize; i > 0; i--) {

            GeneProfile.Builder geneProfileBuilder = new GeneProfile.Builder().forGeneId("" + i);
            geneProfileBuilder.setGeneExpressionPrecondition(geneExpressionPreconditionMock);

            for (int j = 0; j < i; j++) {

                Expression expressionMock = mock(Expression.class);
                when(expressionMock.isGreaterThan(anyDouble())).thenReturn(true);
                when(expressionMock.getLevel()).thenReturn(j + 1D);
                FactorValue factorValue = new FactorValue("factor_type", "", "factor_value" + (j + 1));
                when(expressionMock.isForFactorValue(factorValue)).thenReturn(true);
                when(expressionMock.getAllFactorValues()).thenReturn(Sets.newHashSet(factorValue));
                when(expressionMock.getFactorValue("factor_type")).thenReturn(factorValue);
                geneProfileBuilder.addExpression(expressionMock);

            }

            geneProfiles.add(geneProfileBuilder.create());
        }

        geneProfilesIterator = geneProfiles.iterator();
    }

    @Override
    public GeneProfile readNext() {
        if (geneProfilesIterator.hasNext()) {
            return geneProfilesIterator.next();
        }
        return null;

    }

    @Override
    public void close() {

    }


}
