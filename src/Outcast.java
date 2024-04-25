import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
public class Outcast{
    private final WordNet Wn;
    public Outcast(WordNet wordNet){
        this.Wn = wordNet;
    }

    // given an array of Wordnet nouns, return an outcast
    public String outcast(String[] nouns){
        int N = nouns.length;
        int dmax = 0;
        String out = nouns[0];
        for(int i = 0; i < N; i++){
            int dij = 0;
            for(int j = 0; j < N; j++){
                dij += Wn.distance(nouns[i],nouns[j]);
            }
            if(dij >= dmax){
                dmax = dij;
                out = nouns[i];
            }
        }
        return out;
    }

    public static void main(String[] args){
        WordNet wordnet = new WordNet(args[0],args[1]);
        Outcast outcast = new Outcast(wordnet);
        for(int t = 2; t < args.length; t++){
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}