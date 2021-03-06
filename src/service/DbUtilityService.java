package service;

import model.User;

import java.sql.*;

public class DbUtilityService implements CrudUtility {

    public void save(User user) {
        try {
            Connection connection = DbConnection.getConnection();

//            String createTable = "create table tbl_bankUser(user_id int not null auto_increment primary key ,account_number varchar(255) not null, name varchar(255) not null, address varchar(255) not null, balance double ,PIN int not null)";
//            Statement statement = connection.createStatement();
//            statement.execute(createTable);
//            statement.close();

            String insertOrUpdate;
            String userDetails = "select * from tbl_bankUser where account_number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(userDetails);
            preparedStatement.setString(1, user.getAccountNumber());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                insertOrUpdate = "insert into tbl_bankUser(account_number,name, address, balance, PIN) values(?,?,?,?,?)";
                preparedStatement = getPreparedStatement(user, connection, insertOrUpdate);
            } else {
                insertOrUpdate = "UPDATE tbl_bankUser SET account_number = ?, name = ?, address = ?, balance = ? , PIN = ? where account_number=?";
                preparedStatement = getPreparedStatement(user, connection, insertOrUpdate);
                preparedStatement.setString(6, user.getAccountNumber());
            }
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private PreparedStatement getPreparedStatement(User user, Connection connection, String insertOrUpdate) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(insertOrUpdate);
        preparedStatement.setString(1, user.getAccountNumber());
        preparedStatement.setString(2, user.getName());
        preparedStatement.setString(3, user.getAddress());
        preparedStatement.setDouble(4, user.getBalance());
        preparedStatement.setInt(5, user.getPIN());
        return preparedStatement;
    }

    public User readAccount(String accountNumber) {
        try {
            Connection connection = DbConnection.getConnection();
            String userDetails = "select * from tbl_bankUser where account_number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(userDetails);
            preparedStatement.setString(1, accountNumber);

            ResultSet resultSet = preparedStatement.executeQuery();
            User user = new User();

            while (resultSet.next()) {
                user.setAccountNumber(resultSet.getString("account_number"));
                user.setName(resultSet.getString("name"));
                user.setAddress(resultSet.getString("address"));
                user.setBalance(resultSet.getDouble("balance"));
                user.setPIN(resultSet.getInt("PIN"));
            }
            preparedStatement.close();

            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean searchAccount(String accountNumber) {
        try {
            Connection connection = DbConnection.getConnection();
            String record = "select account_number from tbl_bankUser where account_number=?";
            PreparedStatement preparedStatement = connection.prepareStatement(record);
            preparedStatement.setString(1, accountNumber);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getString("account_number").equals(accountNumber))
                    return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
