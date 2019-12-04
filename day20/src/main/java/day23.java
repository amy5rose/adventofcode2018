import common.DayBase;
import javafx.geometry.Point3D;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class day23 extends DayBase {

    public static void main(String[] args) throws IOException {
        day23 day = new day23();
        day.run();
    }

    @Override
    public String getSampleInputString() {
        return "23input.txt";
    }

    @Override
    public String getSampleAnswer() {
        return "7";
    }

    @Override
    public String getRealInputString() {
        return "23input2.txt";
    }

    public String findAnswer(List<String> list, boolean isSample) {
        List<Bot> bots = new ArrayList<>();
        int maxRadius = 0;
        Bot maxBot = null;
        for (String input : list) {
            String[] values = input.split("[ @,:x#<>=]+");
            Bot bo = new Bot(values[5], values[1],values[2],values[3]);
            bots.add(bo);
            if(maxRadius < bo.radius) {
                maxRadius = bo.radius;
                maxBot = bo;
            }
        }

        int count = 0;
        for(Bot b: bots) {
            if(maxBot.distance(b) <= maxRadius) {
                count++;
            }
        }


        return count + "";
    }

    class Bot {
        int radius;
        Point3D point;

        public Bot(String rad, String x, String y, String z) {
            this(Integer.valueOf(rad),Integer.valueOf(x),Integer.valueOf(y),Integer.valueOf(z));
        }
        public Bot(int rad, int x, int y, int z) {
            radius = rad;
            point = new Point3D(x,y,z);
        }

        int distance(Bot b) {
            return (int)(Math.abs(b.point.getX() - this.point.getX()) +
                    Math.abs(b.point.getY() - this.point.getY()) +
                    Math.abs(b.point.getZ() - this.point.getZ()));
        }
    }
}
