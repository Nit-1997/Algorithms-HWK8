package HashTables;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Hash table representation.
 */
public class Hash {
  /**
   * Key : value pair linked list structure.
   */
  public class ListNode {
    public String key;
    public int value;

    public ListNode(String key, int value) {
      this.key = key;
      this.value = value;
    }
  }

  /**
   * Linked list implementation
   */
  public class LinkedList {
    public ListNode value;
    public LinkedList next;

    LinkedList() {
      this.value = null;
      this.next = null;
    }
  }

  /**
   * catalog of indexes to be mapped using hash function.
   */
  private LinkedList[] catalog;
  private int MAX;


  /**
   * initializing the hash table to have catalog of empty linkedlists.
   */
  public Hash(int max) {
    this.MAX = max;
    this.catalog = new LinkedList[this.MAX];
    Arrays.fill(this.catalog, null);
  }

  /**
   * Insert/Update the key:value pair in the hash table
   *
   * @param key   String word
   * @param value Integer count
   */
  public void insert(String key, int value) {
    // key not present
    int index = this.hash(key);
    if (this.catalog[index] == null) {
      // if the catalog index is empty initialize it with a list
      LinkedList head = new LinkedList();
      head.value = new ListNode(key, value);
      head.next = null;
      this.catalog[index] = head;
    } else {
      LinkedList head = this.catalog[index];
      while (head.next != null) {
        if (head.value.key.equals(key)) {
          // if key already present in the hash table, update the value
          head.value.value = value;
          return;
        }
        head = head.next;
      }
      //if not present append the new node at the end of the list
      LinkedList newElement = new LinkedList();
      newElement.value = new ListNode(key, value);
      newElement.next = null;
      head.next = newElement;
    }
  }

  /**
   * Deletes the key from the hashtable.
   *
   * @param key key to be deleted.
   */
  public void delete(String key) throws Exception {
    int index = this.hash(key);
    if (this.catalog[index] == null) {
      throw new Exception("No keys found in the catalogue to delete");
    } else {
      LinkedList head = this.catalog[index];
      LinkedList prev = null;
      while (head != null) {
        if (head.value.key.equals(key)) {
          if (prev == null) {
            this.catalog[index] = head.next;
            break;
          } else {
            prev.next = head.next;
          }
        }
        prev = head;
        head = head.next;
      }
    }

  }

  /**
   * Increment the key's value by 1.
   *
   * @param key key whose value needs to be incremented.
   */
  public void increase(String key) {
    try {
      int val = this.find(key);
      this.insert(key, val + 1);
    } catch (Exception e) {
      // key not present
      this.insert(key, 1);
    }
  }

  /**
   * Should return the value of the key in hash table.
   *
   * @param key String word
   * @return value of the key , i.e count of the word.
   */
  public int find(String key) throws Exception {
    int index = this.hash(key);
    if (this.catalog[index] == null) {
      throw new Exception("No keys found in the catalogue");
    } else {
      LinkedList head = this.catalog[index];
      while (head != null) {
        if (head.value.key.equals(key)) {
          // if key already present in the hash table, update the value
          return head.value.value;
        }
        head = head.next;
      }
      throw new Exception("Key not found in the designated catalogue index");
    }

  }

  /**
   * Should return the array of all keys inside the hashtable.
   *
   * @return array of all keys in the hashtable.
   */
  public List<String> listAllKeys() {
    List<String> res = new ArrayList<>();
    for (LinkedList listNodes : this.catalog) {
      if (listNodes != null) {
        while (listNodes != null) {
          res.add(listNodes.value.key);
          listNodes = listNodes.next;
        }
      }
    }
    return res;
  }

  /**
   * transforms the given key into an integer in range of 0 to MAX - 1
   *
   * @param key word to be hashed
   * @return index in range of ( 0 - MAX-1) to place the key.
   * <p>
   * This function uses a common approach of iterating over each character
   * in the string and multiplying the current hash value by a prime number (31 in this case)
   * before adding the character’s ASCII value. The result is then taken modulo MAX to ensure
   * that it falls within the desired range.
   * While this is a simple and commonly used approach,
   * it may not necessarily be the optimal choice for all use cases.
   * The quality of a hash function depends on several factors such as
   * how well it distributes keys across the range and how likely it is to produce collisions.
   * It’s important to choose a hash function that works well for your specific data and use case.
   */
  private int hash(String key) {
    int hash = 0;
    for (int i = 0; i < key.length(); i++) {
      hash = (31 * hash + key.charAt(i)) % this.MAX;
    }
    return hash;
  }

  public void printTable() {
    for (LinkedList l : this.catalog) {
      if (l != null) {
        LinkedList head = l;
        while (head != null) {
          System.out.print("(" + head.value.key + " : " + head.value.value + ")  ,  ");
          head = head.next;
        }
        System.out.println("");
      } else {
        System.out.println("null");
      }
    }
  }

  public int[] getCollosionList() {
    int[] collisions = new int[catalog.length];
    int index = 0;
    for (LinkedList l : this.catalog) {
      int count = 0;
      if (l != null) {
        LinkedList head = l;
        while (head != null) {
          head = head.next;
          count++;
        }
      }
      collisions[index] = count;
      index++;
    }
    return collisions;
  }

  public void printHistogram() {
    int[] collisions = getCollosionList();
    System.out.println("collision list is as follows :");
    System.out.println(Arrays.toString(collisions));
    System.out.println("Histogram :");
    int totalCollisions = 0;
    for (int ele : collisions) {
      if(ele == 0 ){
        System.out.println("Empty Slot");
        continue;
      }
      totalCollisions += ele;
      while (ele > 0) {
        System.out.print("*");
        ele--;
      }
      System.out.println("");
    }
    System.out.println("variance of this collision list : ");
    double var = calculateVariance(collisions);
    System.out.println(var);
    double alpha = (double)totalCollisions/ catalog.length;
    System.out.println("Alpha value is : "+alpha);
  }

  public double calculateVariance(int[] collisions) {
    int n = collisions.length;
    int sum = 0;
    for (int collision : collisions) {
      sum += collision;
    }
    double mean = (double) sum / n;

    double variance = 0;
    for (int collision : collisions) {
      variance = variance +  Math.pow((double)collision - mean , 2);
    }
    variance = variance / n;

    return variance;
  }

}
