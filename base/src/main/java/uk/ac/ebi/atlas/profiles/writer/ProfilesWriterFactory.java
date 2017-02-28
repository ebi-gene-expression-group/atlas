package uk.ac.ebi.atlas.profiles.writer;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import org.apache.commons.lang3.ArrayUtils;
import uk.ac.ebi.atlas.experimentpage.context.RequestContext;
import uk.ac.ebi.atlas.model.DescribesDataColumns;
import uk.ac.ebi.atlas.model.Expression;
import uk.ac.ebi.atlas.model.Profile;

import javax.annotation.Nullable;
import java.io.Writer;
import java.util.Collections;
import java.util.List;

public abstract class ProfilesWriterFactory<DataColumnDescriptor extends DescribesDataColumns,
        Expr extends Expression,
        Prof extends Profile<DataColumnDescriptor, Expr>,
        R extends RequestContext<DataColumnDescriptor, ?>,
        DownloadOptions extends ProfilesWriterFactory.ProfileDownloadOptions> {

    protected interface ProfileDownloadOptions {

    }

    protected abstract String getTsvFileMasthead(R requestContext, DownloadOptions profileDownloadOptions);

    protected String[] getProfileIdColumnHeaders(R requestContext, DownloadOptions profileDownloadOption) {
        return new String[]{"Gene ID", "Gene Name"};
    }

    protected Iterable<String> labelsForColumn(R requestContext, DownloadOptions profileDownloadOptions, DataColumnDescriptor dataColumnDescriptor){
        return Collections.singleton(requestContext.displayNameForColumn(dataColumnDescriptor));
    }

    protected Iterable<String> valuesFromColumn(R requestContext, DownloadOptions profileDownloadOptions, Expr expression) {
        return Collections.singleton(Double.toString(expression.getLevel()));
    }

    final protected ProfilesWriter<Prof> create(Writer responseWriter, final R requestContext, final DownloadOptions profileDownloadOptions) {
        final List<DataColumnDescriptor> columns = requestContext.getDataColumnsToReturn();
        Function<Prof, String[]> profileToLine = new Function<Prof, String[]>() {
            @Nullable
            @Override
            public String[] apply(final @Nullable Prof prof) {
                return buildCsvRow(prof.identifiers(), FluentIterable.from(columns).transformAndConcat(
                        new Function<DataColumnDescriptor, Iterable<String>>() {
                    @Nullable
                    @Override
                    public Iterable<String> apply(@Nullable DataColumnDescriptor dataColumnDescriptor) {
                        return valuesFromColumn(requestContext, profileDownloadOptions, prof.getExpression(dataColumnDescriptor));
                    }
                }).toArray(String.class));
            }
        };


        return new ProfilesWriter<>(
                responseWriter,
                getTsvFileMasthead(requestContext, profileDownloadOptions),
                buildCsvHeaderLine(requestContext, profileDownloadOptions, columns),
                profileToLine);
    }

    private String[] buildCsvHeaderLine(final R requestContext, final DownloadOptions profileDownloadOptions, final List<DataColumnDescriptor> columns) {
        return buildCsvRow(getProfileIdColumnHeaders(requestContext, profileDownloadOptions),
                FluentIterable.from(columns)
                        .transformAndConcat(new Function<DataColumnDescriptor, Iterable<String>>() {
                            @Nullable
                            @Override
                            public Iterable<String> apply(@Nullable DataColumnDescriptor dataColumnDescriptor) {
                                return labelsForColumn(requestContext, profileDownloadOptions, dataColumnDescriptor);
                            }
                        })
                        .toArray(String.class)

        );
    }

    private String[] buildCsvRow(String[] rowHeaders, String[] values) {
        return ArrayUtils.addAll(rowHeaders, values);
    }

}
