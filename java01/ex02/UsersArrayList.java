import java.util.ArrayList;

public class UsersArrayList implements UsersList {
	private final ArrayList<User> UsersList = new ArrayList<>(10);

	@Override
	public void addUser(User user) {
		if (user == null)
			return;
		if (UsersList.size() % 10 == 0) {
			UsersList.ensureCapacity(UsersList.size() / 2);
		}
		UsersList.add(user);
	}

	@Override
	public User getUserById(int id) {
		for (User user : UsersList) {
			if (user.getId() == id) {
				return user;
			}
		}
		throw new UserNotFoundException("User Not Found Exception");
	}

	@Override
	public User getUserByIndex(int index) {
		if (index < 0 || index > UsersList.size())
			throw new UserNotFoundException("User Not Found Exception");
		return UsersList.get(index);
	}

	@Override
	public int getSize() {
		return UsersList.size();
	}

}