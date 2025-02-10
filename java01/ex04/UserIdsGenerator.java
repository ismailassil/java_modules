public class UserIdsGenerator {
	static private UserIdsGenerator singleInstance = null;
	static private int lastId;

	private UserIdsGenerator() {
		lastId = 1;
	}

	public static UserIdsGenerator getInstance() {
		if (singleInstance == null) {
			singleInstance = new UserIdsGenerator();
		}
		return singleInstance;
	}

	public int generateId() {
		lastId += 1;
		return lastId;
	}
}
