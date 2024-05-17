public class RedBlackTree<T extends Comparable<T>> {

    /* Root of the tree. */
    RBTreeNode<T> root;

    static class RBTreeNode<T> {

        final T item;
        boolean isBlack;
        RBTreeNode<T> left;
        RBTreeNode<T> right;

        /**
         * Creates a RBTreeNode with item ITEM and color depending on ISBLACK
         * value.
         * @param isBlack
         * @param item
         */
        RBTreeNode(boolean isBlack, T item) {
            this(isBlack, item, null, null);
        }

        /**
         * Creates a RBTreeNode with item ITEM, color depending on ISBLACK
         * value, left child LEFT, and right child RIGHT.
         * @param isBlack
         * @param item
         * @param left
         * @param right
         */
        RBTreeNode(boolean isBlack, T item, RBTreeNode<T> left,
                   RBTreeNode<T> right) {
            this.isBlack = isBlack;
            this.item = item;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * Creates an empty RedBlackTree.
     */
    public RedBlackTree() {
        root = null;
    }

    /**
     * Flips the color of node and its children. Assume that NODE has both left
     * and right children
     * @param node
     */
    void flipColors(RBTreeNode<T> node) {
        if (node == root) {
            System.out.println("翻轉root測試");
            node.left.isBlack = true;
            node.right.isBlack = true;
            return;
        }
        node.isBlack = false;
        node.left.isBlack = true;
        node.right.isBlack = true;
    }

    /**
     * Rotates the given node to the right. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param node
     * @return
     */
    RBTreeNode<T> rotateRight(RBTreeNode<T> node) {
        RBTreeNode<T> newRoot = node.left;
        newRoot.isBlack = true;
        node.left = newRoot.right;
        newRoot.right = node;
        newRoot.right.isBlack = false;
        
        return newRoot;
    }

    /**
     * Rotates the given node to the left. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param node
     * @return
     */
    RBTreeNode<T> rotateLeft(RBTreeNode<T> node) {
        // color switch
        RBTreeNode<T> newRoot = node.right;
        newRoot.isBlack = node.isBlack;
        node.right = newRoot.left;
        newRoot.left = node;
        newRoot.left.isBlack = false;

        return newRoot;
    }


    /**
     * Helper method that returns whether the given node is red. Null nodes (children or leaf
     * nodes) are automatically considered black.
     * @param node
     * @return
     */
    private boolean isRed(RBTreeNode<T> node) {
        return node != null && !node.isBlack;
    }

    /**
     * Inserts the item into the Red Black Tree. Colors the root of the tree black.
     * @param item
     */
    public void insert(T item) {
        System.out.printf("insert %d\n", item);
        root = insert(root, item);
        root.isBlack = true;
    }

    /**
     * Inserts the given node into this Red Black Tree. Comments have been provided to help break
     * down the problem. For each case, consider the scenario needed to perform those operations.
     * Make sure to also review the other methods in this class!
     * @param node
     * @param item
     * @return
     */
    private RBTreeNode<T> insert(RBTreeNode<T> node, T item) {
        // Insert (return) new red leaf node.
        RBTreeNode<T> newRedNode = new RBTreeNode<>(false, item);

        // Handle normal binary search tree insertion.
        if (node == null) {
            node = newRedNode;
            return node;
        }
        
        int cmp = item.compareTo(node.item);

        if (cmp > 0) {
            if (node.right == null) {
                node.right = newRedNode;
            } else {
                node.right = insert(node.right, item);
            }
        }  else if (cmp < 0) {
            if (node.left == null) {
                node.left = newRedNode;
            } else {
                node.left = insert(node.left, item);
            }          
        }

        // Rotate left operation
        if (isRed(node.right) && !isRed(node.left)) {
            node = rotateLeft(node);
        } 
        
        // Rotate right operation
        if (isRed(node.left) && isRed(node.left.left)) {
            node = rotateRight(node);
        }
        
        // flip Color 
        if (isRed(node.left) && isRed(node.right)) {
            flipColors(node);   
        }

        return node; 
    }

}

