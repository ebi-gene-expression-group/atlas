package uk.ac.ebi.atlas.search.diffanalytics;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import oracle.sql.ARRAY;
import org.apache.commons.lang.StringUtils;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

// Prints a DatabaseQuery object as a SQL expression, substituting in parameters. Useful during debugging.
public class DatabaseQueryPrinter {

    public static String print(DatabaseQuery databaseQuery) {
        return substituteQuestionMarksForParameters(databaseQuery.getQuery(), parametersAsSQLExpression(databaseQuery.getParameters()));
    }

    static String substituteQuestionMarksForParameters(String string, List<String> params) {
        StringBuilder result = new StringBuilder();

        checkArgument(StringUtils.countMatches(string, "?") == params.size(), String.format("Number of question marks (%s) does not match number of params (%s)", StringUtils.countMatches(string, "?"), params.size()));

        Iterable < String > nonParameters = Splitter.on('?').split(string);
        Iterator<String> parameters = params.iterator();

        for (String nonParam : nonParameters) {
            result.append(nonParam);
            if (parameters.hasNext()) {
                result.append(parameters.next());
            }
        }

        return result.toString();
    }

    static ImmutableList<String> parametersAsSQLExpression(List<?> params) {
        ImmutableList.Builder<String> builder = ImmutableList.builder();

        for (Object param : params) {
            builder.add(parameterAsSQLExpression(param));
        }

        return builder.build();
    }

    static String parameterAsSQLExpression(Object param) {
        if (param instanceof ARRAY) {
            return oracleArrayToString((ARRAY)param);
        } else if (param instanceof String) {
            return "'" + param + "'";
        } else {
            return param.toString();
        }
    }

    static String oracleArrayToString(ARRAY array) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(getUnqualifiedArrayName(array)).append("('");

            sb.append(Joiner.on("', '").join(array.getOracleArray()));

            sb.append("')");
            return sb.toString();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //eg: return IDENTIFIERS_TABLE (and not ATLAS3DEV.IDENTIFIERS_TABLE)
    static String getUnqualifiedArrayName(ARRAY array) throws SQLException {
        return array.getDescriptor().getSQLName().getSimpleName();
    }

}
