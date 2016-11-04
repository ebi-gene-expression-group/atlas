package uk.ac.ebi.atlas.profiles.differential.rnaseq;

import uk.ac.ebi.atlas.profiles.differential.DifferentialProfilesListBuilder;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.differential.DifferentialProfileComparatorFactory;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.model.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.profiles.MinMaxProfileRanking;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.differential.RankProfilesFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Comparator;

@Named
@Scope("singleton")
public class RankRnaSeqProfilesFactory implements RankProfilesFactory<RnaSeqProfile, DifferentialProfilesList<RnaSeqProfile>, DifferentialProfileStreamOptions> {

    private DifferentialProfileComparatorFactory<RnaSeqProfile> differentialProfileComparatorFactory;
    private DifferentialProfilesListBuilder<RnaSeqProfile> geneProfilesListBuilder;

    @Inject
    public RankRnaSeqProfilesFactory(DifferentialProfileComparatorFactory<RnaSeqProfile> differentialProfileComparatorFactory, DifferentialProfilesListBuilder<RnaSeqProfile> geneProfilesListBuilder) {
        this.differentialProfileComparatorFactory = differentialProfileComparatorFactory;
        this.geneProfilesListBuilder = geneProfilesListBuilder;
    }

    public MinMaxProfileRanking<RnaSeqProfile, DifferentialProfilesList<RnaSeqProfile>> create(DifferentialProfileStreamOptions options) {
        Comparator<RnaSeqProfile> comparator = differentialProfileComparatorFactory.create(options);
        return new MinMaxProfileRanking<>(comparator, geneProfilesListBuilder);
    }

}
