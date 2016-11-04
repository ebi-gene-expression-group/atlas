package uk.ac.ebi.atlas.trader;

import uk.ac.ebi.atlas.model.SpeciesUtils;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import uk.ac.ebi.atlas.dao.OrganismEnsemblDAO;
import uk.ac.ebi.atlas.dao.OrganismKingdomDAO;

public class SpeciesKingdomTrader {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpeciesKingdomTrader.class);

    private ImmutableMap<String, String> speciesKingdomMap;
    private ImmutableMap<String, String> speciesEnsemblMap;


    public SpeciesKingdomTrader(JdbcTemplate jdbcTemplate) {
        this(new OrganismKingdomDAO(jdbcTemplate), new OrganismEnsemblDAO(jdbcTemplate));
    }

    SpeciesKingdomTrader(OrganismKingdomDAO organismKingdomDAO, OrganismEnsemblDAO organismEnsemblDAO) {
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
