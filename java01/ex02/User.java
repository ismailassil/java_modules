
public class User {
	private int id;
	private String name;
	private double balance;

	public User(String name, double balance) {
		if (balance < 0) {
			System.out.println("Balance too low");
			return;
		}
		this.id = UserIdsGenerator.getInstance().generateId();
		this.name = name;
		this.balance = balance;
	}

	public int incoming(double amount) {
		if (amount < 0) {
			System.out.println("Amount too low");
			return 1;
		} else {
			balance += amount;
		}
		return 0;
	}

	public int outgoing(double amount) {
		if (amount < 0) {
			if (balance + amount >= 0) {
				balance += amount;
				return 0;
			} else
				System.out.println("Balance would be negative");
		} else {
			System.out.println("Amount should be negative");
		}
		return 1;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public double getBalance() {
		return balance;
	}

}
