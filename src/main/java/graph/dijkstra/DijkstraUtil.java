package graph.dijkstra;

import graph.model.Node;
import lombok.Getter;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

/**
 * <p>
 * Contém métodos auxiliares para a busca em extensão de grafo
 * </p>
 */
public class DijkstraUtil {

    @Getter
    private final List<DijkstraModel> models;


    public DijkstraUtil(Set<Node> nodes) {
        this.models = mapToDijkstraModel(nodes);
    }

//    public DijkstraUtil(List<DijkstraModel> models) {
//        this.models = models;
//    }


    public DijkstraModel findById(int nodeId) {
        return models.stream()
                .filter(model -> model.equalsById(nodeId))
                .findFirst()
                .orElse(null);
    }

    public DijkstraModel findByNode(Node node) {
        return models.stream()
                .filter(model -> model.equalsByNode(node))
                .findFirst()
                .orElse(null);
    }

    public boolean hasOpenNode() {
        DijkstraModel model = models.stream()
                .filter(DijkstraModel::isOpen)
                .findAny()
                .orElse(null);
        return model != null;
    }

    public DijkstraModel getModelWithLowestEstimative() {
        List<DijkstraModel> openNodes = getOpenNodes();
        DijkstraModel modelWithLowestEstimative = openNodes.get(0);
        for (DijkstraModel openNode : openNodes) {
            if (openNode.getDistance() < modelWithLowestEstimative.getDistance())
                modelWithLowestEstimative = openNode;
        }
        return modelWithLowestEstimative;
    }

    public List<DijkstraModel> getOpenNodes() {
        return models.stream()
                .filter(DijkstraModel::isOpen)
                .collect(toList());
    }

    public List<DijkstraModel> getOpenNodes(List<Node> nodes) {
        return nodes.stream()
                .map(this::findByNode)
                .filter(DijkstraModel::isOpen)
                .collect(toList());
    }

    public List<DijkstraModel> mapToDijkstraModel(Set<Node> nodes){
        return nodes.stream()
                .map(DijkstraModel::new)
                .collect(toList());
    }

}
