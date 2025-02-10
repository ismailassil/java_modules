import java.util.UUID;

public class Program {
	public static void main(String[] args) {
		User user1 = new User(UUID.randomUUID(), "Alice", 1000);
		User user2 = new User(UUID.randomUUID(), "Bob", 500);
		
		System.out.println("=================");
		System.out.println(user1.getName() + " initial balance: " + user1.getBalance());
		System.out.println(user2.getName() + " initial balance: " + user2.getBalance());
		
		Transaction transaction = new Transaction(UUID.randomUUID(), user2, user1, false, 200);
		
		System.out.println("=================");
		System.out.println(user1.getName() + " balance after transaction: " + user1.getBalance());
		System.out.println(user2.getName() + " balance after transaction: " + user2.getBalance());
		
		System.out.println("=================");
		System.out.println("Transaction: " + transaction.getTransferAmount());
	}
}