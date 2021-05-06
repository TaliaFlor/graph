package graph.representation;

import graph.dijkstra.DijkstraModel;
import graph.dijkstra.DijkstraUtil;
import graph.model.Edge;
import graph.model.Node;
import graph.search.BfsModel;
import graph.search.BfsUtil;
import graph.search.Color;
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
    private final List<Edge> edges = new ArrayList<>();

    @Getter(AccessLevel.NONE)
    private BfsUtil bfsUtil;
    @Getter(AccessLevel.NONE)
    private DijkstraUtil dijkstraUtil;


    @Override
    public String toString() {
        return Json.toJson(this);
    }


    // ====================== UTIL ======================

    public List<Node> getAdjacencyList(Node node) {
        return graph.get(node);
    }

    public Edge getEdge(Node origin, Node destiny) {
        return edges.stream()
                .filter(edge -> edge.getOrigin().equals(origin))
                .filter(edge -> edge.getDestiny().equals(destiny))
                .findFirst()
                .orElse(null);
    }

    // ====================== ADD ======================

    public void add(List<Edge> edges) {
        log.trace("Populating graph");
        this.edges.addAll(edges);
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

    // ====================== SEARCH ======================

    public Deque<Node> search(int targetId) {
        log.trace("Searching for target with ID {}", targetId);

        validateGraph();

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
    public Deque<Node> search(int initialNodeId, int targetId) {
        log.trace("Searching for target with ID {} from starting node with ID {}", targetId, initialNodeId);
        validateGraph();
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
        if (!targetFound)
            log.info("Target with ID {} not found! Search unsucessful", targetId);
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

    // ====================== WALK ======================

    public Map<Node, Node> walk() {
        log.trace("Walking through the graph");

        validateGraph();

        int initialNodeId = graph.keySet().stream()
                .findAny()
                .get()
                .getId();

        return breadthFirstSearch(initialNodeId);
    }

    public Map<Node, Node> walk(int initialNodeId) {
        log.trace("Walking through graph starting from node with ID {}", initialNodeId);
        validateGraph();
        return breadthFirstSearch(initialNodeId);
    }

    private Map<Node, Node> breadthFirstSearch(int initialNodeId) {
        List<BfsModel> models = graph.keySet().stream()
                .map(BfsModel::new)
                .collect(toList());
        bfsUtil = new BfsUtil(models);

        BfsModel initialNode = bfsUtil.findById(initialNodeId);
        initialNode.markAsInitialNode();

        Queue<BfsModel> queue = new LinkedList<>();
        queue.offer(initialNode);

        return walkGraph(models, queue);
    }

    private Map<Node, Node> walkGraph(List<BfsModel> models, Queue<BfsModel> queue) {
        Map<Node, Node> predecessors = new HashMap<>();

        boolean firstLoop = true;
        do {
            if (!firstLoop)
                queue.offer(nodeNotVisited(models));

            while (!queue.isEmpty()) {
                BfsModel model = queue.poll();
                for (Node adjacency : getAdjacencyList(model.getNode())) {
                    BfsModel adjacencyModel = bfsUtil.findById(adjacency.getId());
                    if (adjacencyModel.isNotExplored()) {
                        adjacencyModel.markAsFirstVisited(model);
                        predecessors.put(adjacencyModel.getNode(), adjacencyModel.getPredecessor());
                        queue.offer(adjacencyModel);
                    }
                }
                model.markAsExplored();
            }

            firstLoop = false;
        } while (!allNodesVisited(models));

        return predecessors;
    }

    private boolean allNodesVisited(List<BfsModel> models) {
        long whiteNodes = models.stream()
                .filter(model -> model.getColor() == Color.WHITE)
                .count();
        return whiteNodes == 0;
    }

    private BfsModel nodeNotVisited(List<BfsModel> models) {
        return models.stream()
                .filter(model -> model.getColor() == Color.WHITE)
                .findFirst()
                .orElse(null);
    }

    // ====================== CAMINHOS MÍNIMOS ======================

    public List<DijkstraModel> shortestPath(int initialNodeId) {
        log.trace("Searching for shortest path from node with ID {} to all other nodes on the graph", initialNodeId);
        validateGraph();
        return dijkstra(initialNodeId);
    }

    private List<DijkstraModel> dijkstra(int initialNodeId) {
        dijkstraUtil = new DijkstraUtil(graph.keySet());

        DijkstraModel initialNode = dijkstraUtil.findById(initialNodeId);
        initialNode.markAsInitialNode();

        return markPathToTarget();
    }

    private List<DijkstraModel> markPathToTarget() {
        while (dijkstraUtil.hasOpenNode()) {
            DijkstraModel model = dijkstraUtil.getModelWithLowestEstimative();
            model.closeNode();

            List<Node> adjacencyList = getAdjacencyList(model.getNode());
            for (DijkstraModel adjacencyModel : dijkstraUtil.getOpenNodes(adjacencyList)) {
                adjacencyModel.markAsVisited(model, getEdge(model.getNode(), adjacencyModel.getNode()));
            }
        }
        return dijkstraUtil.getModels();
    }

    // ====================== HELPERS ======================

    private void validateGraph() {
        if (graph.isEmpty())
            throw new RuntimeException("Operation prohibited! Graph is empty");
    }

}
