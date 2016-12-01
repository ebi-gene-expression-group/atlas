package uk.ac.ebi.atlas.model.resource;

import uk.ac.ebi.atlas.commons.readers.XmlReaderFactory;
import uk.ac.ebi.atlas.commons.readers.XmlReader;

import java.nio.file.Paths;
import java.text.MessageFormat;

public abstract class XmlFile<T> extends AtlasResource<T>{

    public XmlFile(String dataFilesLocation, String template, String ... args) {
        super(Paths.get(dataFilesLocation, MessageFormat.format(template, args)));
    }


    public static class ReadOnly extends XmlFile<XmlReader> {

        private final boolean splitOnComma;

        public ReadOnly(String dataFilesLocation, String template, boolean splitOnComma, String... args) {
            super(dataFilesLocation, template, args);
            this.splitOnComma = splitOnComma;
        }

        @Override
        public XmlReader get() {
            return new XmlReaderFactory().create(path, splitOnComma);
        }

    }

}
