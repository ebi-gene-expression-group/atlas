package uk.ac.ebi.atlas.geneannotation.mirna;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.StringReader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class MirbaseDataParserTest {

    private MirbaseDataParser suject;

    @Before
    public void initSubject() throws Exception {
        BufferedReader reader = new BufferedReader(new StringReader(INPUT));

        suject = new MirbaseDataParser();
    }

    @Test
    public void testExtractAnnotationsMap() throws Exception {

    }

    @Test
    public void testGetID() throws Exception {
        String id = suject.getID("ID   hsa-mir-6770-3    standard; RNA; HSA; 60 BP.\n");
        assertThat(id, is("hsa-mir-6770-3"));

        id = suject.getID("ID   hsa-mir-6859-3    standard; RNA; HSA; 68 BP.\n");
        assertThat(id, is("hsa-mir-6859-3"));

        id = suject.getID("AC   MI0026419;\n");
        assertThat(id, is(nullValue()));
    }

    @Test
    public void testGetName() throws Exception {


    }

    private static final String INPUT =
            "ID   hsa-mir-6770-3    standard; RNA; HSA; 60 BP.\n" +
                    "XX\n" +
                    "AC   MI0026419;\n" +
                    "XX\n" +
                    "DE   Homo sapiens miR-6770-3 stem-loop\n" +
                    "XX\n" +
                    "RN   [1]\n" +
                    "RX   PUBMED; 22955976.\n" +
                    "RA   Ladewig E, Okamura K, Flynt AS, Westholm JO, Lai EC;\n" +
                    "RT   \"Discovery of hundreds of mirtrons in mouse and human small RNA data\";\n" +
                    "RL   Genome Res. 22:1634-1645(2012).\n" +
                    "XX\n" +
                    "FH   Key             Location/Qualifiers\n" +
                    "FH\n" +
                    "FT   miRNA           6..29\n" +
                    "FT                   /accession=\"MIMAT0027440\"\n" +
                    "FT                   /product=\"hsa-miR-6770-5p\"\n" +
                    "FT                   /evidence=experimental\n" +
                    "FT                   /experiment=\"meta-analysis [1]\"\n" +
                    "FT   miRNA           40..60\n" +
                    "FT                   /accession=\"MIMAT0027441\"\n" +
                    "FT                   /product=\"hsa-miR-6770-3p\"\n" +
                    "FT                   /evidence=experimental\n" +
                    "FT                   /experiment=\"meta-analysis [1]\"\n" +
                    "XX\n" +
                    "SQ   Sequence 60 BP; 10 A; 18 C; 18 G; 0 T; 14 other;\n" +
                    "     uauccugaga aggcacagcu ugcacgugac cuccugggcc uggcggcugu gucuucacag        60\n" +
                    "//\n" +
                    "ID   hsa-mir-6859-2    standard; RNA; HSA; 68 BP.\n" +
                    "XX\n" +
                    "AC   MI0026420;\n" +
                    "XX\n" +
                    "DE   Homo sapiens miR-6859-2 stem-loop\n" +
                    "XX\n" +
                    "RN   [1]\n" +
                    "RX   PUBMED; 22955976.\n" +
                    "RA   Ladewig E, Okamura K, Flynt AS, Westholm JO, Lai EC;\n" +
                    "RT   \"Discovery of hundreds of mirtrons in mouse and human small RNA data\";\n" +
                    "RL   Genome Res. 22:1634-1645(2012).\n" +
                    "XX\n" +
                    "FH   Key             Location/Qualifiers\n" +
                    "FH\n" +
                    "FT   miRNA           6..28\n" +
                    "FT                   /accession=\"MIMAT0027618\"\n" +
                    "FT                   /product=\"hsa-miR-6859-5p\"\n" +
                    "FT                   /evidence=experimental\n" +
                    "FT                   /experiment=\"meta-analysis [1]\"\n" +
                    "FT   miRNA           46..68\n" +
                    "FT                   /accession=\"MIMAT0027619\"\n" +
                    "FT                   /product=\"hsa-miR-6859-3p\"\n" +
                    "FT                   /evidence=experimental\n" +
                    "FT                   /experiment=\"meta-analysis [1]\"\n" +
                    "XX\n" +
                    "SQ   Sequence 68 BP; 12 A; 18 C; 24 G; 0 T; 14 other;\n" +
                    "     ugugggagag gaacaugggc ucaggacagc gggugucagc uugccugacc cccaugucgc        60\n" +
                    "     cucuguag                                                                 68\n" +
                    "//\n" +
                    "ID   hsa-mir-6859-3    standard; RNA; HSA; 68 BP.\n" +
                    "XX\n" +
                    "AC   MI0026421;\n" +
                    "XX\n" +
                    "DE   Homo sapiens miR-6859-3 stem-loop\n" +
                    "XX\n" +
                    "RN   [1]\n" +
                    "RX   PUBMED; 22955976.\n" +
                    "RA   Ladewig E, Okamura K, Flynt AS, Westholm JO, Lai EC;\n" +
                    "RT   \"Discovery of hundreds of mirtrons in mouse and human small RNA data\";\n" +
                    "RL   Genome Res. 22:1634-1645(2012).\n" +
                    "XX\n" +
                    "FH   Key             Location/Qualifiers\n" +
                    "FH\n" +
                    "FT   miRNA           6..28\n" +
                    "FT                   /accession=\"MIMAT0027618\"\n" +
                    "FT                   /product=\"hsa-miR-6859-5p\"\n" +
                    "FT                   /evidence=experimental\n" +
                    "FT                   /experiment=\"meta-analysis [1]\"\n" +
                    "FT   miRNA           46..68\n" +
                    "FT                   /accession=\"MIMAT0027619\"\n" +
                    "FT                   /product=\"hsa-miR-6859-3p\"\n" +
                    "FT                   /evidence=experimental\n" +
                    "FT                   /experiment=\"meta-analysis [1]\"\n" +
                    "XX\n" +
                    "SQ   Sequence 68 BP; 12 A; 18 C; 24 G; 0 T; 14 other;\n" +
                    "     ugugggagag gaacaugggc ucaggacagc gggugucagc uugccugacc cccaugucgc        60\n" +
                    "     cucuguag                                                                 68\n" +
                    "//";
}
