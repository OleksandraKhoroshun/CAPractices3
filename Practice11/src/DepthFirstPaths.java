import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class DepthFirstPaths {
    private boolean[] marked;
    private int[] edgeTo;
    private int s;
    public int count;
    public int count_V;
    public int end;

    public DepthFirstPaths(Graph G, int s,int lamp) {
        count = 0;
        count_V = 0;
        end = lamp;
        this.s = s;
        edgeTo = new int[G.V()];
        marked = new boolean[G.V()];
        dfs(G, s);
    }

    private void dfs_rec(Graph G, int v) {
        Stack<Integer> S = new Stack<Integer>();
        S.push(v);
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                count_V++;
                edgeTo[w] = v;
                dfs_rec(G, w);

            }
        }

    }

    private void dfs(Graph G, int s) {
        Iterator<Integer>[] adj = (Iterator<Integer>[]) new Iterator[G.V()];
        for (int v = 0; v < G.V(); v++)
            adj[v] = G.adj(v).iterator();

        Stack<Integer> stack = new Stack<Integer>();
        marked[s] = true;
        stack.push(s);
        while (!stack.isEmpty()) {
            int v = stack.peek();
            if(v==end) break;
            if (adj[v].hasNext()) {
                int w = adj[v].next();
                if (!marked[w]) {
                    marked[w] = true;
                    edgeTo[w] = v;
                    stack.push(w);
                    count_V++;
                }
            } else {
                stack.pop();
            }
        }
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<Integer>();
        for (int x = v; x != s; x = edgeTo[x]) {
            path.push(x);
            count++;
        }
        path.push(s);
        return path;
    }


}
