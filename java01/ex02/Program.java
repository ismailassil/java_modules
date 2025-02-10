public class Program {
	public static void main(String[] args) {
		UsersArrayList users = new UsersArrayList();

		users.addUser(new User("Ismail", 109));
		users.addUser(new User("Achraf", 14));
		users.addUser(new User("Younes", 14));

		User hmida = new User("Hmida", 3941);
		users.addUser(hmida);

		User tmp;
		try {
			tmp = users.getUserById(0);
			System.out.println(tmp.getId());
		} catch (UserNotFoundException e) {
			System.out.println(e.getMessage());
		}
		User tmp1 = users.getUserByIndex(1);
		System.out.println("tmp1 user: " + tmp1.getName());

		System.out.println("===============");
		System.out.println("List Size: " + users.getSize());
		
		System.out.println("===============");
		System.out.println("Hmida user: " + users.getUserById(hmida.getId()).getName());
	}
}