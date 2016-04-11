package uk.ac.ebi.atlas.experimentpage.baseline;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.profiles.baseline.ProteomicsBaselineProfileInputStreamFactory;
import uk.ac.ebi.atlas.profiles.baseline.RankBaselineProfilesFactory;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@Scope("prototype")
public class ProteomicsBaselineProfilesHeatMap extends BaselineProfilesHeatMap {

    @Inject
    public ProteomicsBaselineProfilesHeatMap(RankBaselineProfilesFactory rankProfilesFactory,
                                             ProteomicsBaselineProfileInputStreamFactory inputStreamFactory) {
        super(rankProfilesFactory, inputStreamFactory);
    }

}
