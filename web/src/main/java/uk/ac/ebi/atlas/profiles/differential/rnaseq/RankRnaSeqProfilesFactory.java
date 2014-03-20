package uk.ac.ebi.atlas.profiles.differential.rnaseq;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.model.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.profiles.GeneProfilesListBuilder;
import uk.ac.ebi.atlas.profiles.RankProfiles;
import uk.ac.ebi.atlas.model.differential.DifferentialProfileComparatorFactory;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.differential.RankDifferentialProfilesFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Comparator;

@Named
@Scope("singleton")
public class RankRnaSeqProfilesFactory implements RankDifferentialProfilesFactory<RnaSeqProfile> {

    private DifferentialProfileComparatorFactory<RnaSeqProfile> differentialProfileComparatorFactory;
    private GeneProfilesListBuilder<DifferentialProfilesList<RnaSeqProfile>> geneProfilesListBuilder;

    @Inject
    public RankRnaSeqProfilesFactory(DifferentialProfileComparatorFactory<RnaSeqProfile> differentialProfileComparatorFactory, GeneProfilesListBuilder<DifferentialProfilesList<RnaSeqProfile>> geneProfilesListBuilder) {
        this.differentialProfileComparatorFactory = differentialProfileComparatorFactory;
        this.geneProfilesListBuilder = geneProfilesListBuilder;
    }

    public RankProfiles<RnaSeqProfile, DifferentialProfilesList<RnaSeqProfile>> create(DifferentialProfileStreamOptions options) {
        Comparator<RnaSeqProfile> comparator = differentialProfileComparatorFactory.create(options);
        return new RankProfiles<>(comparator, geneProfilesListBuilder);
    }

}
