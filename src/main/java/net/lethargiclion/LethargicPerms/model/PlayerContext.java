package net.lethargiclion.LethargicPerms.model;

public class PlayerContext extends Context {

    @Override
    public boolean matches(ContextData match) {
        return matches(match.getName());
    }
    
}
