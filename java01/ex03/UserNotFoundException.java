import java.util.NoSuchElementException;

public class UserNotFoundException extends NoSuchElementException {
	public UserNotFoundException(String errorMessage) {
		super(errorMessage);
	}
}
