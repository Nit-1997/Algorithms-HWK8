package Graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DirectedGraphImpl {
  public static class Edge{
    int weight;
    int src;
    int dest;
    Edge(int src , int dest , int weight){
      this.weight = weight;
      this.src = src;
      this.dest = dest;
    }
  }

  public int vertices;
  public Map<Integer , List<Edge>> adjList;

  DirectedGraphImpl(int v){
    this.adjList = new HashMap<>();
    this.vertices = v;

  }

  public void addEdge(int src , int dest , int wt) {
     Edge e = new Edge(src, dest, wt);
     if(this.adjList.get(src) == null){
       this.adjList.put(src , new LinkedList<>());
       this.adjList.get(src).add(e);
     }else{
       this.adjList.get(src).add(e);
     }
  }
}
