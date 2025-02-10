import java.util.Scanner;

public class Menu {
	public static final int DEBIT = 0;
	public static final int CREDIT = 1;
	private final String regex = "\\s+";
	private final TransactionsService transactionsService = new TransactionsService();
	private Scanner scanner;
	private int numberCmd;

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_RED = "\u001B[1;31m";
	public static final String ANSI_YELLOW = "\u001B[33m";

	public void run() {

		if (transactionsService == null) {
			System.out.println(ANSI_RED + "Error: TransactionsService is not initialized." + ANSI_RESET);
			return;
		}

		scanner = new Scanner(System.in);
		String line;
		while (true) {
			try {
				displayHeader();
				line = scanner.nextLine().trim();
				int cmd = Integer.parseInt(line);
				switch (cmd) {
					case 1 -> addUser();
					case 2 -> viewUserBalance();
					case 3 -> performTransfer();
					case 4 -> viewAllTransactions();
					case 5 -> removeTransferById();
					case 6 -> checkTransferValidity();
					case 7 -> {
						scanner.close();
						return;
					}
					default -> System.out.println(ANSI_RED + "! Invalid option, please try again" + ANSI_RESET);
				}
			} catch (NumberFormatException e) {
				System.out.println(ANSI_RED + "! Please enter a valid number" + ANSI_RESET);
			} catch (RuntimeException e) {
				System.out.println(ANSI_RED + "Unexpected error: " + e.getMessage() + ANSI_RESET);
			}
		}
	}

	private void addUser() {
		numberCmd++;
		System.out.println(ANSI_YELLOW + "Enter a user name and a balance" + ANSI_RESET);
		String inputs[] = getLine();
		if (inputs.length != 2) {
			System.out.println(ANSI_RED + "! Please enter 2 inputs" + ANSI_RESET);
			return;
		}
		User newUser;
		try {
			newUser = new User(inputs[0], Double.parseDouble(inputs[1]));
			transactionsService.addUser(newUser);
			System.out.println("User with id = " + newUser.getId() + " is added");
		} catch (NumberFormatException e) {
			System.out.println(ANSI_RED + "Invalid balance format. Please enter a number." + ANSI_RESET);
		} catch (RuntimeException e) {
			System.out.println(ANSI_RED + "Error: " + e.getMessage() + ANSI_RESET);
		}
	}

	private void viewUserBalance() {
		numberCmd++;
		System.out.println(ANSI_YELLOW + "Enter a user ID" + ANSI_RESET);
		String inputs[] = getLine();
		if (inputs.length != 1) {
			System.out.println(ANSI_RED + "! Please enter 1 input" + ANSI_RESET);
			return;
		}
		try {
			int userId = Integer.parseInt(inputs[0]);
			double balance = transactionsService.getUserBalance(Integer.parseInt(inputs[0]));
			System.out.println(transactionsService.getUserName(userId) + " - " + balance);
		} catch (NumberFormatException e) {
			System.out.println(ANSI_RED + "Invalid user ID. Please enter a number." + ANSI_RESET);
		} catch (RuntimeException e) {
			System.out.println(ANSI_RED + "Error: " + e.getMessage() + ANSI_RESET);
		}
	}

	private void performTransfer() {
		numberCmd++;
		System.out.println(ANSI_YELLOW + "Enter a sender ID, a recipient ID, and a transfer amount" + ANSI_RESET);
		String inputs[] = getLine();
		if (inputs.length != 3) {
			System.out.println(ANSI_RED + "! Please enter 3 inputs" + ANSI_RESET);
			return;
		}
		try {
			int senderId = Integer.parseInt(inputs[0]);
			int recipientId = Integer.parseInt(inputs[1]);
			double amount = Double.parseDouble(inputs[2]);

			transactionsService.doTransaction(senderId, recipientId, amount);
			System.out.println("The transfer is completed");
		} catch (NumberFormatException e) {
			System.out.println(ANSI_RED + "Invalid input format. Please enter numbers correctly." + ANSI_RESET);
		} catch (IllegalTransactionException | UserNotFoundException e) {
			System.out.println(ANSI_RED + "Transaction error: " + e.getMessage() + ANSI_RESET);
		}
	}

	private void viewAllTransactions() {
		numberCmd++;
		System.out.println(ANSI_YELLOW + "Enter a user ID" + ANSI_RESET);
		String inputs[] = getLine();
		if (inputs.length != 1) {
			System.out.println(ANSI_RED + "! Please enter 1 input" + ANSI_RESET);
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
			System.out.println(ANSI_RED + "Error: " + e.getMessage() + ANSI_RESET);
		}
	}

	private void removeTransferById() {
		numberCmd++;
		System.out.println(ANSI_YELLOW + "Enter a user ID and a transfer ID" + ANSI_RESET);
		String inputs[] = getLine();
		if (inputs.length != 2) {
			System.out.println(ANSI_RED + "! Please enter 2 inputs" + ANSI_RESET);
			return;
		}
		try {
			int userId = Integer.parseInt(inputs[0]);
			String transactionId = inputs[1];

			String recipientName = "";
			int recipientId = 0;
			double amount = 0.0;

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
		} catch (NumberFormatException e) {
			System.out.println(ANSI_RED + "Invalid input. Please enter correct numbers." + ANSI_RESET);
		} catch (UserNotFoundException | TransactionNotFoundException e) {
			System.out.println(ANSI_RED + "Error: " + e.getMessage() + ANSI_RESET);
		}
	}

	private void checkTransferValidity() {
		numberCmd++;
		System.out.println(ANSI_YELLOW + "Check results:" + ANSI_RESET);
		Transaction[] transactions = transactionsService.checkTransactions();
		for (Transaction transaction : transactions) {
			System.out.println(transaction.getSender().getName() + "(id = " + transaction.getSender().getId() + ") "
					+ "has an unacknowledged transfer id = " + transaction.getId() + " from "
					+ transaction.getRecipient().getName() + "(id = " + transaction.getRecipient().getId() + " for "
					+ transaction.getTransferAmount());
		}
	}

	private String[] getLine() {
		return scanner.nextLine().trim().split(regex);
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
