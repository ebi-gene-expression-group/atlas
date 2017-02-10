package uk.ac.ebi.atlas.model;

import org.apache.commons.lang3.StringUtils;

import static com.google.common.base.Preconditions.checkArgument;

public abstract class DescribesDataColumns {
    protected final String id;

    public DescribesDataColumns(String id){
        checkArgument(StringUtils.isNotBlank(id));
        this.id = id;
    }

    public String getId() {
        return id;
    }

}
