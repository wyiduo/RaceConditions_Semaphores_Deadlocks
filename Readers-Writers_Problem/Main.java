import java.util.concurrent.*;

public class Main {
	public static void main (String argv[]) {
		Synch.read_lock = new Semaphore(1, true);
		Synch.write_lock = new Semaphore(1, true);

		Synch.mutex_readcount = new Semaphore(1, true);
		Synch.mutex_writecount = new Semaphore(1, true);

		Synch.reader_queue = new Semaphore(1, true);

		// creating several instances of Reader and Writer
		Reader R;
		Writer W;

		for (int i=1; i<=8; i++) {
			W = new Writer(i);
				W.start();
			R = new Reader(i);
				R.start();
		}

		System.out.println("The parent process has ended.");
	}
}
