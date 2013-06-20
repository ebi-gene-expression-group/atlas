package uk.ac.ebi.atlas.geneannotation;

import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

@Named
@Scope("singleton")
public class BioEntityAnnotationDao {

    private static final Logger LOGGER = Logger.getLogger(BioEntityAnnotationDao.class);

    private static final int SUB_BATCH_SIZE = 100;
    private JdbcTemplate jdbcTemplate;

    private final static String BIOENTITY_NAME_MERGE = "MERGE INTO bioentity_name(identifier, name, organism) KEY(identifier) VALUES(?, ?, ?)";

    @Inject
    public BioEntityAnnotationDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void saveAnnotations(final Map<String, String> annotations, final String organism) {

        final ArrayList<String> keys = Lists.newArrayList(annotations.keySet());
        BatchPreparedStatementSetter statementSetter = new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, keys.get(i));
                ps.setString(2, annotations.get(keys.get(i)));
                ps.setString(3, organism);
            }

            @Override
            public int getBatchSize() {
                return annotations.size();
            }
        };

        int[] rows = jdbcTemplate.batchUpdate(BIOENTITY_NAME_MERGE, statementSetter);
        LOGGER.info("Updated " + rows.length + " bioentities");

    }

//    public void writeBioEntities(final Collection<BioEntity> bioEntities, final int batchSize) {
//
//        BatchPreparedStatementSetter statementSetter = new BatchPreparedStatementSetter() {
//            @Override
//            public void setValues(PreparedStatement ps, int i) throws SQLException {
//
//            }
//
//            @Override
//            public int getBatchSize() {
//                return 0;
//            }
//        }
//
//        ListStatementSetter<BioEntity> statementSetter = new ListStatementSetter<BioEntity>() {
//
//            public void setValues(PreparedStatement ps, int i) throws SQLException {
//                ps.setString(1, list.get(i).getIdentifier());
//                ps.setLong(2, list.get(i).getType().getId());
//                ps.setString(3, list.get(i).getIdentifier());
//                ps.setString(4, list.get(i).getName());
//                ps.setLong(5, list.get(i).getOrganism().getId());
//                ps.setLong(6, list.get(i).getType().getId());
//            }
//
//        };
//
//        int loadedRecordsNumber = writeBatchInChunks(query, bioEntities, statementSetter, batchSize);
//        LOGGER.info("BioEntities merged: " + loadedRecordsNumber);
//
//    }
//
//    private <T> int writeBatchInChunks(String query,
//                                       final Map<String, String> entityList,
//                                       ListStatementSetter<T> statementSetter,
//                                       int batchSize) throws DataAccessException {
//        int loadedRecordsNumber = 0;
//
//        int subBatchSize = batchSize != 0 ? batchSize : SUB_BATCH_SIZE;
//
//        entityList.
//        for (partition(entityList, subBatchSize)) {
//            statementSetter.setList(subList);
//            int[] rowsAffectedArray = jdbcTemplate.batchUpdate(query, statementSetter);
//            loadedRecordsNumber += rowsAffectedArray.length;
//        }
//
//        return loadedRecordsNumber;
//    }
//
//    private abstract static class ListStatementSetter<T> implements BatchPreparedStatementSetter {
//        List<T> list;
//
//        public int getBatchSize() {
//            return list.size();
//        }
//
//        public void setList(List<T> list) {
//            this.list = list;
//        }
//    }
}
