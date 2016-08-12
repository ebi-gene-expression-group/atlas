package uk.ac.ebi.atlas.trader;

import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.dao.OrganismEnsemblDAO;
import uk.ac.ebi.atlas.dao.OrganismKingdomDAO;
import uk.ac.ebi.atlas.model.SpeciesUtils;

import javax.inject.Inject;
import javax.inject.Named;


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
        species = SpeciesUtils.convertToEnsemblSpecies(species);
        String kingdom= speciesKingdomMap.get(species);
        if(kingdom==null){
            LOGGER.warn("Missing kingdom for "+species);
            return "";
        } else {
            return kingdom;
        }
    }

    public String getEnsemblDB(String species) {
        return speciesEnsemblMap.get(SpeciesUtils.convertToEnsemblSpecies(species));
    }

}
