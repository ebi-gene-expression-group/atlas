package uk.ac.ebi.atlas.web;

import java.beans.PropertyEditorSupport;
import java.io.UnsupportedEncodingException;

public class SemanticQueryPropertyEditor extends PropertyEditorSupport {


    @Override
    public void setAsText(String text)  {
        try {
            setValue(SemanticQuery.fromUrlEncodedJson(text));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public String getAsText() {
        try {
            return ((SemanticQuery) this.getValue()).toUrlEncodedJson();
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

}
