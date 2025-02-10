import java.util.UUID;

public class TransactionsService {
	private static final int DEBIT = 0;
	private static final int CREDIT = 1;
	private final UsersList users = new UsersArrayList();
	private TransactionsLinkedList unpairedTransaction;

	public void addUser(User user) {
		users.addUser(user);
	}

	public double getUserBalance(int id) {
		return users.getUserById(id).getBalance();
	}

	public String getUserName(int id) {
		return users.getUserById(id).getName();
	}

	public void doTransaction(int senderId, int recipientId, double amount) {
		User sender = users.getUserById(senderId);
		User recipient = users.getUserById(recipientId);

		String transId = UUID.randomUUID().toString();

		int isOk = sender.outgoing(amount * -1);
		if (isOk == 1 || isOk == 2) {
			unpairedTransaction.addTransaction(new Transaction(transId, sender, recipient, CREDIT, amount));
			unpairedTransaction.addTransaction(new Transaction(transId, sender, recipient, DEBIT, amount));
			if (isOk == 2)
				throw new IllegalTransactionException("Illegal Transaction Exception");
		}
		recipient.incoming(amount);

		Transaction senderTransaction = new Transaction(transId, sender, recipient, CREDIT, amount * -1);
		Transaction recipientTransaction = new Transaction(transId, sender, recipient, DEBIT, amount);

		sender.addTransaction(senderTransaction);
		recipient.addTransaction(recipientTransaction);

	}

	public void removeTransactionById(String transId, int userId) {
		User user = users.getUserById(userId);
		TransactionsList allTransactions = user.getTransactionsHistory();
		allTransactions.removeTransactionById(transId);

		Transaction[] userTransactions = allTransactions.toArray();
		for (Transaction transaction : userTransactions) {
			if (transaction.getId().equals(transId)) {
				unpairedTransaction.addTransaction(transaction);
				break;
			}
		}
	}

	public Transaction[] getUserTransactions(int id) {
		return users.getUserById(id).getTransactionsHistory().toArray();
	}

	public Transaction[] checkTransactions() {
		return unpairedTransaction.toArray();
	}
}
