package uk.ac.ebi.atlas.solr.query;

import com.google.common.base.Joiner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class SuggestionServiceIT {

    public static final String[] G_PROTEIN_SUGGESTIONS = new String[]{"bone Gla protein", "G protein-coupled receptor", "Gla-Rich Protein 1", "Gla-Rich Protein 2", "Ras GTPase-activating protein III", "lacrimal gland protein", "leptin receptor gene-related protein", "myeloid granule protein", "neuroendocrine-specific Golgi protein p55 isoform 1", "G-protein coupled receptor signaling pathway", "G-protein coupled receptor activity", "G protein-coupled receptor, rhodopsin-like", "G-protein beta WD-40 repeat", "G-protein beta/gamma-subunit complex binding", "G-protein gamma-like domain"};

    @Inject
    private SuggestionService suggestionService;

    @Test
    public void apop() {
        List<String> suggestions = suggestionService.fetchTopSuggestions("apop", null);
        System.out.println(Joiner.on(",\n").join(suggestions));
        assertThat(suggestions, hasItem("apoptotic process"));
    }

    @Test
    public void gSpaceProtein() {
        List<String> suggestions = suggestionService.fetchTopSuggestions("G protein", null);
        //System.out.println(Joiner.on("\", \"").join(suggestions));
        assertThat(suggestions, contains(G_PROTEIN_SUGGESTIONS));
    }

    @Test
    public void gHyphenProtein() {
        List<String> suggestions = suggestionService.fetchTopSuggestions("G-protein", null);
        //System.out.println(Joiner.on(",\n").join(suggestions));
        assertThat(suggestions, hasItem("G-protein coupled receptor signaling pathway"));
    }

}
