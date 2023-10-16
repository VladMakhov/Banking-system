package dao.classes;

import config.DatabaseConnectionConfig;
import dao.AccountDao;
import model.Account;
import model.Transaction;
import model.TransactionType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountDaoImpl implements AccountDao {

    DatabaseConnectionConfig data = new DatabaseConnectionConfig();
    private final String URL;
    private final String USERNAME;
    private final String PASSWORD;

    public AccountDaoImpl() {
        List<String> databaseConnection = data.loadDatabaseProperties();
        this.URL = databaseConnection.get(0);
        this.USERNAME = databaseConnection.get(1);
        this.PASSWORD = databaseConnection.get(2);
    }

    @Override
    public void save(Account account) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    insert into private.accounts(username, password, balance)
                    values (?, ?, ?);
                    """);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            preparedStatement.setInt(3, 0);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Account> findAccountByUsername(String username) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    select *
                    from private.accounts
                    where username = ?;
                    """);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new Account(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4)));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Transaction> getAccountHistory(Account account) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    SELECT tr.id, tr.amount, tt.type
                    FROM private.transactions as tr
                    inner join private.transaction_type as tt on tr.type = tt.id
                    where tr.account_id = ?
                    order by tr.id desc;
                    """);
            preparedStatement.setInt(1, account.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Transaction> list = new ArrayList<>();
            while (resultSet.next()) {
                TransactionType type = resultSet.getString(3).equals("DEPOSIT") ? TransactionType.DEPOSIT : TransactionType.WITHDRAWAL;
                list.add(new Transaction(resultSet.getInt(1), 0, resultSet.getInt(2), type));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
