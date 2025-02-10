public class Program {
	public static void main(String[] args) {
		Menu menu = new Menu();
		try {
			menu.run();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
