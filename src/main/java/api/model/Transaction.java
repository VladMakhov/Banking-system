package api.model;

import java.util.Date;
/*
 * Blueprint of a Transaction that regulates through the Transaction Service
 * */
public class Transaction {
    private long transactionId;
    private int player;
    private long amount;
    private TransactionType type;
    private Date date;

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public void setPlayer(int player) {
        this.player = player;
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

    public int getPlayer() {
        return player;
    }

    public long getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", playerId=" + player +
                ", amount=" + amount +
                ", type=" + type +
                '}';
    }
}
