import ua.princeton.lib.*;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    private int V;
    private int E;
    private List<Integer>[] arr;
    public int count;


    Graph(int V){
        this.V = V;
        this.E = 0;
        arr = new List[V];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new ArrayList<>();
        }
    }
    //створити порожній граф з V вершин

    Graph(In in){
        count=0;
        this.V = in.readInt();
        arr = new List[V];
        for (int i = 0; i < V; i++) {
            arr[i] = new ArrayList<>();
        }
        int E = in.readInt();
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            addEdge(v, w);
        }
        }
    // створити граф з вхідного потоку

    void addEdge(int v, int w) {
        E++;
        arr[v].add(w);
        arr[w].add(v);
      // Collections.sort(arr[v]);
       // Collections.sort(arr[w]);
    }
    // додати ребро v-w

    Iterable<Integer> adj(int v){
        return arr[v];
    }
    //вершини з’єднані (сусідні) з v

    int V(){
        count++;return V;
    }
    //кількість вершин

    int E(){
        return E;
    }
    //кількість ребер

}
