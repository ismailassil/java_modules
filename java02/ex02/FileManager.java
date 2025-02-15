
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Scanner;

public class FileManager {

	private String path;

	public void run() throws Exception {
		System.out.println(path);
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.print("-> ");
			while (scanner.hasNextLine()) {
				String[] cmd = getLine(scanner);
				if (cmd.length == 0 || cmd[0] == null) {
					break;
				}
				switch (cmd[0]) {
					case "exit" -> {
						scanner.close();
						System.exit(1);
					}
					case "ls" -> lsCmd(cmd);
					case "cd" -> cdCmd(cmd);
					case "mv" -> mvCmd(cmd);
					default -> System.out.println("Command not found");
				}
				System.out.print("-> ");
			}
		}
	}

	public void setPath(String[] args) {
		if (args.length != 1 || !args[0].startsWith("--current-folder=")) {
			throw new RuntimeException("Invalid Parameter - java Program --current-folder=<path>");
		}
		path = args[0].substring(args[0].indexOf("=") + 1);
		if (path.isEmpty())
			throw new RuntimeException("Invalid Parameter - java Program --current-folder=<path>");
		if (path.equals(".")) {
			path = System.getProperty("user.dir");
		}
		if (path.equals("..")) {
			path = System.getProperty("user.dir");
			path = path.substring(0, path.lastIndexOf("/"));
		}
		File file = new File(path);
		if (!file.exists())
			throw new RuntimeException("Please enter a valid Path");
	}

	private String[] getLine(Scanner scanner) {
		return scanner.nextLine().trim().split("\\s+");
	}

	private void lsCmd(String[] cmd) throws Exception {
		if (cmd.length != 1) {
			System.out.println("! <ls> works only with the current folder");
			return;
		}
		if (!path.endsWith("/"))
			path = path + "/";
		File[] files = new File(path).listFiles();

		for (File file : files) {
			try {
				if (file.getName().startsWith(".")) {
					continue;
				}
				Path filepath = Paths.get(path + file.getName());
				double sizeLength = Files.readAttributes(filepath, BasicFileAttributes.class).size() / 1024.0;
				BigDecimal bd = new BigDecimal(sizeLength);
				bd = bd.setScale(2, RoundingMode.FLOOR);
				System.out.printf("%-50s %-10.2f KB\n", file.getName(), bd.doubleValue());
			} catch (Exception e) {
			}
		}
	}

	private void cdCmd(String[] cmd) throws Exception {
		if (cmd.length != 2 || cmd[1].isEmpty()) {
			System.out.println("! <cd> only accepts 1 argument");
			return;
		}
		String newFolder;
		if (path.endsWith("/"))
			path = path.substring(0, path.length() - 1);
		if (cmd[1].startsWith("../")) {
			newFolder = path.substring(0, path.lastIndexOf("/")) + "/" + cmd[1].substring(cmd[1].indexOf("/") + 1);
		} else if (cmd[1].startsWith("/")) {
			newFolder = cmd[1];
		} else {
			if (cmd[1].equals("..")) {
				int pos = path.lastIndexOf("/");
				if (pos == -1) {
					System.out.println("! <cd> folder not found");
					return;
				}
				newFolder = path.substring(0, path.lastIndexOf("/"));
			} else {
				newFolder = path + "/" + cmd[1];
			}
		}
		File newPath = new File(newFolder);
		if (!newPath.exists()) {
			System.out.println("! <cd> folder not found");
			return;
		}
		path = newFolder;
		System.out.println(path);
	}

	private void mvCmd(String[] cmd) {
		if (cmd.length != 3 || cmd[1].isEmpty() || cmd[2].isEmpty()) {
			System.out.println("! <mv> only accepts 2 argument");
			return;
		}

		File whatFile = new File(path + cmd[1]);
		if (!whatFile.exists()) {
			if (whatFile.isDirectory()) {
				System.out.println("! <mv> " + cmd[1] + " is a directory");
				return;
			}
			System.out.println("! <mv> " + cmd[1] + " no such file");
			return;
		}
		String movedFolder;
		if (path.endsWith("/"))
			path = path.substring(0, path.length() - 1);
		if (cmd[2].startsWith("../")) {
			movedFolder = path.substring(0, path.lastIndexOf("/")) + "/" + cmd[2].substring(cmd[2].indexOf("/") + 1)
					+ "/" + cmd[1];
		} else if (cmd[2].startsWith("/")) {
			movedFolder = cmd[2] + "/" + cmd[1];
		} else {
			if (cmd[2].equals("..")) {
				movedFolder = path.substring(0, path.lastIndexOf("/"));
			} else
				movedFolder = path + "/" + cmd[2];
		}

		File whereFile = new File(movedFolder);
		if (whereFile.isDirectory()) {
			movedFolder = movedFolder + "/" + cmd[1];
		}
		whereFile = new File(movedFolder);
		boolean isRen = whatFile.renameTo(whereFile);
		if (!isRen)
			System.out.println("! <mv> rename/move failed");
	}
}
