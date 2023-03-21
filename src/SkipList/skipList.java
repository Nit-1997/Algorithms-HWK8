package SkipList;

import java.util.Random;

public class skipList {
  /**
   * SkipList node representation.
   */
  public static class listNode{
     public int key;
     public listNode down;
     public listNode up;
     public listNode next;
     public listNode prev;

     listNode(int key){
       this.key = key;
       this.up = null;
       this.down = null;
       this.next = null;
       this.prev = null;
     }
  }

  /**
   * Sentinals of skip list.
   */
  public listNode head;
  public listNode tail;
  public int height = 1;

  /**
   * Constructor to initialize a skip list.
   */
  public skipList(){
     this.head = new listNode(Integer.MIN_VALUE);
     this.tail =  new listNode(Integer.MAX_VALUE);
     this.head.next  = this.tail;
     this.tail.prev = this.head;
  }

  /**
   * Prints the skiplist in human readable format.
   */
  public void print() {
    listNode current = head;
    while (current != null) {
      listNode right = current;
      while (right != null) {
        System.out.print(right.key + " ");
        right = right.next;
      }
      System.out.println();
      current = current.down;
    }
  }

  /**
   * Search the appropriate position of the key in the skiplist.
   * @param key key to be searched
   * @return spot node.
   */
  public listNode search(int key){
    listNode headRef = this.head;
    while(headRef != null){
      while(headRef.next != null && headRef.next.key <= key){
          System.out.println("Going right >");
          headRef = headRef.next;
      }
      if(headRef.down != null){
         System.out.println("Going down v");
         headRef = headRef.down;
      }else{
        return headRef;
      }
    }
    return headRef;
  }

  /**
   * Simulates a coin flip.
   * @return HEAD or TAIL randomly.
   */
  public static String coinFlip() {
    Random rand = new Random();
    int num = rand.nextInt(2);
    return num == 1 ? "HEAD" : "TAIL";
  }

  /**
   * Insert node appropriately to the skip list.
   * @param key key to be inserted
   */
  public void insert(int key){
     listNode spotNode = this.search(key);
     int currLevel = 1;
     if(spotNode.key != key){
       listNode newNode = new listNode(key);
       listNode nextPtr = spotNode.next;
       spotNode.next = newNode;
       newNode.prev = spotNode;
       newNode.next = nextPtr;
       nextPtr.prev = newNode;
       while(coinFlip().equals("HEAD")){
          // do some work
         if(currLevel == this.height){
           addLevelToSkipList();
         }
         // create newNode for the new level and connect it to current
         listNode newLvlNode = new listNode(key);
         newLvlNode.down = newNode;
         newNode.up = newLvlNode;
         // backtrack left until it has up , connect it to newNode
         listNode backLeft = newNode;
         backLeft = backLeft.prev;
         while(backLeft.up == null){
           backLeft = backLeft.prev;
         }
         backLeft = backLeft.up;
         // backtrack right until it has up , connect it to newNode
         listNode backRight = newNode;
         backRight = backRight.next;
         while(backRight.up == null){
           backRight = backRight.next;
         }
         backRight = backRight.up;

         //connect
         backLeft.next = newLvlNode;
         newLvlNode.prev = backLeft;
         newLvlNode.next = backRight;
         backRight.prev = newLvlNode;
         newNode = newLvlNode;
         currLevel++;
       }
     }
  }

  /**
   * Add a level to the skip list.
   */
  private void addLevelToSkipList(){
    this.height++;
    listNode newHead = new listNode(Integer.MIN_VALUE);
    listNode newTail = new listNode(Integer.MAX_VALUE);

    newHead.next = newTail;
    newTail.prev = newHead;
    newHead.down = head;
    newTail.down = tail;
    head.up = newHead;
    tail.up = newTail;
    head = newHead;
    tail = newTail;
  }

  /**
   * Deletes all the presence of keys in the skiplist.
   * @param key key to be deleted.
   */
  public void delete(int key){
    listNode spotNode = this.search(key);
    while(spotNode != null) {
      spotNode.prev.next = spotNode.next;
      spotNode = spotNode.up;
    }
  }


  /**
   * Tells if key is present or not in the skip list.
   * @param key key to be searched
   * @return true or false
   */
  public boolean lookup(int key){
    listNode spot = search(key);
    return spot.key == key;
  }
}
