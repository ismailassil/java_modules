import java.util.NoSuchElementException;

public interface UsersList {
	public void addUser(User user);

	public User getUserById(int id) throws NoSuchElementException;

	public User getUserByIndex(int index);

	public int getSize();
}
