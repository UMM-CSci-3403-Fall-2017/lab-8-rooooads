package mpd;

import java.util.Random;

public class ThreadedMain {

    public static void main(String[] args) {
        //int NUM_VALUES = Integer.parseInt(args[0]);
        int NUM_VALUES = 100;
        MinimumPairwiseDistance mpd = new ThreadedMinimumPairwiseDistance();

        Random random = new Random();
        int[] values = new int[NUM_VALUES];
        for (int i = 0; i < NUM_VALUES; ++i) {
            values[i] = random.nextInt();
        }
        
        int result = mpd.minimumPairwiseDistance(values);
        System.out.println("The minimum distance was " + result);
    }

}
