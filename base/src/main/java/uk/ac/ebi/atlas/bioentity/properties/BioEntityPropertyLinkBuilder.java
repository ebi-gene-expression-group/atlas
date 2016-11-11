package uk.ac.ebi.atlas.bioentity.properties;

import uk.ac.ebi.atlas.model.SpeciesUtils;
import uk.ac.ebi.atlas.model.baseline.BioentityPropertyName;
import uk.ac.ebi.atlas.solr.query.SpeciesLookupService;
import com.google.common.base.Optional;
import org.apache.commons.lang.StringUtils;
import uk.ac.ebi.atlas.bioentity.go.GoPoTermTrader;
import uk.ac.ebi.atlas.bioentity.interpro.InterProTrader;
import uk.ac.ebi.atlas.utils.ReactomeClient;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Set;

@Named
public class BioEntityPropertyLinkBuilder {

    private ReactomeClient reactomeClient;
    private BioEntityPropertyDao bioEntityPropertyDao;
    private SpeciesLookupService speciesLookupService;
    private GoPoTermTrader goPoTermTrader;
    private InterProTrader interProTermTrader;

    @Inject
    public BioEntityPropertyLinkBuilder(ReactomeClient reactomeClient,
                                        BioEntityPropertyDao bioEntityPropertyDao, SpeciesLookupService speciesLookupService,
                                        GoPoTermTrader goPoTermTrader, InterProTrader interProTermTrader) {
        this.reactomeClient = reactomeClient;
        this.bioEntityPropertyDao = bioEntityPropertyDao;
        this.speciesLookupService = speciesLookupService;
        this.goPoTermTrader = goPoTermTrader;
        this.interProTermTrader = interProTermTrader;
    }

    Optional<PropertyLink> createLink(String identifier, BioentityPropertyName propertyName, String propertyValue, String species, int
            relevance) {
        final String linkSpecies = SpeciesUtils.convertSpacesToUnderscore(species);

        String linkText = fetchLinkText(propertyName, propertyValue);

        if (linkText == null) {
            return Optional.absent();
        }

        String link = BioEntityCardProperties.linkTemplates.get(propertyName);

        if (link != null) {

            String linkValue = getEncodedString(propertyValue);
            link = MessageFormat.format(link, linkValue, linkSpecies, identifier);

            return Optional.of(new PropertyLink(linkText, link, relevance));
        }
        return Optional.of(new PropertyLink(linkText,relevance));
    }

    private String fetchLinkText(BioentityPropertyName propertyName, String propertyValue) {
        switch (propertyName) {
            case ORTHOLOG:
                return fetchSymbolAndSpeciesForOrtholog(propertyValue);
            case REACTOME:
                return reactomeClient.fetchPathwayNameFailSafe(propertyValue);
            case GO:
                return goPoTermTrader.getTerm(propertyValue).name();
            case INTERPRO:
                return interProTermTrader.getTermName(propertyValue);
            case PO:
                return goPoTermTrader.getTerm(propertyValue).name();
            default:
                return propertyValue;
        }
    }

    private String fetchSymbolAndSpeciesForOrtholog(String identifier) {
        Optional<String> species = speciesLookupService.fetchSpeciesForBioentityId(identifier);
        if (!species.isPresent()) {
            return identifier;
        }
        String speciesToken = " (" + StringUtils.capitalize(species.get()) + ")";

        Set<String> propertyValuesForGeneId = bioEntityPropertyDao.fetchPropertyValuesForGeneId(identifier, BioentityPropertyName.SYMBOL);
        if (!propertyValuesForGeneId.isEmpty()) {
            String symbol = propertyValuesForGeneId.iterator().next();
            return symbol + speciesToken;
        }
        return identifier + speciesToken;
    }

    private String getEncodedString(String value) {
        try {
            return URLEncoder.encode(value, "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("Cannot create URL from " + value, e);
        }
    }

}
