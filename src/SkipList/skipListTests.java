package SkipList;

public class skipListTests {
  public static void main(String[] args) {

     skipList list = new skipList();
     list.insert(10);
     list.insert(20);
     list.insert(30);
     list.insert(50);
     list.insert(60);
     list.insert(55);
     list.insert(65);
     list.insert(12);
     list.insert(24);
     list.insert(39);
    list.insert(110);
    list.insert(120);
    list.insert(320);
    list.insert(510);
    list.insert(620);
    list.insert(515);
    list.insert(615);
    list.insert(112);
    list.insert(124);
    list.insert(329);
     list.print();

    System.out.println(list.search(32).key);

    list.delete(20);
    list.print();
  }
}
