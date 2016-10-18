
package uk.ac.ebi.atlas.web.controllers.rest;


import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityPropertyDao;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Controller
@Scope("request")
public class GeneNameTooltipController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeneNameTooltipController.class);

    private static final String WORD_SPAN_OPEN = "<span class='gxaPropertyValueMarkup'>";
    private static final String WORD_SPAN_CLOSE = "</span>";
    private static final int NUMBER_OF_TERMS_TO_SHOW = 5;
    private static final Gson gson = new Gson();

    private BioEntityPropertyDao bioEntityPropertyDao;

    private Resource htmlTemplateResource;

    private String htmlTemplate;

    @Inject
    public GeneNameTooltipController(BioEntityPropertyDao bioEntityPropertyDao,
                                     @Value("classpath:/html-templates/geneNameTooltipTemplate.html") Resource htmlTemplateResource) {
        this.bioEntityPropertyDao = bioEntityPropertyDao;
        this.htmlTemplateResource = htmlTemplateResource;
    }

    @PostConstruct
    void initTemplate() {
        try (InputStream inputStream = htmlTemplateResource.getInputStream()) {
            htmlTemplate = IOUtils.toString(inputStream);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }
    }

    @RequestMapping(value = "/rest/genename-tooltip", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getTooltipContent(@RequestParam(value = "geneName") String geneName,
                                    @RequestParam(value = "identifier") String identifier) {

        Multimap<String, String> multimap = bioEntityPropertyDao.fetchTooltipProperties(identifier);

        String synonyms = buildSynonyms(multimap);

        String goTerms = format(multimap.get("goterm"), true, NUMBER_OF_TERMS_TO_SHOW);

        String interproTerms = format(multimap.get("interproterm"), true, NUMBER_OF_TERMS_TO_SHOW);

        return MessageFormat.format(htmlTemplate, geneName, synonyms, identifier, goTerms, interproTerms);

    }
    @RequestMapping(value = "/json/genename-tooltip", method = RequestMethod.GET, produces = "application/json;" +
            "charset=UTF-8")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getTooltipContentJson( @RequestParam(value = "identifier") String identifier) {

        return gson.toJson(getTooltipContentJsonObject(identifier));
    }

    public JsonObject getTooltipContentJsonObject(String identifier){
        Multimap<String, String> multimap = bioEntityPropertyDao.fetchTooltipProperties(identifier);

        JsonObject result = new JsonObject();
        result.add("synonyms", formatJson(multimap.get("synonym"),NUMBER_OF_TERMS_TO_SHOW));
        result.add("goterms", formatJson(multimap.get("goterm"),NUMBER_OF_TERMS_TO_SHOW));
        result.add("interproterms", formatJson(multimap.get("interproterm"),NUMBER_OF_TERMS_TO_SHOW));
        return result;
    }

    JsonArray formatJson(Collection<String> values, int restrictListLengthTo){
        JsonArray result = new JsonArray();
        int i = 0;
        loop:
        for(String value: values){
            result.add(new JsonPrimitive(value));
            i++;
            if(i==restrictListLengthTo)
                break loop;
        }
        if(values.size()> restrictListLengthTo){
            result.add(new JsonPrimitive("(...and " + (values.size()-restrictListLengthTo) + " more)"));
        }

        return result;
    }

    String format(Collection<String> values, boolean returnEmptyValuesAsNA, int restrictListLengthTo) {
        if (CollectionUtils.isEmpty(values)) {
            return returnEmptyValuesAsNA ? "NA" : StringUtils.EMPTY;
        }
        List<String> subList;
        if (values.size() > restrictListLengthTo) {
            subList = Collections.list(Collections.enumeration(values)).subList(0, restrictListLengthTo);
            subList.add("(...and " + values.size() + " more)");
        } else {
            subList = Collections.list(Collections.enumeration(values));
        }

        return WORD_SPAN_OPEN + Joiner.on(WORD_SPAN_CLOSE + " " + WORD_SPAN_OPEN).join(subList) + WORD_SPAN_CLOSE;
    }

    String buildSynonyms(Multimap<String, String> multimap) {

        String synonyms = format(multimap.get("synonym"), false, NUMBER_OF_TERMS_TO_SHOW);

        List<String> synonymsList = Lists.newArrayList(synonyms);

        return Joiner.on(" ").join(synonymsList);
    }

}
