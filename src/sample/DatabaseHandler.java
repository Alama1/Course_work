package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseHandler extends DatabaseConfig{
    Connection dbConnection;

    public Connection getDbConnection()
            throws ClassNotFoundException, SQLException{
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;

        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

        return dbConnection;
    }

    public void SignUpUser(User user){
        String insert = "INSERT INTO " + Const.USER_TABLE + "(" + Const.USER_FIRSTNAME + "," +
                Const.USER_LASTNAME + "," + Const.USER_USERNAME + "," + Const.USER_PASSWORD
                + ")" + "VALUES(?,?,?,?)";
        try {
        PreparedStatement prSt = getDbConnection().prepareStatement(insert);
        prSt.setString(1, user.getFirstname());
        prSt.setString(2, user.getLastName());
        prSt.setString(3, user.getUserName());
        prSt.setString(4, user.getPassword());
        prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    public ResultSet getUser(User user){
        ResultSet resSet = null;

        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " + Const.USER_USERNAME + "=? AND " + Const.USER_PASSWORD + "=?";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, user.getUserName());
            prSt.setString(2, user.getPassword());

            resSet = prSt.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return resSet;
    }

    public ResultSet getRace() throws SQLException, ClassNotFoundException {
        ResultSet resSet = null;
        String select = "SELECT * FROM " + "races";
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        resSet = prSt.executeQuery();
        return resSet;
    }

    public ResultSet getRaceAllInf(int id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = null;
        String select = "SELECT * FROM " + "races" + " WHERE race_id =" + id;
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        resultSet = prSt.executeQuery();
        return resultSet;
    }

    public void BookSet(String CurUser, int CurRace_id){
        String insert = "INSERT INTO " + "book_table " + "(" + "book_user" + "," + "book_race_id" + ")" + "VALUES(?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, CurUser);
            prSt.setInt(2, CurRace_id);
            prSt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getRaceId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = null;
        String select = "SELECT * FROM book_table WHERE book_user ='" + CommonData.user.getCurrentUser() + "'";
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        resultSet = prSt.executeQuery();
        return resultSet;
    }

    public ResultSet getRaceBook(int race_id_book) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = null;
        String select = "SELECT * FROM " + "races " + "WHERE race_id ='" +  race_id_book + "'";
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        resultSet = prSt.executeQuery();
        return resultSet;
    }

}
