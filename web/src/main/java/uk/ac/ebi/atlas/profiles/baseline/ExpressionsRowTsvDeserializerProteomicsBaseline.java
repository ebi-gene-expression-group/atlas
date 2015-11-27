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

package uk.ac.ebi.atlas.profiles.baseline;

import com.google.common.base.Joiner;
import uk.ac.ebi.atlas.model.baseline.FactorGroup;
import uk.ac.ebi.atlas.profiles.ExpressionsRowTsvDeserializer;
import uk.ac.ebi.atlas.utils.StringArrayUtil;

import java.util.List;

public class ExpressionsRowTsvDeserializerProteomicsBaseline extends ExpressionsRowTsvDeserializerBaseline {

    private final int[] orderedAssayGroupIndices;

    public ExpressionsRowTsvDeserializerProteomicsBaseline(List<FactorGroup> orderedFactorGroups, int[] orderedAssayGroupIndices) {
        super(orderedFactorGroups);
        this.orderedAssayGroupIndices = orderedAssayGroupIndices;
    }

    @Override
    public ExpressionsRowTsvDeserializer reload(String... values) {
        if (values.length < expectedNumberOfValues) {
            throw new IllegalArgumentException(String.format("Expected %s values but got [%s]", expectedNumberOfValues, Joiner.on(",").join(values)));
        }
        String[] filtered = StringArrayUtil.filterByIndices(values, orderedAssayGroupIndices);
        return super.reload(filtered);
    }


}
