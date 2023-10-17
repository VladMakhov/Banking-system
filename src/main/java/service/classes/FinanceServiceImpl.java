package service.classes;

import dao.FinanceDao;
import model.Account;
import model.Transaction;
import model.TransactionType;
import service.FinanceService;


public class FinanceServiceImpl implements FinanceService {

    private final FinanceDao financeDao;
    private final LogService logService;

    public FinanceServiceImpl(FinanceDao financeDao, LogService logService) {
        this.financeDao = financeDao;
        this.logService = logService;
    }

    @Override
    public void deposit(Account account, String unparsedAmount) {
        try {
            int amount = Integer.parseInt(unparsedAmount);
            if (amount > 0) {

                financeDao.deposit(account, amount);
                account.setBalance(account.getBalance() + amount);
                saveTransaction(amount, account.getId(), TransactionType.DEPOSIT);

                System.out.println("INFO: balance: " + account.getBalance());
                logService.addLog(account.getUsername() + " made deposit transaction on " + amount);
            } else {
                throw new RuntimeException("Can not add negative value");
            }
        } catch (RuntimeException e) {
            System.out.println("ERROR: " + e.getMessage());
            logService.addLog(account.getUsername() + " failed deposit transaction");
        }
    }

    @Override
    public void withdraw(Account account, String unparsedAmount) {
        try {
            int amount = Integer.parseInt(unparsedAmount);
            if (amount > 0) {
                if (account.getBalance() - amount >= 0) {

                    financeDao.withdraw(account, amount);
                    account.setBalance(account.getBalance() - amount);
                    saveTransaction(amount, account.getId(), TransactionType.WITHDRAWAL);

                    System.out.println("INFO: balance: " + account.getBalance());
                    logService.addLog(account.getUsername() + " made withdrawal transaction on " + amount);
                } else {
                    throw new RuntimeException("Not enough money on the account");
                }
            } else {
                throw new RuntimeException("Can not subtract negative value");
            }
        } catch (RuntimeException e) {
            System.out.println("ERROR: " + e.getMessage());
            logService.addLog(account.getUsername() + " failed withdrawal transaction");
        }
    }

    private void saveTransaction(int amount, int accountId, TransactionType type) {
        Transaction transaction = new Transaction(0, accountId, amount, type);
        financeDao.save(transaction);
    }

}

