package uk.ac.ebi.atlas.bioentity.properties;

import com.google.gson.Gson;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class PropertyLinkTest {
    @Test
    public void constructorWithoutUrl() {
        PropertyLink subject = new PropertyLink("foobar", 0);

        // Although equivalent to matching "", we explicitly state that we don’t want url to be null
        assertThat(subject.getUrl(), isEmptyOrNullString());
        assertThat(subject.getUrl(), is(not(nullValue())));
    }

    @Test
    public void toJson() throws Exception {
        PropertyLink subject = new PropertyLink("foobar", "http://foobar.com/foobar", 0);

        Gson gson = new Gson();
        PropertyLink deserializedSubject = gson.fromJson(subject.toJson(), PropertyLink.class);

        assertThat(deserializedSubject.getText(), is(subject.getText()));
        assertThat(deserializedSubject.getUrl(), is(subject.getUrl()));
    }
}