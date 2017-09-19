package uk.ac.ebi.atlas.bioentity.properties;

import uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.bioentity.go.GoPoTrader;
import uk.ac.ebi.atlas.bioentity.interpro.InterProTrader;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesInferrer;
import uk.ac.ebi.atlas.utils.ReactomeClient;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Optional;
import java.util.Set;

@Named
public class BioEntityPropertyLinkBuilder {

    private SpeciesInferrer speciesInferrer;
    private BioEntityPropertyDao bioEntityPropertyDao;
    private ReactomeClient reactomeClient;
    private GoPoTrader goPoTermTrader;
    private InterProTrader interProTermTrader;

    @Inject
    public BioEntityPropertyLinkBuilder(SpeciesInferrer speciesInferrer,
                                        BioEntityPropertyDao bioEntityPropertyDao, ReactomeClient reactomeClient,
                                        GoPoTrader goPoTermTrader, InterProTrader interProTermTrader) {

        this.speciesInferrer = speciesInferrer;
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
            return Optional.empty();
        }

        String linkTemplate = BioEntityCardProperties.linkTemplates.get(propertyName);

        if (linkTemplate != null) {
            String linkValue = getEncodedString(propertyName, propertyValue);
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
                return Optional.ofNullable(reactomeClient.fetchPathwayNameFailSafe(propertyValue));
            case GO:
                return Optional.ofNullable(goPoTermTrader.getTermName(propertyValue));
            case INTERPRO:
                return Optional.ofNullable(interProTermTrader.getTermName(propertyValue));
            case PO:
                return Optional.ofNullable(goPoTermTrader.getTermName(propertyValue));
            default:
                return Optional.of(propertyValue);
        }

    }

    private String fetchSymbolAndSpeciesForOrtholog(String identifier) {

        Species species = speciesInferrer.inferSpeciesForGeneQuery(SemanticQuery.create(identifier));

        if (species.isUnknown()) {
            return identifier;
        }

        String speciesToken = " (" + species.getName() + ")";

        Set<String> propertyValuesForGeneId =
                bioEntityPropertyDao.fetchPropertyValuesForGeneId(identifier, BioentityPropertyName.SYMBOL);

        if (!propertyValuesForGeneId.isEmpty()) {
            String symbol = propertyValuesForGeneId.iterator().next();
            return symbol + speciesToken;
        }

        return identifier + speciesToken;

    }

    private String getEncodedString(BioentityPropertyName propertyName, String value) {

        try {
            if (propertyName == BioentityPropertyName.GO || propertyName == BioentityPropertyName.PO) {
                return URLEncoder.encode(value.replaceAll(":", "_"), "UTF-8");
            } else {
                return URLEncoder.encode(value, "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("Cannot create URL from " + value, e);
        }

    }

}
