public class App {
    public static void main(String[] args) {
		int i = 479598;
		int b = 0;

		while (i > 0)
		{
			b += i % 10;
			i /= 10;
		}
		System.out.println(b);
    }
}
