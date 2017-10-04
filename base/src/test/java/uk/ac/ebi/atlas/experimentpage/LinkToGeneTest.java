package uk.ac.ebi.atlas.experimentpage;

import org.junit.Test;
import uk.ac.ebi.atlas.model.Profile;

import java.util.concurrent.ThreadLocalRandom;

import static org.hamcrest.Matchers.endsWith;
import static org.junit.Assert.assertThat;

public class LinkToGeneTest {

    class DummyProfile extends Profile {
        DummyProfile(String id, String name) {
            super(id, name);
        }

        @Override
        protected Profile createEmptyCopy() {
            return null;
        }
    }

    // Not comprehensive, if gene IDs need to use any of the following chars we need to use a URLEncoder
    String[] ILLEGAL_CHARS = {"%", "ˆ", "|", "<", ">", "`", "#", "\"", "\\", "[", "]", "{", "}"};

    LinkToGene<DummyProfile> subject = new LinkToGene<>();

    @Test
    public void linksPointAtTheInformationTab() throws Exception {
        assertThat(subject.apply(new DummyProfile("geneId", "geneName")).toString(), endsWith("#information"));
    }

    @Test(expected = RuntimeException.class)
    public void uriSyntaxExceptionsAreWrapped() throws Exception {
        String randomIllegalChar = ILLEGAL_CHARS[ThreadLocalRandom.current().nextInt(0, ILLEGAL_CHARS.length)];
        subject.apply(new DummyProfile(randomIllegalChar, ""));
    }

}