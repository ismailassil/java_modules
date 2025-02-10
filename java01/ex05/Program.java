public class Program {
	public static void main(String[] args) {
		String profile = "--profile=production";
		if (args.length == 2)
			profile = args[1];
		else if (args.length == 1 || args.length > 2) {
			System.out.println("java Program [--profile=dev] or [--profile=production] or without");
			return;
		}
		Menu menu = new Menu(profile);
		try {
			menu.run();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
