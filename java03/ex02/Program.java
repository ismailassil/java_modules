
public class Program {
	private static int arraySize, threadsCount, chunkSize;

	public static void main(String[] args) {

		checkArguments(args);

		try {
			int shifter = 1;
			for (int i = 0; i < threadsCount; i++) {
				int start = i != 0 ? (i * chunkSize) + shifter++: i * chunkSize;
				int end = (i == (threadsCount - 1)) ? arraySize : start + chunkSize;

				Thread workingThread = new Thread(new ComputeThread(i + 1, start, end));
				workingThread.start();

				workingThread.join();
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	private static void checkArguments(String[] args) {
		if (args.length != 2 || !args[0].startsWith("--arraySize=") || !args[1].startsWith("--threadsCount=")) {
			System.out.println("Invalid Format - java Program --arraySize=<int> --threadsCount=<int>");
			System.exit(1);
		}
		try {
			arraySize = Integer.parseInt(args[0].substring(args[0].indexOf("=") + 1));
			threadsCount = Integer.parseInt(args[1].substring(args[1].indexOf("=") + 1));
			if (arraySize <= 0 || threadsCount <= 0) {
				System.out.println("arraySize and threadsCount can't be negative or zero");
				System.exit(1);
			}
			if (arraySize > 2000000 || threadsCount > arraySize) {
				System.out.println(
						"arraySize can't exceed 2'000'000 and threadsCount should not be greater than arraySize");
				System.exit(1);
			}

			--arraySize;
			chunkSize = arraySize / threadsCount;
		} catch (NumberFormatException e) {
			System.out.println("Error: " + e.getMessage());
			System.exit(1);
		}
	}
}
