package repository.dao;

import model.Account;
import repository.FinanceRepository;

import java.sql.*;


public class FinanceDao implements FinanceRepository {

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
            System.out.println("ERROR: " + e.getMessage());
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
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}
