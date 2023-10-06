package api;

class Transaction {
    private long transactionId;
    private int player;
    private long amount;
    private TransactionType type;

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
