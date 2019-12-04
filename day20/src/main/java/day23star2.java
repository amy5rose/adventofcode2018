import common.DayBase;
import javafx.geometry.Point3D;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class day23star2 extends DayBase {

    public static void main(String[] args) throws IOException {
        day23star2 day = new day23star2();
        day.run();
    }

    @Override
    public String getSampleInputString() {
        return "23inputstar2.txt";
    }

    @Override
    public String getSampleAnswer() {
        return "36";
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

        int maxX = bots.stream().map(b -> (int)b.point.getX()).sorted().max(Comparator.naturalOrder()).get();
        int minX = bots.stream().map(b -> (int)b.point.getX()).sorted().min(Comparator.naturalOrder()).get();

        int maxY = bots.stream().map(b -> (int)b.point.getY()).sorted().max(Comparator.naturalOrder()).get();
        int minY = bots.stream().map(b -> (int)b.point.getY()).sorted().min(Comparator.naturalOrder()).get();

        int maxZ = bots.stream().map(b -> (int)b.point.getZ()).sorted().max(Comparator.naturalOrder()).get();
        int minZ = bots.stream().map(b -> (int)b.point.getZ()).sorted().min(Comparator.naturalOrder()).get();


        Point3D point = new Point3D(0,0,0);
        int countMax = 0;

        System.out.println("minX:" + minX + " maxX:" + maxX);
        System.out.println("minY:" + minY + " maxY:" + maxY);
        System.out.println("minZ:" + minZ + " maxZ:" + maxZ);
        HashSet<Point3D> seenPoints = new HashSet<>();

        for(Bot center: bots) {
            //for every point in the radius ...
            ///center x,y,z ... radius
            int i = (center.radius*-1) + center.point.getX() < minX ? minX : (int)((center.radius*-1) + center.point.getX());

            System.out.println("center:" + center);
            for(; i <= center.radius+center.point.getX() && i < maxX; i++) {
                int j = (center.radius*-1) + center.point.getY() < minY ? minY : (int)((center.radius*-1) + center.point.getY());

                for(; j <= center.radius+center.point.getY() && j < maxY; j++) {
                    int k =(center.radius*-1) + center.point.getZ() < minZ ? minZ : (int)((center.radius*-1) + center.point.getZ());

                    for(; k <= center.radius + center.point.getZ() && k < maxZ; k++) {
                        Point3D currentPoint = new Point3D(i, j, k );
                        int currentCount = 0;

                        if(( minX <= currentPoint.getX() && currentPoint.getX() <= maxX ) &&
                                ( minY <= currentPoint.getY() && currentPoint.getY() <= maxY ) &&
                                ( minZ <= currentPoint.getZ() && currentPoint.getZ() <= maxZ )) {

                            if(!seenPoints.contains(currentPoint)) {
                                seenPoints.add(currentPoint);
                                for (Bot comparison : bots) {
                                    if (comparison.distance(currentPoint) <= comparison.radius) {
                                        currentCount++;
                                    }
                                }
                                //System.out.println("point:" + currentPoint + " count:" + currentCount);
                                if (countMax < currentCount) {
                                    countMax = currentCount;
                                    point = currentPoint;
                                }
                            }
                        } else {
                            //System.out.println("skipped point:" + currentPoint);

                        }

                    }
                }
            }
        }
        return ((int) (point.getZ() + point.getY() + point.getZ())) + "";
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

        int distance(Point3D point) {
            return (int)(Math.abs(point.getX() - this.point.getX()) +
                    Math.abs(point.getY() - this.point.getY()) +
                    Math.abs(point.getZ() - this.point.getZ()));
        }

        @Override
        public String toString() {
            return "Bot{" +
                    "radius=" + radius +
                    ", point=" + point +
                    '}';
        }
    }
}
