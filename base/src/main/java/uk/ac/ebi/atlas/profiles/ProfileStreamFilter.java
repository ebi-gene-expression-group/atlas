package uk.ac.ebi.atlas.profiles;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.collections.CollectionUtils;
import uk.ac.ebi.atlas.model.DescribesDataColumns;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.profiles.differential.ProfileStreamOptions;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;

import java.util.Collection;
import java.util.Set;
import java.util.function.Predicate;


public class ProfileStreamFilter<DataColumnDescriptor extends DescribesDataColumns,
                                 StreamOptions extends ProfileStreamOptions<DataColumnDescriptor>,
                                 Prof extends Profile<DataColumnDescriptor, ?, Prof>>
implements Predicate<Prof> {

    private final Predicate<Prof> decorated;

    public ProfileStreamFilter(StreamOptions options, GeneQueryResponse geneQueryResponse){
        
        decorated = geneQueryResponse.getAllGeneIds().isEmpty()
                ? keepOnlyProfilesExpressedOnColumns(options.getDataColumnsToReturn())
                : keepOnlyProfilesWithGeneIds(geneQueryResponse.getAllGeneIds())
                  .and(keepOnlyProfilesExpressedOnColumns(options.getDataColumnsToReturn()));
    }

    @Override
    public boolean test(Prof prof) {
        return decorated.test(prof);
    }

    private Predicate<Prof> keepOnlyProfilesWithGeneIds(final Set<String> geneIds) {
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(geneIds));

        ImmutableSet.Builder<String> builder = ImmutableSet.builder();

        for (String geneId : geneIds) {
            builder.add(geneId.toUpperCase());
        }

        final ImmutableSet<String> uppercaseGeneIds = builder.build();

        return prof -> uppercaseGeneIds.contains(prof.getId().toUpperCase());
    }

    private Predicate<Prof> keepOnlyProfilesExpressedOnColumns(final Collection<DataColumnDescriptor>  selectedColumns){
        return prof -> prof.isExpressedOnAnyOf(selectedColumns);
    }

}
