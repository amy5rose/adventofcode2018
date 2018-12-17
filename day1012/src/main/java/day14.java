import common.DayBase;

import java.io.IOException;
import java.util.List;

public class day14 extends DayBase {

  public static void main(String[] args) throws IOException {
    day14 day = new day14();
    day.run();
  }

  @Override
  public String getSampleInputString() {
    return "9";
  }

  @Override
  public String getSampleAnswer() {
    return "5158916779";
  }

  @Override
  public String getRealInputString() {
    return "920831";
  }

  public String findAnswer(List<String> list, boolean isSample) {
    int recipeGoal = Integer.valueOf(list.get(0));

    Node head = new Node(3);
    head.next = head;
    head.previous = head;
    Node end = head.addNext(7);
    end = end.addNext(1);
    end = end.addNext(0);

    Node elfOne = head;
    Node elfTwo = head.next;

    int recipesMade = 4;

    System.out.println("Elf1: " + elfOne.value + " Elf2: " + elfTwo.value);
    print(head, elfOne, elfTwo);

    while (recipesMade < recipeGoal) {
      int product = elfOne.value + elfTwo.value;
      if (product >= 10) {
        recipesMade += 2;
        end = end.addNext(1);
        end = end.addNext(product - 10);
      } else {
        recipesMade += 1;
        end = end.addNext(product);
      }

      int count = elfOne.value + 1;
      for (int index = 0; index < (count); index++) {
        elfOne = elfOne.next;
      }

      count = elfTwo.value + 1;
      for (int index = 0; index < (count); index++) {
        elfTwo = elfTwo.next;
      }

      //System.out.println("Elf1: " + elfOne.value + " Elf2: " + elfTwo.value);
      //print(head, elfOne, elfTwo);
    }
    //ok no wfind the next 10

    recipesMade = 0;
    recipeGoal = 10;
    StringBuffer answer = new StringBuffer();

    while (recipesMade < recipeGoal) {
      int product = elfOne.value + elfTwo.value;
      if (product >= 10) {
        recipesMade += 2;
        end = end.addNext(1);
        end = end.addNext(product - 10);
        answer.append(1);
        answer.append(product - 10);
      } else {
        recipesMade += 1;
        end = end.addNext(product);
        answer.append(product);

      }

      int count = elfOne.value + 1;
      for (int index = 0; index < (count); index++) {
        elfOne = elfOne.next;
      }

      count = elfTwo.value + 1;
      for (int index = 0; index < (count); index++) {
        elfTwo = elfTwo.next;
      }
    }

    return answer.toString();
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
