package redBlackTrees;

import java.util.Scanner;

public class RedBlackTreeDriver {

  public static void main(String[] args) {
    RedBlackTree rb = null;
    while (true) {
      String welcomeMsg = "Welcome to Nitin Bhat's Red black tree implementation:-\n" +
              "1. Insert into RB tree \n" +
              "2. Delete from RB tree \n" +
              "3. Find successor \n" +
              "4. Find Predecessor \n" +
              "5. Find min \n" +
              "6. Find max \n" +
              "7. Print RB tree level Order \n" +
              "8. Print Inorder traversal / Sorted Order \n" +
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
          if (rb == null) {
            rb = new RedBlackTree(inputs[0]);
            for (int i = 1; i < n; i++) {
              rb.insert(rb, inputs[i]);
            }
          } else {
            for (int ele : inputs) {
              rb.insert(rb, ele);
            }
          }
          System.out.println("All insertion operations successful");
          System.out.println("Height of the RB tree is " + rb.height(rb));
          break;
        }
        case 2: {
          System.out.println("Enter the key you want to delete");
          int key = sc.nextInt();
          RedBlackTree.RedBlackTreeNode keyNode = rb.search(rb, key);
          if (keyNode == null) {
            System.out.println("Key not found in RB tree");
          } else {
            RedBlackTree.delete(rb, keyNode);
            System.out.println("Deletion Successful");
            System.out.println("Height of the RB tree is " + rb.height(rb));
          }
          break;
        }
        case 3: {
          System.out.println("Enter the key you want to find successor for");
          int key = sc.nextInt();
          RedBlackTree.RedBlackTreeNode keyNode = rb.search(rb, key);
          if (keyNode == null) {
            System.out.println("Key not found in RB tree");
          } else {
            RedBlackTree.RedBlackTreeNode successor = RedBlackTree.successor(keyNode);
            if (successor == null) {
              System.out.println("Successor Not found / NUll");
            } else {
              System.out.println(successor.key + " , " + successor.color);
            }
            System.out.println("Height of the RB tree is " + rb.height(rb));
          }
          break;
        }
        case 4: {
          System.out.println("Enter the key you want to find predecessor for");
          int key = sc.nextInt();
          RedBlackTree.RedBlackTreeNode keyNode = rb.search(rb, key);
          if (keyNode == null) {
            System.out.println("Key not found in RB tree");
          } else {
            RedBlackTree.RedBlackTreeNode predecessor = RedBlackTree.predecessor(keyNode);
            if (predecessor == null) {
              System.out.println("predecessor Not found / NUll");
            } else {
              System.out.println(predecessor.key + " , " + predecessor.color);
            }
            System.out.println("Height of the RB tree is " + rb.height(rb));
          }
          break;

        }
        case 5: {
          System.out.println("Enter the key in whose subtree you want to find min");
          int key = sc.nextInt();
          RedBlackTree.RedBlackTreeNode keyNode = rb.search(rb, key);
          if (keyNode == null) {
            System.out.println("Key not found in RB tree");
          } else {
            RedBlackTree.RedBlackTreeNode min = RedBlackTree.min(keyNode);
            System.out.println(min.key + " , " + min.color);
            System.out.println("Height of the RB tree is " + rb.height(rb));
          }
          break;
        }
        case 6: {
          System.out.println("Enter the key in whose subtree you want to find max");
          int key = sc.nextInt();
          RedBlackTree.RedBlackTreeNode keyNode = rb.search(rb, key);
          if (keyNode == null) {
            System.out.println("Key not found in RB tree");
          } else {
            RedBlackTree.RedBlackTreeNode max = RedBlackTree.max(keyNode);
            System.out.println(max.key + " , " + max.color);
            System.out.println("Height of the RB tree is " + rb.height(rb));
          }
          break;
        }
        case 7: {
          System.out.println("Here is a level order print of this RB tree");
          rb.printLevelOrder(rb);
          System.out.println(" ");
          System.out.println("Height of the RB tree is " + rb.height(rb));
          break;
        }
        case 8: {
          System.out.println("Here is the sorted order of this RB tree");
          rb.sort(rb);
          System.out.println("");
          System.out.println("Height of the RB tree is " + rb.height(rb));
          break;
        }
        default: {
          System.out.println("Hope you had fun playing around with RB trees!!!!!!!!!\n" +
                  "- garbage collector");
          return;
        }
      }
    }
  }
}
