package mpd;

public class ThreadedMinimumPairwiseDistance implements MinimumPairwiseDistance {

    public static int globMin = Integer.MAX_VALUE;

    @Override
    public int minimumPairwiseDistance(int[] values) throws UnsupportedOperationException{
        //Creating 4 separate thread objects for each "triangle in the efficiency diagram.
        Thread[] threads = new Thread[4];
        Answer answer1 = new Answer();
        threads[0] = new Thread(new MPDThread(values, "LL", answer1));
        threads[1] = new Thread(new MPDThread(values, "BR", answer1));
        threads[2] = new Thread(new MPDThread(values,"TR", answer1));
        threads[3] = new Thread(new MPDThread(values,  "C", answer1));
        //start and join threads
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
        //return the answer objects lowest found value across all threads.
        return answer1.getMin();

    }
    // define the MPDThread class which contains the run() method for the threads.
    private class MPDThread extends Thread{
        Answer sharedAnswer = new Answer();
        //initialize the arguments for the thread class.
            //values is the array that is passed in for every thread.
            //squareLocation is a string that represents which area of the inner and outer loops
            //> will cover for the given thread.
            //sharedAnswer is the class that stores the smallest found distance in the array between given numbers.
        private int[] values;
        private String squareLocation;
        private MPDThread(int [] values, String squareLocation, Answer anAnswer){
            this.values = values;
            this.sharedAnswer = anAnswer;
            this.squareLocation = squareLocation;
        }
        //the run() method will determine what this thread will do with the passed arguments.
        public void run() {
            //Create a case for all situation where the inner loop is < the outer loop.
            if(squareLocation.equals("LL")||squareLocation.equals("BR")||squareLocation.equals("TR")) {
                int outerStart = 0;
                int outerEnd = 0;
                int innerBound = 0;
                //the following if statements determine which half of the inner and outer loop each thread will run.
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
                //this set of loops is bounded by the aforementioned if statements depending on the square location.
                //this contains the comparison with the Answer object that finds the difference between the outer loop
                //and the current inner loop value.  This is then compared to the current smallest Answer object.  If it
                // is indeed smaller, the Answer objects defined minimum value is updated to this new found value.
                for (int i = outerStart; i < outerEnd; ++i) {
                    for(int j = i-innerBound; j >= 0; j--){
                        int diff = Math.abs(values[i] - values[j]);
                        if (diff < sharedAnswer.getMin()) {
                            System.out.println(diff);
                            sharedAnswer.setMinDist(diff);
                        }
                    }
                }

                //Create a case for all situation where the outer loop is < the inner loop.
            } else {
                //for this situation our outer loop is doing what the inner loop in the last set of threads was doing
                for (int j = 0; j < values.length/2; ++j) {
                    int lowerBound = values.length/2;
                    //outerloop must initiate at n/2, and end at n
                    for(int i = j+values.length/2;j >= lowerBound;j++){
                        // Gives us all the pairs (i, j) where 0 <= i <= j + n/2 < values.length
                        int diff = Math.abs(values[i] - values[j]);
                        if (diff < sharedAnswer.getMin()) {
                            System.out.println(diff);
                            sharedAnswer.setMinDist(diff);
                        }
                    }
                }
            }
        }
    }
    // the Answer class stores the smallest value found for distance in an array.  The privately stored integer
    // is updated as smaller values are found using the setMinDist function.  The currently smallest found integer can
    // be retrieved using the getMin function.
    private class Answer {
        private int minDist = Integer.MAX_VALUE;

        public int getMin(){
            return this.minDist;
        }
        public synchronized void setMinDist(int newMin){
            this.minDist = newMin;
        }

    }


}
