package dao;


import model.Account;
import model.Transaction;
import model.TransactionDTO;
import service.classes.LogService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAO {

    private final LogService logService;

    public DAO() {
        this.logService = new LogService();
    }

    private static final String NAME = "root";
    private static final String PASSWORD = "root";
    private static final String URL = "jdbc:mysql://localhost:3306/db";

    public void saveAccount(Account account) {
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
            logService.addLog("Error occurred during execution: ");
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    public Account findAccountByName(String username) {
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
            logService.addLog("Error occurred during execution: ");
            System.out.println("ERROR: " + e.getMessage());
        }
        return null;
    }

    public void executeDeposit(Account account, long amount) {
        try (Connection connection = DriverManager.getConnection(URL, NAME, PASSWORD)) {

            PreparedStatement preparedStatement = connection.prepareStatement("""
                    update accounts set balance = ? where id = ?;
                    """);
            preparedStatement.setLong(1, account.getBalance() + amount);
            preparedStatement.setInt(2, account.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            logService.addLog("Error occurred during execution: ");
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    public void executeWithdraw(Account account, long amount) {
        try (Connection connection = DriverManager.getConnection(URL, NAME, PASSWORD)) {

            PreparedStatement preparedStatement = connection.prepareStatement("""
                    update accounts set balance = ? where id = ?;
                    """);
            preparedStatement.setLong(1, account.getBalance() - amount);
            preparedStatement.setInt(2, account.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            logService.addLog("Error occurred during execution: ");
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    public void saveTransaction(Transaction transaction) {
        try (Connection connection = DriverManager.getConnection(URL, NAME, PASSWORD)) {

            PreparedStatement preparedStatement = connection.prepareStatement("""
                    insert into transactions (id, account_id, amount, type)
                    values (?, ?, ?, ?);
                    """);
            preparedStatement.setLong(1, transaction.transactionId());
            preparedStatement.setInt(2, transaction.accountId());
            preparedStatement.setInt(3, transaction.amount());
            preparedStatement.setInt(4, transaction.type().getTypeId());
            preparedStatement.execute();
        } catch (SQLException e) {
            logService.addLog("Error occurred during execution: ");
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    public List<TransactionDTO> findAccountHistory(Account account) {
        try (Connection connection = DriverManager.getConnection(URL, NAME, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    SELECT tr.id, tr.amount, tt.type
                    FROM db.transactions as tr
                    inner join transaction_type as tt on tr.type = tt.id
                    where tr.account_id = ?;
                    """);
            preparedStatement.setInt(1, account.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            List<TransactionDTO> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(new TransactionDTO(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3)));
            }
            return list;
        } catch (SQLException e) {
            logService.addLog("Error occurred during execution: ");
            System.out.println("ERROR: " + e.getMessage());
        }
        return null;
    }

}
