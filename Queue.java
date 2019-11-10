// bvandyke17@georgefox.edu
// program 8
// 2018-11-06

// import utilities
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A collection designed for holding elements prior to processing. queues
 * provide additional insertion, extraction, and inspection operations.
 * @author Brett Van Dyke <bvandyke17@georgefox.edu>
 */
public class Queue<E> implements Iterable<E> {

    // initiate instance variables
    private QueueNode _head;
    private QueueNode _tail;
    private int _depth;

    /**
     * Constructs an empty queue.
     */
    public Queue()
    {
        // assign instance variables
        _head = null;
        _tail = null;
        _depth = 0;
    }

    /**
     * Inserts the specified element into this queue returning true upon
     * success.
     * @param element the element to enqueue
     * @return returns true
     * @throws OutOfMemoryError if there is not enough memory
     */
    public boolean enqueue(E element) throws OutOfMemoryError
    {
        // crate node to add with specified element
        QueueNode addedNode = new QueueNode(element);

        // set old tail's previous to the new node and the new nodes next to the
        // former tail if more than 1 element in queue
        if (_depth != 0)
        {
            _tail.setPrevious(addedNode);
            addedNode.setNext(_tail);
        }

        // assign new tail to the added node
        _tail = addedNode;

        // assign head to tail node if empty queue
        if (_depth == 0)
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
     * Enqueues all iterable elements into the queue.
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
     * Returns the head element without removing it from the queue.
     * @return returns the head element
     * @throws NoSuchElementException if the queue is empty
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
     * Dequeues the element from the queue and returns it.
     * @return returns the element
     * @throws NoSuchElementException if the queue is empty
     */
    public E dequeue() throws NoSuchElementException
    {
        QueueNode removedNode;
        QueueNode newHeadNode;

        // validate depth
        if (isEmpty())
        {
            throw new NoSuchElementException();
        }

        // assign node to remove from the head node
        removedNode = _head;

        if (_depth > 1)
        {
            // assign the new head not to the former head node's previous
            newHeadNode = _head.getPrevious();

            // remove pointer to former head node
            newHeadNode.setNext(null);

            // assign head to the new node
            _head = newHeadNode;
        }

        _depth--;

        return (E)removedNode.getValue();
    }

    /**
     * Removes all of the elements from this list.
     */
    public void clear()
    {
        // detach all nodes and set reset size of list
        _head = null;
        _tail = null;
        _depth = 0;
    }

    /**
     * Returns how many elements are in the queue.
     * @return returns the amount of elements in the queue
     */
    public int depth()
    {
        return _depth;
    }

    /**
     * Returns true if the queue has at least one element, false if otherwise
     * @return returns true if the list has at least one element, false if no
     * elements in the queue
     */
    public boolean isEmpty()
    {
        return _depth == 0;
    }

    /**
     * Returns a queue iterator.
     * @return a queue iterator
     */
    public Iterator iterator()
    {
        return new QueueIterator();
    }

    /**
     * This class allows iteration of the queue and implements the Iterator
     * interface.
     * @param <E> the type of element
     */
    class QueueIterator<E> implements Iterator<E> {

        /**
         * Creates a queue iterator.
         */
        public QueueIterator()
        {
            // no code necessary because no instances to assign
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
            if (isEmpty())
            {
                throw new NoSuchElementException();
            }

            return (E)dequeue();
        }
    }

    /**
     * This class creates a node that contains the elements for the queue.
     * The queue is made up of these node carriers.
     * @param <E> The type of element
     */
    class QueueNode<E> {

        private E _value;
        private QueueNode _next;
        private QueueNode _prev;

        /**
         * Constructs a queue node with a given element value, previous
         * setter, and next setter.
         * @param value The value of the element
         * @param prev The previous element to set
         * @param next The next element to set
         */
        public QueueNode(E value)
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
         * @return Returns the previous queue node
         */
        public QueueNode getPrevious()
        {
            return _prev;
        }

        /**
         * Sets the link to the previous node.
         * @param prev The queue node to set to
         */
        public void setPrevious(QueueNode<E> prev)
        {
            _prev = prev;
        }

        /**
         * Sets the pointer to the next node.
         * @param next The queue node to set to
         */
        public void setNext(QueueNode<E> next)
        {
            _next = next;
        }
    }
}

