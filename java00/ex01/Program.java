import java.util.InputMismatchException;
import java.util.Scanner;

public class Program {
	public static void main(String[] args) {
		try (Scanner scan = new Scanner(System.in)) {

			int num = 0;
			try {
				if (!scan.hasNext())
					System.exit(-1);
				num = scan.nextInt();
				scan.close();
			} catch (InputMismatchException ex) {
				System.err.println("IllegalArgument");
				System.exit(-1);
			}
			if (num <= 1) {
				System.err.println("IllegalArgument");
				System.exit(-1);
			}
			int i = 2;
			double sqrtNum = Math.sqrt(num);
			while (i <= sqrtNum) {
				if (num % i == 0) {
					System.out.println("false " + (i - 1));
					return;
				}
				i++;
			}
			System.out.println("true " + (i - 1));

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
