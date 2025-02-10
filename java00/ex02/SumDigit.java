public class SumDigit {
	static public int sum(int a) {
		int	i = 0;

		while (a < 0) {
			i += a % 10;
			a /= 10;
		}
		return (i);
	}
}
