package uk.ac.ebi.atlas.profiles.differential.microarray;

import uk.ac.ebi.atlas.model.experiment.differential.DifferentialProfileComparatorFactory;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfilesListBuilder;
import uk.ac.ebi.atlas.profiles.differential.RankProfilesFactory;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.profiles.MinMaxProfileRanking;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamOptions;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Comparator;

@Named
@Scope("singleton")
public class RankMicroarrayProfilesFactory implements RankProfilesFactory<MicroarrayProfile, DifferentialProfilesList<MicroarrayProfile>, DifferentialProfileStreamOptions> {

    private DifferentialProfileComparatorFactory<MicroarrayProfile> differentialProfileComparatorFactory;
    private DifferentialProfilesListBuilder<MicroarrayProfile> geneProfilesListBuilder;

    @Inject
    public RankMicroarrayProfilesFactory(DifferentialProfileComparatorFactory<MicroarrayProfile> differentialProfileComparatorFactory, DifferentialProfilesListBuilder<MicroarrayProfile> geneProfilesListBuilder) {
        this.differentialProfileComparatorFactory = differentialProfileComparatorFactory;
        this.geneProfilesListBuilder = geneProfilesListBuilder;
    }

    public MinMaxProfileRanking<MicroarrayProfile, DifferentialProfilesList<MicroarrayProfile>> create(DifferentialProfileStreamOptions options) {
        Comparator<MicroarrayProfile> comparator = differentialProfileComparatorFactory.create(options);
        return new MinMaxProfileRanking<>(comparator, geneProfilesListBuilder);
    }

}
