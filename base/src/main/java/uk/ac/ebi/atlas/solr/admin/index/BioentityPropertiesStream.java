package uk.ac.ebi.atlas.solr.admin.index;

import uk.ac.ebi.atlas.solr.BioentityProperty;
import au.com.bytecode.opencsv.CSVReader;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class BioentityPropertiesStream implements Closeable {

    private static final Logger LOGGER = LoggerFactory.getLogger(BioentityPropertiesStream.class);

    private static final double BATCH_SIZE = 20000;

    private BioentityPropertiesBuilder bioentityPropertiesBuilder;

    private CSVReader csvReader;

    BioentityPropertiesStream(CSVReader csvReader, BioentityPropertiesBuilder bioentityPropertiesBuilder, String species) throws IOException {
        this.csvReader = csvReader;
        this.bioentityPropertiesBuilder = bioentityPropertiesBuilder;
        List<String> csvHeaders = Lists.newArrayList(csvReader.readNext());
        String bioentityType = csvHeaders.remove(0);
        this.bioentityPropertiesBuilder = bioentityPropertiesBuilder
                .forBioentityType(bioentityType)
                .forSpecies(species)
                .forPropertyNames(csvHeaders);
    }

    public Collection<BioentityProperty> next() throws IOException {
        int lineCount = 0;

        Collection<BioentityProperty> propertiesBuffer = Lists.newArrayList();

        String[] csvValues;
        // observe order here, otherwise some data lines will be lost because of readNext()
        while (propertiesBuffer.size() <= BATCH_SIZE && (csvValues = csvReader.readNext()) != null) {
            lineCount++;

            List<String> propertyValues = Lists.newArrayList(csvValues);
            String bioentityIdentifier = propertyValues.remove(0);

            if (bioentityIdentifier.trim().isEmpty()) {
                LOGGER.warn("Line {} ignored because bioentityIdentifier is empty", lineCount);
            }
            else {
                Collection<BioentityProperty> properties = bioentityPropertiesBuilder
                        .withBioentityIdentifier(bioentityIdentifier)
                        .withPropertyValues(propertyValues)
                        .build();
                propertiesBuffer.addAll(properties);
            }
        }

        if (propertiesBuffer.size() > 0) {
            return propertiesBuffer;
        }

        return null;

    }

    @Override
    public void close() throws IOException {
        LOGGER.debug("<close> close invoked on PropertyStream");
        csvReader.close();
    }

}
