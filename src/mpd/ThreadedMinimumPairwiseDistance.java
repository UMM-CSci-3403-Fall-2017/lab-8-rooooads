package mpd;

public class ThreadedMinimumPairwiseDistance implements MinimumPairwiseDistance {

    public static int globMin = Integer.MAX_VALUE;

    @Override
    public int minimumPairwiseDistance(int[] values) throws UnsupportedOperationException{
        Thread[] threads = new Thread[4];
        int outLoopInt = 0;
        int outLoopEndInt = 0;
        int inLoopInt = 0;
        threads[0] = new Thread(new MPDThread(values, 0, 0, values.length/2, values.length/2, false));
        threads[1] = new Thread(new MPDThread(values, values.length/2, 0, values.length/2, values.length, false));
        threads[2] = new Thread(new MPDThread(values,values.length/2,values.length/2, values.length, values.length, false));
        threads[3] = new Thread(new MPDThread(values, values.length/2, values.length/2, values.length, values.length/2, true));

        try {

            for (int i = 0; i < threads.length; i++) {

                threads[i].start();
            }
            for (int i = 0; i < threads.length; i++) {
                threads[i].join();
            }
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        return globMin;

    }

    private class MPDThread extends Thread{
        private int[] values;
        private int outerLoopInt = 0;
        private int innerLoopInt = 0;
        private int outerLoopEndInt = 0;
        private int innerLoopEndInt = 0;
        private boolean isFourth = false;
        private MPDThread(int [] values, int outerLoopInt, int innerLoopInt, int innerLoopEndInt, int outerLoopEndInt, boolean isFourth){
            this.values = values;
            this.outerLoopInt = outerLoopInt;
            this.innerLoopInt = innerLoopInt;
            this.outerLoopEndInt = outerLoopEndInt;
            this.innerLoopEndInt = innerLoopEndInt;
            this.isFourth = isFourth;
        }

        private int localMin = Integer.MAX_VALUE;
        public void run() {
            //right-side up triangles
            if(!isFourth) {
//                for (int i = outerLoopInt; i < outerLoopEndInt; ++i) {
//                    for (int j = innerLoopInt; j < innerLoopEndInt; ++j) {
//                        // Gives us all the pairs (i, j) where 0 <= j < i < values.length
//                        int diff = Math.abs(values[i] - values[j]);
//                        if (diff < localMin) {
//                            System.out.println(diff);
//                            localMin = diff;
//                        }
//                    }
//                }

                for (int i = outerLoopInt; i < outerLoopEndInt; ++i) {
                    //for (int j = innerLoopInt; j < innerLoopEndInt; ++j) {
                    int upperBound = i;
                    while(upperBound !=0){
                        // Gives us all the pairs (i, j) where 0 <= j < i < values.length
                        int diff = Math.abs(values[i] - values[upperBound]);
                        if (diff < localMin) {
                            System.out.println(diff);
                            localMin = diff;
                        }
                        upperBound--;
                    }
                }

                //upside-down triangles
            } else {
                for (int i = outerLoopInt; i <= outerLoopEndInt; ++i) {
                    for (int j = innerLoopInt; j < innerLoopEndInt; ++j) {
                        // Gives us all the pairs (i, j) where 0 <= j < i < values.length
                        int diff = Math.abs(values[i] - values[j]);
                        if (diff < localMin) {
                            System.out.println(diff);
                            localMin = diff;
                        }
                    }
                }
            }
            
            if(localMin<globMin && localMin !=0){
                globMin = localMin;
            }

        }
    }

}
