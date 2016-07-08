package uk.ac.ebi.atlas.web;

import java.beans.PropertyEditorSupport;
import java.io.UnsupportedEncodingException;

public class GeneQueryPropertyEditor extends PropertyEditorSupport {


    @Override
    public void setAsText(String text)  {
        try {
            setValue(GeneQuery.fromUrlEncodedJson(text));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public String getAsText() {
        try {
            return ((GeneQuery) this.getValue()).toUrlEncodedJson();
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

}
