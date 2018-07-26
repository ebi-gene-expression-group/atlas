package uk.ac.ebi.atlas.dao;

import com.atlassian.util.concurrent.LazyReference;
import com.google.common.collect.ImmutableMap;
import org.springframework.jdbc.core.JdbcTemplate;
import uk.ac.ebi.atlas.model.ArrayDesign;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Named
public class ArrayDesignDAO {

    private final JdbcTemplate jdbcTemplate;

    private final LazyReference<List<ArrayDesign>> arrayDesigns = new LazyReference<List<ArrayDesign>>() {
        @Override
        protected List<ArrayDesign> create() {
            return jdbcTemplate.queryForList("SELECT * FROM ARRAYDESIGN").stream().map(
                    e -> ArrayDesign.create((String) e.get("ACCESSION"), (String)e.get("NAME") )
            ).collect(Collectors.toList());
        }
    };

    @Inject
    public ArrayDesignDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> getDesignElements(String geneIdentifier) {
        String query = "SELECT DESIGNELEMENT FROM DESIGNELEMENT_MAPPING WHERE IDENTIFIER=?";
        return jdbcTemplate.queryForList(query, new String[]{geneIdentifier}, String.class);
    }

    public ArrayDesign getArrayDesign(String accession){
        return arrayDesigns.get().stream()
                .filter(a -> a.accession().equals(accession))
                .findFirst().orElse(ArrayDesign.createForUnknownName(accession));
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
