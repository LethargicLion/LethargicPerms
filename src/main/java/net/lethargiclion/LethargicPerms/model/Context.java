
package net.lethargiclion.LethargicPerms.model;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Represents a context within which permissions apply. Players are
 * granted permissions by the contexts that currently apply to them.</p>
 * 
 * <p>Contexts occupy a tree model; they have up to one parent and may have
 * any number of children. Top-level contexts are applied to any matching
 * players. Child contexts will only be applied to matching players who
 * also match the parent context.</p>
 * 
 * 
 * 
 * @author TerrorBite
 *
 */
public abstract class Context {
    
    private List<String> nodes = new ArrayList<String>();
    
    private String match;
    
    private List<Context> children = new ArrayList<Context>();
    
    public abstract boolean matches(ContextData test);
    
    protected boolean matches(String str) {
        return match.equalsIgnoreCase(str);
    }

    /**
     * @return A list of child contexts.
     */
    public List<Context> getChildren() {
        return children;
    }

    /**
     * @param child The child context to add.
     */
    public void addChild(Context child) {
        this.children.add(child);
    }
    
    public void addNodes(List<String> nodes) {
        this.nodes.addAll(nodes);
    }
    
    public void addNode(String node) {
        this.nodes.add(node);
    }
    
    public void addNodes(PermissionSet set) {
        this.nodes.addAll(set.resolve());
    }
    
    public List<String> getNodes() {
        return this.nodes;
    }

}
