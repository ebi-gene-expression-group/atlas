package uk.ac.ebi.atlas.profiles;

import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.Profile;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PrescribedOrderProfileSelection<T extends Profile, L extends GeneProfilesList<T>> implements SelectProfiles<T, L> {

    private final List<String> geneNamesInOrder;
    private final GeneProfilesListBuilder<L> geneProfilesListBuilder;

    public PrescribedOrderProfileSelection(List<String> geneNamesInOrder, GeneProfilesListBuilder<L> geneProfilesListBuilder) {
        this.geneNamesInOrder = geneNamesInOrder;
        this.geneProfilesListBuilder = geneProfilesListBuilder;
    }


    @Override
    public L select(ObjectInputStream<T> profiles, int maxSize) {
        List<LinkedList<T>> ans = new ArrayList<>(geneNamesInOrder.size());
        for (String s : geneNamesInOrder) {
            ans.add(new LinkedList<>());
        }
        for (T profile : new IterableObjectInputStream<>(profiles)) {
            int pos = geneNamesInOrder.indexOf(profile.getId());
            if (pos > -1) {
                LinkedList<T> l = ans.get(pos);
                l.addLast(profile);
            }

        }

        L result = geneProfilesListBuilder.get();

        int i = 0;
        loop:
        for (LinkedList<T> l : ans) {
            for (T el : l) {
                if (i >= maxSize) {
                    break loop;
                } else {
                    result.add(el);
                    i++;
                }
            }
        }

        return result;
    }
}
