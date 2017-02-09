package uk.ac.ebi.atlas.bioentity.properties;

import uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName;
import uk.ac.ebi.atlas.solr.query.SpeciesLookupService;
import com.google.common.base.Optional;
import uk.ac.ebi.atlas.bioentity.go.GoPoTermTrader;
import uk.ac.ebi.atlas.bioentity.interpro.InterProTrader;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.utils.ReactomeClient;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Set;

@Named
public class BioEntityPropertyLinkBuilder {

    private SpeciesLookupService speciesLookupService;
    private BioEntityPropertyDao bioEntityPropertyDao;
    private ReactomeClient reactomeClient;
    private GoPoTermTrader goPoTermTrader;
    private InterProTrader interProTermTrader;

    @Inject
    public BioEntityPropertyLinkBuilder(SpeciesLookupService speciesLookupService,
                                        BioEntityPropertyDao bioEntityPropertyDao, ReactomeClient reactomeClient,
                                        GoPoTermTrader goPoTermTrader, InterProTrader interProTermTrader) {

        this.speciesLookupService = speciesLookupService;
        this.bioEntityPropertyDao = bioEntityPropertyDao;
        this.reactomeClient = reactomeClient;
        this.goPoTermTrader = goPoTermTrader;
        this.interProTermTrader = interProTermTrader;

    }

    public Optional<PropertyLink> createLink(String identifier, BioentityPropertyName propertyName,
                                             String propertyValue, Species species, int relevance) {

        String linkSpecies = species.getEnsemblName();
        Optional<String> linkText = fetchLinkText(propertyName, propertyValue);

        if (!linkText.isPresent()) {
            return Optional.absent();
        }

        String linkTemplate = BioEntityCardProperties.linkTemplates.get(propertyName);

        if (linkTemplate != null) {
            String linkValue = getEncodedString(propertyValue);
            String href = MessageFormat.format(linkTemplate, linkValue, linkSpecies, identifier);
            return Optional.of(new PropertyLink(linkText.get(), href, relevance));
        } else {
            return Optional.of(new PropertyLink(linkText.get(), relevance));
        }

    }

    private Optional<String> fetchLinkText(BioentityPropertyName propertyName, String propertyValue) {

        switch (propertyName) {
            case ORTHOLOG:
                return Optional.of(fetchSymbolAndSpeciesForOrtholog(propertyValue));
            case REACTOME:
                return Optional.fromNullable(reactomeClient.fetchPathwayNameFailSafe(propertyValue));
            case GO:
                return Optional.fromNullable(goPoTermTrader.getTermName(propertyValue));
            case INTERPRO:
                return Optional.fromNullable(interProTermTrader.getTermName(propertyValue));
            case PO:
                return Optional.fromNullable(goPoTermTrader.getTermName(propertyValue));
            default:
                return Optional.of(propertyValue);
        }

    }

    private String fetchSymbolAndSpeciesForOrtholog(String identifier) {

        Optional<String> species = speciesLookupService.fetchSpeciesForBioentityId(identifier);

        if (!species.isPresent()) {
            return identifier;
        }

        String speciesToken = " (" + species.get() + ")";

        Set<String> propertyValuesForGeneId =
                bioEntityPropertyDao.fetchPropertyValuesForGeneId(identifier, BioentityPropertyName.SYMBOL);

        if (!propertyValuesForGeneId.isEmpty()) {
            String symbol = propertyValuesForGeneId.iterator().next();
            return symbol + speciesToken;
        }

        return identifier + speciesToken;

    }

    private String getEncodedString(String value) {

        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("Cannot create URL from " + value, e);
        }

    }

}
