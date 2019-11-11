// bvandyke17@georgefox.edu
// program 12
// 2018-12-14

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * A directed matrix graph that extends directed graph. Does not implement any
 * other methods other than what is specified in directed graph.
 * @author Brett Van Dyke <bvandyke17@georgefox.edu>
 */
public class MatrixGraph<V,E> extends DirectedGraph<V,E> {

    // initiate private variables
    private Object _matrix[][];
    private HashMap<V, Integer> _matrixIndex;
    private ArrayList<Vertex<E>> _vertices;

    // assign constant
    static final int DEFAULT_CAPACITY = 10;

    /**
     * Constructs a matrix graph with an initial capacity of 10.
     */
    public MatrixGraph()
    {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Constructs a matrix graph with a capacity given in the parameter.
     * @param initialCapacity the initial capacity of the graph
     */
    public MatrixGraph(int initialCapacity)
    {
        // assign variables
        _matrix = new Object[initialCapacity][initialCapacity];
        _matrixIndex = new HashMap();
        _vertices = new ArrayList();
    }

    /**
     * Method to add a vertex to a matrix graph.
     * @param v the vertex to add
     * @throws IllegalArgumentException if the param is null
     * @throws DuplicateVertexException if the vertex already exists in the graph
     */
    public void add(V v) throws IllegalArgumentException,
            DuplicateVertexException
    {
        int maxCapacity;
        Object newArray[][];

        // validate param if null
        if (v == null)
        {
            throw new IllegalArgumentException();
        }

        // validate duplicate vertex
        if (contains(v))
        {
            throw new DuplicateVertexException("Duplicate vertices are"
                    + " not allowed!");
        }

        // add vertex to list
        _vertices.add(new Vertex(v));

        // add label to corrospond with index
        _matrixIndex.put(v, size());

        // grow 2d array and copy elements if array is to small
        if (_matrix.length == size())
        {
            maxCapacity = 2 * _matrix.length;
            newArray = new Object[maxCapacity][maxCapacity];
            for (int i = 0; i < _matrix.length; i++)
            {
                for (int j = 0; j < _matrix[i].length; j++)
                {
                    newArray[i][j] = _matrix[i][j];
                }
            }

            _matrix = newArray;
        }
    }

    /**
     * Method to check if a matrix graph contains a vertex.
     * @param v the vertex to check
     * @return true if the graph contains the vertex, false otherwise
     * @throws IllegalArgumentException if param is null
     */
    public boolean contains(V v) throws IllegalArgumentException
    {
        if (v == null)
        {
            throw new IllegalArgumentException();
        }

        return _matrixIndex.containsKey(v);
    }

    /**
     * Method that removes the vertex from a matrix graph. Returns
     * the vertex if present, null if otherwise.
     * @param v the vertex to remove
     * @return the vertex if present, null if otherwise
     */
    public V remove(V v)
    {
        // initiate variables
        Vertex<E> vertex;
        int vertexIndex;

        // assign returned variable to null for not found vertex
        V label = null;

        if (contains(v))
        {
            // get index of vertex and remove from map
            vertexIndex = _matrixIndex.remove(v);

            // assign removed vertex object to variable
            vertex = _vertices.get(vertexIndex);

            // assign variable to label of removed vertex
            label = (V)vertex.getLabel();

            // remover vertex from array list
            _vertices.remove(vertexIndex);

            // clear matrix
            for (int row = 0; row < size(); row++)
            {
                _matrix[row][vertexIndex] = null;
            }
        }

        return label;
    }

    /**
     * Method to add an edge between two existing vertices.
     * @param u the "from" vertex to the start of the edge
     * @param v the "to" vertex to the end of the edge
     * @param label label of the edge to add
     * @throws IllegalArgumentException if u, v, or the label are null or the
     * vertices don't exist
     * @throws DuplicateEdgeException if the edge already exists
     */
    public void addEdge(V u, V v, E label) throws IllegalArgumentException,
            DuplicateEdgeException
    {
        int uIndex;
        int vIndex;

        // validate param variables
        if (v == null || u == null || !contains(v) || !contains(u))
        {
            throw new IllegalArgumentException();
        }

        // validate duplicate edge
        if (containsEdge(u, v))
        {
            throw new DuplicateEdgeException("Duplicate edges are not"
                    + " allowed!");
        }

        // assign index variables for matrix spots
        uIndex = _matrixIndex.get(u);
        vIndex = _matrixIndex.get(v);

        // add edge to matrix
        _matrix[uIndex][vIndex] = new Edge(u, v, label);
    }

    /**
     * Checks if a matrix graph contains a specified edge.
     * @param u the "from" vertex to the start of the edge
     * @param v the "to" vertex to the end of the edge
     * @return true if the edge is contained, false if otherwise
     * @throws IllegalArgumentException if u or v are null
     */
    public boolean containsEdge(V u, V v) throws IllegalArgumentException
    {
        // validate param variables
        if (v == null || u == null)
        {
            throw new IllegalArgumentException();
        }

        return contains(v) && contains(u) &&
                _matrix[_matrixIndex.get(u)][_matrixIndex.get(v)] != null;
    }

    /**
     * Method that returns a specified edge object if present, null
     * if otherwise
     * @param u the "from" vertex to the start of the edge
     * @param v the "to" vertex to the end of the edge
     * @return the edge object if present, null if otherwise
     */
    public Edge<V,E> getEdge(V u, V v)
    {
        // assign edge to return to null
        Edge<V,E> returnedEdge = null;

        // assign edge variable if contained in matrix
        if (containsEdge(u, v))
        {
            returnedEdge = (Edge)_matrix[_matrixIndex.get(u)]
                    [_matrixIndex.get(v)];
        }

        return returnedEdge;
    }

    /**
     * Removes a specified edge from a matrix graph if present and returns
     * its label, returns null if not present.
     * @param u the "from" vertex to the start of the edge
     * @param v the "to" vertex to the end of the edge
     * @return the label of the edge if present, null if otherwise
     * @throws IllegalArgumentException if u or v are null
     */
    public E removeEdge(V u, V v) throws IllegalArgumentException
    {
        E returnedValue;
        int row;
        int col;
        Edge<V,E> edge;

        // validate param variables
        if (u == null || v == null)
        {
            throw new IllegalArgumentException();
        }

        // assign returned variable
        returnedValue = null;

        // assign row and column variables
        row = _matrixIndex.get(u);
        col = _matrixIndex.get(v);

        // assign edge from matrix
        edge = (Edge<V,E>)_matrix[row][col];

        // assign variable to return if it exists in the matrix
        if (edge != null)
        {
           returnedValue = edge.getLabel();
        }

        // clear edge in matrix
        _matrix[row][col] = null;

        return returnedValue;
    }

    /**
     * Returns the size of a matrix graph (i.e. the number of vertices).
     * @return the number of vertices in the graph
     */
    public int size()
    {
        return _matrixIndex.keySet().size();
    }

    /**
     * Returns the number of edges in a matrix graph.
     * @return the number of edges in a graph
     */
    public int edgeCount()
    {
        // assign edge iterator
        Iterator ittr = edges();

        // assign count variable
        int edgeCount = 0;

        // incriment count for every edge
        while (ittr.hasNext())
        {
            ittr.next();
            edgeCount++;
        }

        return edgeCount;
    }

    /**
     * Returns an iterator for all vertices in the graph.
     * @return a vertex iterator
     */
    public Iterator<V> vertices()
    {
        return _matrixIndex.keySet().iterator();
    }

    /**
     * Returns an vertex iterator for adjacent vertices from a specified vertex.
     * @param v the vertex to get the adjacent vertices from
     * @return an adjacent vertex iterator
     * @throws IllegalArgumentException if v is null
     */
    public Iterator<V> adjacent(V v) throws IllegalArgumentException
    {
        Edge<V,E> edge;

        // create list to store vertices
        LinkedList<V> adjacentList = new LinkedList();

        // assign index of param vertex
        int vertexIndex = _matrixIndex.get(v);

        // validate param variable
        if (v == null)
        {
            throw new IllegalArgumentException();
        }

        // add every edge in matrix with "from" vertex equaling param vertex to
        // list
        for (int row = 0; row < _matrix.length; row++)
        {
            edge = (Edge<V,E>)_matrix[vertexIndex][row];
            if (edge != null)
            {
                if (edge.getU().equals(v));
                {
                    adjacentList.add(edge.getV());
                }
            }
        }

        return adjacentList.iterator();
    }

    /**
     * Returns an edge iterator for all the edges in a matrix graph.
     * @return an edge iterator
     */
    public Iterator<Edge<V,E>> edges()
    {
        Edge<V,E> edge;

        // create list to put all edges into
        LinkedList<Edge<V,E>> edgeList = new LinkedList();

        // iterate through entire matrix and add every edge to the list
        for (int row = 0; row < _matrix.length; row++)
        {
            for (int col = 0; col < _matrix.length; col++)
            {
                edge = (Edge<V,E>)_matrix[row][col];
                if (edge != null)
                {
                    edgeList.add(edge);
                }
            }
        }

        return edgeList.iterator();
    }

    /**
     * Clears a matrix graph.
     */
    public void clear()
    {
        Object tempArray[][];

        // assign tempArray to default capacity
        tempArray = new Object[DEFAULT_CAPACITY][DEFAULT_CAPACITY];

        // assign instance variable
        _matrix = tempArray;

        // clear instance variables
        _matrixIndex.clear();
        _vertices.clear();
    }
}
