package uk.ac.ebi.atlas.web;

import com.google.common.collect.Sets;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Properties;
import java.util.SortedSet;
import java.util.TreeSet;

@Named("applicationProperties")
@Scope("singleton")
public class ApplicationProperties {

    private ExperimentsCache experimentsCache;

    private Properties properties;

    private SortedSet<String> organismParts;

    @Inject
    public void ApplicationProperties(ExperimentsCache experimentsCache, Properties configuration){
        this.experimentsCache = experimentsCache;
        this.properties = configuration;
        this.organismParts = new TreeSet<String>(Sets.newHashSet(this.properties.getProperty("organism.parts").split(",")));
    }

    public SortedSet<String> getAllOrganismParts(){
        return organismParts;
    }

    public String getAnatomogramFileName(String experimentAccession, boolean isMale){
        String specie = experimentsCache.getExperiment(experimentAccession).getSpecie();
        String key = "organism.anatomogram." + specie.toLowerCase();
        String fileName = properties.getProperty( key + (isMale ? ".male" : ".female"));
        return fileName != null ? fileName : properties.getProperty(key);

    }

}
