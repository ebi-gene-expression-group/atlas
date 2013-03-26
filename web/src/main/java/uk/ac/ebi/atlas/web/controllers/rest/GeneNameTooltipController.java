/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.web.controllers.rest;


import com.google.common.base.Joiner;
import com.google.common.collect.Multimap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.geneindex.SolrClient;
import uk.ac.ebi.atlas.streams.differential.DifferentialExpressionsBuffer;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Collection;

@Controller
@Scope("request")
public class GeneNameTooltipController {
    private static final Logger LOGGER = Logger.getLogger(GeneNameTooltipController.class);

    private SolrClient solrClient;

    @Value("classpath:/html-templates/geneNameTooltipTemplate.html")
    private Resource htmlTemplateResource;

    private String htmlTemplate;

    @Inject
    public GeneNameTooltipController(SolrClient solrClient) {
        this.solrClient = solrClient;
    }

    @PostConstruct
    void initTemplate(){
        try(InputStream is = htmlTemplateResource.getInputStream()) {
            htmlTemplate = IOUtils.toString(is);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }
    }

    @RequestMapping(value = "/rest/genename-tooltip", method = RequestMethod.GET, produces = "text/html")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getTooltipContent(@RequestParam(value = "geneName") String geneName,
                                    @RequestParam(value = "identifier") String identifier) {


        Multimap<String, String> multimap = solrClient.fetchTooltipProperties(identifier);

        String synonyms = buildSynonyms(identifier, multimap);

        String goTerms = toCsv(multimap.get("goterm"));

        String interproTerms = toCsv(multimap.get("interproterm"));

        return MessageFormat.format(htmlTemplate, geneName, synonyms, goTerms, interproTerms);

    }

    String toCsv(Collection<String> values){
        return CollectionUtils.isEmpty(values) ? "NA" : Joiner.on(", ").join(values);
    }

    private String buildSynonyms(String identifier, Multimap<String, String> multimap) {

        String synonyms = Joiner.on(", ").join(multimap.get("synonym"));

        if (synonyms.isEmpty()){
            return identifier;
        }

        return Joiner.on(", ").join(synonyms, identifier);
    }

}
