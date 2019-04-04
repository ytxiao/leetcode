package tree;

import java.util.LinkedList;


public class RBTree<E extends Comparable<E>> {
    private static final boolean BLACK = true;
    private static final boolean RED = false;

    private Node<E> root;

    private int size;

    public Node<E> getRoot() {
        return root;
    }

    public int getSize() {
        return size;
    }

    /**
     * 获取颜色值
     *
     * @param p
     */
    private boolean colorOf(Node<E> p) {
        return (p == null ? BLACK : p.color);
    }

    /**
     * 获取父节点
     *
     * @param p
     * @return
     */
    private Node<E> parentOf(Node<E> p) {
        return (p == null ? null : p.parent);
    }

    /**
     * 设置节点的颜色
     *
     * @param p
     * @param color
     */
    private void setColor(Node<E> p, boolean color) {
        if (p != null) {
            p.color = color;
        }
    }

    /**
     * 获取当前节点的左节点
     *
     * @param p
     * @return
     */
    private Node<E> leftOf(Node<E> p) {
        return (p == null ? null : p.leftChild);
    }

    /**
     * 获取当前节点的右节点
     *
     * @param p
     * @return
     */
    private Node<E> rightOf(Node<E> p) {
        return (p == null ? null : p.rightChild);
    }

    /**
     * 左旋转
     *
     * @param x
     */
    public void leftRotate(Node<E> x) {
        if (x == null) {
            return;
        }
        //1.先取到x的右结点
        Node<E> y = x.rightChild;
        //2.把𝞫接上x的右结点上
        x.rightChild = y.leftChild;
        if (y.leftChild != null) {
            y.leftChild.parent = x;
        }
        //3.把y移到x的父结点上
        y.parent = x.parent;
        if (x.parent == null) {
            root = y;
        } else {
            if (x.parent.leftChild == x) {
                x.parent.leftChild = y;
            } else {
                x.parent.rightChild = y;
            }
        }
        y.leftChild = x;
        x.parent = y;
    }

    /**
     * 右旋转
     *
     * @param x
     */
    public void rightRotate(Node<E> x) {
        if (x == null) {
            return;
        }
        //1.先取到x的左结点
        Node<E> y = x.leftChild;
        //2.把𝞫接上x的左结点上
        x.leftChild = y.rightChild;
        if (y.rightChild != null) {
            y.rightChild.parent = x;
        }
        //3.把y移到x的父结点上
        y.parent = x.parent;
        if (x.parent == null) {
            root = y;
        } else {
            if (x.parent.leftChild == x) {
                x.parent.leftChild = y;
            } else {
                x.parent.rightChild = y;
            }
        }
        y.rightChild = x;
        x.parent = y;
    }

    /**
     * 插入节点：先按照二叉排序树的方式插入一个节点，再查找最小不平衡子树，以最小不平衡子树进行下面的操作
     *
     * @param element
     * @return
     */
    public boolean insertElement(E element) {
        if (element == null) {
            return false;
        }
        Node<E> t = root;
        if (t == null) {
            root = new Node<>(element, null);
            size = 1;
            return true;
        }
        int cmp = 0;
        Node<E> parent = null;
        Comparable<E> e = element;
        //1.查找可以插入的位置
        do {
            parent = t;
            cmp = e.compareTo(t.element);
            if (cmp < 0) {//比父结点小，那么查左结点
                t = t.leftChild;
            } else if (cmp > 0) {//比父结点大，那么查右结点
                t = t.rightChild;
            } else {//相等，说明是同一个数据，不需要插入
                return false;
            }
        } while (t != null);
        //2.找到可以插入的位置，进行插入
        Node<E> child = new Node<>(element, parent);
        if (cmp < 0) {
            parent.leftChild = child;
        } else {
            parent.rightChild = child;
        }
        //平衡树出现问题，需要修正
        fixAfterInsertion(child);
        size++;

        return true;
    }

    /**
     * 修正插入之后的平衡树
     *
     * @param node
     */
    public void fixAfterInsertion(Node<E> node) {
        if (node == null) return;

        node.color = RED;

        while (node != null && node != root && node.parent.color == RED) {
            //1.父节点是祖父节点的左孩子，有3种情况
            if (parentOf(node) == leftOf(parentOf(parentOf(node)))) {
                //取叔叔节点
                Node<E> uncleNode = rightOf(parentOf(parentOf(node)));
                if (colorOf(uncleNode) == RED) {//情况1：祖父节点的另一个子节点（叔叔节点）是红色
                    //对策： 将当前节点的父节点和叔叔节点涂黑，祖父节点涂红，把当前节点指向祖父节点，从新的当前节点开始算法
                    setColor(parentOf(node), BLACK);
                    setColor(uncleNode, BLACK);
                    setColor(parentOf(parentOf(node)), RED);
                    node = parentOf(parentOf(node));
                } else {//情况2：叔叔节点是黑色
                    if (node == rightOf(parentOf(node))) {//情况2.1：当前节点是其父节点的右孩子
                        //对策：当前节点的父节点做为新的当前节点，以新当前节点为支点左旋。
                        node = parentOf(node);
                        leftRotate(node);
                    }
                    //情况2.2：当前节点是其父节点的左孩子
                    //对策：父节点变为黑色，祖父节点变红色，再祖父节点为支点进行右旋
                    setColor(parentOf(node), BLACK);
                    setColor(parentOf(parentOf(node)), RED);
                    rightRotate(parentOf(parentOf(node)));
                }
            }
            //2.父节点是祖父节点的右孩子
            else {
                //取叔叔节点
                Node<E> uncleNode = leftOf(parentOf(parentOf(node)));
                if (colorOf(uncleNode) == RED) {//情况1：祖父节点的另一个子节点（叔叔节点）是红色
                    //对策： 将当前节点的父节点和叔叔节点涂黑，祖父节点涂红，把当前节点指向祖父节点，从新的当前节点开始算法
                    setColor(parentOf(node), BLACK);
                    setColor(uncleNode, BLACK);
                    setColor(parentOf(parentOf(node)), RED);
                    node = parentOf(parentOf(node));
                } else {//情况2：叔叔节点是黑色
                    if (node == leftOf(parentOf(node))) {//情况2.1：当前节点是其父节点的左孩子
                        //对策：当前节点的父节点做为新的当前节点，以新当前节点为支点右旋。
                        node = parentOf(node);
                        rightRotate(node);
                    }
                    //情况2.2：当前节点是其父节点的右孩子
                    //对策：父节点变为黑色，祖父节点变红色，再祖父节点为支点进行左旋
                    setColor(parentOf(node), BLACK);
                    setColor(parentOf(parentOf(node)), RED);
                    leftRotate(parentOf(parentOf(node)));
                }
            }
        }
        root.color = BLACK;
    }

    /**
     * 根据当前的值，获取当前节点
     *
     * @param element
     * @return
     */
    public Node<E> getNode(E element) {
        if (element == null) {
            return null;
        }
        Node<E> t = root;
        Comparable<E> e = element;
        while (t != null) {
            int cmp = e.compareTo(t.element);
            if (cmp < 0) {
                t = t.leftChild;
            } else if (cmp > 0) {
                t = t.rightChild;
            } else {
                return t;
            }
        }
        return null;
    }

    /**
     * 获取传入P节点下面最小的节点
     *
     * @param p
     * @return
     */
    public Node<E> getMinLeftNode(Node<E> p) {
        if (p == null) {
            return null;
        }
        Node<E> t = p;
        while (t.leftChild != null) {
            t = t.leftChild;
        }
        return t;
    }

    /**
     * 删除元素
     *
     * @param element
     */
    public void remove(E element) {
        //查找当前的节点
        Node<E> node = getNode(element);
        if (node == null) {
            return;
        }
        deleteElement(node);
    }

    /**
     * 删除当前的节点
     *
     * @param node
     */
    public void deleteElement(Node<E> node) {

        if (node.leftChild != null && node.rightChild != null) {
            Node<E> s = getMinLeftNode(node.rightChild);
            node.element = s.element;
            node = s;
        }

        Node<E> t = (node.leftChild != null ? node.leftChild : node.rightChild);

        if (t == null) {
            Node<E> parent = node.parent;
            if (parent == null) {
                root = null;
            } else {
                // 注重：如果删除节点是黑色，那么一定要先修正红黑二叉树，在做下面的删除动作
                if (node.color == BLACK) {
                    fixAfterDeletion(node);
                }

                if (parent.leftChild == node) {
                    parent.leftChild = null;
                } else {
                    parent.rightChild = null;
                }
                node.parent = null;
            }
        } else {
            t.parent = node.parent;
            if (node.parent == null) {
                root = t;
            } else {
                if (node.parent.leftChild == node) {
                    node.parent.leftChild = t;
                } else {
                    node.parent.rightChild = t;
                }
            }
            node.leftChild = node.rightChild = node.parent = null;
            if (node.color == BLACK) {
                fixAfterDeletion(t);
            }
        }

        size--;
    }

    /**
     * 删除节点后，修正红黑树
     * @param node
     */
    public void fixAfterDeletion(Node<E> node) {
        while (node != root && colorOf(node) == BLACK) {
            if (leftOf(parentOf(node)) == node) { //被删除节点是父节点的左孩子
                //拿到node的兄弟节点
                Node<E> sib = rightOf(parentOf(node));
                if (colorOf(sib) == RED) { //2.2 当前节点x的兄弟节点是红色(此时父节点和兄弟节点的子节点分为黑)
                    //对策：把父节点染成红色，兄弟节点染成黑色，对父节点进行左旋，重新设置x的兄弟节点
                    setColor(parentOf(node), RED);
                    setColor(sib, BLACK);
                    leftRotate(parentOf(node));
                    sib = rightOf(parentOf(node));
                }
                if (colorOf(sib) == BLACK) {//2.3 当前节点x 的兄弟节点是黑色
                    if (colorOf(leftOf(sib)) == BLACK && colorOf(rightOf(sib)) == BLACK) {//2.3.1 兄弟节点的两个孩子都是黑色
                        //对策：将x的兄弟节点设为红色，设置x的父节点为新的x节点
                        setColor(sib, RED);
                        node = parentOf(node);
                    } else {
                        if (colorOf(rightOf(sib)) == BLACK) {//2.3.2 兄弟的右孩子是黑色，左孩子是红色
                            //对策：将x兄弟节点的左孩子设为黑色，将x兄弟节点设置红色，将x的兄弟节点右旋，右旋后，重新设置x的兄弟节点。
                            setColor(leftOf(sib), BLACK);
                            setColor(sib, RED);
                            rightRotate(sib);
                            sib = rightOf(parentOf(node));
                        }
                        //2.3.3 兄弟节点的右孩子是红色
                        //对策：把兄弟节点染成当前节点父节点颜色，把当前节点父节点染成黑色，兄弟节点右孩子染成黑色，再以当前节点的父节点为支点进行左旋，算法结算
                        setColor(sib, colorOf(parentOf(node)));
                        setColor(parentOf(node), BLACK);
                        setColor(rightOf(sib), BLACK);
                        leftRotate(parentOf(node));
                        node = root;
                    }
                }
            } else { //被删除节点是父节点的右孩子, 对策： 把上面的左设置为右
                //拿到node的兄弟节点
                Node<E> sib = leftOf(parentOf(node));
                if (colorOf(sib) == RED) { //2.2 当前节点x的兄弟节点是红色(此时父节点和兄弟节点的子节点分为黑)
                    //对策：把父节点染成红色，兄弟节点染成黑色，对父节点进行右旋，重新设置x的兄弟节点
                    setColor(parentOf(node), RED);
                    setColor(sib, BLACK);
                    rightRotate(parentOf(node));
                    sib = leftOf(parentOf(node));
                }
                if (colorOf(sib) == BLACK) {//2.3 当前节点x 的兄弟节点是黑色
                    if (colorOf(leftOf(sib)) == BLACK && colorOf(rightOf(sib)) == BLACK) {//2.3.1 兄弟节点的两个孩子都是黑色
                        //对策：将x的兄弟节点设为红色，设置x的父节点为新的x节点
                        setColor(sib, RED);
                        node = parentOf(node);
                    } else {
                        if (colorOf(leftOf(sib)) == BLACK) {//2.3.2 兄弟的左孩子是黑色，右孩子是红色
                            //对策：将x兄弟节点的右孩子设为黑色，将x兄弟节点设置红色，将x的兄弟节点左旋，左旋后，重新设置x的兄弟节点。
                            setColor(rightOf(sib), BLACK);
                            setColor(sib, RED);
                            leftRotate(sib);
                            sib = leftOf(parentOf(node));
                        }
                        //2.3.3 兄弟节点的左孩子是红色
                        //对策：把兄弟节点染成当前节点父节点颜色，把当前节点父节点染成黑色，兄弟节点左孩子染成黑色，再以当前节点的父节点为支点进行右旋，算法结算
                        setColor(sib, colorOf(parentOf(node)));
                        setColor(parentOf(node), BLACK);
                        setColor(leftOf(sib), BLACK);
                        rightRotate(parentOf(node));
                        node = root;
                    }
                }
            }
        }
    }

    /**
     * 一层一层打印出数据
     *
     * @param root)
     */
    public void showRBTree(Node<E> root) {
        if (root == null) {
            return;
        }
        LinkedList<Node<E>> list = new LinkedList<>();
        list.offer(root);
        while (!list.isEmpty()) {
            Node<E> node = list.pop();
            System.out.println("node.element = " + node.element + ", color = " + node.color);
            if (node.leftChild != null) {
                list.offer(node.leftChild);
            }
            if (node.rightChild != null) {
                list.offer(node.rightChild);
            }
        }
    }

    /**
     * 红黑树结点
     *
     * @param <E>
     */
    public class Node<E extends Comparable>{
        E element;
        Node<E> leftChild;
        Node<E> rightChild;
        Node<E> parent;

        //0：黑色  1：红色
        boolean color = BLACK;

        public Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
        }
    }

}
