import	java.util.Scanner;

public class Program {
    public static void main(String[] args) {
		Scanner	scan = new Scanner(System.in);

		int		count = 0;
		while (scan.hasNext()) {
			int	num = 0;

			num = scan.nextInt();
			num = SumDigit.sum(num);
			if (Prime.isPrime(num))
				count++;
		}
		scan.close();
		System.out.println("Count of coffee-request : " + count);
    }
}
