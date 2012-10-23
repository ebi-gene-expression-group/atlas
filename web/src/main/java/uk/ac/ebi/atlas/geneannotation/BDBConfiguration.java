package uk.ac.ebi.atlas.geneannotation;

import com.sleepycat.collections.StoredMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;


@Configuration
public class BDBConfiguration {

    @Bean(initMethod = "setup", destroyMethod = "close")
    @Value("#{configuration['genename.bdb.location']}")
    AnnotationEnvironment AnnotationEnvironment(String environmentLocation) {
        return new AnnotationEnvironment(environmentLocation);

    }

    @Bean
    @Inject
    public StoredMap<String, String> geneNames(AnnotationEnvironment annotationEnvironment) {

        return annotationEnvironment.geneNames();
    }

}
