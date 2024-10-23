/*∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗
    ∗ @file: BST.java
    ∗ @description: This program implements a generic
      Binary Search Tree (BST) with operations such as insertion,
      removal, and search, as well as an iterator interface for
      traversing the tree in ascending order.
    ∗ @author: Katherine Demetris
    ∗ @date: September 22 , 2024
∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗*/


import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class BST<T extends Comparable<T>> {

    private Node<T> root; // Root of the BST
    private int nodeCount; // Number of nodes in the BST


    // Implement the constructor
    public BST() {
        root = null;
        nodeCount = 0;
    }

    // Implement the clear method
    public void clear() { // Clears the BST by setting the root to null and resetting the node count.
        root = null;
        nodeCount = 0;
    }

    // Implement the size method
    public int size() { // Returns the number of nodes in the BST.
        return nodeCount;
    }

    //Implement the insert method
    public void insert(T e) { // Inserts a new element into the BST.
        root = insertHelp(root, e);
        nodeCount++;
    }

    // Implement a search method
    public Node<T> find(T key) { // Searches for a node with the given key in the BST.
        return findHelp(root, key);
    }

    // Implement remove method
    public Node<T> remove(T key) { // Removes the node with the given key from the BST.
        Node<T> temp = findHelp(root, key); // First find it
        if (temp != null) {
            root = removeHelp(root, key); // Now remove it
            nodeCount--; // decreases nodeCount by one as we have removed the node
        }
        return temp;
    }

    private Node<T> insertHelp(Node<T> rt, T e) { // Helper method to insert a new element into the BST.
        if (rt == null) { // If the current root is null
            return new Node<T>(e); // create a new node with the element
        }
        if (rt.getElement().compareTo(e) >= 0) { // If the element is less than or equal to the current node
            rt.setLeft(insertHelp(rt.getLeft(), e)); // insert into the left subtree
        } else {
            rt.setRight(insertHelp(rt.getRight(), e)); // else insert into the right subtree
        }
        return rt; // return root of the modified subtree
    }


    private Node<T> findHelp(Node<T> rt, T key) { // helper method to find a node with a given key
        if (rt == null) { // if the root is null then the key is not found
            return null;
        }
        if (rt.getElement().compareTo(key) > 0) { // if the key is less than the current node's element --> search the left subtree
            return findHelp(rt.getLeft(), key);
        } else if (rt.getElement().compareTo(key) == 0) {
            return rt; // if the key is found --> return the current node
        } else {
            return findHelp(rt.getRight(), key); // else search the right subtree.
        }
    }

    private Node<T> deleteMax(Node<T> rt) { // helper method to delete the maximum node from a subtree
        if (rt.getRight() == null) {
            return rt.getLeft(); // if the right child is null then return the left child
        }
        rt.setRight(deleteMax(rt.getRight())); // recursively delete the maximum node from the right subtree.
        return rt;
    }

    private Node<T> getMax(Node<T> rt) { // helper method to get the maximum node in the subtree
        if (rt.getRight() == null) { // if the right child is null then this is the maximum node
            return rt;
        }
        return getMax(rt.getRight()); // recursively find the maximum node in the right subtree
    }

    private Node<T> removeHelp(Node<T> rt, T key) { // helper method to remove a node with a given key from the subtree
        if (rt == null) { // if the root is null the key is not found so return null
            return null;
        }
        if (rt.getElement().compareTo(key) > 0) { // if the key is less than the current node's element
            rt.setLeft(removeHelp(rt.getLeft(), key)); // remove from the left subtree
        } else if (rt.getElement().compareTo(key) < 0) { // if the key is greater than the current node
            rt.setRight(removeHelp(rt.getRight(), key)); // remove from the right subtree
        } else { //found it
            if (rt.getLeft() == null) { // node with no children or one left child
                return rt.getRight();
            } else if (rt.getRight() == null) { // node with no children or one right child
                return rt.getLeft();
            } else { // two children
                Node<T> temp = getMax(rt.getLeft());
                rt.setElement(temp.getElement());
                rt.setLeft(deleteMax(rt.getLeft())); // remove max node from left subtree
            }
        }
        return rt;
    }


    // Implement the iterator method
    public Iterator<T> iterator() {
        return new BSTIterator(root);
    }

    // Implement the BSTIterator class
    private class BSTIterator implements Iterator<T> {
        private Stack<Node<T>> stack = new Stack<>();

        // Constructor
        public BSTIterator(Node<T> root) {
            // Push all the leftmost nodes from the root onto the stack
            pushLeft(root);
        }

        // Helper function to push leftmost nodes onto the stack
        private void pushLeft(Node<T> node) {
            while (node != null) {
                stack.push(node);
                node = node.getLeft();
            }
        }

        // Check if there's a next element
        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        // Return the next element in the traversal
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements in the BST.");
            }

            // Pop the top item from the stack (this is the current smallest node)
            Node<T> node = stack.pop();

            // If the node has a right child, push all the leftmost nodes of the right subtree
            if (node.getRight() != null) {
                pushLeft(node.getRight());
            }

            // Return the value of the popped node
            return node.getElement();
        }
    }

}
