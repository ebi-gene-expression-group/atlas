package uk.ac.ebi.atlas.streams.baseline;

import com.google.common.collect.ImmutableSetMultimap;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.BaselineProfilesList;
import uk.ac.ebi.atlas.model.baseline.Factor;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

@Named
@Scope("prototype")
public class RankBaselineProfilesPipeline {

    private static final Logger LOGGER = Logger.getLogger(RankBaselineProfilesPipeline.class);
    private final RankBaselineProfiles rankBaselineProfiles;
    private final BaselineProfilesPipeline baselineProfilesPipeline;
    private double cutOff = 0.5d;
    private boolean isSpecific;
    private Set<Factor> queryFactors = Collections.emptySet();
    private Set<String> uppercaseGeneIDs = Collections.emptySet();
    private ImmutableSetMultimap<String, String> geneSetIdsToGeneIds = ImmutableSetMultimap.of();
    private Set<Factor> allQueryFactors;
    private int size = 50;
    private ObjectInputStream<BaselineProfile> inputStream;

    @Inject
    public RankBaselineProfilesPipeline(BaselineProfilesPipeline baselineProfilesPipeline, RankBaselineProfiles rankBaselineProfiles) {
        this.baselineProfilesPipeline = baselineProfilesPipeline;
        this.rankBaselineProfiles = rankBaselineProfiles;
    }

    public RankBaselineProfilesPipeline inputStream(ObjectInputStream<BaselineProfile> inputStream) {
        this.inputStream = inputStream;
        return this;
    }

    public RankBaselineProfilesPipeline cutOff(double cutOff) {
        this.cutOff = cutOff;
        return this;
    }

    public RankBaselineProfilesPipeline isSpecific(boolean isSpecific) {
        this.isSpecific = isSpecific;
        return this;
    }

    public RankBaselineProfilesPipeline isSpecific() {
        this.isSpecific = true;
        return this;
    }

    public RankBaselineProfilesPipeline queryFactors(Set<Factor> queryFactors) {
        this.queryFactors = queryFactors;
        return this;
    }

    public RankBaselineProfilesPipeline allQueryFactors(Set<Factor> allQueryFactors) {
        this.allQueryFactors = allQueryFactors;
        return this;
    }


    public RankBaselineProfilesPipeline selectGeneIDs(Set<String> uppercaseGeneIDs) {
        this.uppercaseGeneIDs = uppercaseGeneIDs;
        return this;
    }

    public RankBaselineProfilesPipeline averageIntoGeneSets(ImmutableSetMultimap<String, String> geneSetIdsToGeneIds) {
        this.geneSetIdsToGeneIds = geneSetIdsToGeneIds;
        return this;
    }

    public RankBaselineProfilesPipeline top(int size) {
        this.size = size;
        return this;
    }

    public BaselineProfilesList fetch() {

        checkNotNull(inputStream, "Missing inputStream argument");

        try (ObjectInputStream<BaselineProfile> source = inputStream) {

            Iterable<BaselineProfile> profiles = new IterableObjectInputStream<>(source);

            Iterable<BaselineProfile> mapProfiles = baselineProfilesPipeline.profiles(profiles).
                    isSpecific(isSpecific).
                    selectGeneIDs(uppercaseGeneIDs).
                    queryFactors(queryFactors).
                    allQueryFactors(allQueryFactors).
                    averageIntoGeneSets(geneSetIdsToGeneIds).
                    lazyMap();

            return rankBaselineProfiles.profiles(mapProfiles).
                    queryFactors(queryFactors).
                    allQueryFactors(allQueryFactors).
                    cutOff(cutOff).
                    isSpecific(isSpecific).top(size).rank();

        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }

    }

}
