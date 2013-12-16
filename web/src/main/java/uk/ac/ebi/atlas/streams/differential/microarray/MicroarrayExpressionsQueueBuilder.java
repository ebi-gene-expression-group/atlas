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

package uk.ac.ebi.atlas.streams.differential.microarray;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.cache.microarray.MicroarrayExperimentsCache;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.streams.TsvRowQueue;
import uk.ac.ebi.atlas.streams.differential.DifferentialExpressionsQueueBuilder;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@Scope("prototype")
public class MicroarrayExpressionsQueueBuilder extends DifferentialExpressionsQueueBuilder<MicroarrayExpression, MicroarrayExperiment> {


    @Inject
    public MicroarrayExpressionsQueueBuilder(MicroarrayExperimentsCache experimentsCache) {
        super(experimentsCache);
    }

    @Override
    protected TsvRowQueue<MicroarrayExpression> getBufferInstance(List<Contrast> orderedContrasts) {
        return new MicroarrayExpressionsQueue(orderedContrasts);
    }

}
