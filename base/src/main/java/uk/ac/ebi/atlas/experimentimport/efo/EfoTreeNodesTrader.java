package uk.ac.ebi.atlas.experimentimport.efo;

import com.atlassian.util.concurrent.LazyReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.arrayexpress.utils.efo.EFOLoader;
import uk.ac.ebi.arrayexpress.utils.efo.EFONode;

import javax.inject.Named;
import java.net.URL;
import java.util.Map;

@Named
public class EfoTreeNodesTrader {
    private static final Logger LOGGER = LoggerFactory.getLogger(EfoTreeNodesTrader.class);
    private static final String EFO_OWL_FILE_URL = "https://www.ebi.ac.uk/efo/efo.owl";

    // efoLoader.load is not thread-safe so we serialize concurrent access using a LazyReference
    private final LazyReference<Map<String, EFONode>> urlToEFONode = new LazyReference<Map<String, EFONode>>() {
        @Override
        protected Map<String, EFONode> create() throws Exception {
            EFOLoader efoLoader = new EFOLoader();
            LOGGER.debug("Loading {}...", EFO_OWL_FILE_URL);
            URL efoOwlFileUrl = new URL(EFO_OWL_FILE_URL);
            Map<String, EFONode> urlToEFONodeResult = efoLoader.load(efoOwlFileUrl.openStream()).getMap();
            LOGGER.debug("Loading finished");
            return urlToEFONodeResult;
        }
    };

    public Map<String, EFONode> getTreeNodes() {
        return urlToEFONode.get();
    }
}
