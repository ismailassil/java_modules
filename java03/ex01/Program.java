public class Program {
	private static long times = 0;

	public static void main(String[] args) {

		long time = getCount(args);
		if (time == -1) {
			return;
		}

		Monitor monitor = new Monitor(time);
		Thread eggThead = new Thread(new Egg(monitor));
		Thread henThead = new Thread(new Hen(monitor));

		eggThead.start();
		henThead.start();

		try {
			eggThead.join();
			henThead.join();
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}

	private static long getCount(String[] args) {
		if (args.length != 1 || !args[0].startsWith("--count=")) {
			System.out.println("Invalid Format - java Program --count=<int>");
			System.exit(1);
		}
		try {
			times = Integer.parseInt(args[0].substring(args[0].indexOf("=") + 1));
			if (times <= 0) {
				System.out.println("count number can't be negative or zero");
				System.exit(1);
			}
		} catch (NumberFormatException e) {
			System.out.println("Error: " + e.getMessage());
			System.exit(1);
		}
		return times;
	}
}
