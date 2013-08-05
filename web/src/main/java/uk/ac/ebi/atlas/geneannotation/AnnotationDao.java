package uk.ac.ebi.atlas.geneannotation;

import java.util.List;

public class AnnotationDao {

    protected String getOnly(List<String> objects) {
        if (objects.size() == 1) {
            return objects.get(0);
        } else {
            return null;
        }
    }

}
