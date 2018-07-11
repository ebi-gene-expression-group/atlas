package uk.ac.ebi.atlas.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class PathsConfig {
    private final String dataFilesLocation;

    public PathsConfig(BasePathsConfig basePathsConfig) {
        this.dataFilesLocation = basePathsConfig.dataFilesLocation;
    }

    @Bean
    public Path anatomicalSystemsFilePath() {
        return Paths.get(dataFilesLocation, "ontology", "anatomical_systems.txt");
    }

    @Bean
    public Path organsFilePath() {
        return Paths.get(dataFilesLocation, "ontology", "organs.txt");
    }
}
