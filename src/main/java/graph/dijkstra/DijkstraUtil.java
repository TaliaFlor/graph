package graph.dijkstra;

import graph.model.Node;
import lombok.Getter;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toCollection;
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

    public List<DijkstraModel> mapToDijkstraModel(Set<Node> nodes) {
        return nodes.stream()
                .map(DijkstraModel::new)
                .collect(toList());
    }

    public Deque<DijkstraSimpleModel> path(int targetId) {
        Deque<DijkstraModel> deque = new ArrayDeque<>();

        DijkstraModel target = findById(targetId);
        deque.offerFirst(target);

        DijkstraModel predecessor = findByNode(target.getPredecessor());
        Deque<DijkstraModel> predecessorsDeque = predecessors(deque, predecessor);

        return getSimpleModels(predecessorsDeque);
    }

    /**
     * <p>
     * Returns a deque with the path from the initial node to the target node
     * </p>
     *
     * @param deque a initiliazed deque alredy containing the target node which
     *              will hold all predecessors nodes
     * @param model the model being evalueted
     * @return a deque with the path from the initial node to the target node
     */
    private Deque<DijkstraModel> predecessors(Deque<DijkstraModel> deque, DijkstraModel model) {
        deque.offerFirst(model);

        Node predecessorNode = model.getPredecessor();
        if (predecessorNode != null) {
            DijkstraModel predecessorModel = findByNode(predecessorNode);
            predecessors(deque, predecessorModel);
        }

        return deque;
    }

    public List<DijkstraSimpleModel> getSimpleModels() {
        return models.stream()
                .map(DijkstraSimpleModel::new)
                .collect(toList());
    }

    public Deque<DijkstraSimpleModel> getSimpleModels(Deque<DijkstraModel> deque) {
        return deque.stream()
                .map(DijkstraSimpleModel::new)
                .collect(toCollection(ArrayDeque::new));
    }

}
