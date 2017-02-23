package uk.ac.ebi.atlas.profiles.baseline;

import uk.ac.ebi.atlas.model.experiment.baseline.*;
import uk.ac.ebi.atlas.profiles.TsvInputStream;

import java.io.Reader;
import java.util.List;

public class BaselineProfilesTsvInputStream extends TsvInputStream<BaselineProfile> {

    private BaselineProfileReusableBuilder baselineProfileReusableBuilder;

    private final int[] orderedAssayGroupIndices;


    public BaselineProfilesTsvInputStream(Reader reader,
                                          BaselineExperiment baselineExperiment,
                                          int[] orderedAssayGroupIndices) {

        super(reader);
        readHeaders(); // we throw them away for baseline
        this.orderedAssayGroupIndices = orderedAssayGroupIndices;
    }


    @Override
    protected BaselineProfile profileFromLineOfData(String[] values) {
        return null;
    }
}
