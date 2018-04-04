package uk.ac.ebi.atlas.profiles.writer;

import org.apache.commons.lang3.ArrayUtils;
import uk.ac.ebi.atlas.experimentpage.context.RequestContext;
import uk.ac.ebi.atlas.model.DescribesDataColumns;
import uk.ac.ebi.atlas.model.Expression;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.search.SearchDescription;

import javax.annotation.Nullable;
import java.io.Writer;
import java.util.List;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.wrap;

public abstract class ProfilesWriterFactory<D extends DescribesDataColumns,
                                            E extends Expression,
                                            P extends Profile<D, E, P>,
                                            R extends RequestContext<D, ?, ?, ?>> {

    protected abstract String getTsvFileMasthead(R requestContext, String queryDescription);

    protected String[] getProfileIdColumnHeaders(R requestContext) {
        return new String[]{"Gene ID", "Gene Name"};
    }

    protected Stream<String> labelsForColumn(R requestContext, D dataColumnDescriptor){
        return Stream.of(requestContext.displayNameForColumn(dataColumnDescriptor));
    }

    protected Stream<String> valuesFromColumn(R requestContext, @Nullable E expression) {
        return Stream.of(expression == null ? "" : Double.toString(expression.getLevel()));
    }

    public final ProfilesWriter<P> create(Writer responseWriter, final R requestContext) {
        final List<D> columns = requestContext.getDataColumnsToReturn();
        return new ProfilesWriter<>(
                responseWriter,
                getTsvFileMasthead(requestContext, wrap(SearchDescription.get(requestContext.getGeneQuery()), "'")),
                buildCsvHeaderLine(requestContext, columns),
                prof ->
                        buildCsvRow(
                                prof.identifiers(),
                                columns.stream()
                                       .flatMap(
                                               dataColumnDescriptor ->
                                                       valuesFromColumn(
                                                               requestContext,
                                                               prof.getExpression(dataColumnDescriptor)))
                                       .toArray(String[]::new))
        );
    }

    private String[] buildCsvHeaderLine(final R requestContext, final List<D> columns) {
        return buildCsvRow(
                getProfileIdColumnHeaders(requestContext),
                columns.stream()
                       .flatMap(dataColumnDescriptor -> labelsForColumn(requestContext, dataColumnDescriptor))
                       .toArray(String[]::new));
    }

    private String[] buildCsvRow(String[] rowHeaders, String[] values) {
        return ArrayUtils.addAll(rowHeaders, values);
    }

}
