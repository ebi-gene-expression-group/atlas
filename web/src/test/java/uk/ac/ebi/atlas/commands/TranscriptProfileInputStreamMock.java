package uk.ac.ebi.atlas.commands;

import uk.ac.ebi.atlas.commons.ObjectInputStream;
import uk.ac.ebi.atlas.model.ExpressionLevel;
import uk.ac.ebi.atlas.model.TranscriptProfile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TranscriptProfileInputStreamMock implements ObjectInputStream<TranscriptProfile> {

    private Iterator<TranscriptProfile> transcriptProfilesIterator;

    //This will create a stream containing n Transcript Profiles
    //Each Transcript Profile will contain an increasing number of Expression Levels (1, 2, 3, ....)
    //For each Transcript Profile, each Expression Level will have an increasing RPKM value (1, 2, 3, ...)
    //To make the test more complete the profiles are streamed in an order that is the reverse
    //of the final order required by the user stories, profiles with higher selectivity will be streamed last.
    public TranscriptProfileInputStreamMock(int streamSize){

        List<TranscriptProfile> transcriptProfiles = new ArrayList<TranscriptProfile>();

        for(int i = streamSize; i > 0 ; i--){

            TranscriptProfile.Builder transcriptProfileBuilder = TranscriptProfile.forTranscriptId("" + i);

            for(int j = 0; j < i; j++){

                ExpressionLevel expressionLevelMock = mock(ExpressionLevel.class);
                when(expressionLevelMock.isGreaterThan(anyDouble())).thenReturn(true);
                when(expressionLevelMock.getRpkm()).thenReturn(j+1D);
                transcriptProfileBuilder.addExpressionLevel(expressionLevelMock);

            }

            transcriptProfiles.add(transcriptProfileBuilder.create());
        }

        transcriptProfilesIterator = transcriptProfiles.iterator();
    }

    @Override
    public TranscriptProfile readNext(){
        if (transcriptProfilesIterator.hasNext()){
            return transcriptProfilesIterator.next();
        }
        return null;

    }

    @Override
    public void close(){

    }


}
