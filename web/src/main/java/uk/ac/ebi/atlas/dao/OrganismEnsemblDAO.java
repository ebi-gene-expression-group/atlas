package uk.ac.ebi.atlas.dao;


import com.google.common.collect.ImmutableMap;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

@Named
@Scope("singleton")
public class OrganismEnsemblDAO {

    private static final Logger LOGGER = Logger.getLogger(OrganismEnsemblDAO.class);

    private static final String SELECT_ENSEMBLDB = "SELECT ORGANISM_ENSEMBLDB.ENSEMBLDB, BIOENTITY_ORGANISM.NAME FROM ORGANISM_ENSEMBLDB" +
                                                   " JOIN BIOENTITY_ORGANISM ON ORGANISM_ENSEMBLDB.ORGANISMID=BIOENTITY_ORGANISM.ORGANISMID";

    private JdbcTemplate jdbcTemplate;

    @Inject
    public OrganismEnsemblDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Map<String, String> getEnsemblOrganismNamesMap() {

        ImmutableMap.Builder<String, String> mapBuilder = new ImmutableMap.Builder();

        List<Map<String, Object>> ensemblNames = jdbcTemplate.queryForList(SELECT_ENSEMBLDB);

        for (Map<String, Object> it : ensemblNames) {
            String name = (String) it.get("NAME");
            String ensembldb = (String)it.get("ENSEMBLDB");
            mapBuilder.put(name, ensembldb);
        }

        return mapBuilder.build();
    }

}
