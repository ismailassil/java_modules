/*
 * 
 * `synchronized` methods enable a simple strategy for preventing thread
 * interference and memory consistency errors: if an object is visible to more
 * than one thread, all reads or writes to that object's variables are done
 * through synchronized methods.
 * 
 * `StackOverflow - what does 'synchronized' mean?`
 * 
 */

public class Monitor {
	private int counter = 1;
	private final long count;

	public Monitor(long count) {
		this.count = count;
	}

	public synchronized void printEgg() throws InterruptedException {
		for (long i = 0; i < count; i++) {
			while (counter == 2)
				wait();
			System.out.println("Egg");
			counter = 2;
			notify();
		}
	}

	public synchronized void printHen() throws InterruptedException {
		for (long i = 0; i < count; i++) {
			while (counter == 1)
				wait();
			System.out.println("Hen");
			counter = 1;
			notify();
		}
	}
}
