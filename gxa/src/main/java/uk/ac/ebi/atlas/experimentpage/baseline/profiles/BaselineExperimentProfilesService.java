package uk.ac.ebi.atlas.experimentpage.baseline.profiles;

import com.google.common.collect.ImmutableList;
import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.experimentpage.baseline.topgenes.BaselineExperimentTopGenesService;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfile;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;

import java.util.List;

// Get lists of gene IDs and/or preferences from the experiment page sidebar and get baseline profiles (i.e. heatmap
// rows).

@Component
public class BaselineExperimentProfilesService {
    private final BaselineExperimentTopGenesService baselineExperimentTopGenesService;
    private final BaselineExperimentProfilesDao baselineExperimentProfilesDao;


    public BaselineExperimentProfilesService(BaselineExperimentTopGenesService baselineExperimentTopGenesService,
                                             BaselineExperimentProfilesDao baselineExperimentProfilesDao) {
        this.baselineExperimentTopGenesService = baselineExperimentTopGenesService;
        this.baselineExperimentProfilesDao = baselineExperimentProfilesDao;
    }

    public GeneProfilesList<BaselineProfile> getTopGeneProfiles(String experimentAccession,
                                                                List<AssayGroup> assayGroups,
                                                                BaselineRequestPreferences<?> preferences) {

        List<String> topGeneIds = preferences.isSpecific() ?
                baselineExperimentTopGenesService.searchSpecificGenesInBaselineExperiment(
                        experimentAccession, preferences) :
                baselineExperimentTopGenesService.searchMostExpressedGenesInBaselineExperiment(
                        experimentAccession, preferences);

        return baselineExperimentProfilesDao.fetchProfiles(topGeneIds, assayGroups, preferences, experimentAccession);
    }

    public GeneProfilesList<BaselineProfile> getGeneProfiles(String experimentAccession,
                                                            List<AssayGroup> assayGroups,
                                                            BaselineRequestPreferences<?> preferences,
                                                            String... geneIds) {
        return baselineExperimentProfilesDao.fetchProfiles(
                ImmutableList.copyOf(geneIds), assayGroups, preferences, experimentAccession);
    }


    public long fetchCount(String experimentAccession, BaselineRequestPreferences<?> preferences) {
        return baselineExperimentProfilesDao.fetchCount(experimentAccession, preferences);
    }
}
