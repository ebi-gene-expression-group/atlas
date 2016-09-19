package uk.ac.ebi.atlas.experimentpage.baseline.grouping;

import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.model.OntologyTerm;

import javax.inject.Named;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Named
public class AnatomicalSystemTrader {

    //TODO
    Map<OntologyTerm, AnatomicalSystem> m = new HashMap<>();

    public Collection<AnatomicalSystem> getAnatomicalSystemsIncluding(OntologyTerm ontologyTerm){
        //TODO

        return ImmutableList.of(
                AnatomicalSystem.create("id1", "system with everything in it"),
                AnatomicalSystem.create("id2", (ontologyTerm.hashCode() % 4)+"- other system"));
    }
}
