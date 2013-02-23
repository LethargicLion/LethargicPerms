package net.lethargiclion.LethargicPerms.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class PermissionSet {
    
    private String id;
    
    private List<PermissionSet> supersets;
    
    private List<String> nodes;

    public PermissionSet(String id, List<String> nodes, List<PermissionSet> supersets) {
        this.nodes = nodes;
        this.supersets = supersets;
    }
    
    
    public PermissionSet(String id) {
        this.nodes = new ArrayList<String>();
        this.supersets = new ArrayList<PermissionSet>();
    }
    
    public void addNode(String node) {
        this.nodes.add(node);
    }
    
    public void addParent(PermissionSet p) {
        this.supersets.add(p);
    }
    
    public List<String> resolve() {
        Set<PermissionSet> visited = new java.util.HashSet<PermissionSet>();
        List<String> result = this.resolver(visited);
        return result;
    }
    
    private List<String> resolver(Set<PermissionSet> visited) {
        visited.add(this);
        List<String> result = new ArrayList<String>(nodes);
        Iterator<PermissionSet> i = supersets.iterator();
        while(i.hasNext()) {
            PermissionSet p = i.next();
            if(!visited.contains(p)) result.addAll(p.resolver(visited));
        }
        return result;
    }
    
}
