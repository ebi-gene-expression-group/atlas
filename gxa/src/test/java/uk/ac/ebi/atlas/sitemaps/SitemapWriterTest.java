package uk.ac.ebi.atlas.sitemaps;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.nio.charset.Charset;
import java.util.Collections;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


@RunWith(JUnit4.class)
public class SitemapWriterTest {

    private SitemapWriter subject = new SitemapWriter();

    @Test
    public void testEmptyUrl() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        subject.writeDocument(baos, Collections.emptyList(), "urlset", "url", ImmutableMap.of());

        assertThat(
                "<?xml version='1.0' encoding='UTF-8'?><urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\"/>",
                is(baos.toString(Charset.defaultCharset())));
    }

    @Test
    public void testWriteOneGene() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        subject.writeGenes(baos, Lists.newArrayList(), Lists.newArrayList("ASPM"));

        assertThat(
                baos.toString(Charset.defaultCharset()),
                containsString(
                        "<url><loc>http://www.ebi.ac.uk/gxa/genes/ASPM</loc><changefreq>monthly</changefreq></url>"));
    }

    @Test
    public void testWriteMany() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        subject.writeDocument(baos, Collections.nCopies(10,"myCharacteristicWord"), "urlset", "url", ImmutableMap.of());

        assertThat(baos.toString(Charset.defaultCharset()).split("myCharacteristicWord").length, is(11));
    }

}