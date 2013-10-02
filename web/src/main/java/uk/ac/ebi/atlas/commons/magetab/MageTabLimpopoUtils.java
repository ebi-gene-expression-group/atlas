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

package uk.ac.ebi.atlas.commons.magetab;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.MAGETABInvestigation;
import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;
import uk.ac.ebi.arrayexpress2.magetab.parser.MAGETABParser;

import javax.inject.Named;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.List;

@Named
@Scope("prototype")
public class MageTabLimpopoUtils {

    private static final Logger LOGGER = Logger.getLogger(MageTabLimpopoUtils.class);

    @Value("#{configuration['experiment.magetab.idf.url.template']}")
    private String idfUrlTemplate;

    @Value("#{configuration['experiment.magetab.idf.path.template']}")
    private String idfPathTemplate;

    public List<String> extractPubMedIdsFromIDF(MAGETABInvestigation investigation) {
        return investigation.IDF.pubMedId;
    }

    public MAGETABInvestigation parseInvestigation(String experimentAccession)
            throws ParseException, MalformedURLException {

        String idfFileLocation = MessageFormat.format(idfPathTemplate, experimentAccession);
        LOGGER.info("<parseInvestigation> idfFileLocation = " + idfFileLocation);

        MAGETABParser<MAGETABInvestigation> mageTabParser = new MAGETABParser<>();
        Path idfFilePath = Paths.get(idfFileLocation);
        if (Files.exists(idfFilePath)) {
            LOGGER.info("<parseInvestigation> MAGETAB investigation file exists on the filesystem, going to use it");
            return mageTabParser.parse(idfFilePath.toFile());
        } else {
            LOGGER.debug("<parseInvestigation> MAGETAB investigation file not found on the filesystem, going to use online file");
            URL idfFileURL = new URL(MessageFormat.format(idfUrlTemplate, experimentAccession));
            return mageTabParser.parse(idfFileURL);
        }

    }

    public String extractInvestigationTitle(MAGETABInvestigation investigation) {
        return investigation.IDF.investigationTitle;
    }
}