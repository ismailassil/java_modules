public class Transaction {
	private final String id;
	private final User recipient;
	private final User sender;
	private final int transferCategory;
	private final double transferAmount;

	public Transaction(String id, User recipient, User sender, int transferCategory, double transferAmount) {
		// if (transferAmount < 0 || sender.getBalance() < transferAmount) {
		// System.out.println("Balance too low");
		// return;
		// }

		this.id = id;
		this.recipient = recipient;
		this.sender = sender;
		this.transferCategory = transferCategory;
		this.transferAmount = transferAmount;

		// String msgS = sender.getName() + " -> " + recipient.getName() + ", " + -transferAmount
		// 		+ ", OUTCOME, transaction ID " + this.id;
		// String msgR = recipient.getName() + " -> " + sender.getName() + ", " + transferAmount
		// 		+ ", INCOME, transaction ID " + this.id;
		// System.out.println(msgS);
		// System.out.println(msgR);
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