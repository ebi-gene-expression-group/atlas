package uk.ac.ebi.atlas.geneannotation;

import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import uk.ac.ebi.atlas.geneannotation.mirna.MiRNAEntity;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Named
@Scope("singleton")
public class BioEntityAnnotationDao extends AnnotationDao {

    private static final Logger LOGGER = Logger.getLogger(BioEntityAnnotationDao.class);
    private static final int BATCH_SIZE = 1000;
    private static final String INSERT_QUERY = "INSERT INTO bioentity_name(identifier, name, organism, type) VALUES(?, ?, ?, ?)";
    private static final String DELETE_QUERY = "DELETE FROM bioentity_name WHERE organism = ? AND type = ?";
    private static final String SELECT_QUERY = "SELECT name FROM bioentity_name WHERE identifier = ?";
    private static final int IDENTIFIER_INDEX = 1;
    private static final int NAME_INDEX = 2;
    private static final int ORGANISM_INDEX = 3;
    private static final int TYPE_INDEX = 4;

    private JdbcTemplate jdbcTemplate;

    @Inject
    public BioEntityAnnotationDao(@Qualifier("dataSource") DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void saveAnnotations(Map<String, String> annotations, String organism, String type) {

        List<Map.Entry<String, String>> annotationEntries = Lists.newArrayList(annotations.entrySet());

        List<List<Map.Entry<String, String>>> partitionsOfEntries = Lists.partition(annotationEntries, BATCH_SIZE);

        int total = 0;
        for (List<Map.Entry<String, String>> partition : partitionsOfEntries) {
            int[] rows = performBatchStatement(partition, organism, type);
            total += rows.length;
        }

        LOGGER.info("Updated " + total + " bioentities");
    }

    private int[] performBatchStatement(final List<Map.Entry<String, String>> annotationEntries, final String organism, final String type) {

        BatchPreparedStatementSetter statementSetter = new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(IDENTIFIER_INDEX, annotationEntries.get(i).getKey());
                ps.setString(NAME_INDEX, annotationEntries.get(i).getValue());
                ps.setString(ORGANISM_INDEX, organism);
                ps.setString(TYPE_INDEX, type);
            }

            @Override
            public int getBatchSize() {
                return annotationEntries.size();
            }
        };

        return jdbcTemplate.batchUpdate(INSERT_QUERY, statementSetter);
    }

    public int saveMiRnaAnnotations(List<MiRNAEntity> entities) {


        List<List<MiRNAEntity>> partitionsOfEntries = Lists.partition(entities, BATCH_SIZE);

        int total = 0;
        for (List<MiRNAEntity> partition : partitionsOfEntries) {
            int[] rows = performBatchStatement(partition);
            total += rows.length;
        }

        LOGGER.info("Updated " + total + " bioentities");
        return total;
    }

    private int[] performBatchStatement(final List<MiRNAEntity> entities) {

        BatchPreparedStatementSetter statementSetter = new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(IDENTIFIER_INDEX, entities.get(i).getIdentifier());
                ps.setString(NAME_INDEX, entities.get(i).getAccession());
                ps.setString(ORGANISM_INDEX, entities.get(i).getOrganism());
                ps.setString(TYPE_INDEX, BioEntityType.MIRNA.getName());
            }

            @Override
            public int getBatchSize() {
                return entities.size();
            }
        };

        return jdbcTemplate.batchUpdate(INSERT_QUERY, statementSetter);
    }


    public int deleteAnnotations(String organism, String type) {
        return jdbcTemplate.update(DELETE_QUERY, new Object[]{organism, type});
    }

    public int deleteAnnotations(String type) {
        String query = "DELETE FROM bioentity_name WHERE  type = ?";
        return jdbcTemplate.update(query, new Object[]{type});
    }

    public String getName(String identifier) {

        List<String> names = jdbcTemplate.queryForList(SELECT_QUERY, new String[]{identifier}, String.class);

        return getOnly(names);
    }

}
