package Graphs.PushRelabel;

public class Driver {

  public static void main(String[] args) {
    //int noOfVertices = 3;
    //int source=0;
    //int sink=2;
    //int[][] caps1  = {{0,3,2},{0,0,2},{0,0,0}};
    //int[][] caps2  = {{0,16,13,0,0,0},{0,0,0,12,0,0},{0,4,0,0,14,0},{0,0,9,0,0,20},{0,0,0,7,0,4},{0,0,0,0,0,0}};

//    int[][] caps2 = {
//            {0, 16, 13, 0, 0, 0},
//            {0, 0, 10, 12, 0, 0},
//            {0, 4, 0, 0, 14, 0},
//            {0, 0, 9, 0, 0, 20},
//            {0, 0, 0, 7, 0, 4},
//            {0, 0, 0, 0, 0, 0}
//    };

    int[][] caps = {
            { 0, 3 , 0 , 3 ,0},
            { 0, 0 , 4 , 2 ,0},
            { 0, 0 , 0 , 0 ,3},
            { 0, 0 , 1 , 0 ,2},
            { 0, 0 , 0 , 0 ,0}
    };


    //GraphNodeImpl fn1 = new GraphNodeImpl(noOfVertices,source,sink,caps1);
    GraphNodeImpl fn2 = new GraphNodeImpl(5,0,4,caps);
   // PushRelabel pr1 = new PushRelabel(fn1);
    PushRelabel pr2 = new PushRelabel(fn2);


    //System.out.println(pr1.getMaxFlow());
    System.out.println(pr2.getMaxFlow());
  }
}
