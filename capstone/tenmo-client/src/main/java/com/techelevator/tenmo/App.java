package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;
import com.techelevator.tenmo.services.TransferService;

import java.math.BigDecimal;


public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";
    private static final User user = new User();
    private static final Account account = new Account();
    private static final Transfer transfer = new Transfer();
    private AuthenticatedUser currentUser;

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private final AccountService accountService = new AccountService(API_BASE_URL, user);
    private final TransferService transferService = new TransferService(API_BASE_URL, user, account);




    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

	private void viewCurrentBalance() {
		// TODO Auto-generated method stub
        System.out.println("Your current account balance is: $" + accountService.getBalanceByAccountId(currentUser.getUser().getId()));

	}

	private void viewTransferHistory() {
		// TODO Auto-generated method stub

	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		
	}

	private void sendBucks() {
		// TODO Auto-generated method stub
        System.out.println("-------------------------");
        System.out.println("Users");
        System.out.println("ID          Name");
        System.out.println("-------------------------");
        accountService.printListOfUsers();
        System.out.println("-----------");

        transfer.setAccountFrom(account.getAccountId());
        transfer.setAccountTo(consoleService.promptForInt("Enter ID of user you are sending to (0 to cancel): "));
        transfer.setAmount(consoleService.promptForBigDecimal("Enter amount: "));
        BigDecimal newReceiverBalance = accountService.getBalanceByAccountId(transfer.getAccountTo()).add(transfer.getAmount());
        BigDecimal newSenderBalance = accountService.getBalanceByAccountId(currentUser.getUser().getId()).subtract(transfer.getAmount());
        System.out.println("Your new balance is: " + newSenderBalance);
        System.out.println("Receivers balance is: " + newReceiverBalance);
        System.out.println(transfer.getAccountTo());

        transferService.add(transfer);

	}

	private void requestBucks() {
		// TODO Auto-generated method stub
		
	}





}
