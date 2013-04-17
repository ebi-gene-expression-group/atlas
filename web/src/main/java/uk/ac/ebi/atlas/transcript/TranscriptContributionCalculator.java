package uk.ac.ebi.atlas.transcript;

import com.google.common.collect.Lists;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.ExperimentalFactors;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.baseline.TranscriptProfile;
import uk.ac.ebi.atlas.model.cache.baseline.BaselineExperimentsCache;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

@Named
public class TranscriptContributionCalculator {

    protected static final int TOP_TRANSCRIPTS_NUMBER = 3;
    protected static final String OTHERS = "OTHERS";

    private GeneProfileDao geneProfileDao;

    private BaselineExperimentsCache experimentsCache;

    @Inject
    public TranscriptContributionCalculator(GeneProfileDao geneProfileDao, BaselineExperimentsCache experimentsCache) {
        this.geneProfileDao = geneProfileDao;
        this.experimentsCache = experimentsCache;
    }

    public Map<String, Double> getTranscriptContributions(String geneId, String experimentAccession, Factor factor) {

        List<TranscriptProfile> transcriptProfiles = Lists.newArrayList(geneProfileDao.getTranscriptProfiles(experimentAccession, geneId));

        int factorIndex = getFactorIndex(experimentAccession, factor);

        Collections.sort(transcriptProfiles, getReverseTranscriptProfileComparator(factorIndex));

        Map<String, Double> result = createTopTranscriptsMap(transcriptProfiles, factorIndex);
        return result;
    }

    protected int getFactorIndex(String experimentAccession, Factor factor) {

        BaselineExperiment experiment = experimentsCache.getExperiment(experimentAccession);

        ExperimentalFactors experimentalFactors = experiment.getExperimentalFactors();
        return experimentalFactors.getFactorIndex(factor);
    }

    protected Map<String, Double> createTopTranscriptsMap(List<TranscriptProfile> transcriptProfiles, int factorIndex) {
        Map<String, Double> topTranscripts = new HashMap<>();

        double sum = 0d;

        for (int i = 0; i < transcriptProfiles.size(); i++) {
            TranscriptProfile transcriptProfile = transcriptProfiles.get(i);
            double expression = transcriptProfile.getExpression(factorIndex);
            if (i < TOP_TRANSCRIPTS_NUMBER) {
                if(expression > 0d) {
                    topTranscripts.put(transcriptProfile.getTranscriptId(), expression);
                }
            } else {
                sum += expression;
            }
        }

        if (sum > 0d) {
            topTranscripts.put(OTHERS, sum);
        }
        return topTranscripts;
    }

    protected Comparator<TranscriptProfile> getReverseTranscriptProfileComparator(final int selectedIndex) {
        return new Comparator<TranscriptProfile>() {
            @Override
            public int compare(TranscriptProfile profile1, TranscriptProfile profile2) {
                return Double.compare(profile2.getExpression(selectedIndex), profile1.getExpression(selectedIndex));
            }
        };
    }
}
