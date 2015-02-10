package uk.ac.ebi.atlas.bioentity.go;

import com.google.common.collect.ImmutableMap;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Map;

/**
 * Created by barrera on 21/07/2014.
 * */
@Named
@Scope("singleton")
public class PoTermTrader  {

    private ImmutableMap<String, GoPoTerm> poAccessionToTerm;

    @Inject
    public PoTermTrader(GoPoTermTSVReaderFactory goPoTermTSVReaderFactory) {
        readAll(goPoTermTSVReaderFactory);
    }

    private void readAll(GoPoTermTSVReaderFactory goPoTermTSVReaderFactory) {
        try (GoPoTermTSVReader goPoTermTSVReader = goPoTermTSVReaderFactory.createPoTerms()) {

            goPoTermTSVReader.readAll();

            poAccessionToTerm = goPoTermTSVReader.getAccessionToTermMap();

        } catch (IOException e) {
            throw new PoTraderException("Cannot read from " + goPoTermTSVReaderFactory.getFilePath(), e);
        }
    }

    public String getTermName(String accession) {
        return poAccessionToTerm.get(accession).name();
    }

    public int getDepth(String accession) {
        return poAccessionToTerm.get(accession).depth();
    }

    public GoPoTerm getTerm(String accession) {
        return poAccessionToTerm.get(accession);
    }

    private class PoTraderException extends RuntimeException {
        public PoTraderException(String msg, IOException e) {
            super(msg, e);
        }
    }

}