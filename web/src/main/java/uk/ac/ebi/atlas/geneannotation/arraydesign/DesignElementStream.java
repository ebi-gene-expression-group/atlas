package uk.ac.ebi.atlas.geneannotation.arraydesign;

import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;

import java.io.IOException;

public class DesignElementStream implements ObjectInputStream<String[]> {
    private String input;

    @Override
    public String[] readNext() {
        return new String[0];
    }

    @Override
    public void close() throws IOException {

    }
}
