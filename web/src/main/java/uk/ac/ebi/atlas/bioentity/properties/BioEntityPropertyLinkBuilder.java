package uk.ac.ebi.atlas.bioentity.properties;

import com.google.common.base.Optional;
import org.apache.commons.lang.StringUtils;
import uk.ac.ebi.atlas.bioentity.go.GoPoTermTrader;
import uk.ac.ebi.atlas.bioentity.interpro.InterProTrader;
import uk.ac.ebi.atlas.model.SpeciesUtils;
import uk.ac.ebi.atlas.solr.query.SpeciesLookupService;
import uk.ac.ebi.atlas.utils.ReactomeClient;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Set;

@Named
public class BioEntityPropertyLinkBuilder {

    private BioEntityCardProperties bioEntityCardProperties;
    private ReactomeClient reactomeClient;
    private BioEntityPropertyDao bioEntityPropertyDao;
    private SpeciesLookupService speciesLookupService;
    private GoPoTermTrader goPoTermTrader;
    private InterProTrader interProTermTrader;

    @Inject
    public BioEntityPropertyLinkBuilder(BioEntityCardProperties bioEntityCardProperties, ReactomeClient reactomeClient,
                                        BioEntityPropertyDao bioEntityPropertyDao, SpeciesLookupService speciesLookupService,
                                        GoPoTermTrader goPoTermTrader, InterProTrader interProTermTrader) {
        this.bioEntityCardProperties = bioEntityCardProperties;
        this.reactomeClient = reactomeClient;
        this.bioEntityPropertyDao = bioEntityPropertyDao;
        this.speciesLookupService = speciesLookupService;
        this.goPoTermTrader = goPoTermTrader;
        this.interProTermTrader = interProTermTrader;
    }

    Optional<PropertyLink> createLink(String identifier, String propertyType, String propertyValue, String species) {
        final String linkSpecies = SpeciesUtils.convertSpacesToUnderscore(species);

        String linkText = fetchLinkText(propertyType, propertyValue);

        if (linkText == null) {
            return Optional.absent();
        }

        String link = bioEntityCardProperties.getLinkTemplate(propertyType);

        if (link != null) {

            String linkValue = getEncodedString(propertyValue);
            link = MessageFormat.format(link, linkValue, linkSpecies, identifier);

            return Optional.of(new PropertyLink(linkText, link));
        }
        return Optional.of(new PropertyLink(linkText));
    }

    private String fetchLinkText(String propertyType, String propertyValue) {
        switch (propertyType) {
            case "ortholog":
                return fetchSymbolAndSpeciesForOrtholog(propertyValue);
            case "reactome":
                return reactomeClient.fetchPathwayNameFailSafe(propertyValue);
            case "go":
                return goPoTermTrader.getTerm(propertyValue).name();
            case "interpro":
                return interProTermTrader.getTermName(propertyValue);
            case "po":
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

        Set<String> propertyValuesForGeneId = bioEntityPropertyDao.fetchPropertyValuesForGeneId(identifier, "symbol");
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
