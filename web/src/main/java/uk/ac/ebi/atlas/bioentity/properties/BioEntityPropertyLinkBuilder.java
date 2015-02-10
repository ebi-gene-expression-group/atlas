package uk.ac.ebi.atlas.bioentity.properties;

import com.google.common.base.Optional;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.bioentity.go.GoTermTrader;
import uk.ac.ebi.atlas.bioentity.go.PoTermTrader;
import uk.ac.ebi.atlas.bioentity.interpro.InterProTermTrader;
import uk.ac.ebi.atlas.model.Species;
import uk.ac.ebi.atlas.solr.query.SpeciesLookupService;
import uk.ac.ebi.atlas.utils.ReactomeClient;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Set;

@Named
@Scope("singleton")
public class BioEntityPropertyLinkBuilder {

    private BioEntityCardProperties bioEntityCardProperties;

    private ReactomeClient reactomeClient;

    private BioEntityPropertyDao bioEntityPropertyDao;

    private SpeciesLookupService speciesLookupService;

    private GoTermTrader goTermTrader;

    private InterProTermTrader interProTermTrader;

    private PoTermTrader poTermTrader;

    @Inject
    public BioEntityPropertyLinkBuilder(BioEntityCardProperties bioEntityCardProperties, ReactomeClient reactomeClient,
                                        BioEntityPropertyDao bioEntityPropertyDao, SpeciesLookupService speciesLookupService, GoTermTrader goTermTrader, InterProTermTrader interProTermTrader,
                                        PoTermTrader poTermTrader) {
        this.bioEntityCardProperties = bioEntityCardProperties;
        this.reactomeClient = reactomeClient;
        this.bioEntityPropertyDao = bioEntityPropertyDao;
        this.speciesLookupService = speciesLookupService;
        this.goTermTrader = goTermTrader;
        this.interProTermTrader = interProTermTrader;
        this.poTermTrader = poTermTrader;
    }

    public Optional<PropertyLink> createLink(String identifier, String propertyType, String propertyValue, String species) {
        final String linkSpecies = Species.convertSpacesToUnderscore(species);

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

    String fetchLinkText(String propertyType, String propertyValue) {
        String displayName = propertyValue;
        switch (propertyType) {
            case "ortholog":
                displayName = fetchSymbolAndSpeciesForOrtholog(displayName);
                break;
            case "reactome":
                displayName = reactomeClient.fetchPathwayNameFailSafe(propertyValue);
                break;
            case "go":
                displayName = goTermTrader.getTermName(propertyValue);
                break;
            case "interpro":
                displayName = interProTermTrader.getTerm(propertyValue);
                break;
            case "po":
                displayName = poTermTrader.getTermName(propertyValue);
                break;

        }
        return displayName;
    }

    String fetchSymbolAndSpeciesForOrtholog(String identifier) {
        try {
            String species = speciesLookupService.fetchSpeciesForBioentityId(identifier);

            String speciesToken = " (" + StringUtils.capitalize(species) + ")";

            Set<String> propertyValuesForGeneId = bioEntityPropertyDao.fetchPropertyValuesForGeneId(identifier, "symbol");
            if (!propertyValuesForGeneId.isEmpty()) {
                String symbol = propertyValuesForGeneId.iterator().next();
                return symbol + speciesToken;
            }
            return identifier + speciesToken;
        } catch (Exception e) {
            return identifier;
        }
    }

    String getEncodedString(String value) {
        try {
            return URLEncoder.encode(value, "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("Cannot create URL from " + value, e);
        }
    }
}
