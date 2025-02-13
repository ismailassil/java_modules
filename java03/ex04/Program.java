import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class Program {
	private static int threadsCount;
	private static final LinkedBlockingQueue<HashMap<Integer, String>> links = new LinkedBlockingQueue<>();

	public static void main(String[] args) {
		try {
			getThreadsCount(args);
			getLinks();

			for (int i = 0; i < links.size(); i++) {
				Thread worker = new Thread(new WorkerThread(links, i + 1));
				worker.start();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
	}

	private static void getLinks() throws Exception {
		File file = new File("files_urls.txt");
		if (!file.exists()) {
			throw new RuntimeException("<files_urls.txt> not found");
		}
		List<String> lines = Files.readAllLines(Paths.get("files_urls.txt"));
		int i = 1;
		for (String line : lines) {
			if (!line.isEmpty()) {
				HashMap<Integer, String> map = new HashMap<>();
				map.put(i++, line);
				links.put(map);
			}
		}
	}

	private static void getThreadsCount(String[] args) {
		if (args.length != 1 || !args[0].startsWith("--threadsCount=")) {
			System.out.println("Invalid Format - java Program --threadsCount=<int>");
			System.exit(1);
		}
		try {
			threadsCount = Integer.parseInt(args[0].substring(args[0].indexOf("=") + 1));
			if (threadsCount <= 0) {
				System.out.println("threadsCount can't be negative or zero");
				System.exit(1);
			}
		} catch (NumberFormatException e) {
			System.out.println("Error: " + e.getMessage());
			System.exit(1);
		}
	}
}
