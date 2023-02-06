import java.util.concurrent.*;

public class Synch {
    public static int readcount = 0;
    public static int writecount = 0;

    public static Semaphore read_lock;
    public static Semaphore write_lock;

    public static Semaphore mutex_readcount;
    public static Semaphore mutex_writecount;

    public static Semaphore reader_queue;
}
