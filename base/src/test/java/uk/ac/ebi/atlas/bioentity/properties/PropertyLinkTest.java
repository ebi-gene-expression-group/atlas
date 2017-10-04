package uk.ac.ebi.atlas.bioentity.properties;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.assertThat;

public class PropertyLinkTest {
    @Test
    public void constructorWithoutUrl() {
        PropertyLink subject = new PropertyLink("foobar","", 0);

        assertThat(subject.getUrl(), isEmptyString());
    }

    @Test
    public void toJson() throws Exception {


        assertThat(new PropertyLink("foobar", "http://foobar.com/foobar", 0).toJson(), is(new Gson().fromJson("{\"text\":\"foobar\",\"url\":\"http://foobar.com/foobar\",\"relevance\":0}", JsonObject.class)));

    }
}