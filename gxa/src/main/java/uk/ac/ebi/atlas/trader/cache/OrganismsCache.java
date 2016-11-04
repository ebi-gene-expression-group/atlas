package uk.ac.ebi.atlas.trader.cache;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.dao.OrganismDAO;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by barrera on 04/08/2014.
 *
 * Access to the database and store all the organisms in a list
 *
 */
@Named
@Scope("singleton")
public class OrganismsCache {

    private List<String> organismsList;

    @Inject
    public OrganismsCache(OrganismDAO organismDAO) {
        this.organismsList = organismDAO.getOrganisms();
    }

    public List<String> getOrganismsList() {
        return organismsList;
    }
}
