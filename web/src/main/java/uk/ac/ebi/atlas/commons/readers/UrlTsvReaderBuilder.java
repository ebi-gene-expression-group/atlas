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

package uk.ac.ebi.atlas.commons.readers;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.readers.impl.TsvReaderImpl;

import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.MessageFormat;

@Named
@Scope("prototype")
public class UrlTsvReaderBuilder {

    private String experimentAccession;
    private String tsvFileUrlTemplate;

    UrlTsvReaderBuilder() {
    }

    public UrlTsvReaderBuilder withExperimentAccession(String experimentAccession){
        this.experimentAccession = experimentAccession;
        return this;
    }

    public UrlTsvReaderBuilder forTsvFileUrlTemplate(String tsvFileUrlTemplate) {
        this.tsvFileUrlTemplate = tsvFileUrlTemplate;
        return this;
    }

    public TsvReader build() {
        String tsvFileUrlAsString = MessageFormat.format(tsvFileUrlTemplate, experimentAccession);
        try {
            URL tsvFileUrl = new URL(tsvFileUrlAsString);
            InputStream in = tsvFileUrl.openStream();
            InputStreamReader tsvFileInputStreamReader = new InputStreamReader(tsvFileUrl.openStream());
            return new TsvReaderImpl(tsvFileInputStreamReader);
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read Tsv file from URL " + tsvFileUrlAsString, e);
        }
    }

}
