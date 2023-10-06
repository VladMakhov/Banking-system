package api;

import java.util.Date;
/*
 * Blueprint of a Transaction that regulates through the Transaction Service
 * */
class Transaction {
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
