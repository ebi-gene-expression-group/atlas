package uk.ac.ebi.atlas.geneannotation;

import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Named
@Scope("singleton")
public class BioEntityAnnotationDao extends AnnotationDao{

    private static final Logger LOGGER = Logger.getLogger(BioEntityAnnotationDao.class);

    private JdbcTemplate jdbcTemplate;

    @Inject
    public BioEntityAnnotationDao(@Qualifier("dataSource") DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void saveAnnotations(final Map<String, String> annotations, final String organism, final String type) {

        String query = "MERGE INTO bioentity_name(identifier, name, organism, type) KEY(identifier) VALUES(?, ?, ?, ?)";

        final ArrayList<String> keys = Lists.newArrayList(annotations.keySet());
        BatchPreparedStatementSetter statementSetter = new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, keys.get(i));
                ps.setString(2, annotations.get(keys.get(i)));
                ps.setString(3, organism);
                ps.setString(4, type);
            }

            @Override
            public int getBatchSize() {
                return annotations.size();
            }
        };

        int[] rows = jdbcTemplate.batchUpdate(query, statementSetter);
        LOGGER.info("Updated " + rows.length + " bioentities");
    }

    public String getName(String identifier) {
        String query = "select name from bioentity_name where identifier=?";

        List<String> names = jdbcTemplate.queryForList(query, new String[]{identifier}, String.class);

        return getOnly(names);
    }

}
