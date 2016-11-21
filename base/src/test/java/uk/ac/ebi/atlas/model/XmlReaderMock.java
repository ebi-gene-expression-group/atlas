package uk.ac.ebi.atlas.model;

import org.apache.commons.configuration2.XMLConfiguration;
import org.w3c.dom.Document;
import uk.ac.ebi.atlas.commons.readers.XmlReader;

public class XmlReaderMock extends XmlReader {

    private Document document;

    public XmlReaderMock(XMLConfiguration xmlConfiguration) {
        super(xmlConfiguration);
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    @Override
    public Document getDocument() {
        return document;
    }
}
