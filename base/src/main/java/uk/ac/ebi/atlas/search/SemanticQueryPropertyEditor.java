package uk.ac.ebi.atlas.search;

import com.google.gson.stream.MalformedJsonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyEditorSupport;
import java.io.UnsupportedEncodingException;

public class SemanticQueryPropertyEditor extends PropertyEditorSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(SemanticQueryPropertyEditor.class);

    @Override
    public void setAsText(String text)  {
        try {
            setValue(SemanticQuery.fromUrlEncodedJson(text));
        } catch (UnsupportedEncodingException | MalformedJsonException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getAsText() {
        return ((SemanticQuery) this.getValue()).toUrlEncodedJson();
    }

}
