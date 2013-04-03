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

package uk.ac.ebi.atlas.streams;

import au.com.bytecode.opencsv.CSVReader;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.baseline.BaselineExpressions;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile.BaselineProfileBuilder;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayProfile.MicroarrayProfileBuilder;
import uk.ac.ebi.atlas.model.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.model.differential.rnaseq.RnaSeqProfile.RnaSeqProfileBuilder;
import uk.ac.ebi.atlas.streams.baseline.BaselineExpressionsBuffer;
import uk.ac.ebi.atlas.streams.baseline.BaselineExpressionsInputStream;
import uk.ac.ebi.atlas.streams.baseline.BaselineProfilesInputStream;
import uk.ac.ebi.atlas.streams.differential.DifferentialExpressionsBuffer;
import uk.ac.ebi.atlas.streams.differential.RnaSeqProfilesInputStream;
import uk.ac.ebi.atlas.streams.differential.microarray.MicroarrayExpressionsBuffer;
import uk.ac.ebi.atlas.streams.differential.microarray.MicroarrayProfilesInputStream;

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
    private String baselineExperimentDataFileUrlTemplate;

    @Value("#{configuration['diff.experiment.data.path.template']}")
    private String differentialExperimentDataFileUrlTemplate;

    @Value("#{configuration['microarray.experiment.data.path.template']}")
    private String microarrayExperimentDataFileUrlTemplate;

    private BaselineExpressionsBuffer.Builder baselineExpressionsBufferBuilder;
    private DifferentialExpressionsBuffer.Builder differentialExpressionsBufferBuilder;
    private MicroarrayExpressionsBuffer.Builder microarrayExpressionsBufferBuilder;
    private BaselineProfileBuilder baselineProfileBuilder;
    private MicroarrayProfileBuilder microarrayProfileBuilder;
    private RnaSeqProfileBuilder rnaSeqProfileBuilder;


    @Inject
    public InputStreamFactory(BaselineExpressionsBuffer.Builder baselineExpressionsBufferBuilder
                            , DifferentialExpressionsBuffer.Builder differentialExpressionsBufferBuilder
                            , MicroarrayExpressionsBuffer.Builder microarrayExpressionsBufferBuilder
                            , BaselineProfileBuilder baselineProfileBuilder
                            , MicroarrayProfileBuilder microarrayProfileBuilder
                            , RnaSeqProfileBuilder rnaSeqProfileBuilder) {
        this.baselineExpressionsBufferBuilder = baselineExpressionsBufferBuilder;
        this.differentialExpressionsBufferBuilder = differentialExpressionsBufferBuilder;
        this.microarrayExpressionsBufferBuilder = microarrayExpressionsBufferBuilder;
        this.baselineProfileBuilder = baselineProfileBuilder;
        this.microarrayProfileBuilder = microarrayProfileBuilder;
        this.rnaSeqProfileBuilder = rnaSeqProfileBuilder;

    }

    CSVReader buildCsvReader(String tsvFileURL) {
        try {
            Path filePath = FileSystems.getDefault().getPath(checkNotNull(tsvFileURL));
            Reader dataFileReader = new InputStreamReader(Files.newInputStream(filePath));
            return new CSVReader(dataFileReader, '\t');
         } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException("Error while building GeneProfileInputStream.", e);
        }
    }

    public ObjectInputStream<BaselineProfile> createBaselineProfileInputStream(String experimentAccession) {
        String tsvFileURL = MessageFormat.format(baselineExperimentDataFileUrlTemplate, experimentAccession);
        CSVReader csvReader = buildCsvReader(tsvFileURL);
        return new BaselineProfilesInputStream(csvReader, experimentAccession, baselineExpressionsBufferBuilder, baselineProfileBuilder);
    }

    public ObjectInputStream<BaselineExpressions> createGeneExpressionsInputStream(String experimentAccession) {
        String tsvFileURL = MessageFormat.format(baselineExperimentDataFileUrlTemplate, experimentAccession);
        CSVReader csvReader = buildCsvReader(tsvFileURL);
        return new BaselineExpressionsInputStream(csvReader, experimentAccession, baselineExpressionsBufferBuilder);
    }

    public ObjectInputStream<RnaSeqProfile> createDifferentialProfileInputStream(String experimentAccession) {
        String tsvFileURL = MessageFormat.format(differentialExperimentDataFileUrlTemplate, experimentAccession);
        CSVReader csvReader = buildCsvReader(tsvFileURL);
        return new RnaSeqProfilesInputStream(csvReader, experimentAccession, differentialExpressionsBufferBuilder, rnaSeqProfileBuilder);
    }

    //ToDo: to be implemented...
    public ObjectInputStream<MicroarrayProfile> createMicroarrayProfileInputStream(String experimentAccession, String arrayDesignAccession) {
        String tsvFileURL = MessageFormat.format(microarrayExperimentDataFileUrlTemplate, experimentAccession, arrayDesignAccession);
        CSVReader csvReader = buildCsvReader(tsvFileURL);
        return new MicroarrayProfilesInputStream(csvReader, experimentAccession, microarrayExpressionsBufferBuilder, microarrayProfileBuilder);
    }

}
