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

package uk.ac.ebi.atlas.geneannotation.arraydesign;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.geneannotation.ArrayDesignDao;
import uk.ac.ebi.atlas.geneannotation.FileBasedPropertyMappingFinder;
import uk.ac.ebi.atlas.geneannotation.RestClientBasedPropertyMappingFinder;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;

@Named
@Scope("singleton")
public class DesignElementMappingLoader {

    @Value("#{configuration['de.mapping.gxa.server.url']}")
    private String gxaMappingUrl;

    @Value("#{configuration['de.mirna.mapping.path.template']}")
    private String micrornaMappingPath;

    private ArrayDesignDao arrayDesignDao;

    private RestClientBasedPropertyMappingFinder annotationMappingRestClient;
    private FileBasedPropertyMappingFinder fileAnnotationMappingExtractor;

    @Inject
    public DesignElementMappingLoader(ArrayDesignDao arrayDesignDao, RestClientBasedPropertyMappingFinder annotationMappingRestClient, FileBasedPropertyMappingFinder fileAnnotationMappingExtractor) {
        this.arrayDesignDao = arrayDesignDao;
        this.annotationMappingRestClient = annotationMappingRestClient;
        this.fileAnnotationMappingExtractor = fileAnnotationMappingExtractor;
    }

    public void loadMappings(String annotatedSubject, ArrayDesignType type) {

        clean(annotatedSubject);

        Map<String, String> annotations;
        if (type.equals(ArrayDesignType.MICRO_ARRAY)) {
            annotations = annotationMappingRestClient.executeQuery(gxaMappingUrl, annotatedSubject);
        } else {
            annotations = fileAnnotationMappingExtractor.executeQuery(micrornaMappingPath, annotatedSubject);
        }

        saveMappings(annotations, annotatedSubject, type.getName());

    }

    protected void clean(String annotatedSubject) {
        arrayDesignDao.deleteMappings(annotatedSubject);
    }

    protected void saveMappings(Map<String, String> mappings, String annotatedSubject, String type) {
        arrayDesignDao.saveMappings(mappings, annotatedSubject, type);
    }
}
