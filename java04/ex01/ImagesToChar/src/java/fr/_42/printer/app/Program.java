package fr._42.printer.app;

import fr._42.printer.logic.Transformer;

public class Program {
	private static char white, black;

	public static void main(String[] args) {
		getArguments(args);
		Transformer transformObj = new Transformer(white, black);
		transformObj.run();
	}

	private static void getArguments(String[] args) {
		if (args.length != 2 || !args[0].startsWith("--white=") || !args[1].startsWith("--black=")) {
			System.err.println("Error: java Program --white=<char> --black=<char>");
			System.exit(1);
		}
		String whiteString = args[0].substring(args[0].indexOf("=") + 1);
		String blackString = args[1].substring(args[1].indexOf("=") + 1);
		if (whiteString.length() != 1 || blackString.length() != 1) {
			System.err.println("Error: java Program --white=<char> --black=<char>");
			System.exit(1);
		}
		white = whiteString.charAt(0);
		black = blackString.charAt(0);
	}

}