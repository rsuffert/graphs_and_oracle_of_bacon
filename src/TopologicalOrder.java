

import java.util.List;
import java.security.InvalidParameterException;
import java.util.ArrayList;

/**
 * This class implements the topological order algorithm.
 * This algorithm is only valid for directed acyclic graphs (DAGs).
 * @author Ricardo Süffert
 */
public class TopologicalOrder {
    private List<Integer> postOrder; // stores the postOrder of the graph
    private boolean[] visited; // stores a boolean value representing whether or not the vertex corresponding to that index has been visited

    /**
     * CONSTRUCTOR
     * @param g the graph to be ordered
     * @throws InvalidParameterException if {@code g} is cyclic
     */
    public TopologicalOrder (Digraph g) throws InvalidParameterException {
        this.postOrder = new ArrayList<>(g.V());
        this.visited = new boolean[g.V()];

        DigraphCycle dg = new DigraphCycle(g);
        if (dg.hasCycle()) throw new InvalidParameterException("The digraph must not have any cycles");

        for (int i=0; i<visited.length; i++) { // for each vertex
            if (!visited[i]) topological(g, i); // if it has not been mapped, map it
        }
    }

    /**
     * Implements the topological order algorithm
     * @param g the graph to be ordered
     */
    private void topological(Digraph g, int ref) {
        visited[ref] = true;

        for (int adj : g.adj(ref)) { // for each adjacent vertex
            if (!visited[adj]) { // if it has not been mapped
                topological(g, adj); // visit it
            }
        }

        postOrder.add(ref);
    }

    /**
     * Returns the topological order of the graph passed as parameter to the constructor
     * @return an {@code Iterable} instance containing the topological order of this graph
     */
    public Iterable<Integer> getTopologicalOrder() { 
        // the topological order is the postOrder array inverted
        Stack<Integer> topologicalOrder = new Stack<>();

        for (int e : postOrder) {
            topologicalOrder.push(e);
        }

        return topologicalOrder;
    }

    public static void main(String[] args) {
        Digraph dg = new Digraph(7);
        dg.addEdge(0, 2);
        dg.addEdge(0, 5);
        dg.addEdge(0, 1);
        dg.addEdge(1, 4);
        dg.addEdge(3, 2);
        dg.addEdge(3, 4);
        dg.addEdge(3, 5);
        dg.addEdge(3, 6);
        dg.addEdge(5, 2);
        dg.addEdge(6, 0);
        dg.addEdge(6, 4);

        System.out.println(dg.toDot());

        TopologicalOrder to = new TopologicalOrder(dg);
        Iterable<Integer> topologicalOrder = to.getTopologicalOrder();
        for (int e : topologicalOrder) {
            System.out.print(e + " ");
        }
    }
}
