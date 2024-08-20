import java.util.InputMismatchException;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

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
		while (i <= Math.sqrt(num)) {
			if (num % i == 0) {
				System.out.println("false " + (i - 1));
				return ;
			}
			i++;
		}
		System.out.println("true " + (i - 1));
    }
}
