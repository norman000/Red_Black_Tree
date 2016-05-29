Red-black Tree (on Java)

Based on
http://www.java-tips.org/java-se-tips/java.lang/red-black-tree-implementation-in-java.html
and project n0nick on github.com
 
A red-black tree is a type of self-balancing binary search tree, a data structure used in computer science.
The self-balancing is provided by painting each node with one of two colors (these are typically called 'red' and 'black', hence the name of the trees) in such a way that the resulting painted tree satisfies certain properties that don't allow it to become significantly unbalanced. When the tree is modified, the new tree is subsequently rearranged and repainted to restore the coloring properties. The properties are designed in such a way that this rearranging and recoloring can be performed efficiently.

Properties:
    A node is either red or black.
    The root is black. (This rule is sometimes omitted. Since the root can always be changed from red to black, but not necessarily vice-versa, this rule has little effect on analysis.)
    All leaves (NIL) are black. (All leaves are same color as the root.)
    Every red node must have two black child nodes.
    Every path from a given node to any of its descendant leaves contains the same number of black nodes.

Project have one class RedBlackTree

Base methods implemented: insert, remove, print and so more others methods

Rotation 
In discrete mathematics, tree rotation is an operation on a binary tree that changes the structure without interfering with the order of the elements. A tree rotation moves one node up in the tree and one node down. It is used to change the shape of the tree, and in particular to decrease its height by moving smaller subtrees down and larger subtrees up, resulting in improved performance of many tree operations.

For Rotation two methods: rotateWithLeftChild and rotateWithRightChild

You can see more about this in "Introduction to Algorithms" Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest. In this book you can see more information about this algoritms.

How use this:
For example you can insert something elements 
Remember! The equal elements in a binary tree will not be.
You can print all elements in tree Or delete element from tree
And more: for example
Min
Max
TointArray
Size
Depth (Returns the maximum depth) 
If you want more look comments in the code.

There is a different view of trees.
toString (method for string representation of the tree)
See more in "The Art of Computer Programming" Donald Knuth.
