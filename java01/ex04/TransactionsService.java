import java.util.UUID;

public class TransactionsService {
	public static final int DEBIT = 0;
	public static final int CREDIT = 1;
	private UsersList users;
	private TransactionsLinkedList unpairedTransaction;

	public void addUser(User user) {
		users.addUser(user);
	}

	public double getUserBalance(User user) {
		return user.getBalance();
	}

	public void doTransaction(int senderId, int recipientId, double amount) {
		User sender = users.getUserById(senderId);
		User recipient = users.getUserById(recipientId);
		
		String transId = UUID.randomUUID().toString();
		
		int isOk = sender.outgoing(amount * -1);
		if (isOk == 1 || isOk == 2) {
			unpairedTransaction.addTransaction(new Transaction(transId, sender, recipient, CREDIT, amount));
			if (isOk == 2)
				throw new IllegalTransactionException("Illegal Transaction Exception");
			return;
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
		Transaction[] userTransactions = allTransactions.toArray();
		for (Transaction transaction : userTransactions) {
			if (transaction.getId().equals(transId)) {
				unpairedTransaction.addTransaction(transaction);
				break ;
			}
		}
		allTransactions.removeTransactionById(transId);
	}

	public Transaction[] checkTransactions() {
		return unpairedTransaction.toArray();
	}
}
