package uk.ac.ebi.atlas.experimentpage.baseline.model;

import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.profiles.baseline.viewmodel.AssayGroupFactorViewModel;

import java.util.ArrayList;
import java.util.List;

public class HeaderTreeNode {

    String name;
    List<HeaderTreeNode> children;
    Integer colSpan;
    ImmutableList<AssayGroupFactorViewModel> cellLines;

    public HeaderTreeNode(String name) {
        this.name = name;
    }

    public HeaderTreeNode(ImmutableList<AssayGroupFactorViewModel> cellLines) {
        this.cellLines = cellLines;
    }

    boolean isLeaf(){
        return children==null || children.isEmpty();
    }

    int getLeafs(){
        if(isLeaf()) return cellLines.size();
        int leafs = 0;
        for (HeaderTreeNode child : children) {
            leafs += child.getLeafs();
        }
        return leafs;

    }

    public void addChild(HeaderTreeNode child) {
        if(child!=null) {
            if (this.children == null) this.children = new ArrayList<>();
            this.children.add(child);
        }
    }

    //Rather than doing it every time a child is added to the tree, we
    //execute this method at the end so the tree is traversed only once
    public void setCounters(){
        Integer leafs = getLeafs();
        if(leafs > 1) this.colSpan = leafs; //Only set colSpan if it makes sense
        if(children!=null){
            for (HeaderTreeNode child : children) {
                child.setCounters();
            }
        }
    }
}
