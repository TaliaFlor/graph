package graph.representation;

import graph.model.BfsModel;
import graph.model.Edge;
import graph.model.Node;
import graph.util.BfsUtil;
import graph.util.Json;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

import static java.util.stream.Collectors.toList;


@Slf4j
@Getter
public class Graph {    //TODO atributo com a quantidade de vértices e arestas

    @Getter(AccessLevel.NONE)
    private static final int INFINITE = 2147483647;  //Maior valor inteiro possível em Java

    //    private int nodes;
//    private int edges;
    private final Map<Node, List<Node>> graph = new HashMap<>();     //Transformar isso num objecto e lista de objetos

    @Getter(AccessLevel.NONE)
    private BfsUtil bfsUtil;


    @Override
    public String toString() {
        return Json.toJson(this);
    }


    public List<Node> getAdjacencyList(Node node) {
        return graph.get(node);
    }

    public void add(List<Edge> edges) {
        log.trace("Populating graph");
        for (Edge edge : edges) {
            add(edge);
        }
        log.info("Graph populated");
    }

    public void add(Edge edge) {
        addNode(edge.getOrigin(), edge.getDestiny());
        if (!edge.isDirected()) {
            addNode(edge.getDestiny(), edge.getOrigin());
        }
    }

    private void addNode(Node origin, Node destiny) {
        List<Node> adjacencyList = graph.getOrDefault(origin, new ArrayList<>());
        adjacencyList.add(destiny);
        graph.put(origin, adjacencyList);
    }

    public Deque<Node> search(int targetId) {
        log.trace("Searching for target with ID {}", targetId);

        int initialNodeId = graph.keySet().stream()
                .findAny()
                .get()
                .getId();

        return breadthFirstSearch(initialNodeId, targetId);
    }

    /**
     * <p>
     * Searchs for a path to a specific node from a initial node.
     * </p>
     *
     * @param initialNodeId The initial point of the search
     * @param targetId      the node which is being searched for
     * @return a path from the inital node to the target node. Null if that path does not exist.
     */
    public Deque<Node> search(int initialNodeId, int targetId) {    //TODO walk and walk w/ initial node
        log.trace("Searching for target with ID {} from starting node with ID {}", targetId, initialNodeId);
        return breadthFirstSearch(initialNodeId, targetId);
    }

    private Deque<Node> breadthFirstSearch(int initialNodeId, int targetId) {
        List<BfsModel> models = graph.keySet().stream()
                .map(BfsModel::new)
                .collect(toList());
        bfsUtil = new BfsUtil(models);

        BfsModel initialNode = bfsUtil.findById(initialNodeId);
        initialNode.markAsInitialNode();

        Queue<BfsModel> queue = new LinkedList<>();
        queue.offer(initialNode);

        markPathToTarget(queue, targetId);

        return path(targetId);
    }

    private void markPathToTarget(Queue<BfsModel> queue, int targetId) {
        boolean targetFound = false;
        while (!queue.isEmpty()) {
            BfsModel model = queue.poll();
            for (Node adjacency : getAdjacencyList(model.getNode())) {
                BfsModel adjacencyModel = bfsUtil.findById(adjacency.getId());
                if (adjacencyModel.isNotExplored()) {
                    adjacencyModel.markAsFirstVisited(model);
                    queue.offer(adjacencyModel);
                }

                if (adjacencyModel.equalsById(targetId)) {
                    log.info("Target with ID {} found! Search sucessful", targetId);
                    targetFound = true;
                    break;
                }
            }
            if (targetFound)
                break;

            model.markAsExplored();
        }
    }

    /**
     * <p>
     * Returns a deque with the path from the initial node to the target node
     * </p>
     *
     * @param targetId the id of the target node
     * @return a deque with the path from the initial node to the target node
     */
    private Deque<Node> path(int targetId) {
        Deque<Node> deque = new ArrayDeque<>();

        BfsModel target = bfsUtil.findById(targetId);
        deque.offerFirst(target.getNode());

        BfsModel predecessor = bfsUtil.findByNode(target.getPredecessor());
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
            BfsModel predecessorModel = bfsUtil.findByNode(predecessorNode);
            predecessors(deque, predecessorModel);
        }

        return deque;
    }

}
