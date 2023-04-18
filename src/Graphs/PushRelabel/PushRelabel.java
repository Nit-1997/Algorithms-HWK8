package Graphs.PushRelabel;

import java.util.Scanner;

/**
 Send as much flow from s as possible.
 Build a list of all vertices except s and t.
 As long as we have not traversed the entire list:
 Discharge the current vertex.
 If the height of the current vertex changed:
 Move the current vertex to the front of the list
 Restart the traversal from the front of the list.
*/
 public class PushRelabel {

  private GraphNodeImpl flowNetwork;

  public PushRelabel(GraphNodeImpl flownetwork) {
    flowNetwork = flownetwork;
  }

  /**
   * The push method calculates the amount of flow that can be pushed from one node to another based on
   * the excess flow at the first node and the residual capacity of the edge between them.
   * It then updates the excess flow at both nodes and the flows along the edge accordingly.
   * @param flowNetwork network flow graph
   * @param src src to push excess flow from
   * @param dest destination to send excess flow to
   * @return amount flown from src to dest.
   */
  private int push(GraphNodeImpl flowNetwork, int src, int dest) {
    int flowAmt = Math.min(flowNetwork.excess[src],
            flowNetwork.capacities[src][dest] - flowNetwork.flows[src][dest]);
    flowNetwork.excess[dest] += flowAmt;
    flowNetwork.flows[src][dest] += flowAmt;
    flowNetwork.excess[src] -= flowAmt;
    // adding residual edge in the residual graph
    flowNetwork.flows[dest][src] -= flowAmt;
    return flowAmt;
  }

  /**
   * The relabel method increases the height of a node if it has excess flow but cannot push it to any of
   * its neighbors due to their heights being equal or greater. It does this by finding the minimum height
   * among its neighbors with positive residual capacity and setting its own height to one more than that.
   * @param flowNetwork network flow graph
   * @param target target node to be relabelled
   */
  private void relabel(GraphNodeImpl flowNetwork, int target) {
    int minHeight = Integer.MAX_VALUE;
    System.out.println("Neighbours of "+target+" nodes are :");
    for (int i = 0; i < flowNetwork.neighbList[target].length; i++) {
      int currentNeighbour = flowNetwork.neighbList[target][i];
      System.out.print(currentNeighbour+" ");
      if (flowNetwork.capacities[target][currentNeighbour] - flowNetwork.flows[target][currentNeighbour] > 0
              && flowNetwork.height[currentNeighbour] >= flowNetwork.height[target]) {
        if (flowNetwork.height[currentNeighbour] < minHeight) {
          minHeight = flowNetwork.height[currentNeighbour];
        }
      }
    }
    flowNetwork.height[target] = minHeight + 1;
    System.out.println("");
  }


  /**
   * The discharge method repeatedly pushes flow from a node with excess flow to its neighbors until it
   * has no more excess flow or all its neighbors have been tried. If it still has excess flow after
   * trying all its neighbors, it relabels itself to increase its height.
   * @param flowNetwork network flow graph
   * @param target target node to flow excess from
   * @param currentNeighbList neighbour list of the target node
   * @return
   */
  private int[] discharge(GraphNodeImpl flowNetwork, int target, int[] currentNeighbList) {
    //while target vertex of discharge has excess
    while (flowNetwork.excess[target] > 0) {
      //retrieve the neighbor we are currently inspecting
      int neighbInd = currentNeighbList[target];
      //ensure that we have not looked through all neighbors
      if (neighbInd >= flowNetwork.neighbList[target].length) {
        //if we have, then we must relabel and reset our current inspection to the front of the list
        System.out.println("**************Resiudal Graph before Relabel Operation****************");
        flowNetwork.printResidualGraph();
        System.out.println("Vertex to be relabeled : "+target);
        relabel(flowNetwork, target);
        System.out.println("**************Resiudal Graph after Relabel Operation****************");
        flowNetwork.printResidualGraph();
        Scanner scanner = new Scanner(System.in);
        int holder  = scanner.nextInt();
        currentNeighbList[target] = 0;
      } else {
        //otherwise find the id of the neighbor, check if it is a valid push target and push, if not move to next neighbor
        int neighb = flowNetwork.neighbList[target][neighbInd];
        int residualCap = flowNetwork.capacities[target][neighb] - flowNetwork.flows[target][neighb];
        if (residualCap > 0 && flowNetwork.height[target] == (flowNetwork.height[neighb] + 1)) {
          System.out.println("**********Residual Graph Before Push Operation***************");
          flowNetwork.printResidualGraph();
          push(flowNetwork, target, neighb);
          System.out.println("**********Residual Graph After Push Operation***************");
          flowNetwork.printResidualGraph();
        } else {
          currentNeighbList[target]++;
        }
      }
    }
    return currentNeighbList;
  }

  /**
   * getMaxFlow method runs the algorithm by repeatedly discharging nodes with excess flow
   * until no more nodes have excess flow. The maximum flow is then equal to the excess flow at the
   * sink node..
   * @return max flow
   */
  public int getMaxFlow() {
    this.init();
    //set up the dischargeList, does not include source or sink
    int[] dischargeList = new int[flowNetwork.numVertices - 2];
    //each vertex has a current neighbor being inspected
    int[] currentNeighbList = new int[flowNetwork.numVertices];

    //Init Discharge List
    int counter = 0;
    for (int i = 0; i < flowNetwork.numVertices; i++) {
      if (!(i == flowNetwork.src || i == flowNetwork.sink)) {
        dischargeList[counter] = i;
        counter++;
      }
    }
    int curVertexIndex = 0;
    //if we get to the end then we are finished
    while (curVertexIndex < dischargeList.length) {
      int curVert = dischargeList[curVertexIndex];
      int curVertHeight = flowNetwork.height[curVert];
      discharge(flowNetwork, curVert, currentNeighbList);
      if (flowNetwork.height[curVert] > curVertHeight) {//curVert was relabeled
        dischargeList = moveIndexToFrontOfAry(dischargeList, curVertexIndex);
        curVertexIndex = 0;
      } else {
        curVertexIndex++;
      }
    }
    //solution equals excess at the sink
    return flowNetwork.excess[flowNetwork.sink];
  }

  /**
   * The init method sets up the algorithm by setting the height of the source node to the number of
   * vertices in the network and temporarily allowing it to have infinite excess flow. It then pushes as
   * much flow as possible from the source to its neighbors and sets its excess flow to negative
   * the sum of outflows.
   */
  private void init() {
    flowNetwork.height[flowNetwork.src] = flowNetwork.numVertices;//height of source = |V|
    flowNetwork.excess[flowNetwork.src] = Integer.MAX_VALUE;//temporarily allow source to have infinite excess to utilize push
    int sourceOutflowSum = 0;
    for (int i = 0; i < flowNetwork.neighbList[flowNetwork.src].length; i++) {
      int vertID = flowNetwork.neighbList[flowNetwork.src][i];
      System.out.println("**********Residual Graph Before Push Operation***************");
      flowNetwork.printResidualGraph();
      System.out.println("************Residual Graph After Push Operation**************");
      sourceOutflowSum += push(flowNetwork, flowNetwork.src, vertID);
      flowNetwork.printResidualGraph();
    }
    flowNetwork.excess[flowNetwork.src] = -sourceOutflowSum;
  }

  /**
   * The moveIndexToFrontOfAry method moves an index in an array to the front by shifting all elements
   * between it and the front one position to the right.
   * @param list
   * @param targetInd
   * @return
   */
  private int[] moveIndexToFrontOfAry(int[] list, int targetInd) {
    int vertID = list[targetInd];
    for (int i = targetInd; i > 0; i--) {
      list[i] = list[i - 1];
    }
    list[0] = vertID;
    return list;
  }
}
