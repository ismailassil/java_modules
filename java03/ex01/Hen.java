public class Hen implements Runnable {
	private final Monitor monitor;

	public Hen(Monitor obj) {
		this.monitor = obj;
	}

	@Override
	public void run() {
		try {
			monitor.printHen();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

}
