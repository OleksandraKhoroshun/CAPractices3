import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BreadthFirstPaths{
    private boolean[] marked;
    private int[] edgeTo;
    private int[] distTo;
    public int count;
    public int count_V;
    public int end;

    public BreadthFirstPaths(Graph G, int s,int lamp) {
        count=0;
        count_V=0;
        end =lamp;
        marked = new boolean[G.V()];
        distTo = new int[G.V()];
        edgeTo = new int[G.V()];
        bfs(G, s);

    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public int distTo(int v) {
        return distTo[v];
    }

    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<Integer>();
        int i;
        for (i = v; distTo[i] != 0; i = edgeTo[i]) {
            path.push(i);
            count++;
        }
        path.push(i);
        return path;
    }

    private void bfs(Graph G, int s) {
        Queue<Integer> q = new LinkedList<Integer>();
        q.add(s);
        marked[s] = true;
        distTo[s] = 0;
        while (!q.isEmpty()) {
            int v = q.remove();
            if(v==end) break;
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    count_V++;
                    q.add(w);
                    marked[w] = true;
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                }
            }
        }
    }

}
