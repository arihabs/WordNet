//import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinearProbingHashST;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class WordNet{
    private final Digraph G;

    private final SAP Sap;
    private int V; // number of nodes/synsets
//    private LinearProbingHashST<Integer,String[]> st; //key: node val: synset
    private LinearProbingHashST<Integer,String> st; //key: node val: synset
    private LinearProbingHashST<String, Queue<Integer>> ts; //key: noun val: set of nodes noun is found

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms){
        if(synsets == null || hypernyms == null)
            throw new IllegalArgumentException("Null argument.");
        // Read in synset file
        In synsetIn = new In(synsets);
//        this.st = new LinearProbingHashST<Integer,String[]>();
        this.st = new LinearProbingHashST<Integer,String>();
        this.ts = new LinearProbingHashST<String, Queue<Integer>>();
        while(!synsetIn.isEmpty()){
            String[] line = synsetIn.readLine().split(",");
            int nNode = Integer.parseInt(line[0]);
            String[] currSet = line[1].split(" ");
//            st.put(nNode,currSet);
            st.put(nNode,line[1]);

            // Go through each noun in synset and put it in inverse symbol table
            for(String noun : currSet){
                if(!ts.contains(noun)) ts.put(noun, new Queue<Integer>());
                ts.get(noun).enqueue(nNode);
            }
            this.V++;
        }
        synsetIn.close();

        this.G = new Digraph(this.V);

        // Read in Hypernyms to create digraph
        In hyperIn = new In(hypernyms);
        while(!hyperIn.isEmpty()){
            String[] line = hyperIn.readLine().split(",");
            int v = Integer.parseInt(line[0]);
            for(int i = 1; i < line.length; i++){
                int w = Integer.parseInt(line[i]);
                this.G.addEdge(v,w);
            }
//            int w = Integer.parseInt(line[1]);
//            this.G.addEdge(v,w);
        }
        hyperIn.close();

        // Check to see if Digraph has a directed cycle
        DirectedCycle DirCyc = new DirectedCycle(this.G);
        if(DirCyc.hasCycle())
            throw new IllegalArgumentException("Digraph cycle detected!");

        // Create shortest ancestor path object.
        this.Sap = new SAP(this.G);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns(){
        return this.ts.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word){
        if(word == null)
            throw new IllegalArgumentException("Null argument.");
        return this.ts.contains(word);
    }

    // distance between nounA and nounB
    public int distance(String nounA, String nounB){
        nounCheck(nounA, nounB);
        // Get corresponding node values (int) of each noun
        Queue<Integer> aSet = this.ts.get(nounA);
        Queue<Integer> bSet = this.ts.get(nounB);
        return this.Sap.length(aSet,bSet);
    }

    // a synset that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB){
        nounCheck(nounA, nounB);
        // Get corresponding node values (int) of each noun
        Queue<Integer> aSet = this.ts.get(nounA);
        Queue<Integer> bSet = this.ts.get(nounB);
        int ancestor = this.Sap.ancestor(aSet,bSet);

        // Convert ancestor node number to corresponding string
        return this.st.get(ancestor);
    }

    private void nounCheck(String nounA, String nounB){
        if(nounA == null || nounB == null)
            throw new IllegalArgumentException("Null argument.");
//        if(!this.ts.contains(nounA) || !this.ts.contains(nounB))
        if(!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException("Noun argument is not a WordNet noun.");
    }
    // do unit testing of this class
    public static void main(String[] args){
        WordNet Wn = new WordNet(args[0], args[1]);
        // Go Through each noun and check if it's a noun.
        int nPairs = Wn.V/2;
        Queue<String> nouns = (Queue<String>) Wn.nouns();
        for(int i = 0; i < nPairs; i++){
            String n1 = nouns.dequeue();
            String n2 = nouns.dequeue();
            StdOut.printf("(%s, %s), distance = %d, SAP = {%s}\n",n1,n2,Wn.distance(n1,n2),Wn.sap(n1,n2));
        }
//        for(String n : nouns){
//            StdOut.println(n + " isnoun: "+ Wn.isNoun(n));
//        }
    }

}

// Check that Digraph is a rooted DAG. If not, throw an IllegalArgumentException.
//        DirectedCycle DirCyc = new DirectedCycle(G);
//                if(DirCyc.hasCycle())
//                throw new IllegalArgumentException();