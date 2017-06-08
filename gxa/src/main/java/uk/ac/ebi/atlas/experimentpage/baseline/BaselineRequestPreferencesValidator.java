package uk.ac.ebi.atlas.experimentpage.baseline;

import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.ProteomicsBaselineRequestPreferences;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import uk.ac.ebi.atlas.web.ExperimentPageRequestPreferences;
import uk.ac.ebi.atlas.web.RnaSeqBaselineRequestPreferences;

public class BaselineRequestPreferencesValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        if (aClass.isAssignableFrom(RnaSeqBaselineRequestPreferences.class)) {
            return RnaSeqBaselineRequestPreferences.class.equals(aClass);
        }

        if (aClass.isAssignableFrom(ProteomicsBaselineRequestPreferences.class)) {
            return ProteomicsBaselineRequestPreferences.class.equals(aClass);
        }

        return BaselineRequestPreferences.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        ExperimentPageRequestPreferences requestPreferences = (ExperimentPageRequestPreferences) object;
        if (requestPreferences.getCutoff() < 0 ) {
            errors.reject("cutoffOutOfRange", "The expression level cutoff must be greater than 0");
        }
    }

}
