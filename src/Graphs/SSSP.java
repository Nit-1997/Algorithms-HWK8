package Graphs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import javax.sound.sampled.Port;

public class SSSP {


  public static void relaxation(Map<Integer, Integer> d, int u, int v, int wt) {
    if (d.getOrDefault(u , Integer.MAX_VALUE) + wt < d.getOrDefault(v , Integer.MAX_VALUE)) {
      d.put(v, d.get(u) + wt);
    }
  }

  public static void bellmanFord(DirectedGraphImpl g, int src) {
    //initializing the d map
    Map<Integer, Integer> d = new HashMap<>();
    for (int key : g.adjList.keySet()) {
      d.put(key, Integer.MAX_VALUE);
    }

    d.put(src , 0);

    Queue<DirectedGraphImpl.Edge> q = new LinkedList<>();
    q.add(new DirectedGraphImpl.Edge(src, src, 0));


    while(!q.isEmpty()){
      DirectedGraphImpl.Edge curr = q.poll();
      if (g.adjList.get(curr.dest) == null) {
        continue;
      }
      for(DirectedGraphImpl.Edge e : g.adjList.get(curr.dest)){
          q.add(e);
          relaxation(d,e.src , e.dest , e.weight);
      }
    }
    System.out.println(d);
  }


  public static void main(String[] args) {
    int v = 7;
    DirectedGraphImpl g = new DirectedGraphImpl(v);
    int[] src = {0, 0, 1, 3, 3, 4, 4, 5};
    int[] dest = {3, 1, 2, 2, 4, 5, 6, 6};
    int[] wt = {40, 10, 10, 10, 2, 3, 8, 3};

    for (int i = 0; i < src.length; i++) {
      g.addEdge(src[i], dest[i], wt[i]);
    }

    MSTAlgorithms.print(g);


    System.out.println("BellmanFord Run");

    bellmanFord(g , 0);
  }
}
