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

package uk.ac.ebi.atlas.web.controllers.page;

import com.google.common.collect.Multimap;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.geneindex.SolrClient;
import uk.ac.ebi.atlas.utils.UniProtClient;
import uk.ac.ebi.atlas.web.BioentityPageProperties;

import javax.inject.Inject;
import java.util.Collection;

@Controller
@Scope("request")
public class ProteinPageController extends BioentityPageController {

    public static final String PROPERTY_TYPE_SYMBOL = "uniprot";

    private String proteinPagePropertyTypes;

    private UniProtClient uniProtClient;

    @Inject
    ProteinPageController(SolrClient solrClient,
                          BioentityPageProperties geneCardProperties,
                          @Value("#{configuration['index.types.proteinpage']}") String proteinPagePropertyTypes, UniProtClient uniProtClient) {
        super(solrClient, geneCardProperties);
        this.proteinPagePropertyTypes = proteinPagePropertyTypes;
        this.uniProtClient = uniProtClient;
    }

    @RequestMapping(value = "/proteins/{identifier}")
    public String showGenePage(@PathVariable String identifier, Model model) {
        return super.showGenePage(identifier, model);
    }

    @Override
    String getPagePropertyTypes() {
        return proteinPagePropertyTypes;
    }

    @Override
    String getSymbolType() {
        return PROPERTY_TYPE_SYMBOL;
    }

    @Override
    protected void addExtraProperties(Multimap<String, String> properties) {
        Collection<String> uniprotIds = properties.get("uniprot");
        if (CollectionUtils.isNotEmpty(uniprotIds)) {
            for (String uniprotId : uniprotIds) {
                Collection<String> reactomeIds = uniProtClient.fetchReactomeIds(uniprotId);
                properties.putAll("reactome", reactomeIds);
            }
        }
    }
}