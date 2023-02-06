public class Reader extends Thread {
    int myName; // The name of this thread
    RandomSleep rSleep;

    public Reader(int name) {
        myName = name;
        rSleep = new RandomSleep();
    }

    public void run() {
        for (int I = 0; I < 5; I++) {
            try {
                System.out.println("Reader " + myName + " wants to read.  " +
                    "Beforehand, readcount is " + Synch.readcount);

                Synch.reader_queue.acquire(); // reader_queue is waiting queue for Reader threads; must have this because read_lock will be "queued" by Writer threads ...
                // ... and this means some Reader threads may queue before the Writer thread that is acquiring the read_lock, causing a number of Reader threads to run when Writer threads are waiting
                Synch.read_lock.acquire(); // read_lock is used so that the Writer can acquire read_lock to stop the Reader threads
                Synch.mutex_readcount.acquire(); // mutex_readcount is for making sure only 1 Reader thread is writing to readcount at a time

                Synch.readcount++;
                if (Synch.readcount == 1) { // The first Reader thread will stop Writer threads from writing (only should be acquiring write_lock once)
                    Synch.write_lock.acquire();
                }

                Synch.mutex_readcount.release();
                Synch.read_lock.release();
                Synch.reader_queue.release();

                // Critical section for Reader
                System.out.println("Reader " + myName + " is now reading.  " +
                    "Readcount is " + Synch.readcount);
                rSleep.doSleep(1, 200); // Simulate the time taken for reading


                Synch.mutex_readcount.acquire();
                Synch.readcount--;
                System.out.println("Reader " + myName + " is finished reading.  " +
                    "Readcount decremented to " + Synch.readcount);
                if (Synch.readcount == 0) {
                    Synch.write_lock.release(); // Unlocks write_lock so Writer threads can start writing
                }
                Synch.mutex_readcount.release();

                rSleep.doSleep(1, 1000); // Simulate "doing something else"

            } catch (Exception e) {}

        }
    }
}
