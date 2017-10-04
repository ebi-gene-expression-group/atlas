package uk.ac.ebi.atlas.model;

import java.text.MessageFormat;
import java.util.Objects;
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

    public abstract Set<BiologicalReplicate> biologicalReplicatesForThisDataColumn();

    @Override
    public int hashCode() {return id.hashCode();}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {return true;}
        if (obj == null || getClass() != obj.getClass()) {return false;}
        final DescribesDataColumns other = (DescribesDataColumns) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString(){
        return MessageFormat.format("{0} '{'id={1}'}'", this.getClass().getSimpleName() , id);
    }
}
