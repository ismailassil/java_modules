import	java.util.Scanner;

public class Program {
    public static void main(String[] args) {
		Scanner	scan = new Scanner(System.in);

		int		i = 0;
		int		count = 0;
		while (scan.hasNext()) {
			int		sum = 0;
			boolean	flag = false;
			i = scan.nextInt();

			System.out.println(i);
			while (i > 0) {
				sum += i % 10;
				i /= 10;
			}
			if (sum <= 1)
				continue;
			for ( int j = 2; j < Math.sqrt(sum); j++ ) {
				if (sum % j == 0) {
					flag = true;
					break ;
				}
			}
			if (flag == true)
				continue ;
			count++;
		}
		scan.close();
		System.out.println("Count of coffee-request : " + count);
    }
}
