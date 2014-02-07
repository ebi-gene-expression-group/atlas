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

package uk.ac.ebi.atlas.commands;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commands.context.DifferentialRequestContext;
import uk.ac.ebi.atlas.commands.context.MicroarrayRequestContext;
import uk.ac.ebi.atlas.commands.context.RequestContext;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.commons.streams.SequenceObjectInputStream;
import uk.ac.ebi.atlas.model.differential.DifferentialProfileComparator;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.model.differential.Regulation;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.streams.InputStreamFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Comparator;
import java.util.Queue;
import java.util.Vector;

@Named
@Scope("prototype")
public class RankMicroarrayProfilesCommand extends RankProfilesCommand<DifferentialProfilesList, MicroarrayProfile> {

    private InputStreamFactory inputStreamFactory;
    private MicroarrayRequestContext requestContext;

    @Inject
    public RankMicroarrayProfilesCommand(MicroarrayRequestContext requestContext, InputStreamFactory inputStreamFactory) {
        super(requestContext);
        this.requestContext = requestContext;
        this.inputStreamFactory = inputStreamFactory;
    }

    @Override
    protected DifferentialProfilesList createGeneProfilesList(Queue<MicroarrayProfile> geneProfiles) {
        return new DifferentialProfilesList(geneProfiles);
    }

    @Override
    protected Comparator<MicroarrayProfile> createGeneProfileComparator(RequestContext requestContext) {
        Regulation regulation = ((DifferentialRequestContext) requestContext).getRegulation();
        return new DifferentialProfileComparator(requestContext.isSpecific(), requestContext.getSelectedQueryFactors(),
                requestContext.getAllQueryFactors(), regulation, requestContext.getCutoff());
    }

    @Override
    public ObjectInputStream<MicroarrayProfile> createInputStream(String experimentAccession) {
        MicroarrayExperiment microarrayExperiment = requestContext.getExperiment();

        Vector<ObjectInputStream<MicroarrayProfile>> inputStreams = new Vector<>();
        for (String arrayDesignAccession : microarrayExperiment.getArrayDesignAccessions()) {
            inputStreams.add(inputStreamFactory.createMicroarrayProfileInputStream(experimentAccession, arrayDesignAccession));
        }

        return new SequenceObjectInputStream<>(inputStreams.elements());
    }

    @Override
    ObjectInputStream<MicroarrayProfile> filterInputStream(ObjectInputStream<MicroarrayProfile> inputStream) throws GenesNotFoundException {
        return geneProfilesFilter.filterInputStreamAnySpecies(inputStream, requestContext);
    }

    @Override
    protected DifferentialProfilesList buildAverageGeneSetProfiles(GeneQueryResponse geneQueryResponse,
                                                                   ObjectInputStream<MicroarrayProfile> objectInputStream,
                                                                   Comparator<MicroarrayProfile> comparator) {
        throw new UnsupportedOperationException("Method only supported for Baseline Experiments");
    }
}
