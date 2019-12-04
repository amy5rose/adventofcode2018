import com.google.common.collect.ComparisonChain;
import common.DayBase;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class day15 extends DayBase {

    public static void main(String[] args) throws IOException {
        day15 day = new day15();
        day.run();
    }

    @Override
    public String getSampleInputString() {
        return "15inputAttack.txt";
    }

    @Override
    public String getSampleAnswer() {
        return "1147";
    }

    @Override
    public String getRealInputString() {
        return "15input2.txt";
    }

    public String findAnswer(List<String> list, boolean isSample) {
        Tile[][] gridTiles;

        if (isSample) {
            gridTiles = new Tile[9][9];
        } else {
            gridTiles = new Tile[50][50];
        }

        TreeSet<Person> goblins = new TreeSet<Person>();
        TreeSet<Person> elves = new TreeSet<Person>();
        int turns;

        TreeSet<Person> fighters = new TreeSet<Person>();
        //initialize Grid
        for (int i = 0; i < gridTiles.length; i ++) {
            String input = list.get(i);
            for (int j = 0; j < gridTiles[i].length; j++) {
                String piece = String.valueOf(input.charAt(j));
                if ("#".equals(piece)) {
                    gridTiles[i][j] = new Wall(i,j);
                } else if (".".equals(piece)) {
                    gridTiles[i][j] = new Open(i,j);
                } else if ("E".equals(piece)) {
                    Person p =  new Elf(i,j);
                    gridTiles[i][j] = p;
                    fighters.add(p);
                    elves.add(p);
                } else { // G
                    Person p =  new Goblin(i,j);
                    gridTiles[i][j] = p;
                    fighters.add(p);
                    goblins.add(p);
                }
            }
        }
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Grid Initialized");
        printGrid(gridTiles, true);
        System.out.println("fighters:"+fighters);
        System.out.println("elves:"+elves);
        System.out.println("goblins:"+goblins);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");



        TreeSet<Person> fightersToMove = new TreeSet<Person>();
        TreeSet<Person> fightersToAttack = new TreeSet<Person>();


        //find the next move for everybody, if in range for attack. stay put.
        for(Person fighter : fighters) {
            //should attack
            TreeSet<Person> shouldAttack = shouldAttack(fighter, elves, goblins);
            if (!shouldAttack.isEmpty()) {
                fightersToAttack.add(fighter);
            } else {
                fightersToMove.add(fighter);
                shouldMove(fighter, gridTiles, elves, goblins);

            }
        }

        //do the next move for everybody who can move
        for(Person fighter : fightersToMove) {
            //update grid
        }


        //for everyone who moved, see if they can now attack
        for(Person fighter : fightersToMove) {
            //should attack
            TreeSet<Person> shouldAttack = shouldAttack(fighter, elves, goblins);
            if (!shouldAttack.isEmpty()) {
                fightersToAttack.add(fighter);
            }
        }

        //do the attacks
        for(Person fighter : fightersToAttack) {
            if(fighter.nextMove == NextMove.ATTACK) { //we have not already figured out a next move this turn

                /// fewest hit points, then reading order ...
                Person victim = fighter.willAttack.first();
                while (!victim.isAttackSuccessful() )
                {
                    victim = fighter.willAttack.higher(victim);
                }
                if (victim.isDead()) {
                    goblins.remove(victim);
                    elves.remove(victim);
                    fighters.remove(victim);
                    gridTiles[victim.p.x][victim.p.y] = new Open(victim.p.x, victim.p.y);
                }
            }
        }
        System.out.println("after attacks: elves:"+elves);
        System.out.println("after attacks: goblins:"+goblins);



        //clear all variables
        //clear next move
        //clear willAttack
        //clear fightersToMove
        //clear fightersToAttack



        System.out.println(fighters);
        return "";
    }

    private void shouldMove(Person fighter, Tile[][] gridTiles, TreeSet<Person> elves, TreeSet<Person> goblins) {
        //find next move, or blocked

        //identify victims persons
        TreeSet<Person> victims;
        if (fighter instanceof Elf) {
            victims = goblins;
        } else {
            victims = elves;
        }
        Point start = fighter.p;
        HashMap<Integer, Person> distanceToVictim = new HashMap<Integer, Person>();

        //identify in range targets, which target squares are closest to victims

        //which target squares are reachable, not blocked

        //identify nearest targets, based on distance

        //choose closest target, based on reading order
    }

    private TreeSet<Person> shouldAttack(Person fighter, TreeSet<Person> elves, TreeSet<Person> goblins) {
        TreeSet<Person> shouldAttack = new TreeSet<Person>();
        TreeSet<Person> victims;
        if( fighter instanceof Elf) {
            victims = goblins;
        } else {
            victims = elves;
        }

        for(Person fighterTest : victims) {
            if(fighter.p.x + 1 == fighterTest.p.x && fighter.p.y == fighterTest.p.y) {
                shouldAttack.add(fighterTest);
            } else if(fighter.p.x -1 == fighterTest.p.x && fighter.p.y == fighterTest.p.y) {
                shouldAttack.add(fighterTest);
            }else if(fighter.p.x == fighterTest.p.x && fighter.p.y +1  == fighterTest.p.y) {
                shouldAttack.add(fighterTest);
            }else if(fighter.p.x == fighterTest.p.x && fighter.p.y -1 == fighterTest.p.y) {
                shouldAttack.add(fighterTest);
            }
        }

        //this fighter will potentially attack all victims, and all victims will attack this fighter
        if (!shouldAttack.isEmpty()) {
            for(Person nextVictim : shouldAttack) {
                fighter.nextMove = NextMove.ATTACK;
                fighter.willAttack.add(nextVictim);

                nextVictim.nextMove = NextMove.ATTACK;
                nextVictim.willAttack.add(fighter);
            }
        }
        return shouldAttack;
    }

    //walls (#), open cavern (.), and starting position of every Goblin (G) and Elf (E)
    abstract class Tile {
        String piece;
        Point p;

        public Tile(String piece, int x, int y) {
            this.piece = piece;
            p = new Point(x,y);
        }

        @Override
        public String toString() {
            return piece;
        }
    }

    class Wall extends Tile {
        public Wall(int x, int y) {
            super("#", x,y);
        }
    }
    class Open extends Tile {
        public Open(int x, int y) {
            super(".",x,y);
        }
    }


    abstract class Person extends Tile implements Comparable<Person>{
        Point nextP;
        int health = 200;
        NextMove nextMove;
        TreeSet<Person> willAttack = new TreeSet<Person>(new HealthComparator());

        public Person(String piece, int x, int y) {
            super(piece,x,y);
        }

        int getHealth() {
            return health;
        }

        boolean isDead(){
            return health <= 0;
        }

        public int compareTo(Person other) {
            return ComparisonChain.start()
                    .compare( this.p.x, other.p.x )
                    .compare( this.p.y, other.p.y )
                    .result();
        }

        @Override
        public String toString() {
            return "{" + piece + ": (" + p.x + ":" + p.y + "):"+ health + "}";
        }



        public boolean isAttackSuccessful() {
            if(isDead()) {
                return false;
            } else {
                health -= 3;
                return true;
            }
        }
    }

    class Elf extends Person {
        public Elf(int x, int y) {
            super("E", x, y);
        }

        @Override
        public int compareTo(Person o) {
            return super.compareTo(o);
        }
    }

    class Goblin extends Person {
        public Goblin(int x, int y) {
            super("G", x, y);
        }

        @Override
        public int compareTo(Person o) {
            return super.compareTo(o);
        }
    }

    enum NextMove {
        ATTACK, MOVE, BLOCKED
    }

    class HealthComparator implements Comparator<Person> {

        @Override
        public int compare(Person o1, Person o2) {
            return ComparisonChain.start()
                    .compare( o1.health, o2.health )
                    .compare( o1.p.x, o2.p.x )
                    .compare( o1.p.y, o2.p.y )
                    .result();
        }
    }

    class ReaderOrderPoint implements Comparable<ReaderOrderPoint> {
        Point point;
        Person person;

        public ReaderOrderPoint(int x, int y) {
            point = new Point(x,y);
        }

        public int compareTo(ReaderOrderPoint other) {
            return ComparisonChain.start()
                    .compare( this.point.x, other.point.x )
                    .compare( this.point.y, other.point.y )
                    .result();
        }
    }

    public static void printGrid(Tile[][] grid, boolean force) {
        System.out.println("----------------------------------------------------");
        for (int i = 0; i < grid.length  ; i ++) {
            for (int j = 0; j < grid[i].length; j ++) {
                if((force && i < 50 && j <50) || grid.length < 15) {
                    System.out.print(grid[i][j].piece);
                }
            }

            if((force && i < 50) || grid.length < 15) {
                System.out.println("");
            }
        }
    }
}
