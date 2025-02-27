package top.flydinosaur.experiment4;

public interface Graph<T> {
    int vertexCount();//返回顶点数
    T get(int i);//返回顶点vi元素
    boolean insertVertex(T vertex);//插入顶点
    boolean insertEdge(int i, int j, int weight);//插入边
    boolean removeVertex(int v);//删除顶点
    boolean removeEdge(int i, int j);//删除边
    int getFirstNeighbor(int v);//返回邻接顶点序号
    int getNextNeighbor(int v, int w); //返回下一个邻接顶点
}
