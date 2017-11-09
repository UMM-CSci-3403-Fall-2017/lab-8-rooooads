package mpd;

public class ThreadedMinimumPairwiseDistance implements MinimumPairwiseDistance {

    public static int globMin = Integer.MAX_VALUE;

    @Override
    public int minimumPairwiseDistance(int[] values) throws UnsupportedOperationException{
        Thread[] threads = new Thread[4];
        int outLoopInt = 0;
        int outLoopEndInt = 0;
        int inLoopInt = 0;
        threads[0] = new Thread(new MPDThread(values, 0, 0, values.length/2, values.length/2));
        threads[1] = new Thread(new MPDThread(values, values.length/2, 0, values.length/2, values.length/2));
        threads[2] = new Thread(new MPDThread(values,values.length/2,values.length/2, values.length, values.length));
        threads[3] = new Thread(new MPDThread(values, values.length/2, 0, values.length, values.length/2));



        /*for (int i = 0; i < threads.length; i++){

            threads[i] = new Thread(new MPDThread(values, outLoopInt, inLoopInt, outLoopEndInt));
        }*/

        return globMin;

    }

    private class MPDThread extends Thread{
        private int[] values;
        private int outerLoopInt = 0;
        private int innerLoopInt = 0;
        private int outerLoopEndInt = Integer.MAX_VALUE;
        private int innerLoopEndInt = Integer.MAX_VALUE;

        private MPDThread(int [] values, int outerLoopInt, int innerLoopInt, int outerLoopEndInt, int innerLoopEndInt){
            this.values = values;
            this.outerLoopInt = outerLoopInt;
            this.innerLoopInt = innerLoopInt;
            this.outerLoopEndInt = outerLoopEndInt;
            this.innerLoopEndInt = innerLoopEndInt;
        }

        private int localMin = Integer.MAX_VALUE;
        public void run() {
            for (int i = outerLoopInt; i < outerLoopEndInt; ++i) {
                for (int j = innerLoopInt; j < innerLoopEndInt; ++j) {
                    // Gives us all the pairs (i, j) where 0 <= j < i < values.length
                    int diff = Math.abs(values[i] - values[j]);
                    if (diff < localMin) {
                        localMin = diff;
                    }
                }
            }
            if(localMin<globMin){
                globMin = localMin;
            }

        }
    }

}
