package uk.ac.ebi.atlas.streams;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.model.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.streams.differential.DifferentialComparatorFactory;
import uk.ac.ebi.atlas.streams.differential.DifferentialProfilesCommandOptions;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Comparator;

@Named
@Scope("singleton")
public class RankProfilesFactory {

    private DifferentialComparatorFactory<RnaSeqProfile> differentialComparatorFactory;
    private GeneProfilesListBuilder<DifferentialProfilesList<RnaSeqProfile>> geneProfilesListBuilder;

    @Inject
    public RankProfilesFactory(DifferentialComparatorFactory<RnaSeqProfile> differentialComparatorFactory, GeneProfilesListBuilder<DifferentialProfilesList<RnaSeqProfile>> geneProfilesListBuilder) {
        this.differentialComparatorFactory = differentialComparatorFactory;
        this.geneProfilesListBuilder = geneProfilesListBuilder;
    }

    public RankProfiles<RnaSeqProfile, DifferentialProfilesList<RnaSeqProfile>> create(DifferentialProfilesCommandOptions options) {
        Comparator<RnaSeqProfile> comparator = differentialComparatorFactory.create(options);
        return new RankProfiles<>(comparator, geneProfilesListBuilder);
    }

}
