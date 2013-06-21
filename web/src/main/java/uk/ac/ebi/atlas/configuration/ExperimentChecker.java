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

package uk.ac.ebi.atlas.configuration;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.ExperimentType;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@Scope("prototype")
public class ExperimentChecker {

    private static final Logger LOGGER = Logger.getLogger(ExperimentChecker.class);

    private ConfigurationDao configurationDao;

    @Inject
    public ExperimentChecker(ConfigurationDao configurationDao) {
        this.configurationDao = configurationDao;
    }

    public ExperimentType checkAccessionAndType(String accession, String type) {
        if (StringUtils.isEmpty(accession)) {
            LOGGER.error("<checkAccessionAndType> Experiment accession cannot be empty.");
            throw new IllegalStateException("Experiment accession cannot be empty.");
        }

        if (configurationDao.getExperimentConfiguration(accession) != null) {
            LOGGER.error("<checkAccessionAndType> Experiment with accession " + accession + " already exists.");
            throw new IllegalStateException("Experiment with accession " + accession + " already exists.");
        }

        ExperimentType experimentType;
        try {
            experimentType = ExperimentType.valueOf(type);
        } catch (IllegalArgumentException e) {
            LOGGER.error("<checkAccessionAndType> Illegal ExperimentType specified: " + e.getMessage());
            throw new IllegalStateException("An unknown experiment type has been specified.");
        }

        return experimentType;
    }

}