import common.DayBase;
import common.ReadInput;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class day9star2 extends DayBase {

	public static void main(String[] args) throws IOException {
		day9star2 day = new day9star2();
        day.run();
	}

    @Override
    public String getSampleInputString() {
        return "9 players; last marble is worth 25 points";
    }

    @Override
    public String getSampleAnswer() {
        return "22563";
    }

    @Override
    public String getRealInputString() {
        return "9input.txt";
    }

    public String findAnswer(List<String> list) {
        String[] listS = list.get(0).split(" ");
        int players = Integer.valueOf(listS[0]);
        int marbles = Integer.valueOf(listS[6]) * 100;
//        System.out.println("players:" + players + " marbles:" + marbles);

        HashMap<Integer, Long> scores = new HashMap<>();
        int currentPlayer = 0;

        //begin
        Node game = new Node(0);
        Node head = game;

        game.next = game;
        game.previous = game;

        for(int currentMarble = 1; currentMarble <= marbles; currentMarble++, currentPlayer = (currentPlayer+1)% players ) {
            if(currentMarble % 23 == 0) {
                int score = currentMarble;
                game = game.previous.previous.previous.previous.previous.previous.previous;
                score = score + game.value;
                game = game.remove();
                scores.put(currentPlayer, scores.getOrDefault(currentPlayer, 0l) + score);
//                System.out.println("scores:" + scores);
            } else {
                game = game.addNextNext(currentMarble);
            }
            //head.print();
        }
        System.out.println("scores:" + scores);
        long max = scores.values().stream().max(Comparator.naturalOrder()).get();
        return max + "";
    }

    class Node {
	    Node next;
	    Node previous;
	    int value;

	    public Node (int value) {
	        this.value = value;
        }

        public Node addNextNext(int value) {
	        Node newNode = new Node(value);
            Node currentNext = next;

            newNode.next = currentNext.next;
            newNode.previous = currentNext;

            newNode.next.previous = newNode;
            currentNext.next = newNode;
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

        void print(){
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
