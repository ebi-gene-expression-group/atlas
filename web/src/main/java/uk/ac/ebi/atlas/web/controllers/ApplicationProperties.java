package uk.ac.ebi.atlas.web.controllers;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;

@Named("applicationProperties")
@Scope("singleton")
public class ApplicationProperties {

    private Set<String> organismParts;

    @Inject
    public ApplicationProperties(@Value("#{configuration['organism.parts']}") String organismParts){
        this.organismParts = Sets.newHashSet(organismParts.split(","));
    }

    public Set<String> getOrganismParts(){
        return organismParts;
    }

}
