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

package uk.ac.ebi.atlas.experimentloader.experimentdesign;

import au.com.bytecode.opencsv.CSVWriter;
import uk.ac.ebi.atlas.model.ExperimentDesign;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

public abstract class ExperimentDesignWriter {

    private static final String CHARACTERISTIC_HEADER_TEMPLATE = "Sample Characteristics[{0}]";
    private static final String FACTOR_VALUE_HEADER_TEMPLATE = "Factor Values[{0}]";


    public void write(String experimentAccession, CSVWriter csvWriter) throws IOException {

        ExperimentDesign experimentDesign = getMageTabParser().parse(experimentAccession);
        csvWriter.writeNext(buildColumnHeaders(experimentDesign));

        csvWriter.writeAll(experimentDesign.asTableData());

    }

    String[] buildColumnHeaders(ExperimentDesign experimentDesign) {
        List<String> headers = getCommonColumnHeaders();
        for (String characteristic : experimentDesign.getSampleHeaders()) {
            headers.add(formatColumnHeader(CHARACTERISTIC_HEADER_TEMPLATE, characteristic));
        }
        for (String factorValue : experimentDesign.getFactorHeaders()) {
            headers.add(formatColumnHeader(FACTOR_VALUE_HEADER_TEMPLATE, factorValue));
        }
        return headers.toArray(new String[headers.size()]);
    }

    private String formatColumnHeader(String template, String... parameters){
        return MessageFormat.format(template, parameters);
    }

    protected abstract List<String> getCommonColumnHeaders();

    protected abstract MageTabParser getMageTabParser();
}
