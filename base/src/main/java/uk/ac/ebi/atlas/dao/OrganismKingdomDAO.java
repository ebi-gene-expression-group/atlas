package uk.ac.ebi.atlas.dao;

import com.google.common.collect.ImmutableMap;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

public class OrganismKingdomDAO {

    private static final String SELECT_KINGDOM = "SELECT ORGANISM_KINGDOM.KINGDOM, BIOENTITY_ORGANISM.NAME FROM ORGANISM_KINGDOM" +
                                                   " JOIN BIOENTITY_ORGANISM ON ORGANISM_KINGDOM.ORGANISMID=BIOENTITY_ORGANISM.ORGANISMID";

    private JdbcTemplate jdbcTemplate;

    public OrganismKingdomDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ImmutableMap<String, String> getOrganismKingdomMap() {

        ImmutableMap.Builder<String, String> mapBuilder = new ImmutableMap.Builder<>();

        List<Map<String, Object>> ensemblNames = jdbcTemplate.queryForList(SELECT_KINGDOM);

        for (Map<String, Object> it : ensemblNames) {
            String name = (String) it.get("NAME");
            String kingdom = (String)it.get("KINGDOM");
            mapBuilder.put(name.toLowerCase(), kingdom);
        }

        return mapBuilder.build();
    }

}
