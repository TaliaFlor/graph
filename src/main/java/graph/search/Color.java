package graph.search;

/**
 * <p>
 * Representa os estados possiveís de um nó durante uma busca em extensão
 * </p>
 */
public enum Color {
    WHITE,      //Não visitado
    GREY,       //Visitado, mas tem vizinhos não visitados
    BLACK       //Visitado + vizinhos visitados
}
