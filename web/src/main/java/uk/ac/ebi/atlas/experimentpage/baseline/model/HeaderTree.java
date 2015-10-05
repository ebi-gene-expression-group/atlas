package uk.ac.ebi.atlas.experimentpage.baseline.model;

import java.util.ArrayList;
import java.util.List;

public class HeaderTree {

    private List<HeaderTreeNode> children;

    public HeaderTree() {
    }

    public void addChild(HeaderTreeNode child){
        if(child!=null) {
            if(children==null) children = new ArrayList<>();
            this.children.add(child);
        }
    }

    public List<HeaderTreeNode> getChildren() {
        return children;
    }

    public HeaderTree setCounters(){
        if(children!=null){
            for (HeaderTreeNode child : children) {
                child.setCounters();
            }
        }
        return this;
    }
}
