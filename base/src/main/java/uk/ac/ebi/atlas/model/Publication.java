package uk.ac.ebi.atlas.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Publication {

    @JsonProperty("pmid")
    private String pubmedId;

    private String doi;
    private String title;

    @JsonProperty("authorString")
    private String authors;

    public Publication() {

    }

    public Publication(String pubmedId, String doi, String title) {
        this.pubmedId = pubmedId;
        this.doi = doi;
        this.title = title;
    }

    public String getPubmedId() {
        return pubmedId;
    }

    public String getDoi() {
        return doi;
    }

    public String getTitle() {
        return title;
    }

    public void setPubmedId(String pubmedId) {
        this.pubmedId = pubmedId;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Retrieves authors citation-style. If there are more than 5 authors, it appends "et al" after the 5th
    public String getAuthors() {
        String[] authorsArray = authors.split(",");

        if (authorsArray.length < 5) {
            return authors;
        }
        else {
            String shownAuthors = String.join(",", Arrays.copyOfRange(authorsArray, 0, 5));

            return String.join(" ",shownAuthors, "et al.");
        }
    }

    public String getFullAuthorList() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }
}
