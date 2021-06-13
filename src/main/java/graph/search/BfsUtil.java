package graph.search;

import graph.model.Node;

import java.util.ArrayDeque;
import java.util.Deque;
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

    /**
     * <p>
     * Returns a deque with the path from the initial node to the target node
     * </p>
     *
     * @param targetId the id of the target node
     * @return a deque with the path from the initial node to the target node
     */
    public Deque<Node> path(int targetId) {
        Deque<Node> deque = new ArrayDeque<>();

        BfsModel target = findById(targetId);
        deque.offerFirst(target.getNode());

        BfsModel predecessor = findByNode(target.getPredecessor());
        return predecessors(deque, predecessor);
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
    private Deque<Node> predecessors(Deque<Node> deque, BfsModel model) {
        deque.offerFirst(model.getNode());

        Node predecessorNode = model.getPredecessor();
        if (predecessorNode != null) {
            BfsModel predecessorModel = findByNode(predecessorNode);
            predecessors(deque, predecessorModel);
        }

        return deque;
    }

}
