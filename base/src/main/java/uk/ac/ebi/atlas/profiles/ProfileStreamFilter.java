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

        Predicate<Prof> keepOnlyTheInterestingProfiles =
                options.isSpecific() && !options.getDataColumnsToReturn().equals(options.getAllDataColumns())
                ? keepOnlyProfilesOverExpressedOnColumns(options.getDataColumnsToReturn(), options.getAllDataColumns())
                        : keepOnlyProfilesExpressedOnColumns(options.getDataColumnsToReturn());


        decorated = geneQueryResponse.getAllGeneIds().isEmpty()
                ? keepOnlyTheInterestingProfiles
                : Predicates.and(keepOnlyProfilesWithGeneIds(geneQueryResponse.getAllGeneIds()), keepOnlyTheInterestingProfiles);
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

        return new Predicate<Prof>() {
            @Override
            public boolean apply(Prof prof) {
                return uppercaseGeneIds.contains(prof.getId().toUpperCase());
            }
        };
    }

    Predicate<Prof> keepOnlyProfilesExpressedOnColumns(final Collection<DataColumnDescriptor>  selectedColumns){
        return new Predicate<Prof>() {
            @Override
            public boolean apply(Prof prof) {
                return prof.isExpressedOnAnyOf(selectedColumns);
            }
        };
    }

    Predicate<Prof> keepOnlyProfilesOverExpressedOnColumns
            (final Collection<DataColumnDescriptor> selectedColumns, final Collection<DataColumnDescriptor> allColumns){
        final Set<DataColumnDescriptor> nonSelectedColumns = Sets.difference(ImmutableSet.copyOf(allColumns),
                ImmutableSet.copyOf(selectedColumns));
        return new Predicate<Prof>() {
            @Override
            public boolean apply(Prof prof) {
                return prof.getAverageExpressionLevelOn(selectedColumns)
                        > (prof.isExpressedOnAnyOf(nonSelectedColumns) ? prof.getMaxExpressionLevelOn(nonSelectedColumns) : 0.0d);
            }
        };
    }
}
