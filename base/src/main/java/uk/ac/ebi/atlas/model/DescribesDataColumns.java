package uk.ac.ebi.atlas.model;

import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public abstract class DescribesDataColumns {
    protected final String id;

    public DescribesDataColumns(String id){
        checkArgument(isNotBlank(id));
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public abstract Set<String> assaysAnalyzedForThisDataColumn();
}
