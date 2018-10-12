package uk.ac.ebi.atlas.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;

@Configuration
public class PathsConfig {
    private Path dataFilesPath;

    public PathsConfig(Path dataFilesPath) {
        this.dataFilesPath = dataFilesPath;
    }

    @Bean
    public Path anatomicalSystemsFilePath() {
        return dataFilesPath.resolve("ontology").resolve("anatomical_systems.txt");
    }

    @Bean
    public Path organsFilePath() {
        return dataFilesPath.resolve("ontology").resolve("organs.txt");
    }
}
