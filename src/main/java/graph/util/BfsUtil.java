package graph.util;

import graph.model.BfsModel;
import graph.model.Node;

import java.util.List;

/**
 * <p>
 * Contém métodos auxiliares para a busca em extensão de grafo
 * </p>
 */
public class BfsUtil {

    private final List<BfsModel> models;


    public BfsUtil(List<BfsModel> models) {
        this.models = models;
    }


    public BfsModel findById(int nodeId) {
        return models.stream()
                .filter(model -> model.equalsById(nodeId))
                .findFirst()
                .orElse(null);
    }

    public BfsModel findByNode(Node node) {
        return models.stream()
                .filter(model -> model.equalsByNode(node))
                .findFirst()
                .orElse(null);
    }

}
