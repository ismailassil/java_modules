import java.util.Scanner;

public class Program {
	public static void main(String[] args) {
		try (Scanner scan = new Scanner(System.in)) {
			int count = 0;
			System.out.print("-> ");
			while (scan.hasNext()) {
				int num = 0;

				num = scan.nextInt();
				if (num == 42) {
					break;
				}
				num = sum(num);
				if (isPrime(num)) {
					count++;
				}
				System.out.println(count);
				System.out.print("-> ");
			}
			scan.close();
			System.out.println("Count of coffee-request : " + count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	static public int sum(int a) {
		int i = 0;

		while (a > 0) {
			i += a % 10;
			a /= 10;
		}
		return (i);
	}

	static public double power(double a, double d) {
		double num = 1.0;
		int i = 0;

		if (d < 0)
			return (1.0 / power(a, -d));
		while (i < d) {
			num *= a;
			i++;
		}
		return (num);
	}

	static public boolean isPrime(int number) {
		if (number <= 1)
			return (false);
		int sqrtNumber = (int) Math.sqrt(number);
		for (int i = 2; i <= sqrtNumber; i++) {
			if (number % i == 0)
				return (false);
		}
		return (true);
	}
}
