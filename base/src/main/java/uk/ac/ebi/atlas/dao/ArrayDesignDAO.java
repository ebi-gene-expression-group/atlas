package uk.ac.ebi.atlas.dao;

import com.google.common.collect.ImmutableMap;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Named
public class ArrayDesignDAO {

    private JdbcTemplate jdbcTemplate;

    @Inject
    public ArrayDesignDAO(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<String> getDesignElements(String geneIdentifier) {
        String query = "SELECT DESIGNELEMENT FROM DESIGNELEMENT_MAPPING WHERE IDENTIFIER=?";
        return jdbcTemplate.queryForList(query, new String[]{geneIdentifier}, String.class);
    }

    public Map<String, String> getArrayDesignMapNames() {
        ImmutableMap.Builder<String, String> mapBuilder = new ImmutableMap.Builder<>();

        String query = "SELECT * FROM ARRAYDESIGN";
        List<Map<String, Object>> allArrayDesigns = jdbcTemplate.queryForList(query);

        for (Map<String, Object> arrayDesign : allArrayDesigns) {
            String accession = (String) arrayDesign.get("ACCESSION");
            String name = (String)arrayDesign.get("NAME");
            mapBuilder.put(accession, name);

        }
        return mapBuilder.build();
    }

}
