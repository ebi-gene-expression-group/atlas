package uk.ac.ebi.atlas.trader;

import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.dao.OrganismEnsemblDAO;
import uk.ac.ebi.atlas.dao.OrganismKingdomDAO;
import uk.ac.ebi.atlas.model.Species;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * Created with IntelliJ IDEA.
 * User: barrera
 */
@Named
@Scope("singleton")
public class SpeciesKingdomTrader {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpeciesKingdomTrader.class);

    private ImmutableMap<String, String> speciesKingdomMap;
    private ImmutableMap<String, String> speciesEnsemblMap;

    @Inject
    public SpeciesKingdomTrader(OrganismKingdomDAO organismKingdomDAO, OrganismEnsemblDAO organismEnsemblDAO) {
        this.speciesKingdomMap = organismKingdomDAO.getOrganismKingdomMap();
        this.speciesEnsemblMap = organismEnsemblDAO.getOrganismEnsemblMap();
    }

    public String getKingdom(String species) {
        species = Species.convertToEnsemblSpecies(species);
        String kingdom= speciesKingdomMap.get(species);
        if(kingdom==null){
            LOGGER.warn("Missing kingdom for "+species);
            return "";
        } else {
            return kingdom;
        }
    }

    public String getKingdom(Set<String> species) {
        for (String s : species) {
            String kingdom = getKingdom(s.toLowerCase());
            if (kingdom != null) {
                return kingdom;
            }
        }
        return "";
    }

    public String getEnsemblDB(String species) {
        return speciesEnsemblMap.get(Species.convertToEnsemblSpecies(species));
    }

    public Map<String, ?> getAttributesFor(String species) {
        Map<String, String> result = new HashMap<>();
        //required by autocomplete and heatmap
        result.put("species", species);
        //required for genome track browser in ensembl
        result.put("ensemblDB", getEnsemblDB(species));
        result.put("kingdom", getKingdom(species));
        return result;
    }

}
