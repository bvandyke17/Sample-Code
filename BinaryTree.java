// bvandyke17@georgefox.edu
// program 10
// 2018-11-28

// import utilities
import static java.lang.Integer.max;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Stack;

/**
 * This class is a recursive binary tree that implements the iterable interface.
 * Each tree node is a Binary Tree that has the same characteristics.
 * @author Brett Van Dyke <bvandyke17@georgefox.edu>
 */
public class BinaryTree<E> implements Iterable<E> {

    // assign variables
    private E _element;
    private BinaryTree<E> _parent;
    private BinaryTree<E> _left;
    private BinaryTree<E> _right;

    /**
     * A constructor to create a binary tree with the first element as the
     * parameter.
     * @param element the first element of the tree
     */
    public BinaryTree(E element)
    {
        // assign variables
        _parent = null;
        _left = null;
        _right = null;
        _element = element;
    }

    /**
     * Gets the element of a tree.
     * @return the tree's element
     */
    public E getElement()
    {
        return _element;
    }

    /**
     * Sets the element of a tree.
     * @param element the element to set
     */
    public void setElement(E element)
    {
        _element = element;
    }

    /**
     * Checks if the tree has a left child.
     * @return true if there is a left child, false if otherwise
     */
    public boolean hasLeftChild()
    {
        return getLeftChild() != null;
    }

    /**
     * Returns the left child of a tree.
     * @return the left child and its children
     */
    public BinaryTree<E> getLeftChild()
    {
        return _left;
    }

    /**
     * Sets the left child of a tree node if the child in the parameter is not
     * the node itself or an ancestor of that tree.
     * @param child the child tree to set
     * @return the left child tree and it's descendants
     * @throws IllegalArgumentException if the param child is the tree itself
     * or an ancestor of said tree.
     */
    public BinaryTree<E> setLeftChild(BinaryTree<E> child)
            throws IllegalArgumentException
    {
        BinaryTree returnedChild;

        // validate child to set
        if (child != null && child.isAncestorOf(this) || child == this)
        {
            throw new IllegalArgumentException();
        }

        // assign returned child
        returnedChild = getLeftChild();

        if (child != null && child.getParent() != null)
        {
            if (child.getParent().getLeftChild() == child)
            {
                // detach child from parent if left
                child.getParent().setLeftChild(null);
            }

            else
            {
                // detach child from parent if right
                child.getParent().setRightChild(null);
            }
        }

        if (hasLeftChild())
        {
            // set parent to null
            getLeftChild().setParent(null);
        }

        // assign left child
         _left = child;

         // assign parent if not null
        if (child != null)
        {
           getLeftChild().setParent(this);
        }

        return returnedChild;
    }

    /**
     * Checks if the tree has a right child.
     * @return true if there is a right child, false if otherwise
     */
    public boolean hasRightChild()
    {
        return getRightChild() != null;
    }

    /**
     * Returns the right child of a tree.
     * @return the right child and its children
     */
    public BinaryTree<E> getRightChild()
    {
        return _right;
    }

    /**
     * Sets the right child of a tree if the child in the parameter is not
     * the node itself or an ancestor of that node.
     * @param child the child tree to set
     * @return the right child tree and it's descendants
     * @throws IllegalArgumentException if the param child is the node itself
     * or an ancestor of said tree.
     */
    public BinaryTree<E> setRightChild(BinaryTree<E> child)
            throws IllegalArgumentException
    {
        BinaryTree returnedChild;

        // validate child to set
        if (child != null && child.isAncestorOf(this) || child == this)
        {
            throw new IllegalArgumentException();
        }

        // assign child to return
        returnedChild = getRightChild();

        if (child != null && child.getParent() != null)
        {
            // detach child from parent if left
            if (child.getParent().getLeftChild() == child)
            {
                child.getParent().setLeftChild(null);
            }

            else
            {
                // detach child from parent if right
                child.getParent().setRightChild(null);
            }
        }

        if (hasRightChild())
        {
            // set parent to null if there is one
            getRightChild().setParent(null);
        }

        // assign right child and parent
        _right = child;

        // set parent if not null
        if (child != null)
        {
           getRightChild().setParent(this);
        }

        return returnedChild;
    }

    /**
     * Returns the root of a tree.
     * @return the tree's root
     */
    public BinaryTree<E> getRoot()
    {
        BinaryTree root;

        // assign root to this tree if it has no parent
        if (getParent() == null)
        {
            root = this;
        }

        // call the parent of this tree to get it's root
        else
        {
            root = getParent().getRoot();
        }

        return root;
    }

    /**
     * Returns the parent tree.
     * @return the parent tree
     */
    public BinaryTree<E> getParent()
    {
        return _parent;
    }

    /**
     * This is a private method that sets the parent of a tree.
     * @param parent the parent tree to set to
     */
    private void setParent(BinaryTree<E> parent)
    {
        _parent = parent;
    }

    /**
     * Returns the size of a tree.
     * @return the tree's size
     */
    public int size()
    {
        // assign size to account for the node that is calling
        int size = 1;

        // add left children's size
        if (hasLeftChild())
        {
            size += getLeftChild().size();
        }

        // add right children's size
        if (hasRightChild())
        {
            size += getRightChild().size();
        }

        return size;
    }

    /**
     * Returns the height of a tree.
     * @return the tree's height
     */
    public int height()
    {
        // 1 will be added on return to account for the calling tree to make
        // zero based
        int leftHeight = -1;
        int rightHeight = -1;

        // assign the height of the left children
        if (hasLeftChild())
        {
            leftHeight = getLeftChild().height();
        }

        // assign the height of the right children
        if (hasRightChild())
        {
            rightHeight = getRightChild().height();
        }

        // return the tree with the longest height and add 1 for calling tree
        return max(rightHeight, leftHeight) + 1;
    }

    /**
     * Returns the level of a tree.
     * @return the tree's level
     */
    public int level()
    {
        // assign variable for zero based level
        int level = 0;

        // get the level of the tree's parents and add 1 to account for calling
        // tree
        if (getParent() != null)
        {
            level = getParent().level() + 1;
        }

        return level;
    }

    /**
     * Returns the degree of a tree.
     * @return the tree's degree
     */
    public int degree()
    {
        // assign variable to 1 if tree has 1 child
        int treeDegree = 1;

        // assign variable to zero if tree has no children
        if (!hasLeftChild() && !hasRightChild())
        {
            treeDegree = 0;
        }

        // assign variable to 2 if tree has both children
        else if (hasLeftChild() && hasRightChild())
        {
            treeDegree = 2;
        }

        return treeDegree;
    }

    /**
     * Checks if the tree is a root.
     * @return true if the tree is a root, false otherwise
     */
    public boolean isRoot()
    {
        return getParent() == null;
    }

    /**
     * Checks if the tree is a parent to at least 1 child.
     * @return true if the tree has at least 1 child, false otherwise
     */
    public boolean isParent()
    {
        return getLeftChild() != null || getRightChild() != null;
    }

    /**
     * Checks if the tree is a child to a parent.
     * @return returns true if the tree is a child, false otherwise
     */
    public boolean isChild()
    {
        return getParent() != null;
    }

    /**
     * Checks if the tree is a leaf.
     * @return true if the tree is a leaf, false otherwise
     */
    public boolean isLeaf()
    {
        return getLeftChild() == null && getRightChild() == null;
    }

    /**
     * Checks if a tree is full.
     * @return true if full, false otherwise
     */
    public boolean isFull()
    {
        // assign variable
        boolean full = false;

        // check degree 0 and assign variable
        if (degree() == 0)
        {
            full = true;
        }

        // check degree 1 and assign variable
        else if (degree() == 1)
        {
            full = false;
        }

        // check remaining tree for equal height and assign variable
        else if (getRightChild().height() == getLeftChild().height()
                && getRightChild().isFull() && getLeftChild().isFull())
        {
            full = true;
        }

        return full;
    }

    /**
     * Checks if a tree is complete
     * @return true if complete, false otherwise
     */
    public boolean isComplete()
    {
        boolean completeTree;

        switch (degree())
        {
            // assign variable with no children as true
            case 0:
                completeTree = true;
                break;

            // assign variable with 1 child to true if there is a left child and
            // the left child's size is 1
            case 1:
                completeTree = hasLeftChild() && getLeftChild().size() == 1;
                break;

            // assign variable with full left tree and complete right tree
            // and complete left tree and full right tree as true
            default:
                completeTree = degree() == 2 && getLeftChild().isFull()
                        && getRightChild().isComplete()
                        && getLeftChild().height() == getRightChild().height()
                        || getLeftChild().isComplete()
                        && getRightChild().isFull()
                        && getLeftChild().height()
                        == (getRightChild().height() + 1);
                break;
        }

        return completeTree;
    }

    /**
     * Checks if a tree is degenerate.
     * @return true if degenerate, false otherwise
     */
    public boolean isDegenerate()
    {
        return height() + 1 == size();
    }

    /**
     * Checks if a tree is ancestor of a given tree.
     * @param descendant the tree to check if it is the descendant
     * @return true if the tree is an ancestor, false otherwise
     * @throws IllegalArgumentException if param is null
     */
    public boolean isAncestorOf(BinaryTree<E> descendant)
            throws IllegalArgumentException
    {
        // validate descendant
        if (descendant == null)
        {
            throw new IllegalArgumentException();
        }

        return descendant.isDescendantOf(this);
    }

    /**
     * Checks if a tree is a parent of a given tree.
     * @param child the child tree to check relationship
     * @return true if it is the parent, false otherwise
     * @throws IllegalArgumentException if param
     */
    public boolean isParentOf(BinaryTree<E> child)
            throws IllegalArgumentException
    {
        // validate child
        if (child == null)
        {
            throw new IllegalArgumentException();
        }

        return child.getParent() == this;
    }

    /**
     * Checks if a tree is a sibling of a given tree.
     * @param sibling the sibling tree to check relationship
     * @return true if the tree is a sibling, false otherwise
     * @throws IllegalArgumentException if param is null
     */
    public boolean isSiblingOf(BinaryTree<E> sibling)
            throws IllegalArgumentException
    {
        // validate sibling
        if (sibling == null)
        {
            throw new IllegalArgumentException();
        }

        return getParent() == sibling.getParent() && getParent() != null &&
                sibling != this;
    }

    /**
     * Checks if a tree is a child of a given tree.
     * @param parent the parent tree to check relationship
     * @return true if it is a child of said tree, false if otherwise
     * @throws IllegalArgumentException if param is null
     */
    public boolean isChildOf(BinaryTree<E> parent)
            throws IllegalArgumentException
    {
        // validate parent
        if (parent == null)
        {
            throw new IllegalArgumentException();
        }

        return getParent() == parent;
    }

    /**
     * Checks if a tree is a descendant of a given tree.
     * @param ancestor the ancestor tree to check relationship
     * @return true if the tree is a descendant, false if otherwise
     * @throws IllegalArgumentException if param is null
     */
    public boolean isDescendantOf(BinaryTree<E> ancestor)
            throws IllegalArgumentException
    {
        boolean descendant;

        // validate ancestor
        if (ancestor == null)
        {
            throw new IllegalArgumentException();
        }

        // assign variable tree is root
        if (isRoot())
        {
            descendant = false;
        }

        // assign variable if ancestor is a parent
        else if (getParent() == ancestor)
        {
            descendant = true;
        }

        // get the parent and check descendant
        else
        {
            descendant = getParent().isDescendantOf(ancestor);
        }

        return descendant;
    }

    /**
     * Returns by default an in-order Binary Tree iterator.
     * @return an in-order iterator
     */
    public Iterator<E> iterator()
        {
            return new InOrderIterator();
        }

    /**
     * Returns a pre-order Binary Tree iterator.
     * @return a pre-order iterator
     */
    public Iterator<E> preOrderIterator()
        {
            return new PreOrderIterator();
        }

    /**
     * Returns an in-order Binary Tree iterator.
     * @return an in-order iterator
     */
    public Iterator<E> inOrderIterator()
        {
            return new InOrderIterator();
        }

    /**
     * Returns a post-order Binary Tree iterator.
     * @return a post-order iterator
     */
    public Iterator<E> postOrderIterator()
        {
            return new PostOrderIterator();
        }

    /**
     * Returns a level-order Binary Tree iterator.
     * @return a level-order iterator
     */
    public Iterator<E> levelOrderIterator()
        {
            return new LevelOrderIterator();
        }

    /**
     * This class creates an iterator that is pre order traversal with
     * supporting methods and implements the iterator interface.
     * @param <E> the type of element
     */
    private class PreOrderIterator<E> implements Iterator<E> {

        // assign instance variable
        private Stack<BinaryTree> _stack;

        /**
         * Constructs a pre-order iterator.
         */
        public PreOrderIterator()
        {
            // create empty stack
            _stack = new Stack();

            // push root tree to stack
            _stack.push(getRoot());
        }

        /**
         * Returns true if the iterator has more elements.
         * @return true if more elements, false if otherwise
         */
        public boolean hasNext()
        {
           return !_stack.isEmpty();
        }

        /**
         * Returns the next element in the iteration
         * @return the element of a tree
         * @throws NoSuchElementException if there are no more elements
         */
        public E next() throws NoSuchElementException
        {
            // intiate variable
            BinaryTree<E> tempTree;

            // validate next element
            if (!hasNext())
            {
                throw new NoSuchElementException();
            }

            // assign variable to popped tree
            tempTree = _stack.pop();

            if (tempTree.hasRightChild())
            {
                // push right tree
                _stack.push(tempTree.getRightChild());
            }

            if (tempTree.hasLeftChild())
            {
                // push left tree
                _stack.push(tempTree.getLeftChild());
            }

            return tempTree.getElement();
        }
    }

    /**
     * This class creates an iterator that is in-order traversal with
     * supporting methods and implements the iterator interface.
     * @param <E> the type of element
     */
    private class InOrderIterator<E> implements Iterator<E> {

        // initialize private variables
        private Queue<BinaryTree> _elements;
        private int _next;

        /**
         * This private method supports the traversal of the iterator by adding
         * trees to the queue.
         * @param tree the tree to add to the queue
         */
        private void traverse(BinaryTree tree)
        {
            // traverse in left, root, right order
            if (tree != null)
            {
                // get left children
                traverse(tree.getLeftChild());

                // enqueue children
                _elements.add(tree);

                // get right children
                traverse(tree.getRightChild());
            }
        }

        /**
         * Constructs an in-order Binary Tree iterator.
         */
        public InOrderIterator()
        {
            // assign variables
            _elements = new LinkedList();
            _next = 0;
        }

        /**
         * Returns true if the iterator has more elements.
         * @return true if more elements, false if otherwise
         */
        public boolean hasNext()
        {
            return _next < size();
        }

        /**
         * Returns the next element in the iteration.
         * @return the tree's element
         * @throws NoSuchElementException if there are no more elements to
         * iterate
         */
        public E next() throws NoSuchElementException
        {
            // initiate variable
            BinaryTree<E> tempTree;

            // validate next element
            if (!hasNext())
            {
                throw new NoSuchElementException();
            }

            // add trees to queue
            traverse(getRoot());

            // assign variable and dequeue
            tempTree = _elements.poll();

            // increment count
            _next++;

            return tempTree.getElement();
        }
    }

    /**
     * This class creates an iterator that is post-order traversal with
     * supporting methods and implements the iterator interface.
     * @param <E> the type of element
     */
    private class PostOrderIterator<E> implements Iterator<E> {

        // initiate private variables
        private Queue<BinaryTree> _elements;
        private int _next;

        /**
         * This private method supports the traversal of the iterator by adding
         * trees to the queue.
         * @param tree the tree to add to the queue
         */
        private void traverse(BinaryTree tree)
        {
            // travers in left, right, root order
            if (tree != null)
            {
                // traverse left
                traverse(tree.getLeftChild());

                // traverse right
                traverse(tree.getRightChild());

                // enqueue
                _elements.add(tree);
            }
        }

        /**
         * Constructs a post-order iterator.
         */
        public PostOrderIterator()
        {
            // assign instance variables
            _elements = new LinkedList();
            _next = 0;
        }

        /**
         * Returns true if the iterator has more elements.
         * @return true if more elements, false if otherwise
         */
        public boolean hasNext()
        {
            return _next < size();
        }

        /**
         * Returns the next element in the iteration.
         * @return the tree's element
         * @throws NoSuchElementException if there are no more elements to
         * iterate
         */
        public E next() throws NoSuchElementException
        {
            BinaryTree<E> tempTree;

            // validate next element
            if (!hasNext())
            {
                throw new NoSuchElementException();
            }
            // traverse starting at root
            traverse(getRoot());

            // assign variable and dequeue tree
            tempTree = _elements.poll();

            // increment count
            _next++;

            return tempTree.getElement();
        }
    }

    /**
     * This class creates an iterator that is level-order traversal with
     * supporting methods and implements the iterator interface.
     * @param <E> the type of element
     */
    private class LevelOrderIterator<E> implements Iterator<E> {

        // initiate instance variable
        private Queue<BinaryTree> _currentTree;

        /**
         * Constructs a new level order iterator.
         */
        public LevelOrderIterator()
        {
            // create and assign new queue
            _currentTree = new LinkedList<BinaryTree>();

            // queue root tree
            _currentTree.add(getRoot());
        }

        /**
         * Returns true if the iterator has more elements.
         * @return true if more elements, false if otherwise
         */
        public boolean hasNext()
        {
            return !_currentTree.isEmpty();
        }

        /**
         * Returns the next element in the iteration.
         * @return the tree's element
         * @throws NoSuchElementException if there are no more elements to
         * iterate
         */
        public E next() throws NoSuchElementException
        {
            BinaryTree<E> tempTree;

            // validate next element
            if (!hasNext())
            {
                throw new NoSuchElementException();
            }

            // assign variable and dequeue
            tempTree = _currentTree.poll();

            // enqueue left child
            if (tempTree.hasLeftChild())
            {
                _currentTree.add(tempTree.getLeftChild());
            }

            // enqueue right child
            if (tempTree.hasRightChild())
            {
                _currentTree.add(tempTree.getRightChild());
            }

            return tempTree.getElement();
        }
    }

    /**
     * Returns the tree as a string in in-order traversal.
     * @return an in-order traversal of the tree as a string
     */
    public String toString()
    {
        // create and assign new in-order iterator
        Iterator<E> itr = this.inOrderIterator();

        // create and assign new arraylist
        ArrayList<E> list = new ArrayList();

        // add elements in order to array list
        while (itr.hasNext())
        {
            list.add(itr.next());
        }

        return list.toString();
    }
}
