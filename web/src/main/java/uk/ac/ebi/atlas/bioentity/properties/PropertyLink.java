package uk.ac.ebi.atlas.bioentity.properties;

// Used in bioentity-information.jsp
public class PropertyLink {

    private String text;
    private String url;

    PropertyLink(String text, String url) {
        this.text = text;
        this.url = url;
    }

    PropertyLink(String text) {
        this(text, "");
    }

    public String getText() {
        return text;
    }

    public String getUrl() {
        return url;
    }
}
