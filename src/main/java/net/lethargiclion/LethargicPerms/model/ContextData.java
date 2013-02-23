package net.lethargiclion.LethargicPerms.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores data that can be used to determine whether or
 * not a Subject falls into a particular Context.
 * @author TerrorBite
 *
 */
public class ContextData {
    
    public ContextData(String name, String world, List<String> groups) {
        this.name = name;
        this.world = world;
        this.groups = new ArrayList<String>(groups);
    }

    /**
     * The name of the Subject.
     */
    private String name;
    
    /**
     * The name of the World that this sunject occupies.
     */
    private String world;
    
    /**
     * A list of group names that this Subject is a member of.
     */
    private List<String> groups;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the world
     */
    public String getWorld() {
        return world;
    }

    /**
     * @param world the world to set
     */
    public void setWorld(String world) {
        this.world = world;
    }

    /**
     * @return the groups
     */
    public List<String> getGroups() {
        return groups;
    }

    /**
     * @param groups the groups to set
     */
    public void setGroups(List<String> groups) {
        this.groups = groups;
    }
}
