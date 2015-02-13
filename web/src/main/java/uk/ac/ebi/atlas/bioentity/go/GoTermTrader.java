package uk.ac.ebi.atlas.bioentity.go;

import com.google.common.collect.ImmutableMap;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
@Scope("singleton")
public class GoTermTrader {

    private ImmutableMap<String, GoPoTerm> goAccessionToTerm;

    @Inject
    public GoTermTrader(GoPoTermTSVReaderFactory goPoTermTSVReaderFactory) {
        readAll(goPoTermTSVReaderFactory);
    }

    private void readAll(GoPoTermTSVReaderFactory goPoTermTSVReaderFactory) {
        try (GoPoTermTSVReader goPoTermTSVReader = goPoTermTSVReaderFactory.createGoTerms()) {

            goPoTermTSVReader.readAll();

            goAccessionToTerm = goPoTermTSVReader.getAccessionToTermMap();

        } catch (IOException e) {
            throw new GoTraderException("Cannot read from " + goPoTermTSVReaderFactory.getFilePath(), e);
        }
    }

    public String getTermName(String accession) {
        return goAccessionToTerm.get(accession).name();
    }

    public int getDepth(String accession) {
        return goAccessionToTerm.get(accession).depth();
    }

    public GoPoTerm getTerm(String accession) {
        return goAccessionToTerm.get(accession);
    }

    private class GoTraderException extends RuntimeException {
        public GoTraderException(String msg, IOException e) {
            super(msg, e);
        }
    }

}
