package net.lethargiclion.LethargicPerms;

/*
    This file is part of LethargicPerms

    Foobar is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Foobar is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.lethargiclion.LethargicPerms.model.Context;
import net.lethargiclion.LethargicPerms.model.NodeDiff;
import net.lethargiclion.LethargicPerms.model.Subject;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.permissions.PermissionAttachment;

public final class PermissionsManager implements Listener {

	private LethargicPerms plugin;
    private List<Context> baseContexts;
    private Map<String, Subject> subjects;

	public PermissionsManager(LethargicPerms plugin) {
		this.plugin = plugin;
		
		subjects = new TreeMap<String, Subject>();
	}

	/* EXAMPLE CODE START *
	// This is just one possible event you can hook.
	// See http://jd.bukkit.org/apidocs/ for a full event list.

	// All event handlers must be marked with the @EventHandler annotation 
	// The method name does not matter, only the type of the event parameter
	// is used to distinguish what is handled.

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Bukkit.getServer().broadcastMessage("Player " + event.getPlayer().getName() + " placed " + event.getBlock().getType() + " at " + event.getBlock().getLocation());
	}
	* EXAMPLE CODE END */
	
	@EventHandler(priority=EventPriority.LOWEST)
    public void onPlayerLogin(PlayerLoginEvent event) {
        Player p = event.getPlayer();
        String world = p.getWorld().getName();
        Subject s = getSubject(p.getName(), world);
        if(s.getAttachment() == null) { s.setAttachment(p.addAttachment(plugin)); }
        if(!s.getContextData().getWorld().equals(world)) {
            s.setWorld(world);
            onContextChanged(s);
        }
        
    }
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
	    Subject subject = subjects.get(event.getPlayer().getName());
	    subject.setWorld(event.getPlayer().getWorld().getName());
	    onContextChanged(subject);
	}
	
	public void onContextChanged(Subject s) {
	    NodeDiff diff = s.resolveContexts(baseContexts);
	    
	    PermissionAttachment a = s.getAttachment();
	    for(String node : diff.getNodesToRemove()) a.unsetPermission(node);
	    for(String node : diff.getNodesToAdd()) a.setPermission(node, true);
	}
	
	public Subject getSubject(String name) {
	    return getSubject(name, "");
	}

	private Subject getSubject(String name, String world) {
	    if(subjects.containsKey(name)) return subjects.get(name);
	    
	    Subject s = new Subject(name, world, resolveGroups(name));
	    s.resolveContexts(baseContexts);
	    subjects.put(name, s);
	    return s;
	}

    public List<String> resolveGroups(String name) {
        // TODO Auto-generated method stub
        return new ArrayList<String>();
    }
}
