package dao.classes;

import model.Account;
import model.Transaction;
import dao.FinanceDao;

import java.sql.*;


public class FinanceDaoImpl implements FinanceDao {

    @Override
    public void deposit(Account account, long amount) {
        try (Connection connection = DriverManager.getConnection(URL, NAME, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    update accounts set balance = ? where id = ?;
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
        try (Connection connection = DriverManager.getConnection(URL, NAME, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    update accounts set balance = ? where id = ?;
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
}
