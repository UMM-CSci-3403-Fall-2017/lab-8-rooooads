package mpd;

import java.util.Random;

public class SerialMain {

    public static void main(String[] args) {
        //int NUM_VALUES = Integer.parseInt(args[0]);
        int NUM_VALUES = 100;
        long beginTime = System.currentTimeMillis();
        MinimumPairwiseDistance mpd = new SerialMinimumPairwiseDistance();

        Random random = new Random();
        int[] values = new int[NUM_VALUES];
        for (int i = 0; i < NUM_VALUES; ++i) {
            values[i] = random.nextInt();
        }
        
        int result = mpd.minimumPairwiseDistance(values);
        long endTime = System.currentTimeMillis();
        System.out.println("The time was " + (endTime-beginTime));
        System.out.println("The minimum distance was " + result);
    }

}
