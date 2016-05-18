
package uk.ac.ebi.atlas.profiles.baseline;

import com.google.common.base.Joiner;
import uk.ac.ebi.atlas.model.baseline.FactorGroup;
import uk.ac.ebi.atlas.profiles.ExpressionsRowTsvDeserializer;
import uk.ac.ebi.atlas.utils.StringArrayUtil;

import java.util.List;

public class ExpressionsRowTsvDeserializerProteomicsBaseline extends ExpressionsRowTsvDeserializerBaseline {

    private final int[] orderedAssayGroupIndices;

    public ExpressionsRowTsvDeserializerProteomicsBaseline(List<FactorGroup> orderedFactorGroups, int[] orderedAssayGroupIndices) {
        super(orderedFactorGroups);
        this.orderedAssayGroupIndices = orderedAssayGroupIndices;
    }

    @Override
    public ExpressionsRowTsvDeserializer reload(String... values) {
        if (values.length < expectedNumberOfValues) {
            throw new IllegalArgumentException(String.format("Expected %s values but got [%s]", expectedNumberOfValues, Joiner.on(",").join(values)));
        }
        String[] filtered = StringArrayUtil.filterByIndices(values, orderedAssayGroupIndices);
        return super.reload(filtered);
    }


}
