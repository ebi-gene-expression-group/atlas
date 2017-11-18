package uk.ac.ebi.atlas.sitemaps;

import uk.ac.ebi.atlas.species.SpeciesFactory;
import uk.ac.ebi.atlas.species.SpeciesPropertiesTrader;
import com.google.common.collect.ImmutableList;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.solr.analytics.AnalyticsSearchService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.Collection;


@Controller
@Scope("request")
public class SitemapController {

    private SitemapWriter sitemapWriter = new SitemapWriter();

    private final AnalyticsSearchService solr;
    private final SpeciesFactory speciesFactory;
    private final SpeciesPropertiesTrader speciesPropertiesTrader;

    @Inject
    public SitemapController(AnalyticsSearchService solr, SpeciesFactory speciesFactory, SpeciesPropertiesTrader speciesPropertiesTrader){
        this.solr = solr;
        this.speciesFactory = speciesFactory;
        this.speciesPropertiesTrader = speciesPropertiesTrader;
    }


    @RequestMapping(value = "/sitemap.xml")
    public void mainSitemap(HttpServletResponse response)
    throws ParserConfigurationException, IOException, XMLStreamException {

        response.setContentType(MediaType.TEXT_XML_VALUE);

        sitemapWriter.writeSitemapIndex(response.getOutputStream(), speciesPropertiesTrader.getAll());

    }

    @RequestMapping(value = "/species/{species}/sitemap.xml")
    public void sitemapForSpecies(@PathVariable String species, HttpServletResponse response) throws
            ParserConfigurationException, IOException, XMLStreamException {

        response.setContentType(MediaType.TEXT_XML_VALUE);
        Collection<String> various = ImmutableList.of("/experiments","/plant/experiments");

        sitemapWriter.writeGenes(
                response.getOutputStream(), various,
                solr.getBioentityIdentifiersForSpecies(speciesFactory.create(species).getReferenceName()));

    }

}
