package uk.ac.ebi.atlas.trader;

import com.google.common.collect.Sets;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.dao.OrganismEnsemblDAO;
import uk.ac.ebi.atlas.utils.StringUtil;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;
import java.util.SortedSet;

/**
 * Created with IntelliJ IDEA.
 * User: barrera
 */
@Named
@Scope("singleton")
public class SpeciesEnsemblTrader {

    private Map<String, String> speciesEnsemblMap;

    @Inject
    public SpeciesEnsemblTrader(OrganismEnsemblDAO organismEnsemblDAO) {
        this.speciesEnsemblMap = organismEnsemblDAO.getEnsemblOrganismNamesMap();
    }

    public Map<String, String> getSpeciesEnsemblMap() {
        return speciesEnsemblMap;
    }

    public String getEnsemblAccession(String specie) {
        return speciesEnsemblMap.get(specie);
    }

    public SortedSet<String> getEnsemblAccessions(SortedSet<String> species) {
        SortedSet<String> ensemblAccessions = Sets.newTreeSet();

        for(String specie : species) {
            ensemblAccessions.add(getEnsemblAccession(specie));
        }

        return ensemblAccessions;
    }

}
