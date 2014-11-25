package uk.ac.ebi.atlas.utils;

import uk.ac.ebi.atlas.model.OntologyTerm;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public class OntologyTermUtils {

    private static final String ONTOLOGY_TERM_ID_METHOD = "id";
    private static final String ONTOLOGY_TERM_SOURCE_METHOD = "source";
    private static final String ONTOLOGY_TERM_URI_METHOD = "uri";


    public static final String ONTOLOGY_TERM_DELIMITER = " ";

    public static String joinSources(Set<OntologyTerm> ontologyTerms) {
        return delegator(ontologyTerms, ONTOLOGY_TERM_SOURCE_METHOD);
    }

    public static String joinIds(Set<OntologyTerm> ontologyTerms) {
        return delegator(ontologyTerms, ONTOLOGY_TERM_ID_METHOD);
    }

    public static String joinURIs(Set<OntologyTerm> ontologyTerms) {
        return delegator(ontologyTerms, ONTOLOGY_TERM_URI_METHOD);
    }


    private static String delegator(Set<OntologyTerm> ontologyTerms, String methodId) {
        try {
            return joiner(ontologyTerms, OntologyTerm.class.getMethod(methodId));
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return "";
    }

    private static String joiner(Set<OntologyTerm> ontologyTerms, Method ontologyTermMethod) throws InvocationTargetException, IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        for (OntologyTerm ontologyTerm : ontologyTerms) {
            sb.append(ontologyTermMethod.invoke(ontologyTerm) + ONTOLOGY_TERM_DELIMITER);
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }
}
