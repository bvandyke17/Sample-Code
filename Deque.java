// bvandyke17@georgefox.edu
// program 9
// 2018-11-06

// import utilities
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A linear collection that implements the iterable interface and supports
 * element insertion and removal at both ends. This interface defines methods to
 * access the elements at both ends of the deque. Methods are provided to
 * insert, remove, and examine the element.
 * @author Brett Van Dyke <bvandyke17@georgefox.edu>
 */
public class Deque<E> implements Iterable<E> {

    // initiate instance variables
    private DequeNode _head;
    private DequeNode _tail;
    private int _depth;

    /**
     * Constructs an empty deque.
     */
    public Deque()
    {
        // assign instance variables
        _head = null;
        _tail = null;
        _depth = 0;
    }

    /**
     * Inserts the specified element at the end of this deque. Throws error if
     * there is not enough memory to support the call.
     * @param element the element to enqueue
     * @return returns true
     * @throws OutOfMemoryError if there is not enough memory for the operation
     */
    public boolean enqueue(E element) throws OutOfMemoryError
    {
        // create new element
        DequeNode addedNode = new DequeNode(element);

        // set the existing tails previous reference to the new node and the
        // new node's next reference to the current tail if deque is not empty
        if (_depth != 0)
        {
            _tail.setPrevious(addedNode);
            addedNode.setNext(_tail);
        }
            // assign tail to the new node
            _tail = addedNode;

        // assign head to new node if there are no nodes in the deque
        if (isEmpty())
        {
            _head = _tail;
        }

        _depth++;

        // validate memory
        if (_depth >= Integer.MAX_VALUE)
        {
            throw new OutOfMemoryError();
        }

        return true;
    }

    /**
     * Enqueues all iterable elements into the deque.
     * @param elements the elements to enqueue
     */
    public void enqueueAll(Iterable<E> elements)
    {
        for (E element : elements)
        {
            enqueue(element);
        }
    }

    /**
     * Enqueues an element to the head of the deque.
     * @param element the element to enqueue
     * @return true
     * @throws OutOfMemoryError if there is not enough memory for the operation
     */
    public boolean enqueueHead(E element) throws OutOfMemoryError
    {
        // create new node
        DequeNode addedNode = new DequeNode(element);

        // set new node's previous to current head if the deque isn't empty
        if(_depth != 0)
        {
            addedNode.setPrevious(_head);
        }

        // assign head as added node
        _head = addedNode;

        // assign tail and head to same node if it is the only node in deque
        if (_depth == 0)
        {
            _tail = _head;
        }

        _depth++;

        // validate memory
        if (_depth >= Integer.MAX_VALUE)
        {
            throw new OutOfMemoryError();
        }

        return true;
    }

    /**
     * Retrieves, but does not remove, the first element of this deque.
     * @return the head element in the deque
     * @throws NoSuchElementException if the deque is empty
     */
    public E head() throws NoSuchElementException
    {
        // validate depth
        if (isEmpty())
        {
            throw new NoSuchElementException();
        }

        return (E)_head.getValue();
    }

    /**
     * Retrieves, but does not remove, the last element of this deque.
     * @return the tail element in the deque
     * @throws NoSuchElementException if the deque is empty
     */
    public E tail() throws NoSuchElementException
    {
        // validate depth
        if (isEmpty())
        {
            throw new NoSuchElementException();
        }

        return (E)_tail.getValue();
    }

    /**
     * Retrieves and removes the first element of this deque.
     * @return the first element in the deque
     * @throws NoSuchElementException if the deque is empty
     */
    public E dequeue() throws NoSuchElementException
    {
        // assing node to remove
        DequeNode removedNode = _head;

        // validate depth
        if (isEmpty())
        {
            throw new NoSuchElementException();
        }

        // remove all references and reset the size if there is one last
        if (_depth == 1)
        {
            clear();
        }

        else
        {
            // assing head to the previous node
            _head = removedNode.getPrevious();

            // set the new head's next to null
            _head.setNext(null);

            // decrement
            _depth--;
        }

        return (E)removedNode.getValue();
    }

    /**
     * Retrieves and removes the last element of this deque.
     * @return the last element in the deque
     * @throws NoSuchElementException if the deque is empty
     */
    public E dequeueTail() throws NoSuchElementException
    {
        // assign node to remove to tail
        DequeNode removedNode = _tail;

        // validate depth
        if (isEmpty())
        {
            throw new NoSuchElementException();
        }

        // reset deque if there is one node left to remove
        if (_depth == 1)
        {
            clear();
        }

        else
        {
            // assign new tail
            _tail = removedNode.getNext();

            // assign the new tail's next to nothing
            _tail.setPrevious(null);

            // decrement
            _depth--;
        }

        return (E)removedNode.getValue();
    }

    /**
     * Removes all of the elements from this deque.
     */
    public void clear()
    {
        // detach all nodes and reset size
        _head = null;
        _tail = null;
        _depth = 0;
    }

    /**
     * Returns how many elements are in the deque.
     * @return the number of elements in the deque
     */
    public int depth()
    {
        return _depth;
    }

    /**
     * Returns true if the deque has an element, false if otherwise
     * @return returns true if the deque has at least one element, false if no
     * elements in the deque
     */
    public boolean isEmpty()
    {
        return _depth == 0;
    }

    /**
     * Returns an iterator that goes from the head (beginning) of the deque to
     * tail (end).
     * @return a head to tail iterator
     */
    public Iterator<E> iterator()
    {
        return new DequeIterator(false);
    }

    /**
     * Returns an iterator that goes from the tail (end) of the deque to
     * head (beginning).
     * @return a tail to head iterator
     */
    public Iterator<E> reverseIterator()
    {
        return new DequeIterator(true);
    }

    /**
     * This class allows iteration for the deque and implements the Iterator
     * interface.
     * @param <E> The type of element
     */
    class DequeIterator<E> implements Iterator<E> {

        // initiate instance variable
        private boolean _reverse;

        /**
         * Creates a deque iterator with the option to iterate from
         * the tail to head or head to tail.
         * @param reverse True for tail to head, false for head to tail
         */
        public DequeIterator(boolean reverse)
        {
            _reverse = reverse;
        }

        /**
         * Returns true if the iteration has more elements.
         * @return returns true if there are more elements. Returns false if
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
            Object elementToReturn;

            // validate depth
            if (isEmpty())
            {
                throw new NoSuchElementException();
            }

            // assign variable and dequeue from tail if reverse
            if (_reverse)
            {
                elementToReturn = dequeueTail();

            }

            // assign variable and dequeue
            else
            {
                elementToReturn = dequeue();
            }

            return (E)elementToReturn;
        }
    }

    /**
     * This class creates a node that contains the elements of the deque.
     * The deque is made up of these node carriers.
     * @param <E> The type of element
     */
    class DequeNode<E> {

        // initate instance variable
        private E _value;
        private DequeNode _next;
        private DequeNode _prev;

        /**
         * Constructs a deque node with a given element value, previous
         * setter, and next setter.
         * @param value The value of the element
         * @param prev The previous element to set
         * @param next The next element to set
         */
        public DequeNode(E value)
        {
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
         * @return Returns the previous deque node
         */
        public DequeNode<E> getPrevious()
        {
            return _prev;
        }

        /**
         * Returns the next node.
         * @return Returns the next deque node
         */
        public DequeNode<E> getNext()
        {
            return _next;
        }

        /**
         * Sets the link to the previous node.
         * @param prev The deque node to set as previous
         */
        public void setPrevious(DequeNode<E> prev)
        {
            _prev = prev;
        }

        /**
         * Sets the link to the next node.
         * @param next The deque to set as next
         */
        public void setNext(DequeNode<E> next)
        {
            _next = next;
        }
    }
}


