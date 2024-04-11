import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
//import edu.princeton.cs.algs4.DirectedCycle;

public class SAP{
    private Digraph G;
    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G){
        // Digraph Constructor already checks if input is null

        // Check that Digraph is a rooted DAG. If not, throw an IllegalArgumentException.
//        DirectedCycle DirCyc = new DirectedCycle(G);
//        if(DirCyc.hasCycle())
//            throw new IllegalArgumentException();

        // Keep deep copy so that SAP is immutable.
        this.G = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w){
        return -1;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w){
        return -1;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w){
        return -1;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w){
        return -1;
    }

    // Vertex validation taken from BreadthFirstDirectedPaths.java
    private void validateVertex(int v) {
        int V = this.G.V();
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    private void validateVertices(Iterable<Integer> vertices) {
        if (vertices == null) {
            throw new IllegalArgumentException("argument is null");
        }
        int vertexCount = 0;
        for (Integer v : vertices) {
            vertexCount++;
            if (v == null) {
                throw new IllegalArgumentException("vertex is null");
            }
            validateVertex(v);
        }
        if (vertexCount == 0) {
            throw new IllegalArgumentException("zero vertices");
        }
    }

    // unit testing
    public static void main(String[] args){}
}