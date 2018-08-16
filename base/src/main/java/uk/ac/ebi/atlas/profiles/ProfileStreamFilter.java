package uk.ac.ebi.atlas.profiles;

import uk.ac.ebi.atlas.model.DescribesDataColumns;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.profiles.differential.ProfileStreamOptions;

import java.util.List;
import java.util.function.Predicate;

public class ProfileStreamFilter {
    protected ProfileStreamFilter() {
        throw new UnsupportedOperationException();
    }

    public static <D extends DescribesDataColumns, O extends ProfileStreamOptions<D>, P extends Profile<D, ?, P>>
           Predicate<P> create(final O options) {

        final List<D> dataColumnsToReturn = options.getDataColumnsToReturn();
        return prof -> prof.isExpressedAnywhereOn(dataColumnsToReturn);

    }
}
