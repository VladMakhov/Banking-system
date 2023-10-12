package repository.dao;

import model.Account;
import repository.AccountRepository;

import java.sql.*;

public class AccountDao implements AccountRepository {

    @Override
    public void save(Account account) {
        try (Connection connection = DriverManager.getConnection(URL, NAME, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    insert into accounts(id, username, password, balance)
                    values (?, ?, ?, ?);
                    """);
            preparedStatement.setInt(1, account.getId());
            preparedStatement.setString(2, account.getUsername());
            preparedStatement.setString(3, account.getPassword());
            preparedStatement.setLong(4, 0);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    @Override
    public Account findAccountByUsername(String username) {
        try (Connection connection = DriverManager.getConnection(URL, NAME, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    select *
                    from accounts
                    where username = ?;
                    """);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Account(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4));
            }
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        return null;
    }
}
