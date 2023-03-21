package redBlackTrees;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import static redBlackTrees.RedBlackTree.RedBlackTreeNode.BLACK;
import static redBlackTrees.RedBlackTree.RedBlackTreeNode.RED;

/**
 * Red black tree implementation in JAVA using BST operations.
 */
public class RedBlackTree {
  public class RedBlackTreeNode {
    public static String RED = "red";
    public static String BLACK = "black";
    RedBlackTreeNode left;
    RedBlackTreeNode right;
    RedBlackTreeNode parent;
    int key;
    String color;

    /**
     * Initializes the Red black tree node.
     *
     * @param key key of the node.
     */
    RedBlackTreeNode(int key) {
      this.left = null;
      this.right = null;
      this.parent = null;
      this.color = RED;
      this.key = key;
    }
  }


  RedBlackTreeNode root;

  public RedBlackTree(int root) {
    this.root = new RedBlackTreeNode(root);
  }


  /**
   * Deletes the node with that particular key.
   *
   * @param T root of the BST
   * @param z node to be deleted
   */
  public static void delete(RedBlackTree T, RedBlackTreeNode z) {
    if (z.left == null) {
      transplant(T, z, z.right);
    } else if (z.right == null) {
      transplant(T, z, z.left);
    } else {
      RedBlackTreeNode y = min(z.right);
      if (y.parent != z) {
        transplant(T, y, y.right);
        y.right = z.right;
        y.right.parent = y;
      }
      transplant(T, z, y);
      y.left = z.left;
      y.left.parent = y;
    }
  }

  /**
   * Replace u subtree as a child of it's parent with another subtree v
   *
   * @param T BST
   * @param u first node
   * @param v second node
   */
  public static void transplant(RedBlackTree T, RedBlackTreeNode u, RedBlackTreeNode v) {
    if (u.parent == null) {
      T.root = v;
    } else if (u == u.parent.left) {
      u.parent.left = v;
    } else {
      u.parent.right = v;
    }
    if (v != null) {
      v.parent = u.parent;
    }
  }

  /**
   * Inserts a key into BST.
   *
   * @param key key to be inserted
   */
  public void insert(RedBlackTree T, int key) {
    RedBlackTreeNode y = null;
    RedBlackTreeNode x = T.root;
    RedBlackTreeNode z = new RedBlackTreeNode(key);
    while (x != null) {
      y = x;
      if (z.key < x.key) {
        x = x.left;
      } else {
        x = x.right;
      }
    }
    z.parent = y;
    if (y == null) {
      T.root = z;
    } else if (z.key < y.key) {
      y.left = z;
    } else {
      y.right = z;
    }
    z.left = null;
    z.right = null;
    z.color = RED;
    insertFixUp(T, z);
  }

  /**
   * Performs insert fixup to preserve red black tree properties.
   *
   * @param T rb tree
   * @param z newly inserted node
   */
  public void insertFixUp(RedBlackTree T, RedBlackTreeNode z) {
    while (z.parent != null && z.parent.color.equals(RED)) {
      if (z.parent.parent != null && z.parent == z.parent.parent.left) {
        RedBlackTreeNode y = z.parent.parent.right;
        if (y != null && y.color.equals(RED)) {
          z.parent.color = BLACK;
          y.color = BLACK;
          z.parent.parent.color = RED;
          z = z.parent.parent;
        } else {
          if (z == z.parent.right) {
            z = z.parent;
            leftRotate(T, z);
          }
          z.parent.color = BLACK;
          z.parent.parent.color = RED;
          rightRotate(T, z.parent.parent);
        }
      } else if (z.parent.parent != null && z.parent == z.parent.parent.right) {
        RedBlackTreeNode y = z.parent.parent.left;
        if (y != null && y.color.equals(RED)) {
          z.parent.color = BLACK;
          y.color = BLACK;
          z.parent.parent.color = RED;
          z = z.parent.parent;
        } else {
          if (z == z.parent.left) {
            z = z.parent;
            rightRotate(T, z);
          }
          z.parent.color = BLACK;
          z.parent.parent.color = RED;
          leftRotate(T, z.parent.parent);
        }
      } else {
        break;
      }
    }

    T.root.color = BLACK;
  }

  /**
   *         DEMONSTRATION OF LR AND RR function transformations
   *
   *         x                    LR(x)                y
   *    alp        y              ->            x            gamma
   *          beta    gamma              alpa      beta
   *
   *
   *
   *               y                  RR(y)                        x
   *          x         gamma          ->                  alp            y
   *    alpa     beta                                                beta     gamma
   */


  /**
   * Makes left rotations on RB tree.
   *
   * @param T tree
   * @param x tree node
   */
  public void leftRotate(RedBlackTree T, RedBlackTreeNode x) {
    RedBlackTreeNode y = x.right;
    x.right = y.left;
    if (y.left != null) {
      y.left.parent = x;
    }
    y.parent = x.parent;
    if (x.parent == null) {
      T.root = y;
    } else if (x == x.parent.left) {
      x.parent.left = y;
    } else {
      x.parent.right = y;
    }
    y.left = x;
    x.parent = y;
  }


  /**
   * Makes right rotations on RB tree.
   *
   * @param T tree
   * @param y tree node
   */
  public void rightRotate(RedBlackTree T, RedBlackTreeNode y) {
    RedBlackTreeNode x = y.left;
    y.left = x.right;
    if (x.right != null) {
      x.right.parent = y;
    }
    x.parent = y.parent;

    if (y.parent == null) {
      T.root = x;
    } else if (y == y.parent.left) {
      y.parent.right = x;
    } else {
      y.parent.left = x;
    }
    x.right = y;
    y.parent = x;
  }


  /**
   * Finds the height of the BST.
   * @param T tree
   * @return height of the tree.
   */
  public int height(RedBlackTree T) {
    RedBlackTreeNode root = T.root;
    if (root == null) {
      return 0;
    }
    Queue<RedBlackTreeNode> queue = new LinkedList<>();
    queue.add(root);
    int height = 0;
    while (!queue.isEmpty()) {
      int size = queue.size();
      for (int i = 0; i < size; i++) {
        RedBlackTreeNode node = queue.poll();
        if (node.left != null) {
          queue.add(node.left);
        }
        if (node.right != null) {
          queue.add(node.right);
        }
      }
      height++;
    }
    return height;
  }

  /**
   * Prints the tree level order.
   * @param T BST
   */
  public void printLevelOrder(RedBlackTree T) {
    Queue<RedBlackTreeNode> queue = new LinkedList<>();
    queue.add(root);
    while (!queue.isEmpty()) {
      int size = queue.size();
      for (int i = 0; i < size; i++) {
        RedBlackTreeNode node = queue.poll();
        if (node != null) {
          System.out.print(node.key + " (" + node.color + ") ");
          queue.add(node.left);
          queue.add(node.right);
        } else {
          System.out.print("null ");
        }
      }
      System.out.println();
    }
  }


  /**
   * Prints the nodes of the BST in sorted order.
   * Inorder traversal.
   */
  public void sort(RedBlackTree T) {
    RedBlackTreeNode root = T.root;
    Stack<RedBlackTreeNode> s = new Stack<>();
    RedBlackTreeNode curr = root;
    // traverse the tree
    while (curr != null || s.size() > 0) {
      // Reach the left most Node of the curr Node
      while (curr != null) {
        // place pointer to a tree node on the stack before traversing the node's left subtree
        s.push(curr);
        curr = curr.left;
      }
      // Current must be null at this point
      curr = s.pop();
      System.out.print(curr.key + " ");

      // we have visited the node and its left subtree.  Now, it's right subtree's turn
      curr = curr.right;
    }
  }

  /**
   * Searches the tree for occurance of the given key, returns null if not found.
   *
   * @param key key to be searched.
   * @return node with that key or null.
   */
  public RedBlackTreeNode search(RedBlackTree T, int key) {
    RedBlackTreeNode root = T.root;
    while (root != null) {
      int rootVal = root.key;
      if (key == rootVal) {
        return root;
      } else if (key > rootVal) {
        root = root.right;
      } else {
        root = root.left;
      }
    }
    return null;
  }

  /**
   * Lookup function for a key in the tree.
   *
   * @param key key to be searched
   * @return true if found , false if not found.
   */
  public boolean lookup(RedBlackTree T, int key) {
    return search(T, key) != null;
  }

  /**
   * Returns min node in root's subtree.
   *
   * @param root given node.
   * @return min node from root.
   */
  public static RedBlackTreeNode min(RedBlackTreeNode root) {
    while (root.left != null) {
      root = root.left;
    }
    return root;
  }

  /**
   * Finds the max in the root's subtree.
   *
   * @param root given node.
   * @return max node
   */
  public static RedBlackTreeNode max(RedBlackTreeNode root) {
    while (root.right != null) {
      root = root.right;
    }
    return root;
  }

  /**
   * Finds the successor for the node
   *
   * @param root node for which we need to find successor.
   * @return successor node.
   */
  public static RedBlackTreeNode successor(RedBlackTreeNode root) {
    if (root.right != null) {
      return min(root.right);
    }
    // Finding the first ancestor larger than root
    RedBlackTreeNode parent = root.parent;
    while (parent != null && root == parent.right) {
      root = parent;
      parent = parent.parent;
    }
    return parent;
  }

  /**
   * Finds the predecessor node to the given node.
   *
   * @param root given node.
   * @return predecessor node.
   */
  public static RedBlackTreeNode predecessor(RedBlackTreeNode root) {
    if (root.left != null) {
      return max(root.left);
    }
    // Finding the first ancestor smaller than root
    RedBlackTreeNode parent = root.parent;
    while (parent != null && root == parent.left) {
      root = parent;
      parent = parent.parent;
    }
    return parent;
  }
}

