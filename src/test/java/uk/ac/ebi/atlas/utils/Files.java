
package uk.ac.ebi.atlas.utils;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.StringWriter;

public class Files {

    public static String readTextFileFromClasspath(Class aClass, String fileName) throws IllegalStateException{

        try {

            StringWriter writer = new StringWriter();
            IOUtils.copy(aClass.getResourceAsStream(fileName), writer, "UTF-8");
            return writer.toString();

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

    }

}
