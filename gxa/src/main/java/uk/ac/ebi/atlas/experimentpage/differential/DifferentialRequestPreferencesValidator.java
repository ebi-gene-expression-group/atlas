
package uk.ac.ebi.atlas.experimentpage.differential;

import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;
import uk.ac.ebi.atlas.web.ExperimentPageRequestPreferences;

public class DifferentialRequestPreferencesValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return MicroarrayRequestPreferences.class.equals(aClass)
                || DifferentialRequestPreferences.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        ExperimentPageRequestPreferences requestPreferences = (ExperimentPageRequestPreferences) object;
        if (requestPreferences.getCutoff() < 0 || requestPreferences.getCutoff() > 1) {
            errors.reject("cutoffOutOfRange", "Please select a False Discovery Rate cutoff that is between 0 and 1 (inclusive)");
        }
    }
}
