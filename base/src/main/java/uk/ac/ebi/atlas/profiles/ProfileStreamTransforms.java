package uk.ac.ebi.atlas.profiles;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import uk.ac.ebi.atlas.model.DescribesDataColumns;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.profiles.differential.ProfileStreamOptions;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public abstract class ProfileStreamTransforms<DataColumnDescriptor extends DescribesDataColumns,
        Prof extends Profile<DataColumnDescriptor, ?>>
    implements Function<Iterable<Prof>, Iterable<Prof>> {

    private final List<Function<Iterable<Prof>, Iterable<Prof>>> transforms = new LinkedList<>();

    protected void register(Function<Iterable<Prof>, Iterable<Prof>> f){
        transforms.add(f);
    }

    @Nullable
    @Override
    public Iterable<Prof> apply(@Nullable Iterable<Prof> stream) {
        for(Function<Iterable<Prof>, Iterable<Prof>> f: transforms){
            stream = f.apply(stream);
        }
        return stream;
    }


    protected Function<Iterable<Prof>, Iterable<Prof>> fromPredicate(final Predicate<Prof> predicate){
        return new Function<Iterable<Prof>, Iterable<Prof>>() {
            @Nullable
            @Override
            public Iterable<Prof> apply(@Nullable Iterable<Prof> profs) {
                return Iterables.filter(profs, predicate);
            }
        };
    }

    protected Function<Iterable<Prof>, Iterable<Prof>> keepOnlyProfilesWithGeneIds(final Set<String> geneIds) {
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(geneIds));

        ImmutableSet.Builder<String> builder = ImmutableSet.builder();

        for (String geneId : geneIds) {
            builder.add(geneId.toUpperCase());
        }

        final ImmutableSet<String> uppercaseGeneIds = builder.build();

        return fromPredicate(new Predicate<Prof>() {
            @Override
            public boolean apply(@Nullable Prof prof) {
                return uppercaseGeneIds.contains(prof.getId().toUpperCase());
            }
        });
    }

    protected Function<Iterable<Prof>, Iterable<Prof>> keepOnlyProfilesExpressedOnColumns(final
                                                                                          Collection<DataColumnDescriptor>
                                                                                                  selectedColumns){
        return fromPredicate(new Predicate<Prof>() {
            @Override
            public boolean apply(@Nullable Prof prof) {
                return prof.isExpressedOnAnyOf(selectedColumns);
            }
        });
    }

    protected Function<Iterable<Prof>, Iterable<Prof>> keepOnlyProfilesOverExpressedOnColumns
            (final Collection<DataColumnDescriptor> selectedColumns, final Collection<DataColumnDescriptor> allColumns){
        final Set<DataColumnDescriptor> nonSelectedColumns = Sets.difference(ImmutableSet.copyOf(selectedColumns),
                ImmutableSet.copyOf(allColumns));
        return fromPredicate(new Predicate<Prof>() {
            @Override
            public boolean apply(@Nullable Prof prof) {
                return ! prof.isExpressedOnAnyOf(nonSelectedColumns) || prof.getAverageExpressionLevelOn
                        (selectedColumns) > prof
                        .getMaxExpressionLevelOn
                        (nonSelectedColumns);
            }
        });
    }
}
