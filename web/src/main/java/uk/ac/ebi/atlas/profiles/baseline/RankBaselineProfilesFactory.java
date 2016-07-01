package uk.ac.ebi.atlas.profiles.baseline;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.BaselineProfileComparator;
import uk.ac.ebi.atlas.model.baseline.BaselineProfilesList;
import uk.ac.ebi.atlas.profiles.MinMaxProfileRanking;
import uk.ac.ebi.atlas.profiles.differential.RankProfilesFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Comparator;

@Named
@Scope("singleton")
public class RankBaselineProfilesFactory implements RankProfilesFactory<BaselineProfile, BaselineProfilesList, BaselineProfileStreamOptions> {

    private BaselineProfilesListBuilder geneProfilesListBuilder;

    @Inject
    public RankBaselineProfilesFactory(BaselineProfilesListBuilder geneProfilesListBuilder) {
        this.geneProfilesListBuilder = geneProfilesListBuilder;
    }

    public MinMaxProfileRanking<BaselineProfile, BaselineProfilesList> create(BaselineProfileStreamOptions options) {
        return new MinMaxProfileRanking<>(BaselineProfileComparator.create(options), geneProfilesListBuilder);
    }

}
