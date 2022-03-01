import ua.princeton.lib.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Outcast {

    private WordNet wordnet;

    // конструктор приймає об’єкт WordNet
    public Outcast(WordNet wordnet){
        this.wordnet = wordnet;
    }
    // маючи масив WordNet іменників, повернути «ізгоя»
    public String outcast(String[] nouns){
        String outcast = nouns[0];
        int d = 0;
        for (String n : nouns) {
            d += wordnet.distance(nouns[0], n);
        }
        for (int i = 1; i < nouns.length; i++) {
            int tempdist = 0;
            for (String n : nouns) {
                tempdist += wordnet.distance(nouns[i], n);
            }
            if (d < tempdist) {
                d = tempdist;
                outcast = nouns[i];
            }
        }
        return outcast;
    }
    public static void main(String[] args) throws IOException {
        String s ="synsets.txt";

        String h = "hypernyms.txt";
        WordNet wordnet = new WordNet(s, h);
        Outcast outcast = new Outcast(wordnet);
        //for (int t = 2; t < args.length; t++) {
            String[] nouns = "blue green yellow brown black white orange violet red".split(" ");
            StdOut.println(outcast.outcast(nouns));


        }
   // }

}
