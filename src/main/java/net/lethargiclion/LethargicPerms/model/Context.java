
package net.lethargiclion.LethargicPerms.model;

import java.util.List;

/**
 * <p>Pepresents a context within which permissions apply. Players are
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
    
    private List<String> nodes;
    
    private String match;
    
    private List<Context> children;
    
    public boolean matches(String test) {
        return test.equalsIgnoreCase(match);
    }

}
