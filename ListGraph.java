// bvandyke17@georgefox.edu
// program 12
// 2018-12-14

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * A directed list graph that extends directed graph. Does not implement any
 * other methods other than what is specified in directed graph.
 * @author Brett Van Dyke <bvandyke17@georgefox.edu>
 */
public class ListGraph <V,E> extends DirectedGraph<V,E> {

    // initiate instanace variables
    private HashMap<V, Vertex<E>> _vertexList;
    private HashMap<V, ArrayList<Edge<V,E>>> _edgeList;

    /**
     * Constructs a directed list graph.
     */
    public ListGraph()
    {
        _vertexList = new HashMap();
        _edgeList =  new HashMap();
    }

        /**
     * Method to add a vertex to a list graph.
     * @param v the vertex to add
     * @throws IllegalArgumentException if the param is null
     * @throws DuplicateVertexException if the vertex already exists in the
     * graph
     */
    public void add(V v) throws IllegalArgumentException,
            DuplicateVertexException
    {
        // validate param variable
        if (v == null)
        {
            throw new IllegalArgumentException();
        }

        // validate duplicate vertex
        if (contains(v))
        {
            throw new DuplicateVertexException("Cannot have duplicate"
                    + " vertices!");

        }

        // add vertex label to map of edges
        _edgeList.put(v, new ArrayList());

        // add vertex label to vertex map and create new vertex
        _vertexList.put(v, new Vertex(v));
    }

    /**
     * Method to check if a list graph contains a vertex.
     * @param v the vertex to check
     * @return true if the graph contains the vertex, false otherwise
     * @throws IllegalArgumentException if param is null
     */
    public boolean contains(V v) throws IllegalArgumentException
    {
        // validate param variable
        if (v == null)
        {
            throw new IllegalArgumentException();
        }

        return _vertexList.containsKey(v);
    }

    /**
     * Method that removes the vertex from a list graph. Returns
     * the vertex if present, null if otherwise.
     * @param v the vertex to remove
     * @return the vertex if present, null if otherwise
     */
    public V remove(V v)
    {
        Edge<V,E> checkedEdge;

        // create and assign new edge iterator
        Iterator<Edge<V,E>> ittr = edges();

        // assign returned variable
        V label = null;

        if (contains(v))
        {
            // remove from vertex list
            _vertexList.remove(v);

            while (ittr.hasNext())
            {
                // iterate through edges and remove any with reference to
                //removed vertex
                checkedEdge = ittr.next();
                if (checkedEdge.getU().equals(v) ||
                        checkedEdge.getV().equals(v))
                {
                    // assign returned label
                    label = (V)checkedEdge.getLabel();

                    // remove edge
                    removeEdge(checkedEdge.getU(), checkedEdge.getV());
                }
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
        // validate param variable
        if (v == null || u == null || !contains(v) || !contains(u))
        {
            throw new IllegalArgumentException();
        }

        // validate duplicate edge
        if (containsEdge(u, v))
        {
            throw new DuplicateEdgeException("Cannot have multiple Edges!");
        }

        // add edge to map
        _edgeList.get(u).add(new Edge(u, v, label));
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

        return _edgeList.containsKey(u) && _edgeList.containsKey(v) &&
                _edgeList.get(u).contains(getEdge(u, v));
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
        // assign variable to return
        Edge<V,E> returnedEdge =  null;

        if (_edgeList.containsKey(u) && _edgeList.containsKey(v))
        {
            // search through list of edges and compare to param variables
            for (Edge edge : _edgeList.get(u))
            {
                // assign return variable if equal
                if (edge.getU() == u && edge.getV() == v)
                {
                    returnedEdge = edge;
                }
            }
        }

        return returnedEdge;
    }

    /**
     * Removes a specified edge from a list graph if present and returns
     * its label, returns null if not present.
     * @param u the "from" vertex to the start of the edge
     * @param v the "to" vertex to the end of the edge
     * @return the label of the edge if present, null if otherwise
     * @throws IllegalArgumentException if u or v are null
     */
    public E removeEdge(V u, V v) throws IllegalArgumentException
    {
        Edge<V,E> returnedEdge;

        // assign variable to return
        E edgeLabel = null;

        // validate param variables
        if (u == null || v == null)
        {
            throw new IllegalArgumentException();
        }

        if (containsEdge(u, v))
        {
            // assign edge to remove to return
            returnedEdge = _edgeList.get(u).get(_edgeList.get(u).
                    indexOf(getEdge(u, v)));

            // assign label variable to return
            edgeLabel = returnedEdge.getLabel();

            // remove edge
            _edgeList.get(u).remove(returnedEdge);
        }

        return edgeLabel;
    }

    /**
     * Method that returns the size of a list graph (i.e. the
     * number of vertices.
     * @return the number of vertices in the graph
     */
    public int size()
    {
        return _vertexList.size();
    }

    /**
     * Returns the number of edges in a list graph.
     * @return the number of edges in a graph
     */
    public int edgeCount()
    {
        // assign edge iterator
        Iterator ittr = edges();

        // assign total count variable
        int edgeCount = 0;

        // increment count for every edge in graph
        while (ittr.hasNext())
        {
            edgeCount++;
            ittr.next();
        }

        return edgeCount;
   }

    /**
     * Returns an iterator for all vertices in the graph.
     * @return a vertex iterator
     */
    public Iterator<V> vertices()
    {
        return _vertexList.keySet().iterator();
    }

    /**
     * Returns an vertex iterator for adjacent vertices from a specified vertex.
     * @param v the vertex to get the adjacent vertices from
     * @return an adjacent vertex iterator
     * @throws IllegalArgumentException if v is null
     */
    public Iterator<V> adjacent(V v) throws IllegalArgumentException
    {
        // assign edge iterator
        Iterator ittr = edges();

        // assign and create list to store vertices
        LinkedList<V> adjacentVertices = new LinkedList();

        // initiate variable
        Edge<V,E> visitedEdge;

        // validate param variable
        if (v == null)
        {
            throw new IllegalArgumentException();
        }

        while (ittr.hasNext())
        {
            // assign edge to variable
            visitedEdge = (Edge)ittr.next();

            // add vertex to list if edge's "from" equals the "to" vertex in the
            // param
            if (visitedEdge.getU().equals(v))
            {
                adjacentVertices.add(visitedEdge.getV());
            }
        }

       return adjacentVertices.iterator();
    }

    /**
     * Returns an edge iterator for all the edges in a matrix graph.
     * @return an edge iterator
     */
    public Iterator<Edge<V,E>> edges()
    {
        // create and assign list to store edges
        ArrayList<Edge<V,E>> allEdges = new ArrayList();

        // add every edge from map to list
        for (V label : _edgeList.keySet())
        {
            allEdges.addAll(_edgeList.get(label));
        }

        return allEdges.iterator();
    }

    /**
     * Clears a list graph.
     */
    public void clear()
    {
        _vertexList.clear();
        _edgeList.clear();
    }




}
