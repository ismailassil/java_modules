public class Program {
	public static void main(String[] args) {
		final Dictionary dictionary = new Dictionary();

		try {
			dictionary.readTexts(args);
			dictionary.run();
		} catch (Exception e) {
			System.out.print("Error: ");
			System.out.println(e.getMessage());
		}
	}
}
