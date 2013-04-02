package uk.ac.ebi.atlas.commands.context;

import com.google.common.base.Objects;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.Regulation;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;

public class DifferentialRequestContext extends RequestContext<Contrast, DifferentialRequestPreferences> {
    private Regulation regulation;

    @Override
    public void setRequestPreferences(DifferentialRequestPreferences requestPreferences){
        this.regulation = requestPreferences.getRegulation();
        super.setRequestPreferences(requestPreferences);
    }

    public Regulation getRegulation() {
        return regulation;
    }

    public void setRegulation(Regulation regulation) {
        this.regulation = regulation;
    }

    @Override
    public String toString(){
        return Objects.toStringHelper(this.getClass())
                .add("regulation", regulation).toString();
    }
}
