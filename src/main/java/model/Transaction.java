package model;


public class Transaction {
    private final int transactionId;
    private int accountId;
    private final int amount;
    private final TransactionType type;

    public Transaction(int transactionId, int accountId, int amount, TransactionType type) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.amount = amount;
        this.type = type;
    }

    public Transaction(int transactionId, int amount, TransactionType type) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.type = type;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public int getAccountId() {
        return accountId;
    }

    public int getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

}
