
public class Program {
	public static void main(String[] args) {
		User user1 = new User("ismail", 109);
		User user2 = new User("achraf", 14);
		User user3 = new User("younes", 14);

		System.out.println(user1.getName() + ": " + user1.getId());
		System.out.println(user2.getName() + ": " + user2.getId());
		System.out.println(user3.getName() + ": " + user3.getId());
	}
}