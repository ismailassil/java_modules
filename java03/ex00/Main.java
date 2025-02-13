public class Main implements Runnable {
	private final String threadName;
	private final long times;

	public Main(String name, long times) {
		this.threadName = name;
		this.times = times;
	}

	@Override
	public void run() {
		try {
			for (long i = 0; i < times; i++) {
				System.out.println(threadName);
				Thread.sleep(100);
			}
		} catch (InterruptedException e) {
		}
	}
}
