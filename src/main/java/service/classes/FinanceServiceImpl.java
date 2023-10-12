package service.classes;

import model.Account;
import model.TransactionType;
import repository.FinanceRepository;
import repository.dao.FinanceDao;
import service.FinanceService;
import service.TransactionService;


public class FinanceServiceImpl implements FinanceService {

    private final TransactionService transactionService;
    private final FinanceRepository repository;
    private final LogService logService;

    public FinanceServiceImpl() {
        this.transactionService = new TransactionServiceImpl();
        this.repository = new FinanceDao();
        this.logService = new LogService();
    }

    @Override
    public void withdraw(Account account, String unparsedAmount) {
        try {
            int amount = Integer.parseInt(unparsedAmount);
            if (amount > 0) {
                if (account.getBalance() - amount >= 0) {
                    repository.withdraw(account, amount);
                    account.setBalance(account.getBalance() - amount);
                    transactionService.saveTransaction(amount, account.getId(), TransactionType.WITHDRAWAL);
                    System.out.println("balance: " + account.getBalance());
                    logService.addLog(account.getUsername() + " made withdrawing transaction on " + amount);
                } else {
                    System.out.println("Not enough money on the account");
                }
            } else {
                System.out.println("Can not subtract negative value");
            }
        } catch (RuntimeException e) {
            System.out.println("ERROR: " + e.getMessage());
            logService.addLog(account.getUsername() + " failed withdrawal transaction");
        }
    }

    @Override
    public void deposit(Account account, String unparsedAmount) {
        try {
            int amount = Integer.parseInt(unparsedAmount);
            if (amount > 0) {
                repository.deposit(account, amount);
                account.setBalance(account.getBalance() + amount);
                transactionService.saveTransaction(amount, account.getId(), TransactionType.DEPOSIT);
                System.out.println("Balance: " + account.getBalance());
                logService.addLog(account.getUsername() + " made deposit transaction on " + amount);
            } else {
                System.out.println("Can not add negative value");
            }
        } catch (RuntimeException e) {
            System.out.println("ERROR: " + e.getMessage());
            logService.addLog(account.getUsername() + " failed withdrawal transaction");
        }
    }
}

