package uk.ac.ebi.atlas.streams;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.commons.ObjectInputStream;
import uk.ac.ebi.atlas.model.ExperimentRun;
import uk.ac.ebi.atlas.model.GeneProfile;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;
import utils.ExperimentRunsBuilder;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GeneProfilesInputStreamIT {

    private static final String RUN_ACCESSION_1 = "ERR030872";
    private static final String RUN_ACCESSION_2 = "ERR030873";
    private static final String RUN_ACCESSION_3 = "ERR030874";
    private static final String GENE_ID_1 = "ENST00000000233";
    private static final String GENE_ID_2 = "ENST00000000412";
    private static final String GENE_ID_3 = "ENST00000000442";

    private List<ExperimentRun> experimentRuns;

    private URL dataFileURL;

    private ObjectInputStream<GeneProfile> subject;

    private ExpressionsBuffer.Builder expressionsBufferBuilder;

    @Before
    public void initSubject() throws Exception {

        dataFileURL = GeneProfilesInputStreamIT.class.getResource("testCSVReader-data.tab");

        ExperimentRun experimentRun1 = ExperimentRunsBuilder.forRunAccession(RUN_ACCESSION_1).create();
        ExperimentRun experimentRun2 = ExperimentRunsBuilder.forRunAccession(RUN_ACCESSION_2).create();
        ExperimentRun experimentRun3 = ExperimentRunsBuilder.forRunAccession(RUN_ACCESSION_3).create();

        experimentRuns = Lists.newArrayList(experimentRun2, experimentRun3, experimentRun1);

        ExperimentsCache cache = mock(ExperimentsCache.class);

        when(cache.getExperimentRuns(anyString())).thenReturn(experimentRuns);

        expressionsBufferBuilder = new ExpressionsBuffer.Builder(cache);

        subject = new GeneProfilesInputStream.Builder(new GeneProfilesInputStream(), expressionsBufferBuilder)
                                                .forDataFileInputStream(dataFileURL.openStream())
                .withExperimentAccession("FAKE_ACCESSION") //we injected expressionsBufferBuilder containing a mock ExperimentsCache, so experiment accession is not relevant
                .create();

    }

    @Test
    public void readNextShouldReturnNextExpression() throws IOException {
        //given
        GeneProfile geneProfile = subject.readNext();
        //then
        assertThat(geneProfile.getGeneId(), is(GENE_ID_1));
        assertThat(geneProfile.getSpecificity(), is(1));
        assertThat(geneProfile.iterator().hasNext(), is(true));
        //ToDo: GeneProfile needs a getter for Expressions

        //given we poll twice more
        geneProfile = subject.readNext();
        //then
        assertThat(geneProfile.getGeneId(), is(GENE_ID_2));
        assertThat(geneProfile.getSpecificity(), is(3));

        geneProfile = subject.readNext();

        assertThat(geneProfile.getGeneId(), is(GENE_ID_3));
        assertThat(geneProfile.getSpecificity(), is(2));
    }


    @Test
    public void readNextShouldReturnNullGivenAllExpressionLevelsHaveBeenRead() throws Exception {
        GeneProfile geneProfile;

        for (int i = 0; i < 3; i++) {
            //given
            geneProfile = subject.readNext();
            //then
            assertThat(geneProfile, is(notNullValue()));
        }
        //given
        geneProfile = subject.readNext();
        //then
        assertThat(geneProfile, is(nullValue()));
    }

    @Test
    public void setCutoffChangesSpecificity() throws IOException {

        //given
        subject = new GeneProfilesInputStream.Builder(new GeneProfilesInputStream(), expressionsBufferBuilder)
                                                .forDataFileInputStream(dataFileURL.openStream())
            .withExperimentAccession("FAKE_ACCESSION") //we injected expressionsBufferBuilder containing a mock ExperimentsCache, so experiment accession is not relevant
            .withCutoff(20D)
            .create();

        //when
        subject.readNext();
        GeneProfile geneProfile = subject.readNext();

        //then specificity of second gene should change
        assertThat(geneProfile.getSpecificity(), is(2));

        //then third gene is not created since it has no expressions higher than cutoff.
        assertThat(subject.readNext(), is(nullValue()));

    }

    @Test(expected = IllegalStateException.class)
    public void givenTheReaderHasBeenClosedReadNextShouldThrowIllegalStateException() throws Exception {
        //given
        subject.close();
        //when
        subject.readNext();
    }


    @Test
    public void closingTwiceShouldNotThrowException() throws Exception {
        //given
        subject.close();
        //when
        subject.close();
    }

}
