package uk.ac.ebi.atlas.web.controllers;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;

import com.google.common.collect.Sets;

@Named("applicationProperties")
@Scope("singleton")
public class ApplicationProperties {

    private SortedSet<String> organismParts;

    @Inject
    public ApplicationProperties(@Value("#{configuration['organism.parts']}") String organismParts){
        this.organismParts = new TreeSet<String>(Sets.newHashSet(organismParts.split(",")));
    }

    public SortedSet<String> getOrganismParts(){
        return organismParts;
    }

}
