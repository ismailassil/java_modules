import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

public class WorkerThread implements Runnable {
	private final LinkedBlockingQueue<HashMap<Integer, String>> links;
	private final int threadNumber;

	public WorkerThread(LinkedBlockingQueue<HashMap<Integer, String>> links, int threadNumber) {
		this.links = links;
		this.threadNumber = threadNumber;
	}

	@Override
	public void run() {
		HashMap<Integer, String> link;
		try {
			while ((link = links.poll()) != null) {
				Map.Entry<Integer, String> entry = link.entrySet().iterator().next();
				System.out.println("Thread - " + threadNumber + " start download file number " + entry.getKey());
				downloadData(entry.getValue());
				System.out.println("Thread - " + threadNumber + " finish download file number " + entry.getKey());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void downloadData(String link) throws Exception {
		String filename = link.substring(link.lastIndexOf("/") + 1);
		BufferedInputStream in = new BufferedInputStream(new URL(link).openStream());
		try (FileOutputStream outfile = new FileOutputStream(filename)) {
			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = in.read(buffer, 0, 1024)) != -1) {
				outfile.write(buffer, 0, bytesRead);
			}
		}
	}

}
