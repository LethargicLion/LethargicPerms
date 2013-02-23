package net.lethargiclion.LethargicPerms.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.bukkit.permissions.PermissionAttachment;

public class Subject {

    private List<Context> applicableContexts;

    private Set<String> applicableNodes, replacementNodes;

    private ContextData cData;
    
    /**
     * Attachment to a player's permissions
     */
    private PermissionAttachment attachment;
    
    public Subject(PermissionAttachment attachment, String name, String world, List<String> groups) {
        this(name, world, groups);
        this.attachment = attachment;
    }

    public Subject(String name, String world, List<String> groups) {
        cData = new ContextData(name, world, groups);
        this.applicableContexts = new ArrayList<Context>();
        this.applicableNodes = new TreeSet<String>();
    }

    public NodeDiff resolveContexts(List<Context> baseContexts) {
        this.applicableContexts.clear();
        this.replacementNodes = new TreeSet<String>();
        resolveChildContexts(baseContexts);
        NodeDiff d = generateDiffs(this.applicableNodes, this.replacementNodes);
        this.applicableNodes = this.replacementNodes;
        return d;
    }

    private void resolveChildContexts(List<Context> contexts) {
        Set<String> layerNodes = new TreeSet<String>();
        List<Context> matchingContexts = new ArrayList<Context>();
        for (Context c : contexts) {
            if (c.matches(cData)) {
                layerNodes.addAll(c.getNodes());
                this.applicableContexts.add(c);
                matchingContexts.add(c);
            }
        }
        
        processLayer(layerNodes);
        
        for (Context c : matchingContexts) {
            resolveChildContexts(c.getChildren());
        }
    }

    /**
     * Clean up the set of nodes passed in, and then apply that down onto
     * our list of replacement nodes.
     * @param layerNodes Nodes to process.
     */
    private void processLayer(Set<String> layerNodes) {
        // Locate negative nodes, and remove them from the incoming set
        Set<String> negate = new TreeSet<String>();
        Iterator<String> i = layerNodes.iterator();
        while(i.hasNext()) {
            String s = i.next();
            if(s.startsWith("-")) {
                negate.add(s.substring(1));
                i.remove();
            }
        }
        
        // Apply set down onto our set of replacement nodes (union)
        replacementNodes.addAll(layerNodes);
        
        // and then remove negated nodes if any
        replacementNodes.removeAll(negate);
    }
    
    private class ConcreteNodeDiff implements NodeDiff {
        private Set<String> add, remove;
        
        public ConcreteNodeDiff(Set<String> add, Set<String> remove) {
            this.add = add;
            this.remove = remove;
        }
        
        public Set<String> getNodesToAdd() {
            return this.add;
        }
        
        public Set<String> getNodesToRemove() {
            return this.remove;
        }
    }
    
    private NodeDiff generateDiffs(Set<String> current, Set<String> replacement) {
        Set<String> nodesToAdd = new TreeSet<String>(replacement);
        nodesToAdd.removeAll(current);
        
        Set<String> nodesToRemove = new TreeSet<String>(current);
        nodesToRemove.removeAll(replacement);
        
        return new ConcreteNodeDiff(nodesToAdd, nodesToRemove);
    }
    
    public PermissionAttachment getAttachment() {
        return attachment;
    }
    
    public ContextData getContextData() {
        return cData;
    }
    
    public void setWorld(String world) {
        cData.setWorld(world);
    }
    
    public boolean hasNode(String node) {
        return applicableNodes.contains(node);
    }

    public void setAttachment(PermissionAttachment attachment) {
        this.attachment = attachment;
    }

}
