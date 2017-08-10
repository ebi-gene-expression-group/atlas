package uk.ac.ebi.atlas.profiles;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import uk.ac.ebi.atlas.model.DescribesDataColumns;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.profiles.differential.ProfileStreamOptions;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;

import java.util.Collection;
import java.util.Set;

public class ProfileStreamFilter<DataColumnDescriptor extends DescribesDataColumns,
        StreamOptions extends ProfileStreamOptions<DataColumnDescriptor>,
        Prof extends Profile<DataColumnDescriptor, ?, Prof>> implements Predicate<Prof> {

    private final Predicate<Prof> decorated;

    public ProfileStreamFilter(StreamOptions options, GeneQueryResponse geneQueryResponse){
        
        decorated = geneQueryResponse.getAllGeneIds().isEmpty()
                ? keepOnlyProfilesExpressedOnColumns(options.getDataColumnsToReturn())
                : Predicates.and(
                    keepOnlyProfilesWithGeneIds(geneQueryResponse.getAllGeneIds()),
                    keepOnlyProfilesExpressedOnColumns(options.getDataColumnsToReturn())
                );
    }

    @Override
    public boolean apply(Prof prof) {
        return decorated.apply(prof);
    }


    Predicate<Prof> keepOnlyProfilesWithGeneIds(final Set<String> geneIds) {
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(geneIds));

        ImmutableSet.Builder<String> builder = ImmutableSet.builder();

        for (String geneId : geneIds) {
            builder.add(geneId.toUpperCase());
        }

        final ImmutableSet<String> uppercaseGeneIds = builder.build();

        return prof -> uppercaseGeneIds.contains(prof.getId().toUpperCase());
    }

    Predicate<Prof> keepOnlyProfilesExpressedOnColumns(final Collection<DataColumnDescriptor>  selectedColumns){
        return prof -> prof.isExpressedOnAnyOf(selectedColumns);
    }

}
