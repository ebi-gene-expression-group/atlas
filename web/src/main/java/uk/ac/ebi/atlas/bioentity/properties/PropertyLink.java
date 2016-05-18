
package uk.ac.ebi.atlas.bioentity.properties;

public class PropertyLink {

    private String text;

    private String url;

    public PropertyLink(String text, String url) {
        this.text = text;
        this.url = url;
    }

    public PropertyLink(String text) {
        this(text, "");
    }

    public String getText() {
        return text;
    }

    public String getUrl() {
        return url;
    }
}
