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
import uk.ac.ebi.atlas.geneannotation.arraydesign.DesignElementMappingProvider;
import uk.ac.ebi.atlas.model.baseline.BaselineExpressions;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile.BaselineProfileBuilder;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayProfileBuilder;
import uk.ac.ebi.atlas.model.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.model.differential.rnaseq.RnaSeqProfileBuilder;
import uk.ac.ebi.atlas.streams.baseline.BaselineExpressionsBufferBuilder;
import uk.ac.ebi.atlas.streams.baseline.BaselineExpressionsInputStream;
import uk.ac.ebi.atlas.streams.baseline.BaselineProfilesInputStream;
import uk.ac.ebi.atlas.streams.differential.DifferentialExpressionsBufferBuilder;
import uk.ac.ebi.atlas.streams.differential.RnaSeqProfilesInputStream;
import uk.ac.ebi.atlas.streams.differential.microarray.MicroarrayExpressionsBufferBuilder;
import uk.ac.ebi.atlas.streams.differential.microarray.MicroarrayProfilesInputStream;
import uk.ac.ebi.atlas.utils.CsvReaderBuilder;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;

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

    private BaselineExpressionsBufferBuilder baselineExpressionsBufferBuilder;
    private DifferentialExpressionsBufferBuilder differentialExpressionsBufferBuilder;
    private MicroarrayExpressionsBufferBuilder microarrayExpressionsBufferBuilder;
    private BaselineProfileBuilder baselineProfileBuilder;
    private MicroarrayProfileBuilder microarrayProfileBuilder;
    private RnaSeqProfileBuilder rnaSeqProfileBuilder;
    private DesignElementMappingProvider designElementMappingProvider;

    private CsvReaderBuilder csvReaderBuilder;

    @Inject
    public InputStreamFactory(BaselineExpressionsBufferBuilder baselineExpressionsBufferBuilder,
                              DifferentialExpressionsBufferBuilder differentialExpressionsBufferBuilder,
                              MicroarrayExpressionsBufferBuilder microarrayExpressionsBufferBuilder,
                              BaselineProfileBuilder baselineProfileBuilder,
                              MicroarrayProfileBuilder microarrayProfileBuilder,
                              RnaSeqProfileBuilder rnaSeqProfileBuilder,
                              DesignElementMappingProvider designElementMappingProvider, CsvReaderBuilder csvReaderBuilder) {
        this.baselineExpressionsBufferBuilder = baselineExpressionsBufferBuilder;
        this.differentialExpressionsBufferBuilder = differentialExpressionsBufferBuilder;
        this.microarrayExpressionsBufferBuilder = microarrayExpressionsBufferBuilder;
        this.baselineProfileBuilder = baselineProfileBuilder;
        this.microarrayProfileBuilder = microarrayProfileBuilder;
        this.rnaSeqProfileBuilder = rnaSeqProfileBuilder;

        this.designElementMappingProvider = designElementMappingProvider;
        this.csvReaderBuilder = csvReaderBuilder;
    }


    public ObjectInputStream<BaselineProfile> createBaselineProfileInputStream(String experimentAccession) {
        String tsvFileURL = MessageFormat.format(baselineExperimentDataFileUrlTemplate, experimentAccession);
        CSVReader csvReader = csvReaderBuilder.buildCsvReader(tsvFileURL);
        return new BaselineProfilesInputStream(csvReader, experimentAccession, baselineExpressionsBufferBuilder, baselineProfileBuilder);
    }

    public ObjectInputStream<BaselineExpressions> createGeneExpressionsInputStream(String experimentAccession) {
        String tsvFileURL = MessageFormat.format(baselineExperimentDataFileUrlTemplate, experimentAccession);
        CSVReader csvReader = csvReaderBuilder.buildCsvReader(tsvFileURL);
        return new BaselineExpressionsInputStream(csvReader, experimentAccession, baselineExpressionsBufferBuilder);
    }

    public ObjectInputStream<RnaSeqProfile> createDifferentialProfileInputStream(String experimentAccession) {
        String tsvFileURL = MessageFormat.format(differentialExperimentDataFileUrlTemplate, experimentAccession);
        CSVReader csvReader = csvReaderBuilder.buildCsvReader(tsvFileURL);
        return new RnaSeqProfilesInputStream(csvReader, experimentAccession, differentialExpressionsBufferBuilder, rnaSeqProfileBuilder);
    }

    public ObjectInputStream<MicroarrayProfile> createMicroarrayProfileInputStream(String experimentAccession, String arrayDesignAccession) {
        String tsvFileURL = MessageFormat.format(microarrayExperimentDataFileUrlTemplate, experimentAccession, arrayDesignAccession);
        CSVReader csvReader = csvReaderBuilder.buildCsvReader(tsvFileURL);
        return new MicroarrayProfilesInputStream(csvReader, experimentAccession, microarrayExpressionsBufferBuilder, microarrayProfileBuilder, designElementMappingProvider, arrayDesignAccession);
    }

}
