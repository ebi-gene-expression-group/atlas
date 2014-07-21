package uk.ac.ebi.atlas.bioentity.go;

import com.google.common.collect.ImmutableMap;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
@Scope("singleton")
public class GoTermTrader {

    private ImmutableMap<String, String> goAccessionToTerm;

    @Inject
    public GoTermTrader(GoPoTermTSVReaderFactory goPoTermTSVReaderFactory) {
        goAccessionToTerm = readAll(goPoTermTSVReaderFactory);
    }

    private ImmutableMap<String, String> readAll(GoPoTermTSVReaderFactory goPoTermTSVReaderFactory) {
        try (GoPoTermTSVReader goPoTermTSVReader = goPoTermTSVReaderFactory.createGoTerms()) {

            return goPoTermTSVReader.readAll();

        } catch (IOException e) {
            throw new GoTraderException("Cannot read from " + goPoTermTSVReaderFactory.getFilePath(), e);
        }
    }

    public String getTerm(String accession) {
        return goAccessionToTerm.get(accession);
    }

    private class GoTraderException extends RuntimeException {
        public GoTraderException(String msg, IOException e) {
            super(msg, e);
        }
    }

}
