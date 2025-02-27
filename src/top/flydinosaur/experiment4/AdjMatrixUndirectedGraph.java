package top.flydinosaur.experiment4;

import top.flydinosaur.experiment1.SequenceList;
import top.flydinosaur.experiment2.SequenceQueue;

public class AdjMatrixUndirectedGraph<T> implements Graph<T> {

    protected SequenceList<T> vertexList; //顺序表存储图的顶点集合
    protected int[][] adjMatrix; //图的邻接矩阵
    private final int MAX_WEIGHT = Integer.MAX_VALUE;

    class Edge {
        int weight;
        T startVertex;
        T endVertex;

        public Edge(int weight, T startVertex, T endVertex) {
            this.weight = weight;
            this.startVertex = startVertex;
            this.endVertex = endVertex;
        }

        @Override
        public String toString() {
            return "(" + startVertex.toString() + "- " + weight + "-> " + endVertex.toString() + ")";
        }
    }

    public AdjMatrixUndirectedGraph(SequenceList<T> vertexList) {
        this.vertexList = vertexList;
        adjMatrix = new int[vertexList.size()][vertexList.size()];

        // 初始化邻接矩阵
        for (int i = 0; i < vertexList.size(); i++) {
            for (int j = 0; j < vertexList.size(); j++) {
                adjMatrix[i][j] = (i == j) ? 0 : MAX_WEIGHT; // 自己到自己的距离为0，其他初始化为无穷大
            }
        }
    }

    @Override
    public int vertexCount() {
        return this.vertexList.size();
    }

    @Override
    public T get(int i) {
        if (i < vertexList.size()) {
            return vertexList.get(i);
        }
        return null;
    }

    @Override
    public boolean insertVertex(T vertex) {
        this.vertexList.append(vertex);

        // 创建一个新邻接矩阵，比原矩阵大一行一列
        int newSize = this.vertexList.size();
        int[][] newAdjMatrix = new int[newSize][newSize];

        for (int i = 0; i < newSize; i++) {
            for (int j = 0; j < newSize; j++) {
                if (i < adjMatrix.length && j < adjMatrix.length) {
                    newAdjMatrix[i][j] = adjMatrix[i][j];
                } else {
                    // 新增的行和列初始化为无穷大
                    newAdjMatrix[i][j] = (i == j) ? 0 : MAX_WEIGHT;
                }
            }
        }
        this.adjMatrix = newAdjMatrix;
        return true;
    }

    @Override
    public boolean insertEdge(int from, int to, int weight) {
        // 在现有顶点范围内，不能覆盖原有路径，不能自己到自己
        if (from < vertexList.size() && to < vertexList.size() && from != to && adjMatrix[from][to] == MAX_WEIGHT) {
            adjMatrix[from][to] = weight;
            adjMatrix[to][from] = weight; // 无向图，对称矩阵
            return true;
        }
        return false;
    }

    public void insertEdge(T from, T to, int weight) {
        int fromIndex = vertexList.getIndex(from);
        int toIndex = vertexList.getIndex(to);
        if (fromIndex == -1 || toIndex == -1) {
            return;
        }
        insertEdge(fromIndex, toIndex, weight);
    }

    public void insertEdge(T from, T to) {
        int fromIndex = vertexList.getIndex(from);
        int toIndex = vertexList.getIndex(to);
        if (fromIndex == -1 || toIndex == -1) {
            return;
        }
        insertEdge(fromIndex, toIndex, 1);
    }

    @Override
    public boolean removeVertex(int vertex) {
        if (vertex < 0 || vertex >= vertexList.size()) {
            return false;
        }

        vertexList.remove(vertex);

        int newSize = vertexList.size();
        int[][] newAdjMatrix = new int[newSize][newSize];

        for (int i = 0, newi = 0; i < adjMatrix.length; i++) {
            if (i == vertex) {
                continue;
            }
            for (int j = 0, newj = 0; j < adjMatrix[i].length; j++) {
                if (j == vertex) {
                    continue;
                }
                newAdjMatrix[newi][newj] = adjMatrix[i][j];
                newj++;
            }
            newi++;
        }

        adjMatrix = newAdjMatrix;
        return true;
    }

    public void removeVertex(T vertex) {
        int vertexIndex = vertexList.getIndex(vertex);
        if (vertexIndex == -1) {
            return;
        }
        removeVertex(vertexIndex);
    }

    @Override
    public boolean removeEdge(int from, int to) {
        if (from < vertexList.size() && to < vertexList.size() && from != to) {
            if (adjMatrix[from][to] != MAX_WEIGHT) {
                adjMatrix[from][to] = MAX_WEIGHT;
                adjMatrix[to][from] = MAX_WEIGHT;
                return true;
            }
        }
        return false;
    }

    public void removeEdge(T from, T to) {
        int fromIndex = vertexList.getIndex(from);
        int toIndex = vertexList.getIndex(to);
        if (fromIndex == -1 || toIndex == -1) {
            return;
        }
        removeEdge(fromIndex, toIndex);
    }

    @Override
    public int getFirstNeighbor(int vertex) {
        for (int i = 0; i < vertexList.size(); i++) {
            if (adjMatrix[vertex][i] != MAX_WEIGHT && i != vertex) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getNextNeighbor(int vertex, int currentNeighbor) {
        for (int i = currentNeighbor + 1; i < vertexList.size(); i++) {
            if (adjMatrix[vertex][i] != MAX_WEIGHT && i != vertex) {
                return i;
            }
        }
        return -1;
    }


    public SequenceList<T> DepthFirstSearch() {
        SequenceList<T> result = new SequenceList<>(vertexList.size());
        boolean[] visited = new boolean[vertexList.size()];

        // 从第一个顶点开始遍历
        if (!vertexList.isEmpty()) {
            dfs(0, visited, result);
        }

        return result;
    }

    private void dfs(int vertex, boolean[] visited, SequenceList<T> result) {
        // 标记已访问
        visited[vertex] = true;
        result.append(vertexList.get(vertex));

        // 先访问最近的邻居节点
        int neighbor = getFirstNeighbor(vertex);
        while (neighbor != -1) {
            if (!visited[neighbor]) {  //若这个邻居节点没有访问过，递归
                dfs(neighbor, visited, result);
            }
            //找下一个除访问过的邻居节点
            neighbor = getNextNeighbor(vertex, neighbor);
        }
    }

    public SequenceList<T> BreadthFirstSearch() {
        SequenceList<T> result = new SequenceList<>(vertexList.size());
        boolean[] visited = new boolean[vertexList.size()];
        SequenceQueue<Integer> queue = new SequenceQueue<>(vertexList.size());

        // 从第一个顶点开始遍历
        if (!vertexList.isEmpty()) {
            queue.enqueue(0); // 将起始顶点（索引0）入队
        }

        while (!queue.isEmpty()) {
            int currentVertex = queue.dequeue();

            if (!visited[currentVertex]) {
                visited[currentVertex] = true;
                result.append(vertexList.get(currentVertex));

                // 获取当前顶点的所有邻居并入队未访问的邻居
                int neighbor = getFirstNeighbor(currentVertex);
                while (neighbor != -1) {
                    if (!visited[neighbor]) {
                        queue.enqueue(neighbor); // 将邻居入队
                    }
                    neighbor = getNextNeighbor(currentVertex, neighbor);
                }
            }
        }

        return result;
    }

    // 获取两顶点间的权重
    public int getWeight(T from, T to) {
        int fromIndex = vertexList.getIndex(from);
        int toIndex = vertexList.getIndex(to);
        if (fromIndex != -1 && toIndex != -1) {
            return adjMatrix[fromIndex][toIndex];
        }
        return MAX_WEIGHT;
    }

    public SequenceList<Edge> PrimMST() {
        int vertexCount = vertexList.size();
        SequenceList<Edge> mst = new SequenceList<>(10);
        if (vertexCount == 0) {
            return mst;
        }

        boolean[] visited = new boolean[vertexCount];
        int[] lowCost = new int[vertexCount];
        int[] closest = new int[vertexCount];

        // 初始化lowCost数组为起始顶点（0）到各顶点的权重
        visited[0] = true;
        for (int i = 0; i < vertexCount; i++) {
            lowCost[i] = adjMatrix[0][i];
            closest[i] = 0;
        }

        // 迭代n-1次，每次添加一条边
        for (int i = 1; i < vertexCount; i++) {
            int minWeight = MAX_WEIGHT;
            int selectedVertex = -1;

            // 寻找当前lowCost中的最小值
            for (int j = 0; j < vertexCount; j++) {
                if (!visited[j] && lowCost[j] < minWeight) {
                    minWeight = lowCost[j];
                    selectedVertex = j;
                }
            }

            // 如果未找到有效顶点，说明图不连通，直接返回当前结果（根据需求处理）
            if (selectedVertex == -1) {
                break;
            }

            // 将找到的最小边加入结果
            T start = vertexList.get(closest[selectedVertex]);
            T end = vertexList.get(selectedVertex);
            mst.append(new Edge(minWeight, start, end));
            visited[selectedVertex] = true;

            // 更新lowCost和closest数组
            for (int j = 0; j < vertexCount; j++) {
                if (!visited[j] && adjMatrix[selectedVertex][j] < lowCost[j]) {
                    lowCost[j] = adjMatrix[selectedVertex][j];
                    closest[j] = selectedVertex;
                }
            }
        }

        return mst;
    }


    private void updateLowCost(int[][] currentLowCost, int[] dest, int vertexIndex, boolean[] visited) {
        // 若到其他未访问顶点的权值变小，则更新，并保留来源点
        for (int i = 0; i < vertexList.size(); i++) {
            if (!visited[i] && dest[i] < currentLowCost[i][1]) {
                currentLowCost[i][1] = dest[i];
                currentLowCost[i][0] = vertexIndex;
            }
        }
    }

    // 获取当前最小权值的顶点索引号
    private int getMinimumWeightVertexIndex(int[][] lowCosts, boolean[] visit) {
        int minWeight = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int i = 0; i < lowCosts.length; i++) {
            if (!visit[i] && lowCosts[i][1] < minWeight) {
                minWeight = lowCosts[i][1];
                minIndex = i;
            }
        }

        return minIndex;
    }


    @Override
    public String toString() {
        String temp = "-------------------\nAdjacency Matrix:\n";
        for (int[] row : adjMatrix) {
            for (int value : row) {
                temp += (value == MAX_WEIGHT ? "∞" : value) + "\t";
            }
            temp += "\n";
        }
        temp += "-------------------";
        return temp;
    }

    public static void main(String[] args) {
        SequenceList<String> vertexList = new SequenceList<>(5);
        vertexList.append("A");
        vertexList.append("B");
        vertexList.append("C");
        vertexList.append("D");
        vertexList.append("E");
        AdjMatrixUndirectedGraph<String> graph = new AdjMatrixUndirectedGraph<>(vertexList);
        System.out.println(graph);
        System.out.println(graph.vertexCount());
        graph.insertVertex("F");
        System.out.println(graph);
        graph.insertEdge("A", "B", 5);
        graph.insertEdge("A", "D", 2);
        graph.insertEdge("A", "F", 21);
        graph.insertEdge("B", "C", 7);
        graph.insertEdge("B", "D", 6);
        graph.insertEdge("C", "D", 8);
        graph.insertEdge("D", "E", 9);
        graph.insertEdge("C", "E", 3);
        System.out.println(graph);
        System.out.println(graph.DepthFirstSearch());
        System.out.println(graph.BreadthFirstSearch());
        System.out.println(graph.PrimMST());

    }

}
