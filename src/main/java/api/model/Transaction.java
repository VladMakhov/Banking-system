package api.model;


/*
 * Blueprint of a Transaction that regulates through the Wallet Service
 * */
public class Transaction {
    private final long transactionId;
    private final int accountId;
    private final long amount;
    private final TransactionType type;

    public Transaction(long transactionId, int accountId, long amount, TransactionType type) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.amount = amount;
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
