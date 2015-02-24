package uk.ac.ebi.atlas.trader;

import com.google.common.collect.ImmutableMap;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.dao.OrganismEnsemblDAO;
import uk.ac.ebi.atlas.model.Species;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: barrera
 */
@Named
@Scope("singleton")
public class SpeciesKingdomTrader {

    private ImmutableMap<String, String> speciesEnsemblMap;

    @Inject
    public SpeciesKingdomTrader(OrganismEnsemblDAO organismEnsemblDAO) {
        this.speciesEnsemblMap = organismEnsemblDAO.getEnsemblOrganismNamesMap();
    }

    public String getKingdom(String species) {
        return speciesEnsemblMap.get(Species.convertToEnsemblSpecies(species));
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

}
