package uk.ac.ebi.atlas.utils;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SitemapWriter {

    XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
    XMLEventFactory eventFactory = XMLEventFactory.newInstance();

    private static final String ourAddress = "http://www.ebi.ac.uk/gxa";

    public void writeGenes(OutputStream outputStream, Collection<String> genes) throws XMLStreamException {
        writeUrls(outputStream,Collections2.transform(genes, new Function<String, String>() {
            @Override
            public String apply(String s) {
                return ourAddress+"/genes/"+s;
            }
        }));
    }
    public void writeExperiments(OutputStream outputStream,Collection<String> various, Collection<String> experiments)
            throws
            XMLStreamException {
        List<String> urls = new ArrayList<>();
        urls.addAll(Collections2.transform(various, new Function<String, String>() {
            @Override
            public String apply(String s) {
                return ourAddress+s;
            }
        }));
        urls.addAll(Collections2.transform(experiments, new Function<String, String>() {
            @Override
            public String apply(String s) {
                return ourAddress+"/experiments/"+s;
            }
        }));
        writeUrls(outputStream,urls);
    }

     void writeUrls(OutputStream outputStream, Collection<String> urls) throws XMLStreamException {

        XMLEventWriter writer = outputFactory.createXMLEventWriter(outputStream);

        writer.add(eventFactory.createStartDocument());

        writer.add(eventFactory.createStartElement("","", "urlset"));
        writer.add(eventFactory.createAttribute("xmlns", "http://www.sitemaps.org/schemas/sitemap/0.9"));

        for(String url: urls){
            writeUrl(writer, url);
        }

        writer.add(eventFactory.createEndElement("","", "urlset"));
        writer.add(eventFactory.createEndDocument());
        writer.close();
    }


    private void writeUrl(XMLEventWriter writer, String url) throws XMLStreamException{
        writer.add(eventFactory.createStartElement("","","url"));
        writer.add(eventFactory.createStartElement("","","loc"));
        writer.add(eventFactory.createCharacters(url));
        writer.add(eventFactory.createEndElement("","","loc"));
        writer.add(eventFactory.createStartElement("","","changefreq"));
        writer.add(eventFactory.createCharacters("monthly"));
        writer.add(eventFactory.createEndElement("","","changefreq"));
        writer.add(eventFactory.createEndElement("","","url"));

    }
}
