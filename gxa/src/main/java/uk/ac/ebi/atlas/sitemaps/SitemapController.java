package uk.ac.ebi.atlas.sitemaps;

import uk.ac.ebi.atlas.model.SpeciesUtils;
import uk.ac.ebi.atlas.species.SpeciesPropertiesTrader;
import uk.ac.ebi.atlas.trader.SpeciesFactory;
import com.google.common.collect.ImmutableList;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.search.analyticsindex.AnalyticsSearchService;

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
    private final SpeciesPropertiesTrader speciesTrader;

    @Inject
    public SitemapController(AnalyticsSearchService solr, SpeciesFactory speciesFactory, SpeciesPropertiesTrader speciesTrader){
        this.solr = solr;
        this.speciesFactory = speciesFactory;
        this.speciesTrader = speciesTrader;
    }


    @RequestMapping(value = "/sitemap.xml")
    public void mainSitemap(HttpServletResponse response)
    throws ParserConfigurationException, IOException, XMLStreamException {

        response.setContentType(MediaType.TEXT_XML_VALUE);

        sitemapWriter.writeSitemapIndex(response.getOutputStream(), speciesTrader.getAll());

    }

    @RequestMapping(value = "/species/{species}/sitemap.xml")
    public void sitemapForSpecies(@PathVariable String species, HttpServletResponse response) throws
            ParserConfigurationException, IOException, XMLStreamException {

        response.setContentType(MediaType.TEXT_XML_VALUE);
        Collection<String> various = ImmutableList.of("/experiments","/plant/experiments");

        sitemapWriter.writeGenes(response.getOutputStream(), various, solr.getBioentityIdentifiersForSpecies
                (speciesFactory.create((SpeciesUtils.convertUnderscoreToSpaces(species)))));

    }

}
