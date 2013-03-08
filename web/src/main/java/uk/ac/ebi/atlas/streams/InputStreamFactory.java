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

package uk.ac.ebi.atlas.streams;

import au.com.bytecode.opencsv.CSVReader;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.baseline.GeneProfile;
import uk.ac.ebi.atlas.streams.baseline.BaselineExpressionsBuffer;
import uk.ac.ebi.atlas.streams.baseline.GeneExpressionsInputStream;
import uk.ac.ebi.atlas.streams.baseline.GeneProfilesInputStream;
import uk.ac.ebi.atlas.streams.differential.DifferentialExpressionsBuffer;
import uk.ac.ebi.atlas.streams.differential.DifferentialProfilesInputStream;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;

import static com.google.common.base.Preconditions.checkNotNull;

@Named("geneProfilesInputStreamBuilder")
@Scope("prototype")
public class InputStreamFactory {

    private static final Logger logger = Logger.getLogger(InputStreamFactory.class);

    @Value("#{configuration['experiment.magetab.path.template']}")
    private String baselineDataFileUrlTemplate;

    @Value("#{configuration['diff.experiment.data.path.template']}")
    private String differentialDataFileUrlTemplate;

    private BaselineExpressionsBuffer.Builder baselineExpressionsBufferBuilder;
    private DifferentialExpressionsBuffer.Builder differentialExpressionsBufferBuilder;
    private GeneProfile.Builder geneProfileBuilder;


    @Inject
    public InputStreamFactory(BaselineExpressionsBuffer.Builder baselineExpressionsBufferBuilder, DifferentialExpressionsBuffer.Builder differentialExpressionsBufferBuilder, GeneProfile.Builder geneProfileBuilder) {
        this.baselineExpressionsBufferBuilder = baselineExpressionsBufferBuilder;
        this.differentialExpressionsBufferBuilder = differentialExpressionsBufferBuilder;
        this.geneProfileBuilder = geneProfileBuilder;

    }

    CSVReader buildCsvReader(String experimentAccession, String tsvFileUrlTemplate) {
        String tsvFileUrl = MessageFormat.format(tsvFileUrlTemplate, experimentAccession);
        try {
            Path filePath = FileSystems.getDefault().getPath(checkNotNull(tsvFileUrl));
            Reader dataFileReader = new InputStreamReader(Files.newInputStream(filePath));
            return new CSVReader(dataFileReader, '\t');
         } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException("Error while building GeneProfileInputStream.", e);
        }
    }

    public ObjectInputStream<GeneProfile> createGeneProfileInputStream(String experimentAccession) {
        CSVReader csvReader = buildCsvReader(experimentAccession, baselineDataFileUrlTemplate);
        return new GeneProfilesInputStream(csvReader, experimentAccession, baselineExpressionsBufferBuilder, geneProfileBuilder);
    }

    public GeneExpressionsInputStream createGeneExpressionsInputStream(String experimentAccession) {
        CSVReader csvReader = buildCsvReader(experimentAccession, baselineDataFileUrlTemplate);
        return new GeneExpressionsInputStream(csvReader, experimentAccession, baselineExpressionsBufferBuilder);
    }

    public DifferentialProfilesInputStream createDifferentialProfileInputStream(String experimentAccession) {
        CSVReader csvReader = buildCsvReader(experimentAccession, differentialDataFileUrlTemplate);
        return new DifferentialProfilesInputStream(csvReader, experimentAccession, differentialExpressionsBufferBuilder);
    }

}
