import java.util.Scanner;

public class Menu {
	public static final int DEBIT = 0;
	public static final int CREDIT = 1;
	private final String regex = "\\s+";
	private TransactionsService transactionsService;
	private Scanner scanner;
	private int numberCmd;

	public Menu() {
	}

	public void run() {
		scanner = new Scanner(System.in);
		int cmd;
		while (true) {
			displayHeader();
			try {
				cmd = scanner.nextInt();
				switch (cmd) {
					case 1 -> addUser();
					case 2 -> viewUserBalance();
					case 3 -> performTransfer();
					case 4 -> viewAllTransactions();
					case 5 -> removeTransferById();
					case 6 -> checkTransferValidity();
					case 7 -> {
						break;
					}
				}
			} catch (Exception e) {
				System.out.println("! Please enter a value");
			}
		}
	}

	private void addUser() {
		numberCmd++;
		System.out.println("Enter a user name and a balance");
		String inputs[] = getLine();
		if (inputs.length != 2) {
			System.out.println("! Please enter 2 inputs");
			return;
		}
		User newUser;
		try {
			newUser = new User(inputs[0], Double.parseDouble(inputs[1]));
		} catch (NumberFormatException e) {
			System.out.println(e.getMessage());
			return;
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
			return;
		}
		transactionsService.addUser(newUser);
	}

	private void viewUserBalance() {
		numberCmd++;
		System.out.println("Enter a user ID");
		String inputs[] = getLine();
		if (inputs.length != 1) {
			System.out.println("! Please enter 1 input");
			return;
		}
		try {
			int userId = Integer.parseInt(inputs[0]);
			double balance = transactionsService.getUserBalance(Integer.parseInt(inputs[0]));
			System.out.println(transactionsService.getUserName(userId) + " - " + balance);
		} catch (NumberFormatException e) {
			System.out.println(e.getMessage());
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		}
	}

	private void performTransfer() {
		numberCmd++;
		System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
		String inputs[] = getLine();
		if (inputs.length != 3) {
			System.out.println("! Please enter 3 inputs");
			return;
		}
		try {
			int senderId = Integer.parseInt(inputs[0]);
			int recipientId = Integer.parseInt(inputs[1]);
			double amount = Double.parseDouble(inputs[2]);

			transactionsService.doTransaction(senderId, recipientId, amount);
			System.out.println("The transfer is completed");
		} catch (NumberFormatException | IllegalTransactionException | UserNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	private void viewAllTransactions() {
		numberCmd++;
		System.out.println("Enter a user ID");
		String inputs[] = getLine();
		if (inputs.length != 1) {
			System.out.println("! Please enter 1 input");
			return;
		}
		try {
			int userId = Integer.parseInt(inputs[0]);
			Transaction[] userHistory = transactionsService.getUserTransactions(userId);
			for (Transaction transaction : userHistory) {
				String type = transaction.getTransferCategory() == DEBIT ? "+" : "-";
				String reply = "To " + transaction.getRecipient() + "(id = " + userId + ") " + type
						+ transaction.getTransferAmount()
						+ " with id = " + transaction.getId();
				System.out.println(reply);
			}
		} catch (NumberFormatException | UserNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	private void removeTransferById() {
		numberCmd++;
		System.out.println("Enter a user ID and a transfer ID");
		String inputs[] = getLine();
		if (inputs.length != 2) {
			System.out.println("! Please enter 2 inputs");
			return;
		}
		try {
			String recipientName = "";
			int recipientId = 0;
			double amount = 0.0;
			int userId = Integer.parseInt(inputs[0]);
			String transactionId = inputs[1];

			Transaction[] transactions = transactionsService.getUserTransactions(userId);
			for (Transaction trans : transactions) {
				if (trans.getId().equals(transactionId)) {
					User rec = trans.getRecipient();
					recipientId = rec.getId();
					recipientName = rec.getName();
					amount = trans.getTransferAmount();
					break;
				}
			}
			transactionsService.removeTransactionById(transactionId, userId);
			System.out.println("Transfer To " + recipientName + "(id = " + recipientId + ") " + amount + " removed");
		} catch (UserNotFoundException | TransactionNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	private void checkTransferValidity() {
		numberCmd++;
		System.out.println("Check results:");
		Transaction[] transactions = transactionsService.checkTransactions();
		for (Transaction transaction : transactions) {
			System.out.println(transaction.getSender().getName() + "(id = " + transaction.getSender().getId() + ") "
					+ "has an unacknowledged transfer id = " + transaction.getId() + " from "
					+ transaction.getRecipient().getName() + "(id = " + transaction.getRecipient().getId() + " for "
					+ transaction.getTransferAmount());
		}
	}

	private String[] getLine() {
		String line = scanner.nextLine();
		String inputs[] = line.split(regex);

		return inputs;
	}

	private void displayHeader() {
		System.out.println("1. Add a user");
		System.out.println("2. View user balances");
		System.out.println("3. Perform a transfer");
		System.out.println("4. View all transactions for a specific user");
		System.out.println("5. DEV - remove a transfer by ID");
		System.out.println("6. DEV - check transfer validity");
		System.out.println("7. Finish execution");
	}
}
