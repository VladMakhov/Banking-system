package api.model;


/*
 * Blueprint of a Transaction that regulates through the Wallet Service
 * */
public record Transaction(long transactionId, long amount, TransactionType type) {
}
