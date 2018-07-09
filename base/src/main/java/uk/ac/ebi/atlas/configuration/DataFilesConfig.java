package uk.ac.ebi.atlas.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@PropertySource("classpath:configuration.properties")
public class DataFilesConfig {
    private final String dataFilesLocation;
    private final String experimentFilesLocation;

    public DataFilesConfig(@Value("${data.files.location}") String dataFilesLocation,
                           @Value("${experiment.files.location}") String experimentFilesLocation) {
        this.dataFilesLocation = dataFilesLocation;
        this.experimentFilesLocation = experimentFilesLocation;
    }

    @Bean
    public Path experimentsDirPath() {
        return Paths.get(experimentFilesLocation);
    }

    @Bean
    public Path bioentityPropertiesDirPath() {
        return Paths.get(dataFilesLocation, "bioentity_properties");
    }

    @Bean
    public Path goPoFilePath() {
        return Paths.get(bioentityPropertiesDirPath().toString(), "go", "goIDToTerm.tsv");
    }

    @Bean
    public Path interProFilePath() {
        return Paths.get(bioentityPropertiesDirPath().toString(), "interpro", "interproIDToTypeTerm.tsv");
    }

    @Bean
    public Path annotationsDirPath() {
        return Paths.get(bioentityPropertiesDirPath().toString(), "annotations");
    }

    @Bean
    public Path arrayDesignsDirPath() {
        return Paths.get(bioentityPropertiesDirPath().toString(), "array_designs");
    }

    @Bean
    public Path reactomeDirPath() {
        return Paths.get(bioentityPropertiesDirPath().toString(), "reactome");
    }

    @Bean
    public Path speciesPropertiesFilePath() {
        return Paths.get(dataFilesLocation, "species", "species-properties.json");
    }
}
