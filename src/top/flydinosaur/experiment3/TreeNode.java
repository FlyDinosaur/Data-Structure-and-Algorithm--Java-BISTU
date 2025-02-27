package top.flydinosaur.experiment3;

public class TreeNode<T> {
    T data;
    TreeNode<T> left;
    TreeNode<T> right;

    public TreeNode(T data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }

    public TreeNode(T data, TreeNode<T> left, TreeNode<T> right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        if (left != null && right != null) {
            return left.toString() + "<-" + data.toString() + "->" + right.toString();
        }
        if (left != null) {
            return left.toString() + "<-" + data.toString();
        }
        if (right != null) {
            return right.toString() + "->" + data.toString();
        }
        return data.toString();
    }
}
