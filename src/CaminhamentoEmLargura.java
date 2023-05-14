import java.util.Queue;
import java.util.LinkedList;

/**
 * Utilizando o código base fornecido no repositório do GitHub, implementar o algoritmo de caminhamento em largura (breadth first search). 
 * Dica: comece com o código apresentado no slide 46 (que usa a classe Queue fornecida), mas você pode usar qualquer estrutura de dados que 
 * funcione como fila (ArrayList, LinkedList, ...).
 * Esse algoritmo dá o menor caminho!
 */
public class CaminhamentoEmLargura {
    private boolean[] marked;
    private int[] edgeTo;
    private int[] distTo; // distancia do nodo referencia ao nodo indexado no array
    private int ref; // nodo referencia no grafo

    /**
     * 
     * @param G grafo base
     * @param s nodo referencia
     */
    public CaminhamentoEmLargura(Graph G, int s) {
        this.marked = new boolean[G.V()];
        this.edgeTo = new int[G.V()];
        this.distTo = new int[G.V()];
        this.ref = s;

        bfs(G, s);
    }

    /**
     * Implementa a busca em largura.
     * @param G grafo base
     * @param s nodo referencia
     */
    private void bfs (Graph G, int s) {
        Queue<Integer> q = new LinkedList<>(); // representa a fila dos nodos cujos filhos devem ser visitados

        // adicionando manualmente informacoes do nodo referencia
        q.add(s); // comecar visitando os filhos de s
        marked[s] = true; // marcar s como visitado
        edgeTo[s] = -1; // indice invalido (nodo referencia nao vem de nenhum outro)
        distTo[s] = 0; // distancia do nodo referencia para ele mesmo é 0

        while (!q.isEmpty()) { // enquanto houver nodos cujos adjacentes (i.e., filhos) devem ser visitados
            int source = q.remove(); // obter o proximo elemento cujos adjacentes devem ser visitados
            for (int adjAtual : G.adj(source)) { // iterar sobre cada adjacente
                if (!marked[adjAtual]) { // se nao tiver sido visitado
                    edgeTo[adjAtual] = source; // anotar que se chegou a ele a partir do source
                    marked[adjAtual] = true; // anotar que ele foi visitado
                    distTo[adjAtual] = distTo[source] + 1; // anotar que a distancia para ele é a distancia do anterior mais um
                    q.add(adjAtual); // adiciona-lo a fila de nodos a visitar os filhos
                }
            }
        }
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;

        Bag<Integer> path = new Bag<>(); // lista encadeada onde a insercao e feita na primeira posicao (na hora de navegar, o inicio eh o ultimo nodo que foi inserido, entao eh apropriada para esse caso)
        path.add(v); // adicionando o nodo v (referencia) ao caminho
        int source = edgeTo[v]; // obter de onde o nodo v veio

        while (source != -1) { // enquanto source nao for o valor de onde o nodo referencia veio
            path.add(source); // adicionar source ao caminho
            source = edgeTo[source]; // buscar de onde veio o source
        }

        return path;
    }

    public int distanceTo(int v) {
        return distTo[v];
    }

    public static void main(String[] args) {
        Graph g = new Graph(6);
        g.addEdge(0, 5);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(5, 3);
        g.addEdge(1, 2);
        g.addEdge(2, 3);
        g.addEdge(2, 4);
        g.addEdge(3, 4);

        CaminhamentoEmLargura cl = new CaminhamentoEmLargura (g, 0);
        for (int i=0; i<g.V(); i++) {
            if (cl.hasPathTo(i)) {
                System.out.println("Existe um caminho para alcançar " + i);
                for (int c : cl.pathTo(i)) {
                    System.out.println(c + "; ");
                }
                System.out.println();
            }
            else
                System.out.println("Não existe um caminho");
            System.out.println("--------------------------------\n");
        }

        for (int i = 0; i<=5; i++) {
            System.out.printf("Distância de 0 a %d = %d\n", i, cl.distanceTo(i));
        }
    }
}
