package uk.ac.ebi.atlas.utils;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 12/11/2015.
 */
public class ResourceUtils {
    public static String readPlainTextResource(Resource resource) {
        try (InputStream inputStream = resource.getInputStream()) {
            return IOUtils.toString(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
