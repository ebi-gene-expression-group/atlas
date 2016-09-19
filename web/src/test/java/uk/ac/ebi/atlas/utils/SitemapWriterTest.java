package uk.ac.ebi.atlas.utils;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.util.Collections;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class SitemapWriterTest {

    private SitemapWriter subject = new SitemapWriter();

    @Test
    public void testEmptyUrl() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        subject.writeDocument(baos, Collections.EMPTY_LIST, "urlset", "url", ImmutableMap.<String, String>of());

        assertEquals("<?xml version='1.0' encoding='UTF-8'?><urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\"/>", baos.toString());
    }

    @Test
    public void testWriteOneGene() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        subject.writeGenes(baos, Lists.<String>newArrayList(), Lists.newArrayList("ASPM"));

        assertThat( baos.toString(), Matchers.containsString("<url><loc>http://www.ebi.ac" +
                ".uk/gxa/genes/ASPM</loc><changefreq>monthly</changefreq></url>"));
    }

    @Test
    public void testWriteMany() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        subject.writeDocument(baos, Collections.nCopies(10,"myCharacteristicWord"), "urlset", "url", ImmutableMap.<String, String>of());

        assertEquals(11, baos.toString().split("myCharacteristicWord").length);
    }

}