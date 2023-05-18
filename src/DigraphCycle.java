

/**
 * Tells whether or not an entire Digraph (directed graph) has a cycle in it, considering all its subgraphs as well.
 * @author Ricardo Süffert
 */
public class DigraphCycle {
    /**
     * Represents each stage of analyzing a certain vertex of the graph for cycles.
     * WHITE - have not started analyzing yet.
     * GREY - currently analyzing.
     * BLACK - done analyzing.
     */
    private static enum Stage {WHITE, GREY, BLACK};

    private Stage[] vertexStages;
    private boolean hasCycle;

    /**
     * CONSTRUCTOR.
     * @param g the graph to be analyzed
     */
    public DigraphCycle(Digraph g) {
        if(g.V()>0 && g!=null) {
            this.vertexStages = new Stage[g.V()];
            this.hasCycle = containsCycle(g);
        }
        else this.hasCycle = false; // if the graph is empty or if it is null, there are no cycles
    }

    /**
     * Tells whether or not a certain graph has a cycle in it.
     * @param g the graph to be analyzed
     * @return {@code true} if the graph contains a cycle; {@code false} otherwise
     */
    private boolean containsCycle(Digraph g) {
        for (int i=0; i<g.V(); i++) { // for each vertex in the graph
            vertexStages[i] = Stage.WHITE; // initialize it as WHITE
        }

        for (int i=0; i<g.V(); i++) { // for each vertex in the graph
            if (vertexStages[i] == Stage.WHITE) { // if current vertex is WHITE, that means it has not been visited
                boolean cycleDetected = visit(g, i); // visit and check whether or not there's a cycle
                if (cycleDetected) return true;
            }
        }

        return false;
    }

    /**
     * Tells whether a specific vertex is in a cycle.
     * @param g the graph to be analyzed
     * @param v the vertex to be analyzed
     * @return {@code true} if the vertex is in a cycle; {@code false} otherwise
     */
    private boolean visit(Digraph g, int v) {
        vertexStages[v] = Stage.GREY;

        // for each edge v -> u in g
        for (int u : g.adj(v)) {
            if (vertexStages[u] == Stage.GREY) return true;
            else if (vertexStages[u] == Stage.WHITE) {
                boolean cycleDetected = visit(g, u);
                if(cycleDetected) return true;
            }
        }
        vertexStages[v] = Stage.BLACK;
        return false;
    }

    /**
     * Tells whether the entire graph has a cycle in it, considering its subgraphs as well.
     * @return {@code true} if the graph has a cycle in it; {@code false} otherwise
     */
    public boolean hasCycle() { return hasCycle; }
}
