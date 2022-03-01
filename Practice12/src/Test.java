public class Test {
    public static void main(String[] args){
        String s ="synsets.txt";
        String h = "hypernyms.txt";
        WordNet wordnet = new WordNet(s, h);

        String res = wordnet.sap("cat","dog");
        System.out.println(res);

    }
}
