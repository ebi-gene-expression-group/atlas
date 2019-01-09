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
    private final String dataFilesLocation;
    private final String experimentFilesLocation;

    public BasePathsConfig(@Value("${data.files.location}") String dataFilesLocation,
                           @Value("${experiment.files.location}") String experimentFilesLocation) {
        this.dataFilesLocation = dataFilesLocation;
        this.experimentFilesLocation = experimentFilesLocation;
    }

    @Bean
    public Path dataFilesPath() {
        return Paths.get(dataFilesLocation);
    }

    @Bean
    public Path experimentsDirPath() {
        return Paths.get(experimentFilesLocation);
    }

    @Bean
    public Path bioentityPropertiesDirPath() {
        return dataFilesPath().resolve("bioentity_properties");
    }

    @Bean
    public Path goPoFilePath() {
        return bioentityPropertiesDirPath().resolve("go-po.id-term-depth.tsv");
    }

    @Bean
    public Path interProFilePath() {
        return bioentityPropertiesDirPath().resolve("interpro.term-id-type.tsv");
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

    @Bean
    public Path atlasInformationFilePath() {
        return Paths.get(dataFilesLocation).resolve("species").resolve("atlas-information.json");
    }
}
