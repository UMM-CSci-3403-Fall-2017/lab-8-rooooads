package mpd;

public class ThreadedMinimumPairwiseDistance implements MinimumPairwiseDistance {

    public static int globMin = Integer.MAX_VALUE;

    @Override
    public int minimumPairwiseDistance(int[] values) throws UnsupportedOperationException{
        Thread[] threads = new Thread[4];
        int outLoopInt = 0;
        int outLoopEndInt = 0;
        int inLoopInt = 0;
        threads[0] = new Thread(new MPDThread(values, "LL"));
        threads[1] = new Thread(new MPDThread(values, "BR"));
        threads[2] = new Thread(new MPDThread(values,"TR"));
        threads[3] = new Thread(new MPDThread(values,  "C"));

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
        private String squareLocation;
        private MPDThread(int [] values, String squareLocation){
            this.values = values;
            this.squareLocation = squareLocation;
        }

        private int localMin = Integer.MAX_VALUE;
        public void run() {
            //right-side up triangles
            if(squareLocation.equals("LL")||squareLocation.equals("BR")||squareLocation.equals("TR")) {
                int outerStart = 0;
                int outerEnd = 0;
                int innerBound = 0;
                if(squareLocation.equals("LL")){
                    outerStart = 0;
                    outerEnd = values.length/2;
                    innerBound = 1;
                }
                if(squareLocation.equals("BR")){
                    outerStart = values.length/2;
                    outerEnd = values.length;
                    innerBound = values.length/2;
                }
                if(squareLocation.equals("TR")){
                    outerStart = values.length/2;
                    outerEnd = values.length;
                    innerBound = 1;
                }

                for (int i = outerStart; i < outerEnd; ++i) {
                    //for (int j = innerLoopInt; j < innerLoopEndInt; ++j) {
                    int upperBound = i - innerBound;
                    while(upperBound >= 0){
                        // Gives us all the pairs (i, j) where 0 <= j < i < values.length
                        int diff = Math.abs(values[i] - values[upperBound]);
                        if (diff < localMin) {
                            System.out.println(diff);
                            localMin = diff;
                        }
                        upperBound--;
                    }
                }


            } else {

                for (int j = 0; j < values.length/2; ++j) {
                    int lowerBound = values.length/2;
                    //outerloop must initiate at n/2, and end at n
                    while((j + values.length/2) >= lowerBound){
                        // Gives us all the pairs (i, j) where 0 <= j < i < values.length
                        int diff = Math.abs(values[lowerBound] - values[j]);
                        if (diff < localMin) {
                            System.out.println(diff);
                            localMin = diff;
                        }
                        lowerBound++;
                    }
                }
            }
            
            if(localMin<globMin && localMin !=0){
                globMin = localMin;
            }

        }
    }

}
