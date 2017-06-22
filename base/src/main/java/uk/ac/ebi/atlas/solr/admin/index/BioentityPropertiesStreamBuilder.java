package uk.ac.ebi.atlas.solr.admin.index;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import com.google.common.base.Charsets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

@Named
@Scope("prototype")
public class BioentityPropertiesStreamBuilder {

    private BioentityPropertiesBuilder bioentityPropertiesBuilder;
    private Path bioentityPropertiesFilePath;

    private boolean isForReactome;

    @Inject
    public BioentityPropertiesStreamBuilder(BioentityPropertiesBuilder bioentityPropertiesBuilder) {
        this.bioentityPropertiesBuilder = bioentityPropertiesBuilder;
    }

    public BioentityPropertiesStreamBuilder forPath(Path bioentityPropertiesFilePath) {
        this.bioentityPropertiesFilePath = bioentityPropertiesFilePath;
        return this;
    }

    public BioentityPropertiesStreamBuilder isForReactome(boolean forReactome) {
        isForReactome = forReactome;
        return this;
    }

    public BioentityPropertiesStream build() throws IOException {
        Reader fileReader = Files.newBufferedReader(bioentityPropertiesFilePath, Charsets.UTF_8);
        CSVReader csvReader = new CSVReader(fileReader, '\t', CSVWriter.NO_QUOTE_CHARACTER);
        return new BioentityPropertiesStream(csvReader, bioentityPropertiesBuilder, getSpecies());
    }

    String getFileName() {
        return bioentityPropertiesFilePath.getFileName().toString();
    }

    String getSpecies() {
        return StringUtils.substringBefore(getFileName(), ".");
    }
}
