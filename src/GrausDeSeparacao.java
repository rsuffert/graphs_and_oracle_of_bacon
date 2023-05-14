import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;

/**
 * Com base no código fornecido no Moodle, implementar o algoritmo que calcula os graus de separação entre dois atores/atrizes 
 * (ex: Kevin Bacon e Chuck Norris). Ou seja, cada linha contém os dados de um único filme:
 * - Os campos são separados por /
 * - O primeiro campo é o nome do filme
 * - Os demais campos são os nomes dos atores/atrizes (elenco)
 * - Como apresentado em aula, o grafo deve ser montado com vértices nos nomes dos filmes e elenco, onde as arestas são as ligações entre filmes e elenco (ver figura acima).
 * A partir da leitura e da criação do grafo não dirigido, o programa deve perguntar dois nomes de atores/atrizes e mostrar menor caminho de um até outro (isto é, BFS).
 * Dica: são cerca de 119 mil nomes diferentes (crie um grafo com 120000 vértices).
 * @author Ricardo Süffert
 */
public class GrausDeSeparacao {
    public static final int NRO_APROX_NOMES = 120_000;
    public static final String CAMINHO_ARQUIVO_FONTE = "movies.txt";
    public static Scanner in = new Scanner(System.in);
    public static Map<String, Integer> verticesPId = new HashMap<>(); // mapeia a string do filme para o id do grafo
    public static Map<Integer, String> idPVertices = new HashMap<>(); // mapeia o id do grafo para a string do filme
    public static void main(String[] args) {
        Graph grafo = construirGrafo(NRO_APROX_NOMES, CAMINHO_ARQUIVO_FONTE);

        System.out.print("Digite o nome do primeiro ator que deseja buscar: ");
        String nomeAtor1 = in.nextLine().trim().toUpperCase();
        System.out.print("Digite o nome do segundo ator que deseja buscar: ");
        String nomeAtor2 = in.nextLine().trim().toUpperCase();

        Integer idAtor1 = verticesPId.get(nomeAtor1);
        Integer idAtor2 = verticesPId.get(nomeAtor2);
        if (idAtor1 == null || idAtor2 == null) {
            System.out.println("Ator não se encontra na base de dados!");
            System.exit(0);
        }

        // construir o BFS
        CaminhamentoEmLargura cl = new CaminhamentoEmLargura(grafo, idAtor1);
        Iterable<Integer> path = cl.pathTo(idAtor2);

        if (path == null) { // verificando se ha um caminho
            System.out.println("Atores não têm conexão!");
            System.exit(0);
        }

        // apenas se houver um caminho, imprima-o
        System.out.print("Caminho: ");
        int contador = 0;
        String filme = null;
        for (int idVertice : path) {
            String nome = idPVertices.get(idVertice);

            if (contador % 2 == 0) { // eh ator
                if (filme == null) System.out.printf("%s -> ", nome); // imprimir o primeiro ator (referencia)
                else System.out.printf("%s (%s) -> ", nome, filme); // imprimir os demais atores
            }
            else filme = nome; // eh filme

            contador++;
        }
        System.out.println();
    }

    public static Graph construirGrafo(int nroDeVertices, String caminho) {
        Graph g = new Graph(nroDeVertices);
        
        int contadorVertices = 0;
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(caminho), Charset.forName("utf-8"))) {
            String line;
            while ((line = reader.readLine()) != null) { // enquanto houver linhas para serem lidas
                String[] campos = line.split("/");
                String nomeFilme = campos[0].toUpperCase();

                verticesPId.put(nomeFilme, contadorVertices);
                idPVertices.put(contadorVertices, nomeFilme);
                
                int idFilme = contadorVertices;
                contadorVertices++;
                for (int i=1; i<campos.length; i++) { // adicionar atores ao grafo, relacionando-os com o vertice de seu filme
                    String nomeAtor = campos[i].toUpperCase();

                    Integer idDicionario = verticesPId.get(nomeAtor);
                    if (idDicionario == null) { // esse ator ainda nao esta no dicionario
                        g.addEdge(contadorVertices, idFilme);
                        verticesPId.put(nomeAtor, contadorVertices);
                        idPVertices.put(contadorVertices, nomeAtor);
                        contadorVertices++;
                    }
                    else g.addEdge(idDicionario, idFilme); // esse ator ja esta no dicionario, entao relacionar o filme com o id que ja esta no dicionario
                }
            }
        } catch (IOException e) {
            System.out.println("Não foi possível ler o arquivo!");
        }

        return g;
    }
}
