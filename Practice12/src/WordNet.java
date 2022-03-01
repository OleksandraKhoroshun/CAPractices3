import ua.princeton.lib.*;

import java.util.HashMap;

public class WordNet {

    private Digraph wordNet;
    private HashMap<String, Bag<Integer>> synMap;
    private HashMap<Integer, String[]> synSets;
    private SAP sap;
    private int count;

    // конструктор приймає назви двох файлів
    public WordNet(String synsets, String hypernyms){
        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException("Arguments in WordNet() are null");

        synMap = new HashMap<>();
        synSets = new HashMap<>();

        readSynsets(synsets);
        wordNet = new Digraph(count);

        readHypernyms(hypernyms);

        DirectedCycle dc = new DirectedCycle(wordNet);
        if (dc.hasCycle())
            throw new IllegalArgumentException("Input has a cycle");
        sap = new SAP(wordNet);

        int root = 0;
        for (int v = 0; v < count; v++)
            if (wordNet.outdegree(v) == 0) root++;
        if (root != 1) throw new IllegalArgumentException("Input has " + root + " roots");

    }

    // множина іменників, що повертається як ітератор (без дублікатів)
    public Iterable<String> nouns(){
        return synMap.keySet();
    }

    // чи є слово серед WordNet іменників?
    public boolean isNoun(String word){
        if (word == null) throw new IllegalArgumentException("Input is null");
        return synMap.containsKey(word);
    }

    // відстань між nounA і nounB
    public int distance(String nounA, String nounB){
        if (!synMap.containsKey(nounA) || !synMap.containsKey(nounB))
            throw new IllegalArgumentException("Noun is not in wordNet");

        Bag<Integer> idsA = synMap.get(nounA);
        Bag<Integer> idsB = synMap.get(nounB);

        return sap.length(idsA, idsB);
    }

    // синсет що є спільним предком nounA і nounB
// в найкоршому шляху до предка
    public String sap(String nounA, String nounB){
        if (nounA == null || nounB == null)
            throw new IllegalArgumentException("Arguments in sap() are null");

        Bag<Integer> idsA = synMap.get(nounA);
        Bag<Integer> idsB = synMap.get(nounB);

        int id = sap.ancestor(idsA, idsB);
        return synSets.get(id)[0];
    }

    private void readSynsets(String synsets) {
        In in = new In(synsets);
        count = 0;
        while (in.hasNextLine()) {
            count++;
            String line = in.readLine();
            String[] parts = line.split(",");

            int id = Integer.parseInt(parts[0]);

            String[] arr= new String[2];
            arr[0] = parts[1];
            arr[1] = parts[2];
            synSets.put(id, arr);

            String[] nouns = parts[1].split(" ");

            for (String n : nouns) {
                if (synMap.get(n) != null) {
                    Bag<Integer> bag = synMap.get(n);
                    bag.add(id);
                } else {
                    Bag<Integer> bag = new Bag<>();
                    bag.add(id);
                    synMap.put(n, bag);
                }
            }
        }
    }

    private void readHypernyms(String hypernyms) {
        In in = new In(hypernyms);
        while (in.hasNextLine()) {

            String line = in.readLine();
            String[] parts = line.split(",");

            int v = Integer.parseInt(parts[0]);

            for (int i = 1; i < parts.length; i++) {
                int w = Integer.parseInt(parts[i]);
                wordNet.addEdge(v, w);
            }

        }
    }

    // тестування


}
