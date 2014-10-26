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
import uk.ac.ebi.atlas.profiles.ExpressionsRowDeserializer;
import uk.ac.ebi.atlas.utils.StringArrayUtil;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

public class ExpressionsRowDeserializerProteomicsBaseline extends ExpressionsRowDeserializerBaseline {

    private final int[] orderedAssayGroupIndices;

    public ExpressionsRowDeserializerProteomicsBaseline(List<FactorGroup> orderedFactorGroups, int[] orderedAssayGroupIndices) {
        super(orderedFactorGroups);
        this.orderedAssayGroupIndices = orderedAssayGroupIndices;
    }

    @Override
    public ExpressionsRowDeserializer reload(String... values) {
        checkArgument(values.length >= orderedAssayGroupIndices.length, String.format("Expected at least %s values but got [%s]", orderedAssayGroupIndices.length, Joiner.on(",").join(values)));
        String[] filtered = StringArrayUtil.filterByIndices(values, orderedAssayGroupIndices);
        return super.reload(filtered);
    }


}
