import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP{
    private Digraph G;
    private static final int INFINITY = Integer.MAX_VALUE;
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
        //stats = {minDist, commonAncestor}
        int[] stats = sap(v,w);
        return stats[0];
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w){
        int[] stats = sap(v,w);
        return stats[1];
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w){
        int[] stats = sap(v,w);
        return stats[0];
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w){
        int[] stats = sap(v,w);
        return stats[1];
    }

    private int[] sap(int v, int w){
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(this.G,v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(this.G,w);
        int[] stats = sapStats(bfsV, bfsW);
        return stats;

//        int minDist = INFINITY;
//        int commonAncestor = -1;
//        for(int x = 0; x < this.G.V(); x++){
//            if(!bfsV.hasPathTo(x) || bfsW.hasPathTo(x))
//                continue;
//            int distV = bfsV.distTo(x);
//            int distW = bfsW.distTo(x);
//            int currentDist = distV + distW;
//            if(currentDist < minDist){
//                minDist = currentDist;
//                commonAncestor = x;
//            }
//        }
    }

    private int[] sap(Iterable<Integer> v, Iterable<Integer> w){
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(this.G,v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(this.G,w);
        int[] stats = sapStats(bfsV, bfsW);
        return stats;

//        int minDist = INFINITY;
//        int commonAncestor = -1;
//        for(int x = 0; x < this.G.V(); x++){
//            if(!bfsV.hasPathTo(x) || bfsW.hasPathTo(x))
//                continue;
//            int distV = bfsV.distTo(x);
//            int distW = bfsW.distTo(x);
//            int currentDist = distV + distW;
//            if(currentDist < minDist){
//                minDist = currentDist;
//                commonAncestor = x;
//            }
//        }
    }

    private int[] sapStats(BreadthFirstDirectedPaths bfsV, BreadthFirstDirectedPaths bfsW){
        int minDist = INFINITY;
        int commonAncestor = -1;
        for(int x = 0; x < this.G.V(); x++){
            if(!bfsV.hasPathTo(x) || !bfsW.hasPathTo(x))
                continue;
            int distV = bfsV.distTo(x);
            int distW = bfsW.distTo(x);
            int currentDist = distV + distW;
            if(currentDist < minDist){
                minDist = currentDist;
                commonAncestor = x;
            }
        }
        if(minDist == INFINITY)
            minDist = -1;
        int[] stats = {minDist, commonAncestor};
        return stats;
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
    public static void main(String[] args){
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while(!StdIn.isEmpty()){
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v,w);
            int ancestor = sap.ancestor(v,w);
            StdOut.printf("length = %d, ancestor = %d\n",length,ancestor);
        }
    }
}