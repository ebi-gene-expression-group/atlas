package uk.ac.ebi.atlas.bioentity.properties;

import com.google.gson.JsonObject;
import org.junit.Test;

import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

public class PropertyLinkTest {
    @Test
    public void constructorWithoutUrl() {
        PropertyLink subject = new PropertyLink("foobar", "", 0);
        assertThat(subject.getUrl(), is(emptyString()));
    }

    @Test
    public void toJson() {
        assertThat(
                new PropertyLink("foobar", "http://foobar.com/foobar", 0).toJson(),
                is(GSON.fromJson(
                        "{\"text\":\"foobar\",\"url\":\"http://foobar.com/foobar\",\"relevance\":0}",
                        JsonObject.class)));
    }
}
