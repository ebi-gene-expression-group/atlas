package uk.ac.ebi.atlas.model;

import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

public class TranscriptExpressionsList extends ArrayList<TranscriptExpression>{

    public TranscriptExpressionsList(){
        super();
    }

    public TranscriptExpressionsList(Collection collection){
        super(collection);
    }

    public TranscriptExpressionsList getTop(int size){
        return subList(0, size);
    }

    public Double getExpressionLevel(String transcriptId, String organismPart){
        for (TranscriptExpression transcriptExpression: this) {
            if (checkNotNull(transcriptId).equalsIgnoreCase(transcriptExpression.getTranscriptId())
                && checkNotNull(organismPart).equalsIgnoreCase(transcriptExpression.getOrganismPart())){
                return transcriptExpression.getLevel();
            }
        }
        return null;
    }

    @Override
    public TranscriptExpressionsList subList(int fromIndex, int toIndex){
        if (toIndex > this.size()){
            return this;
        }
        return new TranscriptExpressionsList(super.subList(fromIndex, toIndex));
    }

    public Set<String> getDistinctTranscriptIds(){
        Set<String> transcriptIds = new LinkedHashSet<String>();
        for (TranscriptExpression transcriptExpression: this){
            transcriptIds.add(transcriptExpression.getTranscriptId());
        }
        return transcriptIds;
    }

    public SortedSet<String> getDistinctOrganismParts(Set<String> transcriptIds){
        SortedSet<String> organismParts = new TreeSet<>();

        for (TranscriptExpression transcriptExpression : this) {
            if (transcriptIds.contains(transcriptExpression.getTranscriptId())){
                organismParts.add(transcriptExpression.getOrganismPart());
            }
        }
        return organismParts;
    }

    public Double getMaxExpressionLevel(){
        Double maxExpressionLevel = null;
        for (TranscriptExpression expression: this){
            if (maxExpressionLevel == null || expression.getLevel() > maxExpressionLevel){
                maxExpressionLevel = expression.getLevel();
            }
        }
        return maxExpressionLevel;
    }

    public Double getMinExpressionLevel(){
        Double minExpressionLevel = null;
        for (TranscriptExpression expression: this){
            if (minExpressionLevel == null || expression.getLevel() < minExpressionLevel){
                minExpressionLevel = expression.getLevel();
            }
        }
        return minExpressionLevel;
    }



}
