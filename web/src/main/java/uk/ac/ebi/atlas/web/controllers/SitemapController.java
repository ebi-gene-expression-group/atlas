package uk.ac.ebi.atlas.web.controllers;

import com.google.common.collect.ImmutableList;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.search.analyticsindex.AnalyticsSearchService;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
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
    private final ExperimentTrader experimentTrader;

    @Inject
    public SitemapController(AnalyticsSearchService solr, ExperimentTrader experimentTrader){
        this.solr = solr;
        this.experimentTrader = experimentTrader;
    }


    @RequestMapping(value = "/sitemap.xml")
    public void mainSitemap(HttpServletResponse response) throws
            ParserConfigurationException, IOException, XMLStreamException {

        response.setContentType(MediaType.TEXT_XML_VALUE);


        Collection<String> various = ImmutableList.of("experiments","plant/experiments");

        sitemapWriter.writeExperiments(response.getOutputStream(),various,experimentTrader.getPublicExperimentAccessions());

    }

    @RequestMapping(value = "/experiments/{experimentAccession}/sitemap.xml")
    public void sitemapForExperiment(@PathVariable String experimentAccession, HttpServletResponse response) throws
            ParserConfigurationException, IOException, XMLStreamException {

        response.setContentType(MediaType.TEXT_XML_VALUE);


        sitemapWriter.writeGenes(response.getOutputStream(), solr.getBioentityIdentifiersForExperiment(experimentAccession));

    }

}
