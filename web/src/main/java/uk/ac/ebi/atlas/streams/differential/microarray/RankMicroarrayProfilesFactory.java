package uk.ac.ebi.atlas.streams.differential.microarray;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.differential.DifferentialProfileComparatorFactory;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.streams.GeneProfilesListBuilder;
import uk.ac.ebi.atlas.streams.RankProfiles;
import uk.ac.ebi.atlas.streams.differential.DifferentialProfileStreamOptions;
import uk.ac.ebi.atlas.streams.differential.RankDifferentialProfilesFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Comparator;

@Named
@Scope("singleton")
public class RankMicroarrayProfilesFactory implements RankDifferentialProfilesFactory<MicroarrayProfile> {

    private DifferentialProfileComparatorFactory<MicroarrayProfile> differentialProfileComparatorFactory;
    private GeneProfilesListBuilder<DifferentialProfilesList<MicroarrayProfile>> geneProfilesListBuilder;

    @Inject
    public RankMicroarrayProfilesFactory(DifferentialProfileComparatorFactory<MicroarrayProfile> differentialProfileComparatorFactory, GeneProfilesListBuilder<DifferentialProfilesList<MicroarrayProfile>> geneProfilesListBuilder) {
        this.differentialProfileComparatorFactory = differentialProfileComparatorFactory;
        this.geneProfilesListBuilder = geneProfilesListBuilder;
    }

    public RankProfiles<MicroarrayProfile, DifferentialProfilesList<MicroarrayProfile>> create(DifferentialProfileStreamOptions options) {
        Comparator<MicroarrayProfile> comparator = differentialProfileComparatorFactory.create(options);
        return new RankProfiles<>(comparator, geneProfilesListBuilder);
    }

}
