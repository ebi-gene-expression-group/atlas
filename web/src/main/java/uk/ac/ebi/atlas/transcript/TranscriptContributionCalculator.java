package uk.ac.ebi.atlas.transcript;

import uk.ac.ebi.atlas.model.baseline.*;

import java.util.Map;

public class TranscriptContributionCalculator {

    private GeneProfileDao geneProfileDao;

    public Map<String, Double> getTranscriptContributions(String geneId, BaselineExperiment experiment, Factor factor) {
        TranscriptProfiles transcriptProfiles = geneProfileDao.getTranscriptProfiles(geneId, experiment.getAccession());

        ExperimentalFactors experimentalFactors = experiment.getExperimentalFactors();
        int factorIndex = experimentalFactors.getFactorIndex(factor);

        for (TranscriptProfile transcriptProfile : transcriptProfiles) {

        }

        return null;
    }
}
