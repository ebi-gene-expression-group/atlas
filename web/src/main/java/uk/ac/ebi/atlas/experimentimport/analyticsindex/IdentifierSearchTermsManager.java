package uk.ac.ebi.atlas.experimentimport.analyticsindex;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.jayway.jsonpath.JsonPath;
import org.apache.solr.common.SolrInputDocument;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.support.IdentifierSearchTermsDAO;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.support.BioentityIdentifiersDao;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.support.UUIDsDao;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 17/09/15.
 */

@Named
public class IdentifierSearchTermsManager {

    private BioentityIdentifiersDao bioentityIdentifiersDao;
    private IdentifierSearchTermsDAO identifierSearchTermsDAO;
    private UUIDsDao uuidsDao;
    private AnalyticsIndexDAO analyticsIndexDAO;

    private static final String BIOENTITY_IDENTIFIER_PATH = "$.facets.bioentityIdentifiers.buckets[*].val";
    private static final String ID_PATH = "$.facets.ids.buckets[*].val";

    private static final Joiner joiner = Joiner.on(" ").skipNulls();

    @Inject
    public IdentifierSearchTermsManager(BioentityIdentifiersDao bioentityIdentifiersDao, IdentifierSearchTermsDAO identifierSearchTermsDAO, UUIDsDao uuidsDao, AnalyticsIndexDAO analyticsIndexDAO) {
        this.bioentityIdentifiersDao = bioentityIdentifiersDao;
        this.identifierSearchTermsDAO = identifierSearchTermsDAO;
        this.uuidsDao = uuidsDao;
        this.analyticsIndexDAO = analyticsIndexDAO;
    }

    public int updateSearchTerms(String experimentAccession) {
        return update(bioentityIdentifiersDao.fetchBioentityIdentifiers(experimentAccession));
    }

    public int updateAllSearchTerms() {
        return update(bioentityIdentifiersDao.fetchAllBioentityIdentifiers());
    }

    public int update(String json) {

        List<String> bioentityIdentifiers = JsonPath.read(json, BIOENTITY_IDENTIFIER_PATH);

        for (String bioentityIdentifier: bioentityIdentifiers) {
            Set<String> searchTerms = identifierSearchTermsDAO.fetchSearchTerms(bioentityIdentifier);

            Map<String,Object> setSearchTermsFieldModifier = new HashMap<>(1);
            setSearchTermsFieldModifier.put("set", searchTerms);

            List<String> ids = JsonPath.read(uuidsDao.fetchIds(bioentityIdentifier), ID_PATH);
            ImmutableList.Builder<SolrInputDocument> documentBuilder = new ImmutableList.Builder<>();
            for (String id: ids) {
                SolrInputDocument sdoc = new SolrInputDocument();
                sdoc.addField("id", id);
                sdoc.addField("identifierSearch", setSearchTermsFieldModifier);
                documentBuilder.add(sdoc);
            }

            analyticsIndexDAO.addDocuments(documentBuilder.build());
        }

        return bioentityIdentifiers.size();
    }
}
