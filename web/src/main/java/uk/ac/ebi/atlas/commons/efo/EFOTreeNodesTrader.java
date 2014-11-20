package uk.ac.ebi.atlas.commons.efo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.arrayexpress.utils.efo.EFOLoader;
import uk.ac.ebi.arrayexpress.utils.efo.EFONode;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

@Named
@Scope("singleton")
public class EFOTreeNodesTrader {

    private Map<String, EFONode> urlToEFONode;

    @Inject
    public EFOTreeNodesTrader(@Value("#{configuration['efo.owl.file']}") String efoOwlFilePath, EFOLoader efoLoader) {
        try {
            urlToEFONode = efoLoader.load(new FileInputStream(efoOwlFilePath)).getMap();
        } catch (FileNotFoundException e) {
            throw new EFOTreeNodesTraderException(e);
        }
    }

    public Map<String, EFONode> getTreeNodes() {
        return urlToEFONode;
    }

    private class EFOTreeNodesTraderException extends RuntimeException {
        public EFOTreeNodesTraderException(Throwable e) {
            super(e);
        }
    }

}
