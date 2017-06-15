package uk.ac.ebi.atlas.sitemaps;

import com.google.common.collect.Collections2;
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

public class SitemapWriter {

    private XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
    private XMLEventFactory eventFactory = XMLEventFactory.newInstance();

    private static final String ourAddress = "http://www.ebi.ac.uk/gxa";

    public void writeSitemapIndex(OutputStream outputStream, Collection<SpeciesProperties> speciesProperties) throws XMLStreamException {

        writeDocument(outputStream,Collections2.transform(speciesProperties, s -> ourAddress+"/species/"+ s.ensemblName() + "/sitemap.xml"), "sitemapindex", "sitemap", ImmutableMap.<String,String>of());
    }

    public void writeGenes(OutputStream outputStream, Collection<String> various, Collection<String> genes)
            throws
            XMLStreamException {
        List<String> urls = new ArrayList<>();
        urls.addAll(Collections2.transform(various, s -> ourAddress+s));
        urls.addAll(Collections2.transform(genes, s -> ourAddress+"/genes/"+s));
        writeDocument(outputStream,urls, "urlset", "url", ImmutableMap.of("changefreq","monthly"));
    }


    protected void writeDocument(OutputStream outputStream, Collection<String> urls, String rootName, String childName, Map<String, String> parametersForChildren) throws XMLStreamException {

        XMLEventWriter writer = outputFactory.createXMLEventWriter(outputStream);

        writer.add(eventFactory.createStartDocument());

        writer.add(eventFactory.createStartElement("","", rootName));
        writer.add(eventFactory.createAttribute("xmlns", "http://www.sitemaps.org/schemas/sitemap/0.9"));

        for(String url: urls){
            writeChild(writer, url, childName, parametersForChildren);
        }

        writer.add(eventFactory.createEndElement("","", rootName));
        writer.add(eventFactory.createEndDocument());
        writer.close();
    }


    private void writeChild(XMLEventWriter writer, String url, String childName, Map<String, String> parameters) throws XMLStreamException{
        writer.add(eventFactory.createStartElement("","",childName));
        writer.add(eventFactory.createStartElement("","","loc"));
        writer.add(eventFactory.createCharacters(url));
        writer.add(eventFactory.createEndElement("","","loc"));
        for(Map.Entry<String, String> e: parameters.entrySet()){
            writer.add(eventFactory.createStartElement("","",e.getKey()));
            writer.add(eventFactory.createCharacters(e.getValue()));
            writer.add(eventFactory.createEndElement("","",e.getKey()));
        }
        writer.add(eventFactory.createEndElement("","",childName));

    }
}
