package uk.ac.ebi.atlas.profiles;

import uk.ac.ebi.atlas.model.DescribesDataColumns;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.profiles.differential.ProfileStreamOptions;

import java.util.function.Predicate;


public class ProfileStreamFilter {

    private ProfileStreamFilter() {
    }

    public static <DataColumnDescriptor extends DescribesDataColumns,
            StreamOptions extends ProfileStreamOptions<DataColumnDescriptor>,
            Prof extends Profile<DataColumnDescriptor, ?, Prof>> Predicate<Prof> create(final StreamOptions options) {
        return prof -> prof.getSpecificity(options.getDataColumnsToReturn()) > 0;
    }
}
