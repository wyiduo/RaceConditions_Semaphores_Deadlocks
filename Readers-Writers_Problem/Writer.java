public class Writer extends Thread {
    int myName; // The name of this thread
    RandomSleep rSleep;

    public Writer(int name) {
        myName = name;
        rSleep = new RandomSleep();
    }

    public void run() {
        for (int I = 0; I < 5; I++) {
            try {
                System.out.println("Writer " + myName + " wants to write.  " +
                    "Beforehand, writecount is " + Synch.writecount);

                Synch.mutex_writecount.acquire(); // mutex_writecount is for making sure only 1 Writer thread is writing to writecount at a time
                Synch.writecount++;
                if (Synch.writecount == 1) // The Writer thread will stop Reader threads from reading
                {
                    Synch.read_lock.acquire();
                }
                Synch.mutex_writecount.release();


                Synch.write_lock.acquire(); // Acquires write_lock so that the Writer threads follow a FIFO waiting queue
                // Critical section for Writer
                System.out.println("Writer " + myName + " is now writing");
                rSleep.doSleep(1, 200); // Simulate the time taken by writing
                Synch.write_lock.release();


                Synch.mutex_writecount.acquire();
                Synch.writecount--;
                System.out.println("Writer " + myName + " is finished writing.  " +
                    "Writecount decremented to " + Synch.writecount);
                if (Synch.writecount == 0) {
                    Synch.read_lock.release(); // Only unlocks read_lock when no more Write threads waiting
                }
                Synch.mutex_writecount.release();

                rSleep.doSleep(1, 1000); // Simulate "doing something else"
            } catch (Exception e) {}

        }
    }
}
