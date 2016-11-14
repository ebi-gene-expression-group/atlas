package uk.ac.ebi.atlas.resource;

import java.io.File;

public class MockDataFileHub {

    public static DataFileHub get() {
        File dir = new File(System.getProperty("java.io.tmpdir") + "/" + System.currentTimeMillis());
        dir.mkdir();
        new File(dir.getAbsolutePath()+"/admin").mkdir();
        new File(dir.getAbsolutePath()+"/magetab").mkdir();
        new File(dir.getAbsolutePath()+"/expdesign").mkdir();
        dir.deleteOnExit();

         return new DataFileHub(dir.getPath());

    }
}
