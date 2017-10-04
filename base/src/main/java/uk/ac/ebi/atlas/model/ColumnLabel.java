package uk.ac.ebi.atlas.model;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

public class ColumnLabel extends DescribesDataColumns {

    public ColumnLabel(String id){
        super(id);
    }
    @Override
    public Set<String> assaysAnalyzedForThisDataColumn() {
        return ImmutableSet.of();
    }
}
