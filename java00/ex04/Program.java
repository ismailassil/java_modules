import java.util.*;

public class Program {
	public static void main(String[] args) {
		try (Scanner scan = new Scanner(System.in)) {
			String str = scan.nextLine();
			if (str == null)
				System.exit(-1);
			scan.close();

			int frequency[] = new int[65535];
			char[] array = str.toCharArray();

			for (char c : array) {
				if (c >= '\u0000' && c <= '\uFFFF')
					frequency[c - '\u0000'] += 1;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
}
