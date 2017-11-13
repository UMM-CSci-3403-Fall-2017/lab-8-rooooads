package mpd;

public class SerialMinimumPairwiseDistance implements MinimumPairwiseDistance {

    @Override
    public int minimumPairwiseDistance(int[] values) {
        int result = Integer.MAX_VALUE;
        for (int i = 0; i < values.length; ++i) {
            for (int j = 0; j < i; ++j) {
                // Gives us all the pairs (i, j) where 0 <= j < i < values.length
                int diff = Math.abs(values[i] - values[j]);
                if (diff < result) {
                    System.out.println(diff);
                    result = diff;
                }
            }
        }
        return result;
    }

}
