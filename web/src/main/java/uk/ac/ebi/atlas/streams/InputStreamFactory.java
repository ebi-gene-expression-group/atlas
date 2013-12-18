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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.dao.dto.BaselineExpressionDtoInputStream;
import uk.ac.ebi.atlas.model.baseline.BaselineExpressions;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.BaselineProfilePreconditionBackedBuilder;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayProfileBuilder;
import uk.ac.ebi.atlas.model.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.model.differential.rnaseq.RnaSeqProfileBuilder;
import uk.ac.ebi.atlas.streams.baseline.BaselineExpressionsQueueBuilder;
import uk.ac.ebi.atlas.streams.baseline.BaselineExpressionsInputStream;
import uk.ac.ebi.atlas.streams.baseline.BaselineProfilesInputStream;
import uk.ac.ebi.atlas.streams.differential.RnaSeqExpressionsQueueBuilder;
import uk.ac.ebi.atlas.streams.differential.RnaSeqProfilesInputStream;
import uk.ac.ebi.atlas.streams.differential.microarray.MicroarrayExpressionsQueueBuilder;
import uk.ac.ebi.atlas.streams.differential.microarray.MicroarrayProfilesInputStream;
import uk.ac.ebi.atlas.utils.CsvReaderFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;

@Named("geneProfilesInputStreamBuilder")
@Scope("prototype")
public class InputStreamFactory {

    @Value("#{configuration['experiment.magetab.path.template']}")
    private String baselineExperimentDataFileUrlTemplate;

    @Value("#{configuration['diff.experiment.data.path.template']}")
    private String differentialExperimentDataFileUrlTemplate;

    @Value("#{configuration['microarray.experiment.data.path.template']}")
    private String microarrayExperimentDataFileUrlTemplate;

    private BaselineExpressionsQueueBuilder baselineExpressionsBufferBuilder;
    private RnaSeqExpressionsQueueBuilder rnaSeqExpressionsBufferBuilder;
    private MicroarrayExpressionsQueueBuilder microarrayExpressionsBufferBuilder;
    private BaselineProfilePreconditionBackedBuilder baselineProfilePreconditionBackedBuilder;
    private MicroarrayProfileBuilder microarrayProfileBuilder;
    private RnaSeqProfileBuilder rnaSeqProfileBuilder;

    private CsvReaderFactory csvReaderFactory;

    @Inject
    public InputStreamFactory(BaselineExpressionsQueueBuilder baselineExpressionsBufferBuilder,
                              RnaSeqExpressionsQueueBuilder rnaSeqExpressionsBufferBuilder,
                              MicroarrayExpressionsQueueBuilder microarrayExpressionsBufferBuilder,
                              BaselineProfilePreconditionBackedBuilder baselineProfilePreconditionBackedBuilder,
                              MicroarrayProfileBuilder microarrayProfileBuilder,
                              RnaSeqProfileBuilder rnaSeqProfileBuilder,
                              CsvReaderFactory csvReaderFactory) {
        this.baselineExpressionsBufferBuilder = baselineExpressionsBufferBuilder;
        this.rnaSeqExpressionsBufferBuilder = rnaSeqExpressionsBufferBuilder;
        this.microarrayExpressionsBufferBuilder = microarrayExpressionsBufferBuilder;
        this.baselineProfilePreconditionBackedBuilder = baselineProfilePreconditionBackedBuilder;
        this.microarrayProfileBuilder = microarrayProfileBuilder;
        this.rnaSeqProfileBuilder = rnaSeqProfileBuilder;
        this.csvReaderFactory = csvReaderFactory;
    }

    public ObjectInputStream<BaselineProfile> createBaselineProfileInputStream(String experimentAccession) {
        String tsvFileURL = MessageFormat.format(baselineExperimentDataFileUrlTemplate, experimentAccession);
        CSVReader csvReader = csvReaderFactory.createTsvReader(tsvFileURL);
        return new BaselineProfilesInputStream(csvReader, experimentAccession, baselineExpressionsBufferBuilder, baselineProfilePreconditionBackedBuilder);
    }

    public ObjectInputStream<BaselineExpressions> createGeneExpressionsInputStream(String experimentAccession) {
        String tsvFileURL = MessageFormat.format(baselineExperimentDataFileUrlTemplate, experimentAccession);
        CSVReader csvReader = csvReaderFactory.createTsvReader(tsvFileURL);
        return new BaselineExpressionsInputStream(csvReader, experimentAccession, baselineExpressionsBufferBuilder);
    }

    public ObjectInputStream<RnaSeqProfile> createDifferentialProfileInputStream(String experimentAccession) {
        String tsvFileURL = MessageFormat.format(differentialExperimentDataFileUrlTemplate, experimentAccession);
        CSVReader csvReader = csvReaderFactory.createTsvReader(tsvFileURL);
        return new RnaSeqProfilesInputStream(csvReader, experimentAccession, rnaSeqExpressionsBufferBuilder, rnaSeqProfileBuilder);
    }

    public ObjectInputStream<MicroarrayProfile> createMicroarrayProfileInputStream(String experimentAccession, String arrayDesignAccession) {
        String tsvFileURL = MessageFormat.format(microarrayExperimentDataFileUrlTemplate, experimentAccession, arrayDesignAccession);
        CSVReader csvReader = csvReaderFactory.createTsvReader(tsvFileURL);
        return new MicroarrayProfilesInputStream(csvReader, experimentAccession, arrayDesignAccession, microarrayExpressionsBufferBuilder, microarrayProfileBuilder);
    }

}
