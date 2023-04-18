package Graphs;

import java.util.ArrayList;
import java.util.List;

public class CLRS_22_5_1 {

  public static GraphAdjList generateG2AdjList(GraphAdjList G){
      GraphAdjList G2 = new GraphAdjList(G);

      for(int i = 0 ; i < G2.adjList.length; i++){
         List<Integer> updatedEdges = new ArrayList<>(G2.adjList[i]);
         for(int currNeigh : G2.adjList[i]){
             updatedEdges.addAll(G2.adjList[currNeigh]);
         }
         G2.adjList[i] = updatedEdges;
      }

      return G2;
  }



  //Runner method
  public static void main(String[] args) {
    int v = 6;
    GraphAdjList G = new GraphAdjList(v);


    int[] srcs = {0,1,2,4,3,0};
    int[] dests  = {1,2,4,5,4,3};

    for(int i = 0 ; i< srcs.length ; i++){
      int src = srcs[i];
      int dest = dests[i];
      G.addNode(src,dest);
    }

    System.out.println("Graph G adjList is : ");
    G.printGraph();

    GraphAdjList G2 = generateG2AdjList(G);

    System.out.println("Graph G2 adjList is : ");
    G2.printGraph();


  }
}
