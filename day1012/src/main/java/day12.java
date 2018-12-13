import common.DayBase;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class day12 extends DayBase {

	public static void main(String[] args) throws IOException {
        day12 day = new day12();
        day.run();
	}

    @Override
    public String getSampleInputString() {
        return "12input.txt";
    }

    @Override
    public String getSampleAnswer() {
        return "325";
    }

    @Override
    public String getRealInputString() {
        return  "12input2.txt";
    }

    public String findAnswer(List<String> list, boolean isSample) {
	    String initialState = list.get(0).split(":")[1].trim();

        //list.get(1);
        HashMap<String, String> rules = new HashMap<>();

        for(int index = 2; index < list.size(); index++) {
            String[] temp = list.get(index).split(" ");
            rules.put(temp[0].trim(), temp[2].trim());
        }

        System.out.println("rules:" + rules.size() + "  " + rules);
        //0123456789012345678901234 5678
        //#..#.#..##......###...### ....

        //    0         1         2
        //321 0123456789012345678901234 5678
        //... #..#.#..##......###...### ...........
        String oldState = "..." + initialState + "....";
        int zero = 3;
        int endRelative = initialState.length();

        int first = 3;
        int last = first + initialState.length();
        if(isSample) {
            System.out.println("[  ]:" + "   0         1         2");
            System.out.println("[  ]:" + "32101234567890123456789012345678");
        } else {
            System.out.println("[IT]:" + oldState + "first:" + first + "  last:" + last  + "zero:" + zero + "  end:" + endRelative );

        }
        System.out.println("[ 0]:" + oldState + "first:" + first + "  last:" + last  + "zero:" + zero + "  end:" + endRelative );

        long ageLimit = 20;
        if(!isSample) {
           // ageLimit = 50000000000l;
        }
        for(int age=1; age <= ageLimit; age++) {
            StringBuffer state = new StringBuffer();
            state.append(oldState.charAt(0));
            state.append(oldState.charAt(1));

            int index = 2;
            for(; index+3 <= oldState.length() ; index++) {
                String middle = String.valueOf(oldState.charAt(index));
                String sub = oldState.substring(index-2, index+3);
                if (rules.containsKey(sub)) {
                    middle = rules.get(sub);

                    if (index < first && middle.equals("#")) {
                        first = index;
                    }
                    if (index > last && middle.equals("#")) {
                        last = index;
                    }
                } else {
                    middle = ".";
                }
                state.append(middle);

            }
            for(; index < oldState.length() ; index++) {
                state.append(oldState.charAt(index));
            }

            //grow the string if needed ...
            if(first <= 2) {
                //at the front
                state.insert(0, ".");
                first++;
                last++;
                zero++;
                //System.out.println("[  ]:" + "    0         1         2");
                //System.out.println("[  ]:" + "432101234567890123456789012345678");
            }

            if (last > endRelative) {
                state.append(".");

            }


            oldState = state.toString();
            if(age > 1000 && age % 1000 == 0) {
                System.out.println("[" + age + "]:" + oldState + "first:" + first + "  last:" + last);
            }

            if ( !isSample && age <10 ) {
                System.out.println("[ " + age + "]:" + oldState + "first:" + first + "  last:" + last  + "zero:" + zero + "  end:" + endRelative );

            } else {
                if (!isSample) {
                    System.out.println("[" + age + "]:" + oldState + "first:" + first + "  last:" + last  + "zero:" + zero + "  end:" + endRelative );
                }
            }

        }

        System.out.println("[ND]:" + oldState + "first:" + first + "  last:" + last  + "zero:" + zero + "  end:" + endRelative );
        int sum = 0;
        //count pots
        for(int index = 0; index < oldState.length(); index++) {
            if("#".equals(String.valueOf(oldState.charAt(index)))) {
                sum+= (index - zero);
                System.out.println(""+(index - zero));

            }

        }


        return "" + sum;
	}


    class Node {
        Node next;
        Node previous;
        String value;
        boolean isLife = false;

        public Node (String value, boolean isLife) {
            this.value = value;
            this.isLife = isLife;
        }

        public Node (String value) {
            this(value, false);
        }

        public Node addNext(String value) {
            Node newNode = new Node(value);

            newNode.next = this.next;
            newNode.previous = this;

            this.next.previous = newNode;
            this.next = newNode;
            return newNode;
        }

        public Node addBefore(String value) {
            Node newNode = new Node(value);

            newNode.next = this;
            newNode.previous = this.previous;

            this.previous.next = newNode;
            this.previous = newNode;
            return newNode;
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
