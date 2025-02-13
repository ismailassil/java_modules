public class ComputeThread implements Runnable {
	private final int threadIndex, start, end;
	private int sum;

	public ComputeThread(int threadIndex, int start, int end) {
		this.threadIndex = threadIndex;
		this.start = start;
		this.end = end;
	}

	@Override
	public void run() {
		for (int i = start; i != end + 1; i++) {
			sum += 1;
		}
		System.out.println("Thread " + threadIndex + ": from " + start + " to " + end + " sum is " + sum);
	}

}
