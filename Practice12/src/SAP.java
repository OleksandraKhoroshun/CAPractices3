import ua.princeton.lib.*;

import java.util.HashMap;
import java.util.HashSet;

public class SAP {
    private Digraph digraph;

    private HashMap<HashSet<Integer>, int[]> cache;

    // конструктор приймає орграф (не обов’язково DAG)
    public SAP(Digraph G){
        if (G == null)
            throw new IllegalArgumentException("Argument is null");

        digraph = new Digraph(G);

        cache = new HashMap<>();
    }

    // довжина найкоротшого шляху до спільного батька v та w
//-1 якщо такого шляху немає
    public int length(int v, int w){
        sap(v, w);
        HashSet<Integer> key = new HashSet<>();
        key.add(v);
        key.add(w);
        int[] value = cache.get(key);
        return value[0];
    }

    // спільний батько v та w,  з найкоротшого шляху
//-1 якщо такого шляху немає
    public int ancestor(int v, int w){
        sap(v, w);
        HashSet<Integer> key = new HashSet<>();
        key.add(v);
        key.add(w);
        int[] value = cache.get(key);
        return value[1];
    }

    // довжина найкоротшого шляху між будь-якою вершиною з v та з w;
//-1 якщо такого шляху немає
    public int length(Iterable<Integer> v, Iterable<Integer> w){
        return sap(v, w)[0];
    }

    // спільний батько з найкоротшого шляху …;
//-1 якщо такого шляху немає
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w){
        return sap(v, w)[1];
    }

    private void sap(int v, int w) {
        if (v < 0 || w < 0)
            throw new IllegalArgumentException("Argument is negative");

        HashSet<Integer> key = new HashSet<>();
        key.add(v);
        key.add(w);

        if (cache.containsKey(key)) return;

        BreadthFirstDirectedPaths vPath = new BreadthFirstDirectedPaths(digraph, v);

        BreadthFirstDirectedPaths wPath = new BreadthFirstDirectedPaths(digraph, w);

        int dist = Integer.MAX_VALUE;
        int ancestor = -2;

        for (int vx = 0; vx < digraph.V(); vx++) {
            if (vPath.hasPathTo(vx) && vPath.distTo(vx) < dist && wPath.hasPathTo(vx)
                    && wPath.distTo(vx) < dist) {
                int sum = vPath.distTo(vx) + wPath.distTo(vx);
                if (dist > sum) {
                    dist = sum;
                    ancestor = vx;
                }
            }
        }

        if (dist == Integer.MAX_VALUE) {
            dist = -1;
            ancestor = -1;
        }

        int[] value = new int[] { dist, ancestor };
        cache.put(key, value);
    }

    private int[] sap(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            throw new IllegalArgumentException("Argument is null");

        BreadthFirstDirectedPaths vPath = new BreadthFirstDirectedPaths(digraph, v);

        BreadthFirstDirectedPaths wPath = new BreadthFirstDirectedPaths(digraph, w);

        int dist = Integer.MAX_VALUE;
        int ancestor=-2;

        for (int vx = 0; vx < digraph.V(); vx++) {
            if (vPath.hasPathTo(vx) && vPath.distTo(vx) < dist && wPath.hasPathTo(vx)
                    && wPath.distTo(vx) < dist) {
                int sum = vPath.distTo(vx) + wPath.distTo(vx);
                if (dist > sum) {
                    dist = sum;
                    ancestor = vx;
                }
            }
        }

        if (dist != Integer.MAX_VALUE) {
            return new int[] { dist, ancestor };
        } else {
            return new int[] { -1, -1 };
        }
    }

    public static void main(String[] args) {
        In in = new In("test.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }


}
