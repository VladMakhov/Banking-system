package dao.classes;

import model.Account;
import model.Transaction;
import dao.FinanceDao;

import java.sql.*;
import java.util.List;

public class FinanceDaoImpl implements FinanceDao {

    @Override
    public void deposit(Account account, long amount) {
        List<String> DatabaseConnection = loadDatabaseProperties();
        try (Connection connection = DriverManager.getConnection(DatabaseConnection.get(0), DatabaseConnection.get(1), DatabaseConnection.get(2))) {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    update private.accounts set balance = ? where id = ?;
                    """);
            preparedStatement.setLong(1, account.getBalance() + amount);
            preparedStatement.setInt(2, account.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void withdraw(Account account, long amount) {
        List<String> DatabaseConnection = loadDatabaseProperties();
        try (Connection connection = DriverManager.getConnection(DatabaseConnection.get(0), DatabaseConnection.get(1), DatabaseConnection.get(2))) {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    update private.accounts set balance = ? where id = ?;
                    """);
            preparedStatement.setLong(1, account.getBalance() - amount);
            preparedStatement.setInt(2, account.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Transaction transaction) {
        List<String> DatabaseConnection = loadDatabaseProperties();
        try (Connection connection = DriverManager.getConnection(DatabaseConnection.get(0), DatabaseConnection.get(1), DatabaseConnection.get(2))) {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    insert into private.transactions (account_id, amount, type)
                    values (?, ?, ?);
                    """);
            preparedStatement.setInt(1, transaction.accountId());
            preparedStatement.setInt(2, transaction.amount());
            preparedStatement.setInt(3, transaction.type().getTypeId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
