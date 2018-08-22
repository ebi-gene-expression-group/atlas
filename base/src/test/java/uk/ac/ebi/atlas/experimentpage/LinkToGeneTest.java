package uk.ac.ebi.atlas.experimentpage;

import org.junit.Test;
import uk.ac.ebi.atlas.model.Profile;

import static org.hamcrest.Matchers.endsWith;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

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
    private static final String[] ILLEGAL_CHARS = {"%", "^", "|", "<", ">", "`", "\"", "\\", "[", "]", "{", "}"};

    private LinkToGene<DummyProfile> subject = new LinkToGene<>();

    // The hash will be set by the view, see search-results.jsp
    @Test
    public void linksAtNoSpecificTab() {
        assertThat(subject.apply(new DummyProfile("geneId", "geneName")).toString(), endsWith("geneId"));
    }

    @Test
    public void uriSyntaxExceptionsAreWrapped() {
        for (String illegalChar: ILLEGAL_CHARS) {
            try {
                subject.apply(new DummyProfile(illegalChar, ""));
                fail("Did not throw:" + illegalChar);
            } catch (RuntimeException e) {
                //yum
            }
        }
    }
}
