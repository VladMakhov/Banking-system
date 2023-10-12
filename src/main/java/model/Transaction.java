package model;


public class Transaction {
    private int transactionId;
    private int accountId;
    private int amount;
    private TransactionType type;

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

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }
}
