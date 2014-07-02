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

package uk.ac.ebi.atlas.search.EFO;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@Scope("singleton")
public class EFOChildrenClient {

    private static final Logger LOGGER = Logger.getLogger(EFOChildrenClient.class);

    private RestTemplate restTemplate;

    private String url;

    @Inject
    public EFOChildrenClient(RestTemplate restTemplate, @Value("#{configuration['efo.term.expansion.query.url']}") String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    public ImmutableList<String> fetchEFOChildren(String term) {

        if (StringUtils.isBlank(term)) {
            return ImmutableList.of();
        }

        try {
            String result = restTemplate.getForObject(url, String.class, term);

            return extractIds(result);

        } catch (RestClientException e) {
            // log error but continue
            LOGGER.error(e);
            return ImmutableList.of();
        }

    }

    ImmutableList<String> extractIds(String jsonString) {

        JsonElement json = new JsonParser().parse(jsonString);
        JsonObject root = json.getAsJsonObject();

        ImmutableList.Builder<String> builder = ImmutableList.builder();
        for (JsonElement element : root.getAsJsonArray("tree")) {
            String id = element.getAsJsonObject().get("id").getAsString().replace("EFO_", "EFO:");
            builder.add(id);
        }

        return builder.build();
    }

}
