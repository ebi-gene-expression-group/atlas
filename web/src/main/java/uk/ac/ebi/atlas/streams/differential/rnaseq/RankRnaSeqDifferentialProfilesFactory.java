package uk.ac.ebi.atlas.streams.differential.rnaseq;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.model.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.streams.GeneProfilesListBuilder;
import uk.ac.ebi.atlas.streams.RankProfiles;
import uk.ac.ebi.atlas.model.differential.DifferentialProfileComparatorFactory;
import uk.ac.ebi.atlas.streams.differential.DifferentialProfileStreamOptions;
import uk.ac.ebi.atlas.streams.differential.RankDifferentialProfilesFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Comparator;

@Named
@Scope("singleton")
public class RankRnaSeqDifferentialProfilesFactory implements RankDifferentialProfilesFactory<RnaSeqProfile> {

    private DifferentialProfileComparatorFactory<RnaSeqProfile> differentialProfileComparatorFactory;
    private GeneProfilesListBuilder<DifferentialProfilesList<RnaSeqProfile>> geneProfilesListBuilder;

    @Inject
    public RankRnaSeqDifferentialProfilesFactory(DifferentialProfileComparatorFactory<RnaSeqProfile> differentialProfileComparatorFactory, GeneProfilesListBuilder<DifferentialProfilesList<RnaSeqProfile>> geneProfilesListBuilder) {
        this.differentialProfileComparatorFactory = differentialProfileComparatorFactory;
        this.geneProfilesListBuilder = geneProfilesListBuilder;
    }

    public RankProfiles<RnaSeqProfile, DifferentialProfilesList<RnaSeqProfile>> create(DifferentialProfileStreamOptions options) {
        Comparator<RnaSeqProfile> comparator = differentialProfileComparatorFactory.create(options);
        return new RankProfiles<>(comparator, geneProfilesListBuilder);
    }

}
