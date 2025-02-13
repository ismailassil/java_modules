public class Egg implements Runnable {
	private final Monitor monitor;

	public Egg(Monitor obj) {
		this.monitor = obj;
	}

	@Override
	public void run() {
		try {
			monitor.printEgg();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

}
