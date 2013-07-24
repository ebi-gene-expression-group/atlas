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

package uk.ac.ebi.atlas.transcript;

import com.google.common.collect.Lists;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.ExperimentalFactors;
import uk.ac.ebi.atlas.model.baseline.FactorGroup;
import uk.ac.ebi.atlas.model.baseline.TranscriptProfile;
import uk.ac.ebi.atlas.model.cache.baseline.BaselineExperimentsCache;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Named
public class TranscriptContributionsCalculator {

    protected static final int TOP_TRANSCRIPTS_NUMBER = 3;

    private TranscriptProfileDAO transcriptProfileDAO;

    private BaselineExperimentsCache experimentsCache;

    @Inject
    public TranscriptContributionsCalculator(TranscriptProfileDAO transcriptProfileDAO, BaselineExperimentsCache experimentsCache) {
        this.transcriptProfileDAO = transcriptProfileDAO;
        this.experimentsCache = experimentsCache;
    }

    public TranscriptContributions getTranscriptsContribution(String geneId, String experimentAccession, FactorGroup factorGroup) {

        List<TranscriptProfile> transcriptProfiles = Lists.newArrayList(transcriptProfileDAO.findTranscriptProfiles(experimentAccession, geneId));

        int factorIndex = getFactorIndex(experimentAccession, factorGroup);

        return createTranscriptContributions(transcriptProfiles, factorIndex);
    }

    int getFactorIndex(String experimentAccession, FactorGroup factorGroup) {

        BaselineExperiment experiment = experimentsCache.getExperiment(experimentAccession);

        ExperimentalFactors experimentalFactors = experiment.getExperimentalFactors();
        return experimentalFactors.getFactorIndex(factorGroup);
    }

    TranscriptContributions createTranscriptContributions(List<TranscriptProfile> transcriptProfiles, int factorIndex) {
        Collections.sort(transcriptProfiles, getReverseTranscriptProfileComparator(factorIndex));

        TranscriptContributions transcriptContributions = new TranscriptContributions();

        transcriptContributions.setTotalTranscriptsCount(transcriptProfiles.size());

        double sum = 0d;

        int expressedTranscriptsCount = 0;

        for (int i = 0; i < transcriptProfiles.size(); i++) {
            TranscriptProfile transcriptProfile = transcriptProfiles.get(i);
            double expression = transcriptProfile.getExpression(factorIndex);
            if (expression != 0d) {
                expressedTranscriptsCount++;
            }
            if (i < TOP_TRANSCRIPTS_NUMBER) {
                if (expression != 0d) {
                    transcriptContributions.put(transcriptProfile.getTranscriptId(), expression);
                }
            } else {
                sum += expression;
            }
        }

        if (sum > 0d) {
            transcriptContributions.put(TranscriptContributions.OTHERS, sum);
        }

        transcriptContributions.setExpressedTranscriptsCount(expressedTranscriptsCount);

        return transcriptContributions;
    }

    Comparator<TranscriptProfile> getReverseTranscriptProfileComparator(final int selectedIndex) {
        return new Comparator<TranscriptProfile>() {
            @Override
            public int compare(TranscriptProfile profile1, TranscriptProfile profile2) {
                return Double.compare(Math.abs(profile2.getExpression(selectedIndex)), Math.abs(profile1.getExpression(selectedIndex)));
            }
        };
    }
}
