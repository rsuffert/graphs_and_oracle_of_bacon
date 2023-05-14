/*
 * Finds paths to a certain vertex in a given graph.
 * Does not necessarily finds the shortest path.
 */
public class CaminhamentoEmProfundidade {
    private boolean[] marked; // tells whether or not a vertex has been visited.
    private int[] edgeTo; // stores the paths.
    private int s; // the destination vertex.

    /*
     * Finds paths in G to s.
     */
    public CaminhamentoEmProfundidade (Graph G, int s) {
        this.marked = new boolean[G.V()];
        this.edgeTo = new int[G.V()];
        this.s = s;

        dfs(G, s); // finds the path
    }

    /*
     * Tells whether s has a path to v.
     */
    public boolean hasPathTo(int v) {
        return marked[v];
    }

    /*
     * Returns the path from s to v.
     */
    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null; // se nao houver caminho, nao pode navegar
        
        Bag<Integer> path = new Bag<>(); // lista encadeada onde a insercao e feita na primeira posicao (primeiro inserido eh o ultimo a ser removido)
        int aux = edgeTo[v];
        path.add(v); // incluindo elemento de saida
        while (aux != s) { // enquanto nao chegar na origem (ponto de referencia/entrada)
            path.add(aux); // incluir a informacao no caminho
            aux = edgeTo[aux];
        }
        path.add(s); // incluindo o elemento de entrada

        return path;
    }

    /*
     * Implements the depth-first search algorithm
     */
    private void dfs(Graph G, int v) {
        marked[v] = true; // marcar que o vertice atual esta sendo visitado
        
        for (int w : G.adj(v)) { // para cada vertice adjacente de v (i.e., w), se nao tiver visitado, visita-lo
            if (!marked[w]) { // se nao tiver sido visitado
                edgeTo[w] = v; // anotar quem eu sou e de onde vim, para estabelecer o caminho
                dfs(G, w); // navegar a partir dele p/ visitar seus "filhos"
            }
        }
    }
}
