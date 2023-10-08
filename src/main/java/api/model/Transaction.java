package api.model;


/*
 * Blueprint of a Transaction that regulates through the Wallet Service
 * */
public class Transaction {
    private long transactionId;
    private int accountId;
    private long amount;
    private TransactionType type;

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public long getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", accountId=" + accountId +
                ", amount=" + amount +
                ", type=" + type +
                '}';
    }
}
