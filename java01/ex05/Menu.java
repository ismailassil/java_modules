import java.util.Scanner;

public class Menu {
	private static final int ADD_USER_CMD = 1;
	private static final int VIEW_USER_BALANCE_CMD = 2;
	private static final int PERFORM_TRANSFER_CMD = 3;
	private static final int VIEW_ALL_TRANSACTIONS_CMD = 4;
	private static final int REMOVE_TRANSFER_CMD = 5;
	private static final int CHECK_TRANSFER_VALIDITY_CMD = 6;
	private static final int FINISH = 7;

	public static final int DEBIT = 0;
	public static final int CREDIT = 1;
	private final String regex = "\\s+";
	private boolean isDev = false;
	private final TransactionsService transactionsService = new TransactionsService();
	private Scanner scanner;

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_RED = "\u001B[1;31m";
	public static final String ANSI_YELLOW = "\u001B[33m";

	public Menu(String profile) {
		if ((!profile.equals("--profile=production") && !profile.equals("--profile=dev")))
			throw new RuntimeException("Invalid Parameter");
		if (profile.equals("--profile=dev"))
			this.isDev = true;
	}

	public void run() {

		if (transactionsService == null) {
			System.out.println(ANSI_RED + "Error: TransactionsService is not initialized." + ANSI_RESET);
			return;
		}

		scanner = new Scanner(System.in);
		displayHeader();
		String line;
		while (scanner.hasNextLine()) {
			try {
				line = scanner.nextLine().trim();
				int cmd = Integer.parseInt(line);
				switch (cmd) {
					case ADD_USER_CMD -> addUser();
					case VIEW_USER_BALANCE_CMD -> viewUserBalance();
					case PERFORM_TRANSFER_CMD -> performTransfer();
					case VIEW_ALL_TRANSACTIONS_CMD -> viewAllTransactions();
					case REMOVE_TRANSFER_CMD -> {
						if (!isDev) {
							scanner.close();
							return;
						}
						removeTransferById();
					}
					case CHECK_TRANSFER_VALIDITY_CMD -> {
						if (!isDev) {
							System.out.println(ANSI_RED + "! Invalid option, please try again" + ANSI_RESET);
						} else {
							checkTransferValidity();
						}
					}
					case FINISH -> {
						if (isDev) {
							scanner.close();
							return;
						}
						System.out.println(ANSI_RED + "! Invalid option, please try again" + ANSI_RESET);
					}
					default -> System.out.println(ANSI_RED + "! Invalid option, please try again" + ANSI_RESET);
				}
				System.out.println("---------------------------------------------------------------------------------");
				displayHeader();
			} catch (NumberFormatException e) {
				System.out.println(ANSI_RED + "! Please enter a valid number" + ANSI_RESET);
				System.out.println("---------------------------------------------------------------------------------");
			} catch (RuntimeException e) {
				System.out.println(ANSI_RED + "Unexpected error: " + e.getMessage() + ANSI_RESET);
				System.out.println("---------------------------------------------------------------------------------");
			}
		}
		scanner.close();
	}

	private void addUser() {
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
				String reply = "";
				if (transaction.getTransferCategory() == CREDIT) {
					reply = "To " + transaction.getRecipient().getName() + "(id = " + transaction.getRecipient().getId()
							+ ") "
							+ transaction.getTransferAmount() + " with id = " + transaction.getId();
				} else if (transaction.getTransferCategory() == CREDIT) {
					reply = "From " + transaction.getSender().getName() + "(id = " + transaction.getRecipient().getId()
							+ ") "
							+ transaction.getTransferAmount() + " with id = " + transaction.getId();
				}
				System.out.println(reply);
			}
		} catch (NumberFormatException | UserNotFoundException e) {
			System.out.println(ANSI_RED + "Error: " + e.getMessage() + ANSI_RESET);
		}
	}

	private void removeTransferById() {
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
					amount = amount < 0 ? amount * -1 : amount;
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
		System.out.println(ANSI_YELLOW + "Check results:" + ANSI_RESET);
		Transaction[] transactions = transactionsService.checkTransactions();
		for (Transaction transaction : transactions) {
			double amount = transaction.getTransferAmount();
			amount = amount < 0 ? amount * -1 : amount;
			System.out
					.println(transaction.getRecipient().getName() + "(id = " + transaction.getRecipient().getId() + ") "
							+ "has an unacknowledged transfer id = " + transaction.getId() + " from "
							+ transaction.getSender().getName() + "(id = " + transaction.getSender().getId() + ") for "
							+ amount);
		}
	}

	private String[] getLine() {
		String[] line = null;
		while (scanner.hasNextLine())
			line = scanner.nextLine().trim().split(regex);
		if (line == null) {
			scanner.close();
			System.exit(1);
		}
		return line;
	}

	private void displayHeader() {
		if (isDev) {
			System.out.println("1. Add a user");
			System.out.println("2. View user balances");
			System.out.println("3. Perform a transfer");
			System.out.println("4. View all transactions for a specific user");
			System.out.println("5. DEV - remove a transfer by ID");
			System.out.println("6. DEV - check transfer validity");
			System.out.println("7. Finish execution");
		} else {
			System.out.println("1. Add a user");
			System.out.println("2. View user balances");
			System.out.println("3. Perform a transfer");
			System.out.println("4. View all transactions for a specific user");
			System.out.println("5. Finish execution");
		}
	}
}
