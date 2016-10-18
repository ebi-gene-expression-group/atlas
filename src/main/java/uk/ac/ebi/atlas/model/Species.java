package uk.ac.ebi.atlas.model;

import com.google.common.base.Objects;

import java.util.HashMap;
import java.util.Map;

public class Species {

    public final String originalName;
    public final String mappedName;
    public final String ensemblDb;
    public final String kingdom;

    public Species(String originalName, String mappedName, String ensemblDb, String kingdom) {
        this.originalName = originalName;
        this.mappedName = mappedName;
        this.ensemblDb = ensemblDb;
        this.kingdom = kingdom;
    }

    public Species(String name, String ensemblDb, String kingdom) {
        this.originalName = name;
        this.mappedName = name;
        this.ensemblDb = ensemblDb;
        this.kingdom = kingdom;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof Species)) return false;
        Species species = (Species) o;
        return Objects.equal(mappedName, species.mappedName);
    }

    @Override
    public int hashCode(){
        return Objects.hashCode(mappedName);
    }

    @Override
    public String toString(){
        if (originalName.equals(mappedName)) {
            return String.format("%s (ensemblDb: %s,kingdom: %s)", originalName, ensemblDb, kingdom);
        } else {
            return String.format("%s->%s (ensemblDb: %s,kingdom: %s)", originalName, mappedName, ensemblDb, kingdom);
        }
    }
    
    public boolean isBlank(){
        return false;
    }

    public boolean isPlant() {
        return kingdom.equalsIgnoreCase("plants");
    }

    public String defaultQueryFactorType(){
        return "caenorhabditis elegans".equalsIgnoreCase(mappedName) ? "DEVELOPMENTAL_STAGE" : "ORGANISM_PART";
    }

    public Map<String, ?> getAttributes(){
        Map<String, Object> result = new HashMap<>();
        //required by autocomplete and heatmap
        result.put("species", originalName);
        //required for genome track browser in ensembl
        result.put("ensemblDB", ensemblDb);
        result.put("kingdom", kingdom);
        return result;
    }


}
