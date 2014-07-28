package uk.ac.ebi.atlas.bioentity.properties;

import com.google.common.base.Optional;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.bioentity.go.GoTermTrader;
import uk.ac.ebi.atlas.bioentity.go.PoTermTrader;
import uk.ac.ebi.atlas.bioentity.interpro.InterProTermTrader;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.utils.ReactomeBiomartClient;
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

    private ReactomeBiomartClient reactomeBiomartClient;

    private ReactomeClient reactomeClient;

    private SolrQueryService solrQueryService;

    private GoTermTrader goTermTrader;

    private InterProTermTrader interProTermTrader;

    private PoTermTrader poTermTrader;

    @Inject
    public BioEntityPropertyLinkBuilder(BioEntityCardProperties bioEntityCardProperties, ReactomeClient reactomeClient,
                                        SolrQueryService solrQueryService, GoTermTrader goTermTrader, InterProTermTrader interProTermTrader,
                                        PoTermTrader poTermTrader) {
        this.bioEntityCardProperties = bioEntityCardProperties;
        this.reactomeClient = reactomeClient;
        this.solrQueryService = solrQueryService;
        this.goTermTrader = goTermTrader;
        this.interProTermTrader = interProTermTrader;
        this.poTermTrader = poTermTrader;
    }

    public Optional<PropertyLink> createLink(String identifier, String propertyType, String propertyValue, String species) {
        final String linkSpecies = species.replaceAll(" ", "_");

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
                displayName = transformOrthologToSymbol(displayName);
                break;
            case "reactome":
                displayName = reactomeClient.fetchPathwayNameFailSafe(propertyValue);
                break;
            case "go":
                displayName = goTermTrader.getTerm(propertyValue);
                break;
            case "interpro":
                displayName = interProTermTrader.getTerm(propertyValue);
                break;
            case "po":
                displayName = poTermTrader.getTerm(propertyValue);
                break;

        }
        return displayName;
    }

    String transformOrthologToSymbol(String identifier) {
        try {
            String species = solrQueryService.findSpeciesForBioentityId(identifier);

            String speciesToken = " (" + StringUtils.capitalize(species) + ")";

            Set<String> propertyValuesForGeneId = solrQueryService.findPropertyValuesForGeneId(identifier, "symbol");
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
