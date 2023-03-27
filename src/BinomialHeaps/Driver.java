package BinomialHeaps;


import java.util.Scanner;

public class Driver {
  public static void main(String[] args) {
    BinomialHeap bh = null;
    while (true) {
      String welcomeMsg = "Welcome to Nitin Bhat's Binomial Heap implementation:-\n" +
              "1. Insert into heap \n" +
              "2. Delete from heap \n" +
              "3. Decrease key \n" +
              "4. Union \n" +
              "5. Extract Min \n" +
              "6. Get Min \n" +
              "7. Print \n" +
              "Press 0 to Exit \n";

      System.out.println(welcomeMsg);
      Scanner sc = new Scanner(System.in);
      System.out.println("Choose Your operation :");
      switch (sc.nextInt()) {
        case 1: {
          System.out.println("Enter the number of insertions you want to perform :");
          int n = sc.nextInt();
          int[] inputs = new int[n];
          System.out.println("Enter your inputs");
          for (int i = 0; i < n; i++) {
            inputs[i] = sc.nextInt();
          }
          if (bh == null) {
            bh = new BinomialHeap();
          }
          for (int ele : inputs) {
            bh.insert(ele);
          }
          System.out.println("All insertion operations successful");
          break;
        }
        case 2: {
          System.out.println("Enter the key you want to delete");
          int key = sc.nextInt();
          bh.delete(key);
          break;
        }
        case 3: {
          System.out.println("Enter the key you want to Decrease");
          int key = sc.nextInt();
          System.out.println("Enter the new key you want to Decrease it to");
          int newKey = sc.nextInt();
          bh.decreaseKeyValue(key , newKey);
          break;
        }
        case 4: {
          System.out.println("Create a new Binomial Heap to do a union with : ");
          System.out.println("Enter the number of keys in the new heap");
          int n = sc.nextInt();
          int[] inputs = new int[n];
          System.out.println("Enter each key");
          for(int  i = 0 ; i < n ; i++){
            inputs[i] = sc.nextInt();
          }
          System.out.println("Creating new Heap h2 for you .......");
          BinomialHeap h2 = new BinomialHeap();
          for(int ele : inputs){
            h2.insert(ele);
          }
          System.out.println("############## H2 looks like this #################");
          h2.printHeap();
          System.out.println("Merging h2 to original heap");
          bh.union(h2.Nodes);
          System.out.println("Heap after union");
          bh.printHeap();
          break;
        }
        case 5: {
          System.out.println("Extracting min .....");
          int min = bh.extractMin();
          System.out.println("Min extracted and the value is : "+min);
          break;
        }
        case 6: {
          System.out.println("Getting the min value ..........");
          int min = bh.findMinimum();
          System.out.println("Miniumum value is : ");
          System.out.println(min);
          break;
        }
        case 7: {
          System.out.println(".....................Printing Heap .........................");
          bh.printHeap();
          break;
        }
        default: {
          System.out.println("Hope you had fun playing around with Binomial Heaps!!!!!!!!!\n" +
                  "- garbage collector");
          return;
        }
      }
    }
  }
}
