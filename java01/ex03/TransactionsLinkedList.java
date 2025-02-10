import java.util.LinkedList;

public class TransactionsLinkedList implements TransactionsList {
	private final LinkedList<Transaction> transactionList = new LinkedList<>();

	@Override
	public void addTransaction(Transaction transaction) {
		if (transaction == null)
			return;
		transactionList.add(transaction);
	}

	@Override
	public void removeTransactionById(String id) {
		if (!transactionList.removeIf(trans -> trans.getId().equals(id)))
			throw new TransactionNotFoundException("Transaction Not Found");
	}

	@Override
	public Transaction[] toArray() {
		/*
		 * Transaction[]::new will generate the Size
		 * of the Actual LinkedList of transactions when it's invoked
		 */
		return transactionList.toArray(Transaction[]::new);
	}

}