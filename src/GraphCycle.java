import java.util.Set;
import java.util.HashSet;

/**
 * Tells whether or not the subgraph to which a certain vertex of a graph belongs has a cycle in it.
 */
public class GraphCycle{
    private Set<String> edges;
    private boolean[] marked;
    private boolean hasCycle;

    /**
     * CONSTRUCTOR.
     * @param g the graph to be analyzed.
     * @param s the reference vertex (i.e., the program will look for cycles only in the subgraph to which this vertex belongs).
     */
    public GraphCycle(Graph g, int s) {
        if(g.V()>0 && g!=null) {
            this.edges = new HashSet<String>();
            this.marked = new boolean [g.V()];
            this.hasCycle = checkCycle(g,s);
        }
        else this.hasCycle = false; // if the graph is empty or if it is null, there are no cycles
    }

    /**
     * Finds out whether the subgraph to which a certain reference vertex belongs has a cycle or not.
     * @param g the graph to be analyzed
     * @param ref the reference vertex
     * @return {@code true} if the subgraph has a cycle in it; {@code false} otherwise
     */
    private boolean checkCycle(Graph g, int ref) {
        marked[ref] = true;
        String edge;
        boolean cycleDetected = false;

        //System.out.println("-=-=-=-=-=-=-=-=-=");
        //System.out.println("Visitando "+ ref);
        //System.out.println("Arestas até agora:");
        //System.out.println(edges);
        //System.out.println("Visitando vertices adj:");
        for(int w : g.adj(ref)) { // for each adjacent vertex to the reference
            //System.out.println("  Vertices adj a "+ref+" =>"+w);
            // fill the edge with who led to that adjacent vertex, following a certain order for comparison reasons
            if(w>ref) edge = ref + "." + w;
            else      edge = w + "." + ref;

            if(!marked[w]) {  // if that adjacent vertex has not been visited, recursively visit it
                //System.out.println("  Nunca foi visitado");
                edges.add(edge);
                cycleDetected = checkCycle(g, w);
            }
            else { // if that adjacent vertex has been visited
                //System.out.println("  Já foi visitado");
                if(!edges.contains(edge)) cycleDetected = true; // if the vertex that led to it was not me, then there is a cycle
            }

            if(cycleDetected) { // if the cycle has been detected, no need to keep looking
                //System.out.println("  ACHAMOS UM CICLO");
                break;
            } 
        }
        return cycleDetected;
    }

    /**
     * Tells whether or not the subgraph to which the reference node passed as parameter to the construtor belongs has a cycle.
     * @return {@code true} if the subgraph has a cycle; {@code false} otherwise
     */
    public boolean hasCycle() { return hasCycle; }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = new Graph(in);
        StdOut.println(G);
        StdOut.println();
        StdOut.println(G.toDot());

        System.out.println("Estou na classe análise de ciclos...");

        int vertice = 4;
        GraphCycle cep = new GraphCycle(G,vertice);
        System.out.println("Existe um ciclo no grafo partindo de ? " + (cep.hasCycle()? "SIM" : "NÃO"));            
    }



}