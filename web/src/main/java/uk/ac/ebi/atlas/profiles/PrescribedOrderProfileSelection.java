package uk.ac.ebi.atlas.profiles;

import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.Profile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class PrescribedOrderProfileSelection<T extends Profile, L extends GeneProfilesList<T>> implements SelectProfiles<T,L>{

    private final List<String> geneNamesInOrder;
    private final GeneProfilesListBuilder<L> geneProfilesListBuilder;

    public PrescribedOrderProfileSelection(List<String> geneNamesInOrder,GeneProfilesListBuilder<L> geneProfilesListBuilder){
        this.geneNamesInOrder = geneNamesInOrder;
        this.geneProfilesListBuilder = geneProfilesListBuilder;
    }


    @Override
    public L select(Iterable<T> profiles, int maxSize) {
        List<LinkedList<T>> ans = new ArrayList<>(geneNamesInOrder.size());
        for(String _ : geneNamesInOrder){
            ans.add(new LinkedList<T>());
        }
        for(T profile: profiles){
            int pos = geneNamesInOrder.indexOf(profile.getName());
            if(pos >-1){
                LinkedList<T> l = ans.get(pos);
                l.addLast(profile);
            }

        }

        L result = geneProfilesListBuilder.create();
        for(LinkedList<T> l : ans){
            result.addAll(l);
        }



        return result;
    }
}
