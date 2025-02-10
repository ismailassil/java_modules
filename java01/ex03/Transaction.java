
import java.util.UUID;

public class Transaction {
	private String id;
	private User recipient;
	private User sender;
	private boolean transferCategory;
	private double transferAmount;

	public Transaction(String id, User recipient, User sender, boolean transferCategory, double transferAmount) {
		if (transferAmount < 0 || sender.getBalance() < transferAmount) {
			System.out.println("Balance too low");
			return;
		}

		this.id = UUID.randomUUID().toString();
		this.recipient = recipient;
		this.sender = sender;
		this.transferCategory = transferCategory;
		this.transferAmount = transferAmount;

		if (sender.outgoing(-transferAmount) == 0) {
			recipient.incoming(transferAmount);
		}
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

	public boolean getTransferCategory() {
		return transferCategory;
	}

	public double getTransferAmount() {
		return transferAmount;
	}
}