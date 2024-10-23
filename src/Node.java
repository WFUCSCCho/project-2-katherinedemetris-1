/*∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗
    ∗ @file: Node.java
    ∗ @description: This class implements a generic node for use in a
       Binary Search Tree (BST). Each node contains a generic element,
       a reference to its left child, and a reference to its right child.
       It also provides methods to manipulate and access these members.
    ∗ @author: Katherine Demetris
    ∗ @date: September 22 , 2024
∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗*/

public class Node <T extends Comparable<T>>  {

    private T element; // element for this node
    private Node<T> left; // pointer for left child
    private Node<T> right; // pointer for right child

    // Implement the constructor
    public Node(){
        element = null;
        left = right = null; // initialize left and right node as null
    }

    // Constructor to initialize a node with a given element
    public Node(T val) {
        element = val; // initialize element to provided val
        left = right = null;
    }

    // Constructor to initialize a node with a given element and child nodes.
    public Node(T val, Node<T> l, Node<T> r){
        left = l;
        right = r;
        element = val;
    }

    // Implement the setElement method
    public void setElement(T e){ //sets the element stored in this node
        element = e; // update the element stored in the node
    }

    // Implement the setLeft method
    public void setLeft(Node<T> p) { // sets the left child of this node
        left = p;
    }

    // Implement the setRight method
    public void setRight(Node<T> p) { // sets the right child of this node
        right = p;
    }

    // Implement the getLeft method
    public Node<T> getLeft() { // get the left child
        return left;
    }

    // Implement the getRight method
    public Node<T> getRight() { // get the right child
        return right;
    }

    // Implement the getElement method
    public T getElement() { // get the element
        return element;
    }

    // Implement the isLeaf method
    public boolean isLeaf() { // checks if the node is a leaf --> essentially has no children
        return left == null && right == null;
    }
}
