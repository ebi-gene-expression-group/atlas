package uk.ac.ebi.atlas.web.controllers;

import com.google.common.collect.ImmutableList;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.dao.OrganismEnsemblDAO;
import uk.ac.ebi.atlas.model.Species;
import uk.ac.ebi.atlas.search.analyticsindex.AnalyticsSearchService;
import uk.ac.ebi.atlas.utils.SitemapWriter;

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
    private final OrganismEnsemblDAO organismEnsemblDAO;

    @Inject
    public SitemapController(AnalyticsSearchService solr, OrganismEnsemblDAO organismEnsemblDAO){
        this.solr = solr;
        this.organismEnsemblDAO = organismEnsemblDAO;
    }


    @RequestMapping(value = "/sitemap.xml")
    public void mainSitemap(HttpServletResponse response) throws
            ParserConfigurationException, IOException, XMLStreamException {



        response.setContentType(MediaType.TEXT_XML_VALUE);

        sitemapWriter.writeSitemapIndex(response.getOutputStream(),organismEnsemblDAO.getOrganismEnsemblMap().keySet());

    }

    @RequestMapping(value = "/species/{species}/sitemap.xml")
    public void sitemapForSpecies(@PathVariable String species, HttpServletResponse response) throws
            ParserConfigurationException, IOException, XMLStreamException {

        response.setContentType(MediaType.TEXT_XML_VALUE);
        Collection<String> various = ImmutableList.of("/experiments","/plant/experiments");

        sitemapWriter.writeGenes(response.getOutputStream(), various, solr.getBioentityIdentifiersForSpecies
                (Species.convertUnderscoreToSpaces(species)));

    }

}
