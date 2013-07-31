package uk.ac.ebi.atlas.solr.index;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Named
@Scope("prototype")
public class BioentityIndexBuilder {

    @Value("#{configuration['bioentity.properties']}")
    private String bioentityPropertyDirectory;

    @Value("#{configuration['solr.data.location']}")
    private String solrDataLocation;

    private BioentityIndex bioentityIndex;

    @Inject
    BioentityIndexBuilder(BioentityIndex bioentityIndex){
        this.bioentityIndex = bioentityIndex;
    }

    public void index() throws IOException{

        deleteOldIndex(solrDataLocation);
        try (DirectoryStream<Path> bioentityPropertyPathsStream = Files.newDirectoryStream(Paths.get(bioentityPropertyDirectory))) {
            for (Path filePath : bioentityPropertyPathsStream) {
                bioentityIndex.add(filePath);
            }
        }
    }

    void deleteOldIndex(String solrDataLocation) throws IOException {
        FileUtils.deleteDirectory(Paths.get(solrDataLocation, "index").toFile());
        FileUtils.deleteDirectory(Paths.get(solrDataLocation, "tlog").toFile());
    }
}
