package model;


public enum TransactionType {
    DEPOSIT(1),
    WITHDRAWAL(2);

    final int typeId;

    TransactionType(int typeId) {
        this.typeId = typeId;
    }

    public int getTypeId() {
        return typeId;
    }
}
