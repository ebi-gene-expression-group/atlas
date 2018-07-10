package uk.ac.ebi.atlas.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@PropertySource("classpath:configuration.properties")
public class BasePathsConfig {
    protected final String dataFilesLocation;
    protected final String experimentFilesLocation;

    public BasePathsConfig(@Value("${data.files.location}") String dataFilesLocation,
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
        return Paths.get(dataFilesLocation).resolve("bioentity_properties");
    }

    @Bean
    public Path goPoFilePath() {
        return bioentityPropertiesDirPath().resolve("go").resolve("goIDToTerm.tsv");
    }

    @Bean
    public Path interProFilePath() {
        return bioentityPropertiesDirPath().resolve("interpro").resolve("interproIDToTypeTerm.tsv");
    }

    @Bean
    public Path annotationsDirPath() {
        return bioentityPropertiesDirPath().resolve("annotations");
    }

    @Bean
    public Path arrayDesignsDirPath() {
        return bioentityPropertiesDirPath().resolve("array_designs");
    }

    @Bean
    public Path reactomeDirPath() {
        return bioentityPropertiesDirPath().resolve("reactome");
    }

    @Bean
    public Path speciesPropertiesFilePath() {
        return Paths.get(dataFilesLocation).resolve("species").resolve("species-properties.json");
    }
}
