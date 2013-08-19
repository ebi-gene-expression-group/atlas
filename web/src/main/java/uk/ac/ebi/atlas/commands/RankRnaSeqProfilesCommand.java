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
import uk.ac.ebi.atlas.commands.context.RequestContext;
import uk.ac.ebi.atlas.commands.context.RnaSeqRequestContext;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.differential.DifferentialProfileComparator;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.model.differential.Regulation;
import uk.ac.ebi.atlas.model.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.streams.InputStreamFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Comparator;
import java.util.Queue;

@Named
@Scope("prototype")
public class RankRnaSeqProfilesCommand extends RankProfilesCommand<DifferentialProfilesList, RnaSeqProfile> {

    private InputStreamFactory inputStreamFactory;

    @Inject
    public RankRnaSeqProfilesCommand(RnaSeqRequestContext requestContext, InputStreamFactory inputStreamFactory) {
        super(requestContext);
        this.inputStreamFactory = inputStreamFactory;
    }

    @Override
    protected DifferentialProfilesList buildAverageGeneSetProfiles(GeneQueryResponse geneQueryResponse,
                                                                   ObjectInputStream<RnaSeqProfile> objectInputStream,
                                                                   Comparator<RnaSeqProfile> rnaSeqProfileComparator) {
        throw new UnsupportedOperationException("Method only supported for Baseline Experiments");
    }

    @Override
    protected DifferentialProfilesList createGeneProfilesList(Queue<RnaSeqProfile> geneProfiles) {
        return new DifferentialProfilesList(geneProfiles);
    }

    @Override
    protected Comparator<RnaSeqProfile> createGeneProfileComparator(RequestContext requestContext) {
        Regulation regulation = ((DifferentialRequestContext) requestContext).getRegulation();
        return new DifferentialProfileComparator(requestContext.isSpecific(), requestContext.getSelectedQueryFactors(),
                requestContext.getAllQueryFactors(), regulation, requestContext.getCutoff());
    }

    @Override
    public ObjectInputStream<RnaSeqProfile> createInputStream(String experimentAccession) {
        return inputStreamFactory.createDifferentialProfileInputStream(experimentAccession);
    }
}
