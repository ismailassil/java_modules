
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FileType {
	private final Scanner inputScanner = new Scanner(System.in);
	private Map<String, String> map = new HashMap<>();
	private final File outfile = new File("./resutls.txt");

	public void run() {
		try {
			map = readSignatures();
			outfile.createNewFile();
			try (FileOutputStream resutFile = new FileOutputStream(outfile)) {
				while (true) {
					System.out.print("-> ");
					String line = readUserInput();
					try (InputStream pathFile = new FileInputStream(line)) {
						byte[] bytes = new byte[8];
						pathFile.read(bytes);
						String fileType = getFileType(bytes);
						if (fileType != null) {
							System.out.println("PROCESSED");
							String out = fileType.toUpperCase() + "\n";
							resutFile.write(out.getBytes());
						}
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private String getFileType(byte[] bytes) {
		String signature = bytesToHex(bytes);
		for (Map.Entry<String, String> en : map.entrySet()) {
			String key = en.getKey();
			String val = en.getValue();
			if (signature.startsWith(val)) {
				return key;
			}
		}
		return null;
	}

	private String bytesToHex(byte[] bytes) {
		StringBuilder res = new StringBuilder();
		for (byte b : bytes) {
			res.append(String.format("%02X", b));
		}
		return res.toString();
	}

	private Map<String, String> readSignatures() throws Exception {
		File signature = new File("./signatures.txt");
		try (Scanner reader = new Scanner(signature)) {
			while (reader.hasNextLine()) {
				String line = reader.nextLine();
				if (line.isEmpty()) {
					continue;
				}
				int pos = line.indexOf(',');
				if (pos == -1) {
					throw new RuntimeException("Invalid Format");
				}
				String type = line.substring(0, pos).trim();
				String HexString = line.substring(pos + 1).trim().replaceAll("\\s+", "");
				map.put(type, HexString);
			}
		}
		return map;
	}

	private String readUserInput() {
		return inputScanner.nextLine().trim().replace("\\", "");
	}
}
