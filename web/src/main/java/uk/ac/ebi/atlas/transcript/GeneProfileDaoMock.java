package uk.ac.ebi.atlas.transcript;

import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import uk.ac.ebi.atlas.model.baseline.TranscriptProfile;
import uk.ac.ebi.atlas.model.baseline.TranscriptProfiles;

import javax.inject.Named;


@Named
public class GeneProfileDaoMock {

    private static final Logger LOGGER = Logger.getLogger(GeneProfileDaoMock.class);

    private TranscriptProfile profile1 = new TranscriptProfile("T1", Lists.newArrayList(1d, 1d, 3d, 1d, 1d, 3d, 1d, 1d, 3d, 1d, 1d, 3d, 1d, 1d, 3d));
    private TranscriptProfile profile2 = new TranscriptProfile("T2", Lists.newArrayList(5d, 2d, 6d, 5d, 2d, 6d, 5d, 2d, 6d, 5d, 2d, 6d, 5d, 2d, 6d));
    private TranscriptProfile profile3 = new TranscriptProfile("T3", Lists.newArrayList(7d, 3d, 0d, 7d, 3d, 0d, 7d, 3d, 0d, 7d, 3d, 0d, 7d, 3d, 0d));
    private TranscriptProfile profile4 = new TranscriptProfile("T4", Lists.newArrayList(0d, 4d, 3d, 0d, 4d, 3d, 0d, 4d, 3d, 0d, 4d, 3d, 0d, 4d, 3d));


    public TranscriptProfiles getTranscriptProfiles(String experimentAccession, String geneId) {
       return new TranscriptProfiles(Lists.newArrayList(profile1, profile2, profile3, profile4));

    }
}
