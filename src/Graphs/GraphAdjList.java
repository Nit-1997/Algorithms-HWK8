package Graphs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GraphAdjList {

  public List<Integer>[] adjList;
  public int V;

  GraphAdjList(int V){
    this.V = V;
    adjList = new List[V];
    for(int i=0 ; i< adjList.length; i++){
      adjList[i] = new ArrayList<>();
    }
  }

  GraphAdjList(GraphAdjList G){
    adjList = new List[G.V];
    V = G.V;
    for(int i=0 ; i< adjList.length; i++){
      adjList[i] = new ArrayList<>(G.adjList[i]);
    }
  }

  public void addNode(int src , int dest){
    adjList[src].add(dest);
  }

  public void printGraph(){
    int i = 0;
    for(List<Integer> node : adjList){
      System.out.print("Node  [ "+i+" ] : ");
      for(int ele : node){
        System.out.print(ele+" ");
      }
      System.out.println("");
      i++;
    }
  }

  public int findMinDist(int src , int dest){
    Queue<Integer> q = new LinkedList<>();
    boolean[] visited = new boolean[adjList.length];
    int[] parent = new int[adjList.length];

    int curr = src;
    parent[curr] = -1;
    q.add(curr);
    visited[curr] = true;
    while(!q.isEmpty()){
      curr = q.poll();
      for(int neighbour : adjList[curr]){
        if(visited[neighbour]){
          continue;
        }
        q.add(neighbour);
        visited[neighbour] = true;
        parent[neighbour] = curr;
      }
    }

    int dist = 0;
    int iterator = dest;
    while(parent[iterator] != -1){
      iterator = parent[iterator];
      dist++;
    }
    return dist;
  }


}
