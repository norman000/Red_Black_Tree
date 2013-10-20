/**
 * Created with IntelliJ IDEA.
 */
public class RBTree {
    //Constant marking a key as that of an empty leaf
    static final int NilValue = -1;
    //Constant returned when requesting a min / max value when tree is empty
    static final int EmptyMinMaxValue = -1;
    //Pointer to root node
    private RBNode root;
    //Current size of tree (number of non-nil nodes)
    private int size;
    //Current smallest key in the tree
    private int min;
    //Current greatest key in the tree
    private int max;

    //Creates a new, empty instance
    public RBTree() {
        this.root = new RBNode(RBTree.NilValue);
        this.size = 0;
        this.min = EmptyMinMaxValue;
        this.max = EmptyMinMaxValue;
    }

    //Returns pointer to root node
    private RBNode getRoot() {
        return this.root;
    }

    //Sets root node
    private void setRoot(RBNode root) {
        this.root = root;
    }

    //Returns true if and only if the tree is empty
    public boolean empty() {
        return root.isNil();
    }

    //Returns true if and only if the tree contains i
    public boolean contains(int i) {
        if (!empty()) {
            return root.contains(i);
        } else {
            return false;
        }
    }

    //Inserts the integer i into the binary tree
    public void insert(int i) {
        RBNode newNode = new RBNode(i);
        if (empty()) {
            setRoot(newNode);
            this.min = i;
            this.max = i;
        } else {
            redBlackInsert(newNode);
            if (this.min > i) {
                this.min = i;
            }
            if (this.max < i) {
                this.max = i;
            }
        }
        this.size++;
    }

    //Inserts a node to a Red-Black tree in a valid way
    private void redBlackInsert(RBNode newNode) {
        RBNode y;
        if (getRoot().insert(newNode)) {
            newNode.setRed();
            while (newNode != getRoot() && newNode.getParent().isRed()) {
                if (newNode.getParent() == newNode.getGrandParent().getLeftChild()) {
                    y = newNode.getGrandParent().getRightChild();
                    if (!y.isNil() && y.isRed()) {
                        newNode.getParent().setBlack();
                        y.setBlack();
                        newNode.getGrandParent().setRed();
                        newNode = newNode.getGrandParent();
                    } else {
                        if (newNode == newNode.getParent().getRightChild()) {
                            newNode = newNode.getParent();
                            leftRotate(newNode);
                        }
                        if (newNode.hasParent()) {
                            newNode.getParent().setBlack();
                            if (newNode.hasGrandParent()) {
                                newNode.getGrandParent().setRed();
                                rightRotate(newNode.getGrandParent());
                            }
                        }
                    }
                } else {
                    y = newNode.getGrandParent().getLeftChild();
                    if (!y.isNil() && y.isRed()) {
                        newNode.getParent().setBlack();
                        y.setBlack();
                        newNode.getGrandParent().setRed();
                        newNode = newNode.getGrandParent();
                    } else {
                        if (newNode == newNode.getParent().getLeftChild()) {
                            newNode = newNode.getParent();
                            rightRotate(newNode);
                        }
                        if (newNode.hasParent()) {
                            newNode.getParent().setBlack();
                            if (newNode.hasGrandParent()) {
                                newNode.getGrandParent().setRed();
                                leftRotate(newNode.getGrandParent());
                            }
                        }
                    }
                }

            }
            getRoot().setBlack();
        }
    }

    //Deletes the integer i from the binary tree, if it is there
    public void delete(int i) {
        RBNode z = getRoot().search(i);
        if (z == null) {
            return;
        } else {
            RBNode x, y;
            if (!z.hasLeftChild() || !z.hasRightChild()) {
                y = z;
            } else {
                y = successor(z);
            }
            if (y.hasLeftChild()) {
                x = y.getLeftChild();
            } else {
                x = y.getRightChild();
            }
            x.setParent(y.getParent());
            if (getRoot() == y) {
                setRoot(x);
            } else if (y == y.getParent().getLeftChild()) {
                y.getParent().setLeftChild(x);
            } else {
                y.getParent().setRightChild(x);
            }
            if (y != z) {
                z.setKey(y.getKey());
            }
            if (y.isBlack()) {
                deleteFixup(x);
            }
            this.size--;
            if (this.size == 0) {
                this.min = EmptyMinMaxValue;
                this.max = EmptyMinMaxValue;
            } else {
                if (this.min == i) {
                    this.min = getRoot().minValue();
                }
                if (this.max == i) {
                    this.max = getRoot().maxValue();
                }
            }
        }
    }

    //Fixes up tree after a delete action
    private void deleteFixup(RBNode x) {
        RBNode w;
        while (getRoot() != x && x.isBlack()) {
            if (x == x.getParent().getLeftChild()) {
                w = x.getParent().getRightChild();
                if (w.isRed()) {
                    w.setBlack();
                    x.getParent().setRed();
                    leftRotate(x.getParent());
                    w = x.getParent().getRightChild();
                }
                if (w.getLeftChild().isBlack() && w.getRightChild().isBlack()) {
                    w.setRed();
                    x = x.getParent();
                } else {
                    if (w.getRightChild().isBlack()) {
                        w.getLeftChild().setBlack();
                        w.setRed();
                        rightRotate(w);
                        w = x.getParent().getRightChild();
                    }
                    w.setBlack(x.getParent().isBlack());
                    x.getParent().setBlack();
                    w.getRightChild().setBlack();
                    leftRotate(x.getParent());
                    x = getRoot();
                }
            } else {
                w = x.getParent().getLeftChild();
                if (w.isRed()) {
                    w.setBlack();
                    x.getParent().setRed();
                    rightRotate(x.getParent());
                    w = x.getParent().getLeftChild();
                }
                if (w.getRightChild().isBlack() && w.getLeftChild().isBlack()) {
                    w.setRed();
                    x = x.getParent();
                } else {
                    if (w.getLeftChild().isBlack()) {
                        w.getRightChild().setBlack();
                        w.setRed();
                        leftRotate(w);
                        w = x.getParent().getLeftChild();
                    }
                    w.setBlack(x.getParent().isBlack());
                    x.getParent().setBlack();
                    w.getLeftChild().setBlack();
                    rightRotate(x.getParent());
                    x = getRoot();
                }
            }
        }
        x.setBlack();
    }

    //Returns the successor node for a given node in the tree
    private RBNode successor(RBNode x) {
        if (x.hasRightChild()) {
            return x.getRightChild().minNode();
        } else {
            RBNode y = x.getParent();
            while (!y.isNil() && x == y.getRightChild()) {
                x = y;
                y = y.getParent();
            }
            return y;
        }
    }

    //Returns the smallest key in the tree
    public int min() {
        return this.min;
    }

    //Returns the largest key in the tree
    public int max() {
        return this.max;
    }

    //Returns an int[] array containing the values stored in the tree
    public int[] toIntArray() {
        int[] arr = new int[size()];
        getRoot().fillIntArray(arr, 0);
        return arr;
    }

    //Returns true if and only if the tree is a valid red-black tree
    public boolean isValid() {
        if (root.isNil()) {
            return true;
        } else {
            return getRoot().isBSTValid() &&
                    getRoot().isBlackValid() &&
                    getRoot().isRedValid();
        }
    }

    //Returns the maximum depth of a node in the tree
    public int maxDepth() {
        if (empty()) {
            return -1;
        } else {
            return getRoot().maxDepth();
        }
    }

    //Returns the minimum depth of a leaf in the tree
    public int minLeafDepth() {
        if (empty()) {
            return -1;
        } else {
            return getRoot().minLeafDepth();
        }
    }

    //Returns the number of nodes in the tree
    public int size() {
        return size;
    }

    //Returns a string representation of the tree
    public String toString() {
        if (!empty()) {
            return String.format("<Tree %s>", root);
        } else {
            return "<Tree empty>";
        }
    }

    //Applies the Left Rotate action on a given node
    private void leftRotate(RBNode x) {
        RBNode y = x.getRightChild();
        x.setRightChild(y.getLeftChild());
        if (y.hasLeftChild()) {
            y.getLeftChild().setParent(x);
        }
        y.setParent(x.getParent());
        if (!x.hasParent()) {
            setRoot(y);
        } else if (x == x.getParent().getLeftChild()) {
            x.getParent().setLeftChild(y);
        } else {
            x.getParent().setRightChild(y);
        }
        y.setLeftChild(x);
        x.setParent(y);
    }

    //Applies the Right Rotate action on a given node
    private void rightRotate(RBNode x) {
        RBNode y = x.getLeftChild();
        x.setLeftChild(y.getRightChild());
        if (y.hasRightChild()) {
            y.getRightChild().setParent(x);
        }
        y.setParent(x.getParent());
        if (!x.hasParent()) {
            setRoot(y);
        } else if (x == x.getParent().getRightChild()) {
            x.getParent().setRightChild(y);
        } else {
            x.getParent().setLeftChild(y);
        }
        y.setRightChild(x);
        x.setParent(y);
    }

    //public class RBNode
    public class RBNode {
        //Key stored in node (a unique positive integer)
        private int key;
        //True iff the node is black
        private boolean isBlack;
        //Pointer to a left child node
        private RBNode leftChild;
        //Pointer to a right child node
        private RBNode rightChild;
        //Pointer to the parent node
        private RBNode parent;

        //Creates a new node instance, given a key and color
        public RBNode(int key, boolean isBlack) {
            this.key = key;
            this.isBlack = isBlack;
            if (!isNil()) {
                setLeftChild(new RBNode());
                setRightChild(new RBNode());
            }
        }

        //Creates a new black node, given a key
        public RBNode(int key) {
            this(key, true);
        }

        //Creates a new, empty leaf
        public RBNode() {
            this(RBTree.NilValue, true);
        }

        //Returns true if the node is an empty leaf
        private boolean isNil() {
            return this.key == RBTree.NilValue;
        }

        //Return Pointer to parent node
        public RBNode getParent() {
            return this.parent;
        }

        //Returns true if the node has a parent
        public boolean hasParent() {
            return parent != null;
        }

        //Returns the pointer to the node's grandparent node
        public RBNode getGrandParent() {
            return getParent().getParent();
        }

        //Returns true if node has a grandparent node
        public boolean hasGrandParent() {
            return hasParent() && getParent().hasParent();
        }

        //Sets the node's parent node
        public void setParent(RBNode parent) {
            this.parent = parent;
        }

        //Returns node's key value
        public int getKey() {
            return key;
        }

        //Sets the node's key value
        public void setKey(int key) {
            this.key = key;
        }

        //Returns true if the node is black
        public boolean isBlack() {
            return isBlack;
        }

        //Sets node's color to be black
        public void setBlack() {
            this.isBlack = true;
        }

        //Sets node's blackness
        public void setBlack(boolean isBlack) {
            this.isBlack = isBlack;
        }

        //Returns true if node is red
        public boolean isRed() {
            return !isBlack();
        }

        //Sets node's color to be red
        public void setRed() {
            this.isBlack = false;
        }

        //Returns a pointer to the node's left child
        public RBNode getLeftChild() {
            return leftChild;
        }

        //Sets node's left child
        public void setLeftChild(RBNode leftChild) {
            this.leftChild = leftChild;
            if (hasLeftChild()) {
                leftChild.setParent(this);
            }
        }

        //Returns a pointer to the node's right child
        public RBNode getRightChild() {
            return rightChild;
        }

        //Sets node's right child
        public void setRightChild(RBNode rightChild) {
            this.rightChild = rightChild;
            if (hasRightChild()) {
                rightChild.setParent(this);
            }
        }

        //Returns true if the node is a leaf
        public boolean isLeaf() {
            return !hasLeftChild() && !hasRightChild();
        }

        //Returns true if node has a left child
        public boolean hasLeftChild() {
            return !leftChild.isNil();
        }

        //Returns true if node has a right child
        public boolean hasRightChild() {
            return !rightChild.isNil();
        }

        //Returns pointer to node containing a requested key
        public RBNode search(int i) {
            if (isNil()) {
                return null;
            }
            else if (getKey() == i) {
                return this;
            } else {
                if (i < getKey() && hasLeftChild()) {
                    return getLeftChild().search(i);
                } else if (hasRightChild()) {
                    return getRightChild().search(i);
                }
            }
            return null;
        }

        //Returns true iff the requested key is contained
        public boolean contains(int i) {
            return search(i) != null;
        }

        //Inserts a new node below this node
        public boolean insert(RBNode newNode) {
            if (newNode.getKey() < getKey()) {
                if (hasLeftChild()) {
                    return getLeftChild().insert(newNode);
                } else {
                    setLeftChild(newNode);
                    return true;
                }
            } else if (newNode.getKey() > getKey()) {
                if (hasRightChild()) {
                    return getRightChild().insert(newNode);
                } else {
                    setRightChild(newNode);
                    return true;
                }
            } else {
                return false;
            }
        }

        //Returns a pointer to the node containing the smallest key
        private RBNode minNode() {
            if (hasLeftChild()) {
                return getLeftChild().minNode();
            } else {
                return this;
            }
        }

        //Returns the key of minimal node (i.e., minimal key in tree)
        private int minValue() {
            return minNode().getKey();
        }

        //Returns a pointer to the node containing the largest key
        private RBNode maxNode() {
            if (hasRightChild()) {
                return getRightChild().maxNode();
            } else {
                return this;
            }
        }

        //Returns the key of maximal node (i.e., maximal key in tree)
        private int maxValue() {
            return maxNode().getKey();
        }

        //Recursively fill tree's keys in an array
        public int fillIntArray(int[] arr, int loc) {
            if (hasLeftChild()) {
                loc = getLeftChild().fillIntArray(arr, loc);
            }
            arr[loc++] = getKey();
            if (hasRightChild()) {
                loc = getRightChild().fillIntArray(arr, loc);
            }
            return loc;
        }

        //Returns a string representation of the node and its offsprings
        public String toString() {
            String leftString = hasLeftChild() ? getLeftChild().toString() : "x";
            String rightString = hasRightChild() ? getRightChild().toString() : "x";
            return String.format("[ %d%s %s %s ]", getKey(), isBlack() ? "b" : "r", leftString, rightString);
        }

        //Returns the maximum depth of a node in the tree
        public int maxDepth() {
            if (isLeaf()) {
                return 0;
            } else {
                if (hasLeftChild() && hasRightChild()) {
                    return 1 + Math.max(getLeftChild().maxDepth(),
                            getRightChild().maxDepth());
                } else if (hasLeftChild()) {
                    return 1 + getLeftChild().maxDepth();
                } else {
                    return 1 + getRightChild().maxDepth();
                }
            }
        }

        //Returns the minimum depth of a leaf in the tree
        public int minLeafDepth() {
            if (isLeaf()) {
                return 0;
            } else {
                if (hasLeftChild() && hasRightChild()) {
                    return 1 + Math.min(getLeftChild().minLeafDepth(),
                            getRightChild().minLeafDepth());
                } else if (hasLeftChild()) {
                    return 1 + getLeftChild().minLeafDepth();
                } else {
                    return 1 + getRightChild().minLeafDepth();
                }
            }
        }

        //Returns true if and only if the tree is a valid BST
        private boolean isBSTValid() {
            if (isNil()) {
                return true;
            } else {
                if (hasLeftChild() && getKey() < getLeftChild().getKey()) {
                    return false;
                } else if (hasRightChild() && getKey() > getRightChild().getKey()) {
                    return false;
                } else {
                    return getLeftChild().isBSTValid() &&
                            getRightChild().isBSTValid();
                }
            }
        }

        //Returns true iff node and its offsprings follow the Red rule
        private boolean isRedValid() {
            if (isLeaf()) {
                return true;
            } else {
                if (isBlack()) {
                    if (hasLeftChild() && hasRightChild()) {
                        return getLeftChild().isRedValid() && getRightChild().isRedValid();
                    } else if (hasLeftChild()) {
                        return getLeftChild().isRedValid();
                    } else {
                        return getRightChild().isRedValid();
                    }
                } else {
                    if (hasLeftChild() && hasRightChild()) {
                        return getLeftChild().isBlack() && getLeftChild().isRedValid() &&
                                getRightChild().isBlack() && getRightChild().isRedValid();
                    } else if (hasLeftChild()) {
                        return getLeftChild().isBlack() && getLeftChild().isRedValid();
                    } else {
                        return getRightChild().isBlack() && getRightChild().isRedValid();
                    }
                }
            }
        }

        //Returns the node's black depth
        private int blackDepth() {
            int me = isBlack() ? 1 : 0;
            if (hasLeftChild()) {
                return me + getLeftChild().blackDepth();
            } else {
                return me;
            }
        }

        //Returns true iff node and its offsprings follow the Black rule
        private boolean isBlackValid() {
            if (isLeaf()) {
                return true;
            } else {
                if (hasRightChild() && hasLeftChild()) {
                    return getRightChild().blackDepth() == getLeftChild().blackDepth();
                } else if (hasLeftChild()) {
                    return getLeftChild().blackDepth() == 0;
                } else {
                    return getRightChild().blackDepth() == 0;
                }
            }
        }
    }


    public static void main(String[] args) {
        RBTree tree = new RBTree();
        tree.insert(1);
        tree.insert(12);
        tree.insert(13);
        tree.insert(2);
        tree.delete(2);
        System.out.print(tree);
    }

}