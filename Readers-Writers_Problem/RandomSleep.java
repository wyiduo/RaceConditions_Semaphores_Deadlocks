public class RandomSleep {

    // doSleep(lower, upper) executes a "sleep" command for a random period of time within the range [lower, upper]
    public void doSleep(int lower, int upper) {
        if ((lower >= 0) && (upper >= lower)) {
            try {
                java.lang.Thread.sleep((int)(((upper - lower) * Math.random()) + lower));
            } catch (Exception e) {
                System.exit(0);
            }
        } else
            System.out.println("Invalid Parameters to doSleep()");
    }
}
