package HashTables;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HashTests {

  public static void main(String[] args) throws Exception {
    String filePath = "input.txt";
    String input = "";
    try {
      input = new String(Files.readAllBytes(Paths.get(filePath)));

    } catch (IOException e) {
      System.out.println(e);
      System.err.println("Error reading file: " + e.getMessage());
    }
    String[] bagOfKeys = input.split(" ");

    Hash map = new Hash(30);
    for (String key : bagOfKeys) {
      try {
        int val = map.find(key);
        map.increase(key);
      } catch (Exception e) {
        map.insert(key, 1);
      }
    }
    map.printHistogram();

    map = new Hash(300);
    for (String key : bagOfKeys) {
      try {
        int val = map.find(key);
        map.increase(key);
      } catch (Exception e) {
        map.insert(key, 1);
      }
    }
    map.printHistogram();


    map = new Hash(1000);
    for (String key : bagOfKeys) {
      try {
        int val = map.find(key);
        map.increase(key);
      } catch (Exception e) {
        map.insert(key, 1);
      }
    }
    map.printHistogram();


  }
}
