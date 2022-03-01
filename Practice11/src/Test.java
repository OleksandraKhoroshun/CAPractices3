import ua.princeton.lib.*;

import java.util.Random;

public class Test {
    public static void main(String[] args) {
        In in = new In("test.txt");
        Graph G = new Graph(in);
        int s = 0;
        Random rand = new Random();
        int lamp;
        lamp= rand.nextInt(G.V());
        lamp=166;
        System.out.println("lamp: "+lamp);
        System.out.println("DFS:");

        DepthFirstPaths dfs = new DepthFirstPaths(G, s,lamp);
            int n=0;
            if (dfs.hasPathTo(lamp)) {
                System.out.println(s+" to "+lamp+": ");
                for (int x : dfs.pathTo(lamp)) {
                    System.out.println(x);
                }
                System.out.println("length: "+dfs.count);
                System.out.println("visited: "+dfs.count_V);
            }
            else System.out.println("No path.");


        System.out.println("BFS:");

        BreadthFirstPaths bfs = new BreadthFirstPaths(G, s,lamp);

        if (bfs.hasPathTo(lamp)) {
            System.out.println(s+" to "+lamp+": ");
            for (int x : bfs.pathTo(lamp)) {
                System.out.println(x);
            }
            System.out.println("length: "+bfs.count);
            System.out.println("visited: "+bfs.count_V);
        }
        else System.out.println("No path.");





    }
}