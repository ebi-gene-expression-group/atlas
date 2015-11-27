package uk.ac.ebi.atlas.commons.efo;

import com.atlassian.util.concurrent.LazyReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.arrayexpress.utils.efo.EFOLoader;
import uk.ac.ebi.arrayexpress.utils.efo.EFONode;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.FileInputStream;
import java.util.Map;

@Named
@Scope("singleton")
public class EFOTreeNodesTrader {

    private static final Logger LOGGER = LoggerFactory.getLogger(EFOTreeNodesTrader.class);

    private String efoOwlFilePath;

    // efoLoader.load is not thread-safe so we serialize concurrent access using a LazyReference
    private LazyReference<Map<String, EFONode>> urlToEFONode = new LazyReference<Map<String, EFONode>>() {
        @Override
        protected Map<String, EFONode> create() throws Exception {
            EFOLoader efoLoader = new EFOLoader();
            LOGGER.debug("load {}", efoOwlFilePath);
            Map<String, EFONode> urlToEFONode = efoLoader.load(new FileInputStream(efoOwlFilePath)).getMap();
            LOGGER.debug("load done");
            return urlToEFONode;
        }
    };

    @Inject
    public EFOTreeNodesTrader(@Value("#{configuration['efo.owl.file']}") String efoOwlFilePath) {
        this.efoOwlFilePath = efoOwlFilePath;
    }

    public Map<String, EFONode> getTreeNodes() {
        return urlToEFONode.get();
    }

}
