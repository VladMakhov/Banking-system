package model;


public record Transaction(long transactionId, String account, long amount, TransactionType type) {
}
