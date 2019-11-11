// bvandyke17@georgefox.edu
// program 6
// 2018-10-27

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *Doubly-linked list implementation of the List interface. Implements all
 * optional list operations, and permits all elements (including null).
 * All of the operations perform as could be expected for a doubly-linked list.
 * @author Brett Van Dyke <bvandyke17@georgefox.edu>
 */
public class LinkedList<E> implements Iterable<E> {

    // initiate instance variables
    private LinkedListNode _head;
    private LinkedListNode _tail;
    private int _size;
    
    /**
     * Constructs an empty list.
     */
    public LinkedList()
    {
        // assign instance variables
        _head = null;
        _tail = null;
        _size = 0;
    }
    
    /**
     * Inserts the specified element at the specified position in this list.
     * @param index index at which the specified element is to be inserted
     * @param element element to be inserted
     * @throws if the index is out of range (index less than 0 || index greater
     * than size())
     */
    public void add(int index, E element) throws IndexOutOfBoundsException
    {
        // initate variables
        LinkedListNode currentNode;
        LinkedListNode addedNode; 
        LinkedListNode nextNode;
        
        // throw
        if (index < 0 || index > size())
        {
            throw new IndexOutOfBoundsException();
        }
        
        // add element if at beginning or end
        if (index == 0 || index == _size)
        {
            add(element);
        }
        
        // else add between beginning and end
        else
        {
            // assign head
            currentNode = _head;
            
            // assign node at before index
            for (int i = 1; i < index; i++)
            {
                currentNode = currentNode.getNext();
            }
            
            // get the node after the index
            nextNode = currentNode.getNext();
            
            // create new node and assign previous and next nodes 
            addedNode = new LinkedListNode(element, currentNode, nextNode);
            
            // set the node before the index next node to the new node
            currentNode.setNext(addedNode);
            
            // set the node after the index previous node to the new node
            nextNode.setPrevious(addedNode);
            
            // add element size of list
            _size++;
        }
    }
    
    /**
     * Appends the specified element to the end of this list and returns true.
     * @param element the element to be appended
     * @return returns true
     */
    public boolean add(E element)
    {
        // initiate variable for new node
        LinkedListNode addedNode;
        
        // add new node if empty and assign the beginning and end to said node
        if(_tail == null)
        {
            _tail = new LinkedListNode(element);
            _head = _tail;
        }
        
        else
        {
            // assign new nodes previous to tail and next to empty pointer
            addedNode = new LinkedListNode(element, _tail, null);
            
            // set previous end node's next to the added node
            _tail.setNext(addedNode);
            
            // reassign the tail instance variable
            _tail = addedNode;    
        }
        
        // increase size of list
        _size++;
            
        return true;
    }
    
    /**
     * Removes all of the elements from this list.
     */
    public void clear()
    {
        // un attach all nodes and set reset size of list
        _head = null;
        _tail = null;
        _size = 0;           
    }
    
    /**
     * Returns the element at the specified position in this list.
     * @param index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range(index less
     * than 0 || index greater than or equal to size())
     */
    public E get(int index) throws IndexOutOfBoundsException
    {
        // assign head of list
        LinkedListNode currentNode = _head;
        
        // validate index
        if (index < 0 || index >= size())
        {
            throw new IndexOutOfBoundsException();
        }
        
        // get the node of the index
        for (int i = 0; i < index; i++)
        {          
            currentNode = currentNode.getNext();               
        }
        
        // return element of index'ed node
        return (E)currentNode.getValue();
        
        
    }
    
    /**
     * Returns the index of the first occurrence of the specified element in
     * this list, or -1 if this list does not contain the element.
     * @param element the element to find the index of
     * @return the index of the element
     */
    public int indexOf(E element)
    {
        // create constant for not found
        final int NOT_FOUND = -1;
        
        // assign variable of element index to not found result
        int indexOfElement = NOT_FOUND;
        
        // assign loop variables
        int i = 0;
        boolean found = false;
        
        // assign head node
        LinkedListNode currentNode = _head;
        
        while (i < size() && !found)
        {
            // assign found index and flag if element desired is null and value
            //is null
            if (element == null && currentNode.getValue() == null)
            {
                indexOfElement = i;
                found = true;
            }
            
            // assign found index and flag to end loop if element desired equals
            // the value of node
            else if (currentNode.getValue().equals(element) )
            {
                indexOfElement = i;
                found = true;
            }
            
            // get next node
            currentNode = currentNode.getNext();

            i++;
        }
        
        return indexOfElement;
    }
    
    /**
     * Returns true if the list has an element, false if otherwise
     * @return returns true if the list has at least one element, false if no
     * elements in the list
     */
    public boolean isEmpty()
    {
        return _size == 0;
    }
    
    /**
     * Removes the element at the specified position in this list.
     * @param index the index of the element to be removed
     * @return the element that was removed from the list
     * @throws IndexOutOfBoundsException if the index is out of range 
     * (index less than 0 || index greater than or equal to size())
     */
    public E remove(int index) throws IndexOutOfBoundsException
    {
        // initiate variables
        LinkedListNode nodeToRemove;
        LinkedListNode nextNode;
        
        // assign head node
        LinkedListNode currentNode = _head;
        
        // validate index
        if (index < 0 || index >= size())
        {
            throw new IndexOutOfBoundsException();
        }
        
        // remove head node if index is zero and re assign the head to the next
        // node
        if (index == 0)
        {
            nodeToRemove = currentNode;
            
            _head = currentNode.getNext();
        }
        
        // remove the tail if the index is 1 less than size.
        // re assign tail to the previous
        else if (index == _size - 1)
        {
            nodeToRemove = _tail;
            _tail = nodeToRemove.getPrevious();
        }
            
        else
        {
            // get the node before the one to remove
            for (int i = 1; i < index; i++)
            {
                currentNode = currentNode.getNext();
            }
            
            // assign the node to remove
            nodeToRemove = currentNode.getNext();
            
            // assign the node after the one to remove
            nextNode = nodeToRemove.getNext();
            
            // set the node before the one to remove to the one after the node
            // to remove
            currentNode.setNext(nextNode);
            
            // set the nod after the one to remove to the one before the node
            // to remove
            nextNode.setPrevious(currentNode);
        }
        
        // remove 1 from list size
        _size--;
        
        return (E)nodeToRemove.getValue();
    }
    
    /**
     * Replaces the element at the specified position in this list with the 
     * specified element.
     * @param index index of the element to replace
     * @param element element to be stored at the specified position
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range 
     * (index less than 0 || index greater than or equal to size())
     */
    public E set(int index, E element) throws IndexOutOfBoundsException
    {
        // initiate variable
        Object elementToReturn;
        
        // assign head node
        LinkedListNode currentNode = _head;
        
        // validate index
        if (index < 0 || index >= size())
        {
            throw new IndexOutOfBoundsException();
        }
        
        // get node at index
        for (int i = 0; i < index; i++)
        {
            currentNode = currentNode.getNext();
        }
        
        // assign return variable to the value of the index'ed node
        elementToReturn = currentNode.getValue();
        
        // set new value
        currentNode.setValue(element);
        
        return (E)elementToReturn;

    }
    
    /**
     * Returns the number of elements in the list. 
     * @return returns the number of elements in the list
     */
    public int size()
    {
        return _size;
    }
    
    /**
     * Returns an iterator that goes from the head (beginning) of the list to 
     * tail (end).
     * @return Returns a head to tail iterator
     */
    public Iterator<E> iterator()
    {
        return new LinkedListIterator(false);
    }
    
    /**
     * Returns an iterator that goes from the tail (end) of the list to 
     * head (beginning).
     * @return 
     */
    public Iterator<E> reverseIterator()
    {
        return new LinkedListIterator(true);
    }
    
    /**
     * This class allows iteration for the LinkedList and implements the Iterator
     * interface.
     * @param <E> The type of element
     */
    class LinkedListIterator<E> implements Iterator<E> {
        
        // initiate tracking and reverse instance variables
        private int _next;
        private boolean _reverse;
        
        /**
         * Creates a linked list iterator with the option to iterate from 
         * the tail to head.
         * @param reverse True for reverse, false for head to tail
         */
        public LinkedListIterator(boolean reverse)
        {
            _reverse = reverse;
            
            // assign next count to end of list if reverse
            if (_reverse)
            {
                _next = size() - 1;
            }
            
            // assign the next count zero
            else
            {
                _next = 0;
            }       
        }
        
        /**
         * Returns true if the iteration has more elements.
         * @return returns true if there is more elements. Returns false if
         * there are no more elements.
         */
        public boolean hasNext()
        {
            boolean elementsInDirection;
            
            // check if more elements from tail to head
            if (_reverse)
            {
                elementsInDirection = _next >= 0;
            }
            
            // check if more elements from head to tail
            else
            {
                elementsInDirection = _next < size();
            }
            return elementsInDirection;
        }
        
        /**
         * Returns the next element in the iteration.
         * @return returns the next element of iteration.
         * @throws NoSuchElementException NoSuchElementException if there are no
         * more elements.
         */
        public E next() throws NoSuchElementException
        {
            Object returnedElement;
            
            // throw exception if out of range
            if (_next >= size() || _next < 0)
            {
                throw new NoSuchElementException();
            }
            
            // get next element and decrement if reverse
            if (_reverse)
            {
                returnedElement = get(_next--);
            }
            
            // get next element and increment if forward
            else
            {
                returnedElement = get(_next++);
            }
           
            return (E)returnedElement;
        }
    }
    
    /**
     * This class creates a node that contains the elements of the linked list.
     * The linked list is made up of these node carriers.
     * @param <E> The type of element
     */
    class LinkedListNode<E> {
        
        private E _value;
        private LinkedListNode _next;
        private LinkedListNode _prev;
        
        /**
         * Constructs an empty linked list node. 
         */
        public LinkedListNode()
        {
            _value = null;
            _next = null;
            _prev = null;  
        }
        
        /**
         * Constructs a linked list node with a given element value. 
         * @param value the value of the element
         */
        public LinkedListNode(E value)
        {
            _value = value;
            _next = null;
            _prev = null;
                    
        }
        
        /**
         * Constructs a linked list node with a given element value, previous
         * setter, and next setter. 
         * @param value The value of the element
         * @param prev The previous element to set
         * @param next The next element to set
         */
        public LinkedListNode(E value, LinkedListNode<E> prev,
                LinkedListNode<E> next)
        {
            _value = value;
            _prev = prev;
            _next = next;
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
         * @return Returns the previous linked list node
         */
        public LinkedListNode<E> getPrevious()
        {
            return _prev;
        }
        
        /**
         * Returns the next node. 
         * @return Returns the next linked list node
         */
        public LinkedListNode<E> getNext()
        {
            return _next;
        }
        
        /**
         * Sets the value of the node.
         * @param value the value to set
         */
        public void setValue(E value)
        {
            _value = value;
        }
        
        /**
         * Sets the link to the previous node.
         * @param prev The linked list node to set to
         */
        public void setPrevious(LinkedListNode<E> prev)
        {
            _prev = prev;
        }
        
        /**
         * Sets the link to the next node. 
         * @param next The linked list to set to
         */
        public void setNext(LinkedListNode<E> next)
        {
            _next = next;
        }
    }
    
}
