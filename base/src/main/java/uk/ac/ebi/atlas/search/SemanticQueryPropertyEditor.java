package uk.ac.ebi.atlas.search;

import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.MalformedJsonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.controllers.UnparseableSemanticQueryException;

import java.beans.PropertyEditorSupport;
import java.io.UnsupportedEncodingException;

public class SemanticQueryPropertyEditor extends PropertyEditorSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(SemanticQueryPropertyEditor.class);

    @Override
    public void setAsText(String text)  {
        try {
            setValue(SemanticQuery.fromUrlEncodedJson(text));
        } catch (MalformedJsonException | JsonSyntaxException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new UnparseableSemanticQueryException("Your query <em>" + text + "</em> could not be understood. It should be a JSON-formatted query or a plain text string.");
        }
    }

    @Override
    public String getAsText() {
        return ((SemanticQuery) this.getValue()).toUrlEncodedJson();
    }

}
