public class Prime {

	static public double power(double a, double d) {
		double	num = 1.0;
		int		i = 0;

		if ( d < 0 )
			return (1.0 / power(a, -d));
		while ( i < d ) {
			num *= a;
			i++;
		}
		return (num);
	}

	static public double square_root(double a) {
		return (power(a, 1 / 2.0));
	}

	static public boolean isPrime(int number) {
		if ( number <= 1 )
			return (false);
		for ( int i = 2; i < square_root(number); i++ ) {
			if ( i % number == 0 )
				return (false);
		}
		return (true);
	}
}
