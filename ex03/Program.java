import	java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Program {
    public static void main(String[] args) {
		Scanner	scan = new Scanner(System.in);

		int i = 1;
		List<Integer> list = new ArrayList<>();
		while (scan.hasNext()) {
			String	str = scan.nextLine();
			if (str == null)
				System.exit(-1);
			if (str.equals("42"))
				break ;
			if (i > 18)
				break ;
			if (!str.equals("Week " + i)) {
				System.err.println("IllegalArgument");
				System.exit(-1);
			}
			i++;
			int grades = 5;
			int	grade = 0;
			int	min = 0;
			while (grades > 0) {
				grade = scan.nextInt();
				if (grade <= 0 || grade >= 10) {
					System.err.println("IllegalArgument");
					System.exit(-1);
				}
				if (grades == 5)
					min = grade;
				if (min > grade)
					min = grade;
				grades--;
			}
			list.add(min);
			scan.nextLine();
		}
		int	week = 1;
		for (int a : list) {
			System.out.print("Week " + week++ + " ");
			for ( int j = 0; j < a; j++ ) {
				System.out.print("=");
			}
			System.out.println(">");
		}
		scan.close();
    }
}
