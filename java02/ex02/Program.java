public class Program {
	public static void main(String[] args) {
		FileManager FileManager = new FileManager();

		try {
			FileManager.setPath(args);
			FileManager.run();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}