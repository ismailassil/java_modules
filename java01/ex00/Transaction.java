public class Transaction {
	private final String id;
	private final User recipient;
	private final User sender;
	private final int transferCategory;
	private final double transferAmount;

	public Transaction(String id, User recipient, User sender, int transferCategory, double transferAmount) {
		this.id = id;
		this.recipient = recipient;
		this.sender = sender;
		this.transferCategory = transferCategory;
		this.transferAmount = transferAmount;
	}

	public String getId() {
		return id;
	}

	public User getRecipient() {
		return recipient;
	}

	public User getSender() {
		return sender;
	}

	public int getTransferCategory() {
		return transferCategory;
	}

	public double getTransferAmount() {
		return transferAmount;
	}
}