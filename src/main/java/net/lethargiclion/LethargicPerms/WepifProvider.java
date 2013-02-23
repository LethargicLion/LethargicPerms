package net.lethargiclion.LethargicPerms;

import java.util.List;

import net.lethargiclion.LethargicPerms.model.Subject;

import org.bukkit.OfflinePlayer;
import com.sk89q.wepif.PermissionsProvider;

/**
 * LethargicPerms WepifProvider
 * 
 * <p>This class provides an interface for the WorldEdit Permissions
 * Interoperability Framework (WEPIF) to access LethargicPerms.</p>
 * <p>This allows WEPIF-using plugins (including WorldEdit and WorldGuard)
 * to retrieve data that is unavailable through the standard SuperPerms
 * framework, i.e. player group membership information. Without this
 * interface, plugins that require membership data would need to fall
 * back to workarounds such as <code>group.<i>groupname</i></code> nodes.</p>
 * @author TerrorBite
 *
 */
public class WepifProvider implements PermissionsProvider {
    
    private LethargicPerms plugin;

    public WepifProvider(LethargicPerms plugin) {
        this.plugin = plugin;
    }

    /**
     * Provides a list of groups that this user is in, given a player name.
     */
    @Override
    public String[] getGroups(String playerName) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Provides a list of groups that this user is in, given an OfflinePlayer.
     */
    @Override
    public String[] getGroups(OfflinePlayer player) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Returns whether the named user has the named permission node.
     */
    @Override
    public boolean hasPermission(String playerName, String permission) {
        return plugin.getManager().getSubject(playerName).hasNode(permission);
    }

    /**
     * Returns whether the given OfflinePlayer has the named permission node.
     */
    @Override
    public boolean hasPermission(OfflinePlayer player, String permission) {
        return hasPermission(player.getName(), permission);
    }

    /**
     * Returns whether the named player has the named permission node in a named world.
     */
    @Override
    public boolean hasPermission(String worldName, String playerName, String permission) {
        List<String> groups = plugin.getManager().resolveGroups(playerName);
        Subject testSubject = new Subject(playerName, worldName, groups);
        return testSubject.hasNode(permission);
    }

    /**
     * Returns whether the given OfflinePlayer has the named permission node in a named world.
     */
    @Override
    public boolean hasPermission(String worldName, OfflinePlayer player, String permission) {
        return hasPermission(worldName, player.getName(), permission);
    }

    @Override
    /**
     * Returns whether the named player is in the named group.
     */
    public boolean inGroup(String playerName, String group) {
        return plugin.getManager().resolveGroups(playerName).contains(group);
    }

    /**
     * Returns whether the given OfflinePlayer is in the named group
     */
    @Override
    public boolean inGroup(OfflinePlayer player, String group) {
        return inGroup(player.getName(), group);
    }

}
