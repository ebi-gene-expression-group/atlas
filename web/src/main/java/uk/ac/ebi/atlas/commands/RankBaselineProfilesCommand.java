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
import uk.ac.ebi.atlas.commands.context.BaselineRequestContext;
import uk.ac.ebi.atlas.commands.context.RequestContext;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.BaselineProfileComparator;
import uk.ac.ebi.atlas.model.baseline.BaselineProfilesList;
import uk.ac.ebi.atlas.model.baseline.GeneSetProfilesBuilder;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.streams.InputStreamFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Comparator;
import java.util.Queue;

@Named
@Scope("request")
public class RankBaselineProfilesCommand extends RankProfilesCommand<BaselineProfilesList, BaselineProfile> {

    private InputStreamFactory inputStreamFactory;
    private GeneSetProfilesBuilder geneSetProfilesBuilder;

    @Inject
    public RankBaselineProfilesCommand(BaselineRequestContext requestContext, InputStreamFactory inputStreamFactory,
                                       GeneSetProfilesBuilder geneSetProfilesBuilder) {
        super(requestContext);
        this.inputStreamFactory = inputStreamFactory;
        this.geneSetProfilesBuilder = geneSetProfilesBuilder;
    }


    @Override
    protected BaselineProfilesList createGeneProfilesList(Queue<BaselineProfile> geneProfiles) {
        return new BaselineProfilesList(geneProfiles);
    }

    @Override
    protected Comparator<BaselineProfile> createGeneProfileComparator(RequestContext requestContext) {
        return new BaselineProfileComparator(requestContext.isSpecific(), requestContext.getSelectedQueryFactors(),
                requestContext.getAllQueryFactors(), requestContext.getCutoff());
    }

    @Override
    public ObjectInputStream<BaselineProfile> createInputStream(String experimentAccession) {
        return inputStreamFactory.createBaselineProfileInputStream(experimentAccession);
    }

    @Override
    protected BaselineProfilesList buildAverageGeneSetProfiles(GeneQueryResponse geneQueryResponse,
                                                               ObjectInputStream<BaselineProfile> inputStream,
                                                               Comparator<BaselineProfile> baselineProfileComparator) {

        return geneSetProfilesBuilder.forGeneQueryResponse(geneQueryResponse)
                .withInputStream(inputStream)
                .withBaselineComparator(baselineProfileComparator).build();
    }

}
