package Graphs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class MSTAlgorithms {

  public static class djikstrasPair implements Comparable<djikstrasPair> {
    int vertex;
    String pathSoFar;
    int weightSoFar;

    djikstrasPair(int v, String p, int w) {
      vertex = v;
      pathSoFar = p;
      weightSoFar = w;
    }

    @Override
    public int compareTo(djikstrasPair o) {
      return this.weightSoFar - o.weightSoFar;
    }
  }

  public static class PrimsPair implements Comparable<PrimsPair> {
    int vertex; // current vertex
    int acquiringVertex; // vertex it came from
    int weight;

    PrimsPair(int s, int d, int w) {
      vertex = s;
      acquiringVertex = d;
      weight = w;
    }

    @Override
    public int compareTo(PrimsPair o) {
      return this.weight - o.weight;
    }
  }


  public static void djisktras(DirectedGraphImpl g, int src) {
    djikstrasPair init = new djikstrasPair(src, src + "", 0);
    PriorityQueue<djikstrasPair> pq = new PriorityQueue<>();
    Set<Integer> visited = new HashSet<>();
    pq.add(init);
    while (!pq.isEmpty()) {
      // poll
      djikstrasPair curr = pq.poll();
      // mark
      if (visited.contains(curr.vertex)) {
        continue;
      }
      visited.add(curr.vertex);
      // print
      System.out.println("path : " + curr.pathSoFar + " via weight : " + curr.weightSoFar);
      // explore neigh
      if (g.adjList.get(curr.vertex) == null) {
        continue;
      }
      for (DirectedGraphImpl.Edge e : g.adjList.get(curr.vertex)) {
        if (visited.contains(e.dest)) {
          continue;
        }
        pq.add(new djikstrasPair(e.dest, curr.pathSoFar + " -> " + e.dest, curr.weightSoFar + e.weight));
      }
    }
  }


  public static void prims(DirectedGraphImpl g, int src) {

    PrimsPair init = new PrimsPair(src, -1, 0);
    PriorityQueue<PrimsPair> pq = new PriorityQueue<>();
    Set<Integer> visited = new HashSet<>();
    pq.add(init);

    while (!pq.isEmpty()) {
      // poll
      PrimsPair curr = pq.poll();
      // mark
      if (visited.contains(curr.vertex)) {
        continue;
      }
      visited.add(curr.vertex);
      // print
      if (curr.acquiringVertex != -1) {
        System.out.println("src : " + curr.vertex + " acqVertex : " + curr.acquiringVertex + " via weight : " + curr.weight);
      }
      // explore neigh
      if (g.adjList.get(curr.vertex) == null) {
        continue;
      }
      for (DirectedGraphImpl.Edge e : g.adjList.get(curr.vertex)) {
        pq.add(new PrimsPair(e.dest, curr.vertex, e.weight));
      }
    }
  }


  public static void bfs(DirectedGraphImpl g, int src) {
    DirectedGraphImpl.Edge init = new DirectedGraphImpl.Edge(src, src, 0);
    Queue<DirectedGraphImpl.Edge> q = new LinkedList<>();
    Set<DirectedGraphImpl.Edge> visited = new HashSet<>();

    q.add(init);
    visited.add(init);
    int flag = 0;

    while (!q.isEmpty()) {
      DirectedGraphImpl.Edge curr = q.poll();
      if (flag != 0) {
        System.out.println(curr.src + " -> " + curr.dest + " with weight " + curr.weight);
      }
      flag = 1;
      if (g.adjList.get(curr.dest) == null) {
        continue;
      }
      for (DirectedGraphImpl.Edge e : g.adjList.get(curr.dest)) {
        if (visited.contains(e)) {
          continue;
        }
        q.add(e);
        visited.add(e);
      }
    }

  }

  public static void print(DirectedGraphImpl g) {
    for (int node : g.adjList.keySet()) {
      System.out.print("node [ " + node + " ] : ");
      for (DirectedGraphImpl.Edge e : g.adjList.get(node)) {
        System.out.print("< dest : " + e.dest + " , wt : " + e.weight + " > , ");
      }
      System.out.println("");
    }
  }

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

    for (int i = 0; i < g.vertices - 1; i++) {
      for (int u : g.adjList.keySet()) {
        for (DirectedGraphImpl.Edge e : g.adjList.get(u)) {
          relaxation(d, e.src, e.dest, e.weight);
        }
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

    print(g);

    System.out.println("Prinitng BFS");
    bfs(g, 0);

    System.out.println("Prinitng Shortest path using Djikstras");
    djisktras(g, 0);

    System.out.println("BellmanFord Run");
    bellmanFord(g , 0);

    System.out.println("Printing MST using Prims algorithm");
    prims(g, 0);
  }
}
