package uk.ac.ebi.atlas.resource;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashSet;

public class MockDataFileHub extends DataFileHub {

    final File dir;

    public MockDataFileHub(File dir) {
        super(dir.getPath());
        this.dir = dir;
    }

    public void addTemporaryTsv(String where, Collection<String[]> lines){
        addTemporaryFile( where,
                FluentIterable.from(lines).transform(new Function<String[], String>() {
                    @Nullable
                    @Override
                    public String apply(@Nullable String[] strings) {
                        return Joiner.on('\t').join(strings);
                    }
                }).toList());
    }

    public void addFpkmsExpressionFile(String accession, Collection<String[]> lines){
        addTemporaryTsv(MessageFormat.format(RNASEQ_BASELINE_FPKMS_FILE_PATH_TEMPLATE, accession), lines);
    }

    public void addTpmsExpressionFile(String accession, Collection<String[]> lines){
        addTemporaryTsv(MessageFormat.format(RNASEQ_BASELINE_TPMS_FILE_PATH_TEMPLATE, accession), lines);
    }


    public void addProteomicsExpressionFile(String accession, Collection<String[]> lines){
        addTemporaryTsv(MessageFormat.format(PROTEOMICS_BASELINE_EXPRESSION_FILE_PATH_TEMPLATE, accession), lines);
    }

    public void addTemporaryEmptyFile(String where){
       addTemporaryTsv(where, new HashSet<String[]>());
    }

    public void addTemporaryFile(String where, Collection<String> lines){
        File f = new File(dir.getAbsolutePath()+where);
        f.deleteOnExit();
        f.getParentFile().mkdirs();
        try {
            f.createNewFile();
            Files.write(f.toPath(), lines, Charset.forName("UTF-8"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static MockDataFileHub get() {
        File dir = new File(System.getProperty("java.io.tmpdir") + "/" + System.currentTimeMillis());
        dir.mkdir();
        new File(dir.getAbsolutePath()+"/admin").mkdir();
        new File(dir.getAbsolutePath()+"/magetab").mkdir();
        new File(dir.getAbsolutePath()+"/expdesign").mkdir();
        new File(dir.getAbsolutePath()+"/serialized_expression").mkdir();
        dir.deleteOnExit();

         return new MockDataFileHub(dir);

    }
}
