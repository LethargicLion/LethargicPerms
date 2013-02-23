package net.lethargiclion.LethargicPerms.model;

/**
 * Represents a context that always applies to all subjects.
 * @author TerrorBite
 *
 */
public class GlobalContext extends Context {

    @Override
    public boolean matches(ContextData match) {
        return true;
    }
    
    
}
