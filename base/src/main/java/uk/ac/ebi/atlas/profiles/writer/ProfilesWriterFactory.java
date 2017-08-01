package uk.ac.ebi.atlas.profiles.writer;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import org.apache.commons.lang3.ArrayUtils;
import uk.ac.ebi.atlas.experimentpage.context.RequestContext;
import uk.ac.ebi.atlas.model.DescribesDataColumns;
import uk.ac.ebi.atlas.model.Expression;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.search.SearchDescription;

import javax.annotation.Nullable;
import java.io.Writer;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.wrap;

public abstract class ProfilesWriterFactory<DataColumnDescriptor extends DescribesDataColumns,
        Expr extends Expression,
        Prof extends Profile<DataColumnDescriptor, Expr, Prof>,
        R extends RequestContext<DataColumnDescriptor,?, ?>> {

    protected abstract String getTsvFileMasthead(R requestContext, String queryDescription);

    protected String[] getProfileIdColumnHeaders(R requestContext) {
        return new String[]{"Gene ID", "Gene Name"};
    }

    protected Iterable<String> labelsForColumn(R requestContext, DataColumnDescriptor dataColumnDescriptor){
        return Collections.singleton(requestContext.displayNameForColumn(dataColumnDescriptor));
    }

    protected Iterable<String> valuesFromColumn(R requestContext, @Nullable Expr expression) {
        return Collections.singleton(expression == null ? "" : Double.toString(expression.getLevel()));
    }

    public final ProfilesWriter<Prof> create(Writer responseWriter, final R requestContext) {
        final List<DataColumnDescriptor> columns = requestContext.getDataColumnsToReturn();
        return new ProfilesWriter<>(
                responseWriter,
                getTsvFileMasthead(requestContext, wrap(SearchDescription.get(requestContext.getGeneQuery()), "'")),
                buildCsvHeaderLine(requestContext, columns),
                prof -> buildCsvRow(prof.identifiers(), FluentIterable.from(columns).transformAndConcat(
                        dataColumnDescriptor -> valuesFromColumn(requestContext, prof.getExpression(dataColumnDescriptor)))
                       .toArray(String.class)));
    }

    private String[] buildCsvHeaderLine(final R requestContext, final List<DataColumnDescriptor> columns) {
        return buildCsvRow(getProfileIdColumnHeaders(requestContext),
                FluentIterable.from(columns)
                        .transformAndConcat(new Function<DataColumnDescriptor, Iterable<String>>() {
                            @Nullable
                            @Override
                            public Iterable<String> apply(@Nullable DataColumnDescriptor dataColumnDescriptor) {
                                return labelsForColumn(requestContext, dataColumnDescriptor);
                            }
                        })
                        .toArray(String.class)

        );
    }

    private String[] buildCsvRow(String[] rowHeaders, String[] values) {
        return ArrayUtils.addAll(rowHeaders, values);
    }

}
