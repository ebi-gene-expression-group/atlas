package uk.ac.ebi.atlas.services;

import uk.ac.ebi.atlas.model.ExpressionLevel;

import java.util.List;

//ToDo: remove this interface, interfaces required when we have polymorphism/templating or generics
public interface ExpressionLevelService {

    public List<ExpressionLevel> getExpressionLevels();

}
