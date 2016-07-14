package uk.ac.ebi.atlas.web;

import autovalue.shaded.com.google.common.common.base.Throwables;
import com.google.gson.stream.MalformedJsonException;

import java.beans.PropertyEditorSupport;
import java.io.UnsupportedEncodingException;

public class GeneQueryPropertyEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text)  {
        try {
            setValue(GeneQuery.fromUrlEncodedJson(text));
        } catch (UnsupportedEncodingException | MalformedJsonException e) {
            throw Throwables.propagate(e);
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
