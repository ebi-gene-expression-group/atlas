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
public class ArrayDesignDao extends AnnotationDao{

    private static final Logger LOGGER = Logger.getLogger(ArrayDesignDao.class);

    private static final int SUB_BATCH_SIZE = 100;
    private JdbcTemplate jdbcTemplate;

    @Inject
    public ArrayDesignDao(@Qualifier("annotationDataSource") DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void saveMappings(final Map<String, String> annotations, final String arrayDesign) {

        String query = "INSERT INTO designelement_mapping(designelement, identifier, arraydesign) VALUES(?, ?, ?)";

        final ArrayList<String> keys = Lists.newArrayList(annotations.keySet());
        BatchPreparedStatementSetter statementSetter = new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, keys.get(i));
                ps.setString(2, annotations.get(keys.get(i)));
                ps.setString(3, arrayDesign);
            }

            @Override
            public int getBatchSize() {
                return annotations.size();
            }
        };

        int[] rows = jdbcTemplate.batchUpdate(query, statementSetter);
        LOGGER.info("Updated " + rows.length + " bioentities");
    }

    public void deleteMappings(String arrayDesign) {
        String query = "delete from designelement_mapping where arraydesign=?";

        jdbcTemplate.update(query, new String[]{arrayDesign});
    }

    public String getIdentifier(String designElement, String arrayDesign) {
        String query = "select identifier from designelement_mapping where designelement=? and arraydesign=?";

        List<String> names = jdbcTemplate.queryForList(query, new String[]{designElement, arrayDesign}, String.class);

        return getOnly(names);
    }

    public boolean isArrayDesignPresent(String arrayDesign) {
        String query = "select count(*) from designelement_mapping where arraydesign=?";

        int count = jdbcTemplate.queryForObject(query, new String[]{arrayDesign}, Integer.class);

        return count > 0;
    }

}
