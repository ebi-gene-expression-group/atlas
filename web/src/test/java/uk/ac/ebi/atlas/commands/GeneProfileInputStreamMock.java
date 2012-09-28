package uk.ac.ebi.atlas.commands;

import uk.ac.ebi.atlas.commons.ObjectInputStream;
import uk.ac.ebi.atlas.model.Expression;
import uk.ac.ebi.atlas.model.GeneProfile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

        List<GeneProfile> geneProfiles = new ArrayList<GeneProfile>();

        for (int i = streamSize; i > 0; i--) {

            GeneProfile.Builder geneProfileBuilder = GeneProfile.forGeneId("" + i);

            for (int j = 0; j < i; j++) {

                Expression expressionMock = mock(Expression.class);
                when(expressionMock.isGreaterThan(anyDouble())).thenReturn(true);
                when(expressionMock.getLevel()).thenReturn(j + 1D);
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
