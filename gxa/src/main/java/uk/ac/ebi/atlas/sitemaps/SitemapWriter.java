package uk.ac.ebi.atlas.sitemaps;

import com.google.common.collect.ImmutableMap;
import uk.ac.ebi.atlas.species.SpeciesProperties;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.ImmutableList.toImmutableList;

public class SitemapWriter {

    private XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
    private XMLEventFactory eventFactory = XMLEventFactory.newInstance();

    // TODO Build URL with ServletUriComponentsBuilder.fromCurrentContextPath()
    private static final String OUR_ADDRESS = "http://www.ebi.ac.uk/gxa";

    public void writeSitemapIndex(OutputStream outputStream,
                                  Collection<SpeciesProperties> speciesProperties) throws XMLStreamException {

        writeDocument(
                outputStream,
                speciesProperties.stream()
                        .map(s -> OUR_ADDRESS + "/species/" + s.ensemblName() + "/sitemap.xml")
                        .collect(toImmutableList()),
                "sitemapindex", "sitemap", ImmutableMap.of());
    }

    public void writeGenes(OutputStream outputStream, Collection<String> various, Collection<String> genes)
            throws
            XMLStreamException {
        List<String> urls = new ArrayList<>();
        urls.addAll(various.stream().map(s -> OUR_ADDRESS + s).collect(toImmutableList()));
        urls.addAll(genes.stream().map(s -> OUR_ADDRESS + "/genes/" + s).collect(toImmutableList()));
        writeDocument(outputStream, urls, "urlset", "url", ImmutableMap.of("changefreq", "monthly"));
    }


    protected void writeDocument(OutputStream outputStream,
                                 Collection<String> urls,
                                 String rootName,
                                 String childName,
                                 Map<String, String> parametersForChildren) throws XMLStreamException {

        XMLEventWriter writer = outputFactory.createXMLEventWriter(outputStream);

        writer.add(eventFactory.createStartDocument());

        writer.add(eventFactory.createStartElement("", "", rootName));
        writer.add(eventFactory.createAttribute("xmlns", "http://www.sitemaps.org/schemas/sitemap/0.9"));

        for (String url: urls) {
            writeChild(writer, url, childName, parametersForChildren);
        }

        writer.add(eventFactory.createEndElement("", "", rootName));
        writer.add(eventFactory.createEndDocument());
        writer.close();
    }


    private void writeChild(XMLEventWriter writer,
                            String url,
                            String childName,
                            Map<String, String> parameters) throws XMLStreamException {
        writer.add(eventFactory.createStartElement("", "", childName));
        writer.add(eventFactory.createStartElement("", "", "loc"));
        writer.add(eventFactory.createCharacters(url));
        writer.add(eventFactory.createEndElement("", "", "loc"));

        for (Map.Entry<String, String> e: parameters.entrySet()) {
            writer.add(eventFactory.createStartElement("", "", e.getKey()));
            writer.add(eventFactory.createCharacters(e.getValue()));
            writer.add(eventFactory.createEndElement("", "", e.getKey()));
        }

        writer.add(eventFactory.createEndElement("", "", childName));

    }
}
