package top.flydinosaur.experiment3;

import top.flydinosaur.experiment1.SequenceList;
import top.flydinosaur.experiment2.SequenceQueue;
import top.flydinosaur.experiment2.SequenceStack;

import java.util.Scanner;

public class BinaryTreeImpl<T> implements BinaryTree<T> {
    private TreeNode<T> root;
    private int index;

    public BinaryTreeImpl() {
        root = null;
        index = 0;
    }

    public BinaryTreeImpl(TreeNode<T> root) {
        this.root = root;
        index = 0;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public int count() {
        return count(root);
    }

    public int count(TreeNode<T> p) {
        if (p!=null)
            return 1+count(p.left)+count(p.right);
        else
            return 0;
    }

    @Override
    public int height() {
        return height(root);
    }

    //高度计算：每个节点的高度为其左或右孩子的最大高度+1
    @Override
    public int height(TreeNode<T> node) {
        if (node == null) {
            return 0;
        }
        int leftHeight = height(node.left);
        int rightHeight = height(node.right);
        return max(leftHeight, rightHeight) + 1;
    }

    private int max(int a, int b) {
        return a > b ? a : b;
    }

    @Override
    public TreeNode<T> getRoot() {
        return root;
    }

    @Override
    public TreeNode<T> getParent(TreeNode<T> node) {
        return getParent(root, node);
    }

    private TreeNode<T> getParent(TreeNode<T> current, TreeNode<T> target) {
        if (current == null || target == null) {
            return null;
        }
        if ((current.left != null && current.left.equals(target)) ||
                (current.right != null && current.right.equals(target))) {
            return current;
        }

        TreeNode<T> foundInLeft = getParent(current.left, target);
        if (foundInLeft != null) {
            return foundInLeft;
        }

        // 如果在左子树中未找到，则在右子树中查找
        return getParent(current.right, target);
    }


    public SequenceList<T> getTraverseResultList(TreeNode<T> node, int traverseMethod) {
        SequenceList<T> list = new SequenceList<>(10);
        switch (traverseMethod) {
            case 0:
                preOrder(node, list);
                break;
            case 1:
                inOrder(node, list);
                break;
            case 2:
                postOrder(node, list);
                break;
            case 3:
                levelOrder(node, list);
                break;
            case 4:
                nonRecursivePreOrder(node, list);
                break;
            case 5:
                nonRecursiveInOrder(node, list);
                break;
            case 6:
                nonRecursivePostOrder(node, list);
                break;
            default:
                System.out.println("输入异常");
                break;
        }
        return list;
    }

    @Override
    public void preOrder(TreeNode<T> node, SequenceList<T> result) {
        if (node == null) {
            return;
        }
        //根-左-右
        result.append(node.data);
        preOrder(node.left, result);
        preOrder(node.right, result);
    }

    @Override
    public void inOrder(TreeNode<T> node, SequenceList<T> result) {
        if (node == null) {
            return;
        }
        //左-根-右
        inOrder(node.left, result);
        result.append(node.data);
        inOrder(node.right, result);
    }

    @Override
    public void postOrder(TreeNode<T> node, SequenceList<T> result) {
        if (node == null)
            return;
        //左-右-根
        postOrder(node.left, result);
        postOrder(node.right, result);
        result.append(node.data);
    }

    public void nonRecursivePreOrder(TreeNode<T> node, SequenceList<T> result) {
        if (node == null) {
            return;
        }
        SequenceStack<TreeNode<T>> stack = new SequenceStack<>(10);
        stack.push(node);
        while (!stack.isEmpty()) {
            TreeNode<T> current = stack.pop();
            result.append(current.data);
            if (current.right != null) {
                stack.push(current.right);
            }
            if (current.left != null) {
                stack.push(current.left);
            }
        }
    }

    public void nonRecursiveInOrder(TreeNode<T> node, SequenceList<T> result) {
        SequenceStack<TreeNode<T>> stack = new SequenceStack<>(10);
        TreeNode<T> current = node;
        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            current = stack.pop();
            result.append(current.data);
            current = current.right;
        }
    }


    public void nonRecursivePostOrder(TreeNode<T> node, SequenceList<T> result) {
        if (node == null) {
            return;
        }
        SequenceStack<TreeNode<T>> stack = new SequenceStack<>(10);
        TreeNode<T> current = node;
        TreeNode<T> lastVisited = null;

        while (current != null || !stack.isEmpty()) {
            if (current != null) {
                stack.push(current);
                current = current.left;
            } else {
                TreeNode<T> peekNode = stack.peek();
                if (peekNode.right != null && lastVisited != peekNode.right) {
                    current = peekNode.right;
                } else {
                    stack.pop();
                    result.append(peekNode.data);
                    lastVisited = peekNode;
                    current = null;
                }
            }
        }
    }


    @Override
    public void levelOrder(TreeNode<T> root, SequenceList<T> result) {
        if (root == null) {
            return;
        }
        SequenceQueue<TreeNode<T>> queue = new SequenceQueue<>();
        queue.enqueue(root);
        while (!queue.isEmpty()) {
            TreeNode<T> node = queue.dequeue();
            result.append(node.data);
            if (node.left != null) {
                queue.enqueue(node.left);
            }
            if (node.right != null) {
                queue.enqueue(node.right);
            }
        }
    }


    @Override
    public TreeNode<T> search(T element) {
        // 从根节点开始查找
        return searchPreOrder(root, element);
    }

    private TreeNode<T> searchPreOrder(TreeNode<T> node, T element) {
        if (node == null) {
            return null;
        }
        // 如果当前节点正好是要找的元素，则直接返回
        if (node.data.equals(element)) {
            return node;
        }

        TreeNode<T> foundNode = searchPreOrder(node.left, element);
        if (foundNode != null) {
            return foundNode;
        }

        return searchPreOrder(node.right, element);
    }

    @Override
    public void insert(TreeNode<T> p, T element, boolean leftChild) {
        if (element == null) {
            return;
        }
        if (p.left == null && leftChild) {
            p.left = new TreeNode<>(element);
        }
        if (p.right == null && !leftChild) {
            p.right = new TreeNode<>(element);
        }
    }

    @Override
    public void remove(TreeNode<T> p, boolean leftChild) {
        if (leftChild && p.left != null) {
            p.left = null;
        }
        if (!leftChild && p.right != null) {
            p.right = null;
        }
    }

    @Override
    public void clear() {
        root = null;
    }

    public int countLeaves(TreeNode<T> node) {
        if (node == null) {
            return 0;
        }
        if (node.left == null && node.right == null) {
            return 1;
        }
        return countLeaves(node.left) + countLeaves(node.right);
    }

    public void buildTreeByPreOrderList(SequenceList<T> list) {
        root = create(list);
        index = 0;
    }

    private TreeNode<T> create(SequenceList<T> list){
        if (list.isEmpty() || list.get(0) == "#" || index > list.size() - 1) {
            return null;
        }
        T value = list.get(index);
        index++;
        if (value.equals('#')) {
            return null;
        }

        TreeNode<T> node = new TreeNode<>(value);
        node.left = create(list);
        node.right = create(list);
        return node;
    }

    public static void main(String[] args) {
        SequenceList<Character> preOrderList = new SequenceList<>(10);
        preOrderList.append('A');
        preOrderList.append('B');
        preOrderList.append('D');
        preOrderList.append('#');
        preOrderList.append('H');
        preOrderList.append('#');
        preOrderList.append('#');
        preOrderList.append('E');
        preOrderList.append('I');
        preOrderList.append('#');
        preOrderList.append('#');
        preOrderList.append('J');
        preOrderList.append('#');
        preOrderList.append('#');
        preOrderList.append('C');
        preOrderList.append('F');
        preOrderList.append('#');
        preOrderList.append('K');
        preOrderList.append('#');
        preOrderList.append('#');
        preOrderList.append('G');
        preOrderList.append('#');
        preOrderList.append('#');
        System.out.println("测试数据:" + preOrderList);

        BinaryTreeImpl<Character> binaryTree = new BinaryTreeImpl<>();
        binaryTree.buildTreeByPreOrderList(preOrderList);

//        用户界面
//        System.out.print("请输入遍历方式：\n0-前序遍历(递归方法)\t1-中序遍历(递归方法)\t2-后序遍历(递归方法)\t3-层序遍历\n4-前序遍历(非递归方法)\t5-中序遍历(非递归方法)\t6-后序遍历(非递归方法):");
//        Scanner scanner = new Scanner(System.in);
//        int method = scanner.nextInt();

        System.out.println("前序遍历（递归）结果：" + binaryTree.getTraverseResultList(binaryTree.getRoot(), 0));
        System.out.println("中序遍历（递归）结果：" + binaryTree.getTraverseResultList(binaryTree.getRoot(), 1));
        System.out.println("后序遍历（递归）结果：" + binaryTree.getTraverseResultList(binaryTree.getRoot(), 2));
        System.out.println("层序遍历结果：" + binaryTree.getTraverseResultList(binaryTree.getRoot(), 3));
        System.out.println("前序遍历（非递归）结果：" + binaryTree.getTraverseResultList(binaryTree.getRoot(), 4));
        System.out.println("中序遍历（非递归）结果：" + binaryTree.getTraverseResultList(binaryTree.getRoot(), 5));
        System.out.println("后序遍历（非递归）结果：" + binaryTree.getTraverseResultList(binaryTree.getRoot(), 6));
        System.out.println("叶节点的数量：" + binaryTree.countLeaves(binaryTree.getRoot()));
        System.out.println("二叉树的深度：" + binaryTree.height());
    }
}
