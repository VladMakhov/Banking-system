package model;


public record Transaction(int transactionId, int accountId, int amount, TransactionType type) {

}
