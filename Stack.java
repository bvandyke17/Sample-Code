// bvandyke17@georgefox.edu
// program 7
// 2018-11-06

// import utilities
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *The Stack class represents a last-in-first-out (LIFO) stack of objects.
 * @author Brett Van Dyke <bvandyke17@georgefox.edu>
 */
public class Stack<E> implements Iterable<E> {

    // initiate head variable and depth
    private StackNode _head;
    private int _depth;

    /**
     * Constructs an empty stack.
     */
    public Stack()
    {
        // assign instances
        _head = null;
        _depth = 0;
    }

    /**
     * Pushes an item onto the top of this stack.
     * @param element the element to be pushed
     * @return returns true
     * @throws OutOfMemoryError if there is not enough memory
     */
    public boolean push(E element) throws OutOfMemoryError
    {
        // initiate variable for new node
        StackNode addedNode = new StackNode(element);

        // set new node's previous to current head
        if(_depth != 0)
        {
            addedNode.setPrevious(_head);
        }

        // assign head to the added node to the stack
        _head = addedNode;

        _depth++;

        // validate memory
        if (_depth >= Integer.MAX_VALUE)
        {
            throw new OutOfMemoryError();
        }

        return true;
    }

    /**
     * Pushes all iterable elements into a stack.
     * @param elements the elements to be pushed
     */
    public void pushAll(Iterable<E> elements)
    {
        for (E element : elements)
        {
            push(element);
        }
    }

    /**
     * Looks at the object at the top of this stack without removing it from the
     * stack.
     * @return returns the value of the element
     * @throws NoSuchElementException if the stack is empty
     */
    public E peek() throws NoSuchElementException
    {
        // validate depth
        if (isEmpty())
        {
            throw new NoSuchElementException();
        }

        return (E)_head.getValue();
    }

    /**
     * Removes the object at the top of this stack and returns that object as
     * the value of this function.
     * @return the value of the popped element
     * @throws NoSuchElementException if the stack is empty
     */
    public E pop() throws NoSuchElementException
    {
        StackNode nextNodeUp;
        StackNode poppedNode;

        // validate depth
        if (isEmpty())
        {
            throw new NoSuchElementException();
        }

        // assign node to return value
        poppedNode = _head;

        // set previous node and assign it to the new head if more than 1 node
        // in stack
        if (_depth > 1)
        {
            nextNodeUp =  _head.getPrevious();
            _head = nextNodeUp;
        }

        _depth--;

        return (E)poppedNode.getValue();
    }

    /**
     * Removes all of the elements from this stack.
     */
    public void clear()
    {
        // reset node pointeer and size of stack
        _head = null;
        _depth = 0;
    }

    /**
     * Returns how many objects are in the stack.
     * @return the number of objects in the stack
     */
    public int depth()
    {
        return _depth;
    }

    /**
     * Returns true if the stack has an element, false if otherwise
     * @return returns true if the list has at least one element, false if no
     * elements in the stack
     */
    public boolean isEmpty()
    {
        return _depth == 0;
    }

    /**
     * Returns a stack iterator.
     * @return Returns a stack iterator
     */
    public Iterator iterator()
    {
        return new StackIterator();
    }

    /**
     * This class allows iteration for the Stack and implements the Iterator
     * interface.
     * @param <E> The type of element
     */
    class StackIterator<E> implements Iterator<E> {

        /**
         * Constructs a stack iterator.
         */
        public StackIterator()
        {
            // no code because there are no instances to set
        }

        /**
         * Returns true if the iteration has more elements.
         * @return returns true if there is more elements. Returns false if
         * there are no more elements.
         */
        public boolean hasNext()
        {
            return depth() != 0;
        }

        /**
         * Returns the next element in the iteration.
         * @return returns the next element of iteration.
         * @throws NoSuchElementException NoSuchElementException if there are no
         * more elements.
         */
        public E next() throws NoSuchElementException
        {
            // validate depth
            if (isEmpty())
            {
                throw new NoSuchElementException();
            }

            return (E)pop();
        }
    }

    /**
     * This class creates a node that contains the elements of the stack.
     * The stack is made up of these node carriers.
     * @param <E> the type of element
     */
    class StackNode<E> {

        // initate instances
        private E _value;
        private StackNode _prev;
        private StackNode _next;

        /**
         * Constructs a stack node with a given element value.
         * @param value the value of the element
         */
        public StackNode(E value)
        {
            // assign instances
            _value = value;
            _prev = null;
            _next = null;
        }

        /**
         * Returns the value of the node's element.
         * @return Returns the value
         */
        public E getValue()
        {
            return _value;
        }

        /**
         * Returns the previous node.
         * @return Returns the previous stack node
         */
        public StackNode getPrevious()
        {
            return _prev;
        }

        /**
         * Sets the previous stack node.
         * @param prev the node to set as the previous
         */
        public void setPrevious(StackNode<E> prev)
        {
            _prev = prev;
        }
    }
}
