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

package uk.ac.ebi.atlas.streams.baseline;

import uk.ac.ebi.atlas.commands.context.BaselineRequestContext;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.baseline.impl.FactorSet;
import uk.ac.ebi.atlas.streams.baseline.BaselineExpressionIsAboveCutoffAndForFilterFactors;
import uk.ac.ebi.atlas.streams.baseline.BaselineProfileConditionalBuilder;
import uk.ac.ebi.atlas.streams.baseline.BaselineProfileIsSpecific;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GeneProfileInputStreamMock implements ObjectInputStream<BaselineProfile> {

    private Iterator<BaselineProfile> geneProfilesIterator;

    //This will create a stream containing n Gene Profiles
    //Each Gene Profile will contain an increasing number of Expressions (1, 2, 3, ....)
    //For each Gene Profile, each BaselineExpression will have an increasing BaselineExpression Level value (1, 2, 3, ...)
    //To make the test more complete the profiles are streamed in an order that is the reverse
    //of the final order required by the user stories, profiles with higher selectivity will be streamed last.
    public GeneProfileInputStreamMock(int streamSize) {

        BaselineRequestContext requestContextMock = mock(BaselineRequestContext.class);
        when(requestContextMock.getQueryFactorType()).thenReturn("ORGANISM_PART");

        BaselineExpressionIsAboveCutoffAndForFilterFactors baselineExpressionIsAboveCutoffAndForFilterFactorsMock = mock(BaselineExpressionIsAboveCutoffAndForFilterFactors.class);
        when(baselineExpressionIsAboveCutoffAndForFilterFactorsMock.setCutoff(anyDouble())).thenReturn(baselineExpressionIsAboveCutoffAndForFilterFactorsMock);
        when(baselineExpressionIsAboveCutoffAndForFilterFactorsMock.setFilterFactors(anySet())).thenReturn(baselineExpressionIsAboveCutoffAndForFilterFactorsMock);
        when(baselineExpressionIsAboveCutoffAndForFilterFactorsMock.apply(any(BaselineExpression.class))).thenReturn(true);

        BaselineProfileIsSpecific baselineProfileIsSpecificMock = mock(BaselineProfileIsSpecific.class);
        when(baselineProfileIsSpecificMock.setSelectedQueryFactors(anySet())).thenReturn(baselineProfileIsSpecificMock);
        when(baselineProfileIsSpecificMock.setSpecific(anyBoolean())).thenReturn(baselineProfileIsSpecificMock);
        when(baselineProfileIsSpecificMock.setAllQueryFactors(anySet())).thenReturn(baselineProfileIsSpecificMock);
        when(baselineProfileIsSpecificMock.apply(any(BaselineProfile.class))).thenReturn(true);

        List<BaselineProfile> baselineProfiles = new ArrayList<BaselineProfile>();

        for (int i = streamSize; i > 0; i--) {

            BaselineProfileConditionalBuilder geneProfileBuilder = new BaselineProfileConditionalBuilder(requestContextMock,
                    baselineExpressionIsAboveCutoffAndForFilterFactorsMock, baselineProfileIsSpecificMock);
            geneProfileBuilder.forGeneIdAndName("" + i, "name" +i);

            for (int j = 0; j < i; j++) {

                BaselineExpression expressionMock = mock(BaselineExpression.class);
                when(expressionMock.isGreaterThan(anyDouble())).thenReturn(true);
                when(expressionMock.getLevel()).thenReturn(j + 1D);
                when(expressionMock.isKnown()).thenReturn(true);
                Factor factor = new Factor("ORGANISM_PART", "factor_value" + (j + 1));
                when(expressionMock.getFactorGroup()).thenReturn(new FactorSet().add(factor));
                when(expressionMock.getFactor("ORGANISM_PART")).thenReturn(factor);
                geneProfileBuilder.addExpression(expressionMock);

            }

            baselineProfiles.add(geneProfileBuilder.create());
        }

        geneProfilesIterator = baselineProfiles.iterator();
    }

    @Override
    public BaselineProfile readNext() {
        if (geneProfilesIterator.hasNext()) {
            return geneProfilesIterator.next();
        }
        return null;

    }

    @Override
    public void close() {

    }


}
