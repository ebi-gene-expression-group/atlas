package uk.ac.ebi.atlas.profiles.baseline;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.BaselineProfileComparatorFactory;
import uk.ac.ebi.atlas.model.baseline.BaselineProfilesList;
import uk.ac.ebi.atlas.profiles.RankProfiles;
import uk.ac.ebi.atlas.profiles.differential.RankProfilesFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Comparator;

@Named
@Scope("singleton")
public class RankBaselineProfilesFactory implements RankProfilesFactory<BaselineProfile, BaselineProfilesList, BaselineProfileStreamOptions> {

    private BaselineProfileComparatorFactory baselineProfileComparatorFactory;
    private BaselineProfilesListBuilder geneProfilesListBuilder;

    @Inject
    public RankBaselineProfilesFactory(BaselineProfileComparatorFactory baselineProfileComparatorFactory, BaselineProfilesListBuilder geneProfilesListBuilder) {
        this.baselineProfileComparatorFactory = baselineProfileComparatorFactory;
        this.geneProfilesListBuilder = geneProfilesListBuilder;
    }

    public RankProfiles<BaselineProfile, BaselineProfilesList> create(BaselineProfileStreamOptions options) {
        Comparator<BaselineProfile> comparator = baselineProfileComparatorFactory.create(options);
        return new RankProfiles<>(comparator, geneProfilesListBuilder);
    }

}
