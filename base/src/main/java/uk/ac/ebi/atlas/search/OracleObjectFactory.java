package uk.ac.ebi.atlas.search;

import oracle.jdbc.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import uk.ac.ebi.atlas.solr.query.conditions.IndexedAssayGroup;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

@Named
public class OracleObjectFactory {

    private final JdbcTemplate jdbcTemplate;

    @Inject
    public OracleObjectFactory(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public oracle.sql.ARRAY createOracleArrayForIdentifiers(Collection<String> geneIds) {
        final String[] ids = geneIds.toArray(new String[geneIds.size()]);

        return jdbcTemplate.execute(new ConnectionCallback<ARRAY>() {
            public oracle.sql.ARRAY doInConnection(Connection connection) throws SQLException, DataAccessException {
                OracleConnection oracleConnection = connection.unwrap(OracleConnection.class);
                ArrayDescriptor descriptor = ArrayDescriptor.createDescriptor("IDENTIFIERS_TABLE", oracleConnection);
                return new oracle.sql.ARRAY(descriptor, oracleConnection, ids);
            }
        });
    }

    public oracle.sql.ARRAY createOracleArrayForIndexedAssayGroup(final Collection<IndexedAssayGroup> indexedAssayGroups) {

        return jdbcTemplate.execute(new ConnectionCallback<oracle.sql.ARRAY>() {
            public oracle.sql.ARRAY doInConnection(Connection connection) throws SQLException, DataAccessException {
                OracleConnection oracleConnection = connection.unwrap(OracleConnection.class);

                StructDescriptor exprContrastDescriptor = StructDescriptor.createDescriptor("EXPR_CONTRAST", oracleConnection);

                STRUCT[] exprContrasts = new STRUCT[indexedAssayGroups.size()];

                int i = 0;
                for (IndexedAssayGroup iag : indexedAssayGroups) {
                    exprContrasts[i++] = new STRUCT(exprContrastDescriptor, oracleConnection, new Object[]{iag.getExperimentAccession(), iag.getAssayGroupOrContrastId()});
                }

                ArrayDescriptor descriptor = ArrayDescriptor.createDescriptor("EXPR_CONTRAST_TABLE", oracleConnection);
                return new oracle.sql.ARRAY(descriptor, oracleConnection, exprContrasts);
            }
        });
    }

}
