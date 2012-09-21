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

    public Double getRpkmValue(String transcriptId, String organismPart){
        for (TranscriptExpression transcriptExpression: this) {
            if (checkNotNull(transcriptId).equalsIgnoreCase(transcriptExpression.getTranscriptId())
                && checkNotNull(organismPart).equalsIgnoreCase(transcriptExpression.getOrganismPart())){
                return transcriptExpression.getRpkm();
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

    public Double getMaxFpkm(){
        Double maxRpkm = null;
        for (TranscriptExpression expression: this){
            if (maxRpkm == null || expression.getRpkm() > maxRpkm){
                maxRpkm = expression.getRpkm();
            }
        }
        return maxRpkm;
    }

    public Double getMinFpkm(){
        Double minRpkm = null;
        for (TranscriptExpression expression: this){
            if (minRpkm == null || expression.getRpkm() < minRpkm){
                minRpkm = expression.getRpkm();
            }
        }
        return minRpkm;
    }



}
