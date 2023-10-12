package repository.dao;

import model.Account;
import model.Transaction;
import model.TransactionType;
import repository.TransactionRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDao implements TransactionRepository {

    @Override
    public void saveTransaction(Transaction transaction) {
        try (Connection connection = DriverManager.getConnection(URL, NAME, PASSWORD)) {

            PreparedStatement preparedStatement = connection.prepareStatement("""
                    insert into transactions (id, account_id, amount, type)
                    values (?, ?, ?, ?);
                    """);
            preparedStatement.setLong(1, transaction.getTransactionId());
            preparedStatement.setInt(2, transaction.getAccountId());
            preparedStatement.setInt(3, transaction.getAmount());
            preparedStatement.setInt(4, transaction.getType().getTypeId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Transaction> getAccountHistory(Account account) {
        try (Connection connection = DriverManager.getConnection(URL, NAME, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    SELECT tr.id, tr.amount, tt.type
                    FROM db.transactions as tr
                    inner join transaction_type as tt on tr.type = tt.id
                    where tr.account_id = ?;
                    """);
            preparedStatement.setInt(1, account.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Transaction> list = new ArrayList<>();
            while (resultSet.next()) {
                TransactionType type = resultSet.getString(3).equals("DEPOSIT") ? TransactionType.DEPOSIT : TransactionType.WITHDRAWAL;
                list.add(new Transaction(resultSet.getInt(1), resultSet.getInt(2), type));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
