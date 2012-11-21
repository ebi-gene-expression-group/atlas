/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

package uk.ac.ebi.atlas.geneannotation.biomart;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;

@Named("martQueryBuilder")
@Scope("prototype")
public class MartQueryBuilder {
    private static final String DEFAULT_VIRTUAL_SCHEMA = "default";

    private String martQueryXmlTemplate;

    private String martQueryXmlAttributeTemplate;


    private List<String> attributes = new ArrayList<String>();

    private String dataset;

    @Inject
    public MartQueryBuilder(
                @Value("#{configuration['biomart.query.xml.template']}") String martQueryXmlTemplate,
                @Value("#{configuration['biomart.query.xml.attribute']}") String martQueryXmlAttributeTemplate) {
        this.martQueryXmlTemplate = martQueryXmlTemplate;
        this.martQueryXmlAttributeTemplate = martQueryXmlAttributeTemplate;
    }

    /**
     * @param dataset a name of a dataset within a virtualSchema (mart) to query data from
     */
    public MartQueryBuilder setDataset(String dataset){
        StringUtils.strip(dataset,"\"");
        this.dataset = dataset;
        return this;
    }

    /**
     * Adds an attribute to the query; attributes specify the data columns returned in the query response.
     *
     * @param attribute an attribute
     * @return the original mart query object
     */
    public MartQueryBuilder addAttribute(String attribute) {
        this.attributes.add(attribute);
        return this;
    }

    public String build() {
        checkState(dataset != null, "Please set the dataset before building the query !");
        checkState(!attributes.isEmpty(), "Please set what attributes you want to load before building the query !");

        StringBuilder xmlAttributesBuilder = new StringBuilder();

        for (String attribute: attributes){
            xmlAttributesBuilder.append(MessageFormat.format(martQueryXmlAttributeTemplate, attribute));
        }

        return MessageFormat.format(martQueryXmlTemplate, DEFAULT_VIRTUAL_SCHEMA, dataset, xmlAttributesBuilder.toString());
    }

}

