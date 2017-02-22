package uk.ac.ebi.atlas.profiles.baseline;

import uk.ac.ebi.atlas.model.experiment.baseline.OldBaselineProfile;
import uk.ac.ebi.atlas.model.experiment.baseline.OldBaselineProfileComparator;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfilesList;
import uk.ac.ebi.atlas.profiles.MinMaxProfileRanking;
import uk.ac.ebi.atlas.profiles.differential.RankProfilesFactory;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class RankBaselineProfilesFactory implements RankProfilesFactory<OldBaselineProfile,
        BaselineProfilesList,
        BaselineProfileStreamOptions> {

    private BaselineProfilesListBuilder geneProfilesListBuilder;

    @Inject
    public RankBaselineProfilesFactory(BaselineProfilesListBuilder geneProfilesListBuilder) {
        this.geneProfilesListBuilder = geneProfilesListBuilder;
    }

    public MinMaxProfileRanking<OldBaselineProfile, BaselineProfilesList> create(BaselineProfileStreamOptions options) {
        return new MinMaxProfileRanking<>(OldBaselineProfileComparator.create(options), geneProfilesListBuilder);
    }

}
