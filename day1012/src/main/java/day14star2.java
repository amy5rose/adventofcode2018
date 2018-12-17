import common.DayBase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class day14star2 extends DayBase {

  public static void main(String[] args) throws IOException {
    day14star2 day = new day14star2();
    day.run();
  }

  @Override
  public String getSampleInputString() {
    return "01245";
  }

  @Override
  public String getSampleAnswer() {
    return "5";
  }

  @Override
  public String getRealInputString() {
    return "920831";
  }

  public String findAnswer(List<String> list, boolean isSample) {
    String recipeGoal = list.get(0);

    Node head = new Node(3);
    head.next = head;
    head.previous = head;
    Node end = head.addNext(7);
    end = end.addNext(1);
    end = end.addNext(0);

    Node elfOne = head;
    Node elfTwo = head.next;

    int recipesMade = 4;

    //3  7  1  0  1  0  1  2 [4](5) 1  5  8
    //

    StringBuffer tempString = new StringBuffer();
    System.out.println("Elf1: " + elfOne.value + " Elf2: " + elfTwo.value);
    print(head, elfOne, elfTwo);

    while (true) {
      int product = elfOne.value + elfTwo.value;
      if (product >= 10) {
        recipesMade += 1;
        end = end.addNext(1);

        tempString.append(1);
        if (findMatch(recipeGoal, recipesMade, tempString)) {

          return recipesMade - recipeGoal.length() + "";
        }

        recipesMade += 1;
        end = end.addNext(product - 10);
        tempString.append(product - 10);
        if (findMatch(recipeGoal, recipesMade, tempString)) {
          return recipesMade - recipeGoal.length() + "";
        }

      } else {
        recipesMade += 1;
        end = end.addNext(product);
        tempString.append(product);

        if (findMatch(recipeGoal, recipesMade, tempString)) {
          return recipesMade - recipeGoal.length() + "";
        }
      }


      int count = elfOne.value + 1;
      for (int index = 0; index < (count); index++) {
        elfOne = elfOne.next;
      }

      count = elfTwo.value + 1;
      for (int index = 0; index < (count); index++) {
        elfTwo = elfTwo.next;
      }

      //System.out.println("temp:" + tempString.toString());

      //System.out.println("Elf1: " + elfOne.value + " Elf2: " + elfTwo.value);
      //print(head, elfOne, elfTwo);

    }

    //failed: 20236442 <- too high

  }

  private boolean findMatch(String recipeGoal, int recipesMade, StringBuffer tempString) {
    if(tempString.toString().length() < recipeGoal.length()) {
      //continue, the thing is too short.
    } else {
      if (tempString.toString().equals(recipeGoal)) {
        return true;
      } else {
        tempString.deleteCharAt(0);
      }
    }
    return false;
  }


  void print(Node head, Node elf1, Node elf2) {
    Node start = head;
    Node next = head.next;

    if (start == elf1) {
      System.out.print("(" + start.value);
    } else if (start == elf2) {
      System.out.print("<" + start.value);
    } else {
      System.out.print(" " + start.value);
    }

    while (next != start) {

      if (next == elf1) {
        System.out.print("(" + next.value);
      } else if (next == elf2) {
        System.out.print("<" + next.value);
      } else {
        System.out.print(" " + next.value);

      }

      next = next.next;
    }
    System.out.println(" ");
  }

  class Node {
    Node next;
    Node previous;
    int value;

    public Node(int value) {
      this.value = value;
    }


    public Node addNext(int value) {
      Node newNode = new Node(value);

      newNode.next = this.next;
      newNode.previous = this;

      newNode.next.previous = newNode;
      this.next = newNode;
      return newNode;
    }


    public Node remove() {
      previous.next = next;
      next.previous = previous;
      return next;
    }

    @Override
    public String toString() {
      return value + "";
    }

    void print() {
      Node start = this;
      Node next = this.next;
      System.out.print(value + " ");
      while (next != start) {
        System.out.print(next.value + " ");
        next = next.next;
      }
      System.out.println(" ");
    }
  }
}
