package net.lethargiclion.LethargicPerms.model;

import java.util.Set;

public interface NodeDiff {
    public Set<String> getNodesToAdd();
    
    public Set<String> getNodesToRemove();
}
