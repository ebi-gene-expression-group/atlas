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

package uk.ac.ebi.atlas.experimentpage.baseline.genedistribution;

import au.com.bytecode.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.profiles.BaselineExpressionsKryoReader;
import uk.ac.ebi.atlas.profiles.baseline.ExpressionsRowDeserializerBaselineBuilder;
import uk.ac.ebi.atlas.profiles.baseline.ExpressionsRowDeserializerProteomicsBaselineBuilder;
import uk.ac.ebi.atlas.profiles.baseline.ExpressionsRowRawDeserializerBaselineBuilder;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.utils.CsvReaderFactory;
import uk.ac.ebi.atlas.utils.KryoReaderFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;

@Named
@Scope("prototype")
public class BaselineExpressionsInputStreamFactory {

    @Value("#{configuration['experiment.magetab.path.template']}")
    private String baselineExperimentDataFileUrlTemplate;

    @Value("#{configuration['experiment.kryo_expressions.path.template']}")
    private String serializedBaselineExperimentDataFileUrlTemplate;

    private ExpressionsRowRawDeserializerBaselineBuilder expressionsRowRawDeserializerBaselineBuilder;
    private ExpressionsRowDeserializerProteomicsBaselineBuilder expressionsRowDeserializerProteomicsBaselineBuilder;
    private CsvReaderFactory csvReaderFactory;
    private KryoReaderFactory kryoReaderFactory;
    private ExperimentTrader experimentTrader;

    private BarChartExperimentAccessKeyTrader barChartExperimentAccessKeyTrader;

    @Inject
    public BaselineExpressionsInputStreamFactory(ExpressionsRowRawDeserializerBaselineBuilder expressionsRowRawDeserializerBaselineBuilder,
                                                 ExpressionsRowDeserializerProteomicsBaselineBuilder expressionsRowDeserializerProteomicsBaselineBuilder,
                                                 CsvReaderFactory csvReaderFactory,
                                                 KryoReaderFactory kryoReaderFactory,
                                                 ExperimentTrader experimentTrader,
                                                 BarChartExperimentAccessKeyTrader barChartExperimentAccessKeyTrader) {
        this.expressionsRowRawDeserializerBaselineBuilder = expressionsRowRawDeserializerBaselineBuilder;
        this.expressionsRowDeserializerProteomicsBaselineBuilder = expressionsRowDeserializerProteomicsBaselineBuilder;
        this.csvReaderFactory = csvReaderFactory;
        this.kryoReaderFactory = kryoReaderFactory;
        this.experimentTrader = experimentTrader;
        this.barChartExperimentAccessKeyTrader = barChartExperimentAccessKeyTrader;
    }

    public ObjectInputStream<BaselineExpressions> createGeneExpressionsInputStream(String experimentAccession) {

        String accessKey = barChartExperimentAccessKeyTrader.getAccessKey(experimentAccession);

        Experiment experiment = experimentTrader.getExperiment(experimentAccession, accessKey);

        if(experiment.getType().isProteomicsBaseline()) {
            String tsvFileURL = MessageFormat.format(baselineExperimentDataFileUrlTemplate, experimentAccession);
            CSVReader csvReader = csvReaderFactory.createTsvReader(tsvFileURL);
            return new BaselineExpressionsTsvInputStream(csvReader, experimentAccession, expressionsRowDeserializerProteomicsBaselineBuilder);
        }
        else {
            String serializedFileURL = MessageFormat.format(serializedBaselineExperimentDataFileUrlTemplate, experimentAccession);
            BaselineExpressionsKryoReader kryoReader = kryoReaderFactory.createBaselineExpressionsKryoReader(serializedFileURL);
            return new BaselineExpressionsKryoInputStream(kryoReader, experimentAccession, expressionsRowRawDeserializerBaselineBuilder);
        }
    }

}
