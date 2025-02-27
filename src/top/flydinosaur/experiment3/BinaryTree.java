package top.flydinosaur.experiment3;

import top.flydinosaur.experiment1.SequenceList;

public interface BinaryTree<T> {
    boolean isEmpty();//判断是否空二叉树
    int count();//返回二叉树的结点个数
    int height();//返回二叉树的高度
    int height(TreeNode<T> node);

    TreeNode<T> getRoot();//返回二叉树的根结点
    TreeNode<T> getParent(TreeNode<T> node);//返回node父母结点
    void preOrder(TreeNode<T> node, SequenceList<T> result);//先根次序遍历二叉树
    void inOrder(TreeNode<T> node, SequenceList<T> result);//中根次序遍历二叉树
    void postOrder(TreeNode<T> node, SequenceList<T> result);//后根次序遍历二叉树
    void levelOrder(TreeNode<T> node, SequenceList<T> result);//按层次遍历二叉树
    TreeNode<T> search(T element);//查找并返回元素为element结点
    void insert(TreeNode<T> p, T element, boolean leftChild);
    //插入element元素作为p结点的左/右孩子
    void remove(TreeNode<T> p, boolean leftChild);//删除p的左/右子树
    void clear();//清空

}
