package model;


public class Transaction {
    private long transactionId;
    private int accountId;
    private final int amount;
    private final TransactionType type;

    public Transaction(int accountId, int amount, TransactionType type) {
        this.accountId = accountId;
        this.amount = amount;
        this.type = type;
    }

    public Transaction(long transactionId, int amount, TransactionType type) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.type = type;
    }

    public long getTransactionId() {
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
